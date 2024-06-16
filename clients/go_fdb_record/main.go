package main

import (
	pb "cio/fdb/grpc/client/protos"
	fdbgrpc "cio/fdb/grpc/src/main/go"
	"context"
	"fmt"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"google.golang.org/protobuf/proto"
	"google.golang.org/protobuf/reflect/protodesc"
	"log"
	"math/rand/v2"
	"time"
)

func newSession(fdbRemote fdbgrpc.FDBRemoteClient) (*fdbgrpc.FDBRemoteSessionHandle, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	sessionRequest := fdbgrpc.FDBRemoteSessionRequest{
		ClientId: rand.Int64(),
	}
	return fdbRemote.NewSession(ctx, &sessionRequest)
}

func newRecordStore(fdbRemote fdbgrpc.FDBRemoteClient, session *fdbgrpc.FDBRemoteSessionHandle) (*fdbgrpc.FDBRemoteRecordStoreHandle, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	recordStoreRequest := fdbgrpc.FDBRemoteRecordStoreRequest{
		Session:  session,
		Metadata: protodesc.ToFileDescriptorProto(pb.File_sample_proto),
		KeySpace: &fdbgrpc.FDBDirectory{
			Name:  "environment",
			Value: "demo",
			SubDirectory: &fdbgrpc.FDBDirectory{
				Name:  "environment",
				Value: "demo",
			},
		},
	}
	return fdbRemote.NewRecordStore(ctx, &recordStoreRequest)
}

func saveRecord(fdbRemote fdbgrpc.FDBRemoteClient, recordStore *fdbgrpc.FDBRemoteRecordStoreHandle) (*fdbgrpc.FDBSaveRecordResult, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	vendorId := int64(1023)
	vendorName := "vendor 1"
	vendor := pb.Vendor{
		VendorId:   &vendorId,
		VendorName: &vendorName,
	}
	record, err := proto.Marshal(&vendor)
	if err != nil {
		return nil, err
	}
	saveRecordCommand := &fdbgrpc.FDBSaveRecordCommand{
		Store:  recordStore,
		Table:  "Vendor",
		Record: record,
	}
	return fdbRemote.SaveRecord(ctx, saveRecordCommand)
}

func main() {
	conn, err := grpc.NewClient(
		"localhost:8080",
		grpc.WithTransportCredentials(insecure.NewCredentials()),
	)
	if err != nil {
		log.Fatalf("could not connect to localhost:8080: %v", err)
	}
	defer func(conn *grpc.ClientConn) {
		err := conn.Close()
		if err != nil {
			log.Fatalf("Error in closing connection: %v", err)
		}
	}(conn)

	fdbRemote := fdbgrpc.NewFDBRemoteClient(conn)
	session, _ := newSession(fdbRemote)
	recordStore, _ := newRecordStore(fdbRemote, session)
	record, _ := saveRecord(fdbRemote, recordStore)
	var savedRecord pb.Vendor
	err = proto.Unmarshal(record.Record, &savedRecord)
	if err != nil {
		log.Fatalf("Error parsing proto: %v", err)
	}
	fmt.Println(*savedRecord.VendorName)
}
