package main

import (
	"context"
	"fmt"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"io"
	fdbgrpc "io/fdb/grpc/src/main/go"
	"log"
	"log/slog"
	"sync"
	"time"
)

type StreamObserver[T any] interface {
	onNext(*T)
	onError(error)
	onFinished()
	close()
}

type GrpcStreamer[Rq any, Rs any] interface {
	Send(int64, *Rq, func(*Rs, error) error) error
	Done() error
	Wait() error
}

type grpcStreamerImpl[Rq any, Rs any] struct {
	stream   grpc.ClientStream
	id       func(*Rs) int64
	handlers sync.Map

	doneCh chan error
	logger slog.Logger

	StreamObserver[Rq]
	GrpcStreamer[Rq, Rs]
}

func (s *grpcStreamerImpl[Rq, Rs]) Send(id int64, msg *Rq, handler func(*Rs, error) error) error {
	s.handlers.Store(id, handler)
	return s.stream.SendMsg(msg)
}

func (s *grpcStreamerImpl[Rq, Rs]) Done() error {
	return s.stream.CloseSend()
}

func (s *grpcStreamerImpl[Rq, Rs]) Wait() error {
	return <-s.doneCh
}

func (s *grpcStreamerImpl[Rq, Rs]) onNext(msg *Rs) {
	id := s.id(msg)
	log.Println("Received  from client msg:", msg)
	handler, ok := s.handlers.Load(id)
	if !ok {
		log.Println("no handler for id:", id)
	}
	err := handler.(func(*Rs, error) error)(msg, nil)
	if err != nil {
		log.Println("handler error:", err)
	}
}
func (s *grpcStreamerImpl[Rq, Rs]) onError(err error) {
	log.Println("Error while receiving from server error:", err)
	s.doneCh <- err
}
func (s *grpcStreamerImpl[Rq, Rs]) onFinished() {
	log.Println("Server finished from server")
	s.doneCh <- nil
}
func (s *grpcStreamerImpl[Rq, Rs]) close() {
	close(s.doneCh)
}

func loopForMessages[Rs any](stream grpc.ClientStream, observer StreamObserver[Rs]) {
	defer observer.close()
	for {
		msg := new(Rs)
		err := stream.RecvMsg(msg)
		if err == io.EOF {
			observer.onFinished()
			return
		} else if err != nil {
			observer.onError(err)
			return
		} else {
			observer.onNext(msg)
		}
	}
}

func RunGrpcSession[Rq any, Rs any](stream grpc.ClientStream, id func(*Rs) int64) GrpcStreamer[Rq, Rs] {
	streamer := &grpcStreamerImpl[Rq, Rs]{
		stream:   stream,
		id:       id,
		handlers: sync.Map{},
		doneCh:   make(chan error),
	}
	go loopForMessages[Rs](stream, streamer)
	return streamer
}

func RunAndWaitGrpcSession[Rq any, Rs any](
	stream grpc.ClientStream,
	id func(*Rs) int64,
	handler func(GrpcStreamer[Rq, Rs]),
) error {
	streamer := RunGrpcSession[Rq, Rs](stream, id)
	handler(streamer)
	return streamer.Wait()
}

func RunAndWaitEchoExample() {
	conn, err := grpc.NewClient(
		"localhost:8080",
		grpc.WithTransportCredentials(insecure.NewCredentials()),
	)
	if err != nil {
		log.Fatalln("Failed to connect to server:", err)
	}
	client := fdbgrpc.NewRawStreamingSessionServiceClient(conn)
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	defer func(conn *grpc.ClientConn) {
		err := conn.Close()
		if err != nil {
			log.Fatalln("Failed to close connection to server:", err)
		}
		cancel()
	}(conn)
	session, err := client.Execute(ctx)
	if err != nil {
		log.Fatalln("Failed to initiate stream to server:", err)
	}
	err = RunAndWaitGrpcSession[fdbgrpc.StreamingSessionRequest, fdbgrpc.StreamingSessionResponse](
		session,
		func(f *fdbgrpc.StreamingSessionResponse) int64 {
			return f.GetId()
		},
		func(g GrpcStreamer[fdbgrpc.StreamingSessionRequest, fdbgrpc.StreamingSessionResponse]) {
			ids := []int{1, 2, 3, 4, 6, 7}
			for _, id := range ids {
				req := &fdbgrpc.StreamingSessionRequest{
					Id:      int64(id),
					Command: []byte(fmt.Sprintf(" This is a test message id: %v", id)),
				}
				err := g.Send(int64(id), req, func(resp *fdbgrpc.StreamingSessionResponse, err error) error {
					if err != nil {
						log.Println(" Error received from  server", err)
					}
					return nil
				})
				if err != nil {
					log.Println("Error sending request to server:", err)
				}
			}
			err := g.Done()
			if err != nil {
				log.Println("Error finishing client stream:", err)
			}
		},
	)
	if err != nil {
		log.Fatalln("Session finished with error:", err)
	}
}
