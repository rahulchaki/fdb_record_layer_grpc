package main

import (
	fdbgrpc "cio/fdb/grpc/src/main/go"
	"context"
	"google.golang.org/protobuf/proto"
	"google.golang.org/protobuf/reflect/protodesc"
	"google.golang.org/protobuf/reflect/protoreflect"
	"math/rand/v2"
	"time"
)

//type FDBKeySpace struct {
//	dir *fdbgrpc.FDBDirectory
//}
//
//func NewFDBKeySpace(name, value string) *FDBKeySpace {
//	return &FDBKeySpace{
//		dir: &fdbgrpc.FDBDirectory{
//			Name:  name,
//			Value: value,
//		},
//	}
//}
//func (ks *FDBKeySpace) add(name, value string) *FDBKeySpace {
//	ks.dir.Name = name
//	ks.dir.Value = value
//	ks.dir.SubDirectory = &fdbgrpc.FDBDirectory{}
//	return &FDBKeySpace{
//		dir: ks.dir.SubDirectory,
//	}
//}
//func (ks *FDBKeySpace) asFDBDirectory() *fdbgrpc.FDBDirectory { return ks.dir }

type RemoteFDBSession struct {
	Client fdbgrpc.FDBRemoteInteractiveSessionClient
}

func (fdb *RemoteFDBSession) newSession() (*fdbgrpc.FDBRemoteSessionHandle, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	sessionRequest := fdbgrpc.FDBRemoteSessionRequest{
		ClientId: rand.Int64(),
	}
	return fdb.Client.NewSession(ctx, &sessionRequest)
}

func (fdb *RemoteFDBSession) newRecordStore(
	session *fdbgrpc.FDBRemoteSessionHandle,
	descriptor protoreflect.FileDescriptor,
	keySpace *fdbgrpc.FDBDirectory,
) (*fdbgrpc.FDBRemoteRecordStoreHandle, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	recordStoreRequest := fdbgrpc.FDBRemoteRecordStoreRequest{
		Session:  session,
		Metadata: protodesc.ToFileDescriptorProto(descriptor),
		KeySpace: keySpace,
	}
	return fdb.Client.NewRecordStore(ctx, &recordStoreRequest)
}

func (fdb *RemoteFDBSession) saveRecord(
	recordStore *fdbgrpc.FDBRemoteRecordStoreHandle,
	table string,
	record proto.Message,
) (*fdbgrpc.FDBSaveRecordResult, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	bytes, err := proto.Marshal(record)
	if err != nil {
		return nil, err
	}
	saveRecordCommand := &fdbgrpc.FDBSaveRecordCommand{
		Store:  recordStore,
		Table:  table,
		Record: bytes,
	}
	return fdb.Client.SaveRecord(ctx, saveRecordCommand)
}
