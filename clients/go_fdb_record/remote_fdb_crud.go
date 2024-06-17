package main

import (
	fdbgrpc "cio/fdb/grpc/src/main/go"
	fdbrecord "cio/fdb/record/src"
	"context"
	"github.com/apple/foundationdb/bindings/go/src/fdb/tuple"
	"google.golang.org/protobuf/proto"
	"google.golang.org/protobuf/reflect/protodesc"
	"google.golang.org/protobuf/reflect/protoreflect"
	"time"
)

type RemoteFDBCRUD struct {
	Client fdbgrpc.FDBRemoteCRUDClient
}

func (fdb *RemoteFDBCRUD) RegisterSchema(
	namespace []string,
	descriptor protoreflect.FileDescriptor,
) (*fdbrecord.MetaData, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	command := fdbgrpc.FDBRegisterSchemaCommand{
		Path:   namespace,
		Schema: protodesc.ToFileDescriptorProto(descriptor),
	}
	response, err := fdb.Client.RegisterSchema(ctx, &command)
	if err != nil {
		return nil, err
	}
	return response.GetMetadata(), nil
}

func (fdb *RemoteFDBCRUD) LoadMetadata(
	namespace []string,
) (*fdbrecord.MetaData, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	command := fdbgrpc.FDBLoadMetadataCommand{
		Path: namespace,
	}
	response, err := fdb.Client.LoadMetadata(ctx, &command)
	if err != nil {
		return nil, err
	}
	return response.GetMetadata(), nil
}

func marshalRecords(records []proto.Message) (*fdbgrpc.FDBCRUDCommand_Records, error) {
	packedRecords := make([][]byte, len(records))
	for _, record := range records {
		data, err := proto.Marshal(record)
		if err != nil {
			return nil, err
		}
		_ = append(packedRecords, data)
	}
	result := fdbgrpc.FDBCRUDCommand_Records{
		Records: &fdbgrpc.RecordsList{
			Records: packedRecords,
		},
	}
	return &result, nil
}

func marshalKeys(keys []tuple.Tuple) (*fdbgrpc.FDBCRUDCommand_Keys, error) {
	packedKeys := make([][]byte, len(keys))
	for _, key := range keys {
		_ = append(packedKeys, key.Pack())
	}
	result := fdbgrpc.FDBCRUDCommand_Keys{
		Keys: &fdbgrpc.KeysList{
			Keys: packedKeys,
		},
	}
	return &result, nil
}

func unmarshalRecords(
	response *fdbgrpc.FDBCRUDResponse,
	makeNew func() proto.Message,
) ([]proto.Message, error) {
	records := make([]proto.Message, len(response.GetRecords().Records))
	for _, record := range response.GetRecords().Records {
		holder := makeNew()
		err := proto.Unmarshal(record, holder)
		if err != nil {
			return nil, err
		}
		_ = append(records, holder)
	}
	return records, nil
}

func unmarshalKeys(response *fdbgrpc.FDBCRUDResponse) ([]tuple.Tuple, error) {
	keys := make([]tuple.Tuple, len(response.GetKeys().Keys))
	for _, key := range response.GetKeys().Keys {
		tup, err := tuple.Unpack(key)
		if err != nil {
			return nil, err
		}
		_ = append(keys, tup)
	}
	return keys, nil
}

func (fdb *RemoteFDBCRUD) execute(
	table string,
	operation fdbgrpc.FDBCRUDCommand_OPERATION,
	records []proto.Message,
	keys []tuple.Tuple,
	query *fdbgrpc.BooleanQuery,
) (*fdbgrpc.FDBCRUDResponse, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	command := fdbgrpc.FDBCRUDCommand{
		Table:     table,
		Operation: operation,
		Data:      nil,
	}
	if records != nil {
		recordsMarshalled, err := marshalRecords(records)
		if err != nil {
			return nil, err
		}
		command.Data = recordsMarshalled
	} else if keys != nil {
		keysMarshalled, err := marshalKeys(keys)
		if err != nil {
			return nil, err
		}
		command.Data = keysMarshalled
	} else {
		command.Data = &fdbgrpc.FDBCRUDCommand_Query{Query: query}
	}
	response, err := fdb.Client.Execute(ctx, &command)
	if err != nil {
		return nil, err
	}
	return response, nil
}

func (fdb *RemoteFDBCRUD) CreateAll(
	table string,
	records []proto.Message,
) ([]tuple.Tuple, error) {
	response, err := fdb.execute(table, fdbgrpc.FDBCRUDCommand_CREATE, records, nil, nil)
	if err != nil {
		return nil, err
	}
	return unmarshalKeys(response)
}

func (fdb *RemoteFDBCRUD) LoadAllKeys(
	table string,
	keys []tuple.Tuple,
	makeNew func() proto.Message,
) ([]proto.Message, error) {
	response, err := fdb.execute(table, fdbgrpc.FDBCRUDCommand_LOAD, nil, keys, nil)
	if err != nil {
		return nil, err
	}
	return unmarshalRecords(response, makeNew)
}

func (fdb *RemoteFDBCRUD) LoadAllQuery(
	table string,
	query *fdbgrpc.BooleanQuery,
	makeNew func() proto.Message,
) ([]proto.Message, error) {
	response, err := fdb.execute(table, fdbgrpc.FDBCRUDCommand_LOAD, nil, nil, query)
	if err != nil {
		return nil, err
	}
	return unmarshalRecords(response, makeNew)
}

func (fdb *RemoteFDBCRUD) DeleteAllKeys(
	table string,
	keys []tuple.Tuple,
) (bool, error) {
	response, err := fdb.execute(table, fdbgrpc.FDBCRUDCommand_DELETE, nil, keys, nil)
	if err != nil {
		return false, err
	}
	return response.GetSuccess(), nil
}

func (fdb *RemoteFDBCRUD) DeleteAllQuery(
	table string,
	query *fdbgrpc.BooleanQuery,
) (bool, error) {
	response, err := fdb.execute(table, fdbgrpc.FDBCRUDCommand_DELETE, nil, nil, query)
	if err != nil {
		return false, err
	}
	return response.GetSuccess(), nil
}
