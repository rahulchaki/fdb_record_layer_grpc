package main

import (
	fdbgrpc "cio/fdb/grpc/src/main/go"
	"context"
	"github.com/apple/foundationdb/bindings/go/src/fdb/tuple"
	"google.golang.org/protobuf/proto"
	"io"
	"time"
)

type fdbSessionContextResultChannels struct {
	result chan *fdbgrpc.FDBCrudResponse
	err    chan error
}

type FDBSessionContext struct {
	database string
	stream   fdbgrpc.FDBStreamingSession_ExecuteClient
	requests map[int64]*fdbSessionContextResultChannels

	initializedCh chan error
	doneCh        chan error

	//wait()
	//onReady()
}

func (session *FDBSessionContext) execute(
	table string,
	operation fdbgrpc.FDBCrudCommand_OPERATION,
	records []proto.Message,
	keys []tuple.Tuple,
	query *fdbgrpc.BooleanQuery,
	resultCh chan *fdbgrpc.FDBCrudResponse,
	errCh chan error,
) error {
	execCommand := fdbgrpc.FDBCrudCommand{
		Database:  &session.database,
		Table:     table,
		Operation: operation,
		Data:      nil,
	}
	if records != nil {
		recordsMarshalled, err := marshalRecords(records)
		if err != nil {
			return err
		}
		execCommand.Data = recordsMarshalled
	} else if keys != nil {
		keysMarshalled, err := marshalKeys(keys)
		if err != nil {
			return err
		}
		execCommand.Data = keysMarshalled
	} else {
		execCommand.Data = &fdbgrpc.FDBCrudCommand_Query{Query: query}
	}

	command := fdbgrpc.FDBStreamingSessionComand{
		CommandId: int64(0),
		Command: &fdbgrpc.FDBStreamingSessionComand_Execute{
			Execute: &execCommand,
		},
	}
	session.requests[command.CommandId] = &fdbSessionContextResultChannels{
		result: resultCh,
		err:    errCh,
	}
	return session.stream.Send(&command)
}

type RemoteFdbSession struct {
	client fdbgrpc.FDBStreamingSessionClient
}

func (this *RemoteFdbSession) Execute(database string) (*FDBSessionContext, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	stream, err := this.client.Execute(ctx)
	if err != nil {
		return nil, err
	}
	beginCommand := fdbgrpc.FDBStreamingSessionComand{
		CommandId: int64(0),
		Command: &fdbgrpc.FDBStreamingSessionComand_Begin{
			Begin: &fdbgrpc.FDBNewStreamingSessionComand{
				Database: database,
			},
		},
	}
	err = stream.Send(&beginCommand)
	if err != nil {
		return nil, err
	}
	fdbContext := FDBSessionContext{
		database:      database,
		stream:        stream,
		requests:      make(map[int64]*fdbSessionContextResultChannels),
		initializedCh: make(chan error),
		doneCh:        make(chan error),
	}
	go func() {
		for {
			response, err := stream.Recv()
			if err == io.EOF {

				// if any channel open throw and error on that channel and close

				fdbContext.doneCh <- nil
				close(fdbContext.doneCh)
				return
			}
			if err != nil {
				// Is this correct ??
				//fdbContext.doneCh <- err
				//close(fdbContext.doneCh)
			}
			if response.CommandId == 0 {
				fdbContext.initializedCh <- nil
				close(fdbContext.initializedCh)
			} else {
				fdbContext.requests[response.CommandId].result <- response.GetExecResponse()
				fdbContext.requests[response.CommandId].err <- nil
				close(fdbContext.requests[response.CommandId].result)
				close(fdbContext.requests[response.CommandId].err)
			}
		}
	}()
	return &fdbContext, err
}
