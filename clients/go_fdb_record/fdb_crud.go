package main

import (
	fdbgrpc "cio/fdb/grpc/src/main/go"
	"context"
	"github.com/apple/foundationdb/bindings/go/src/fdb/tuple"
	"google.golang.org/protobuf/proto"
	"time"
)

type RemoteFDBCrud struct {
	client   fdbgrpc.FDBCrudClient
	Database string
}

func marshalRecords(records []proto.Message) (*fdbgrpc.FDBCrudCommand_Records, error) {
	packedRecords := make([][]byte, len(records))
	for i, record := range records {
		data, err := proto.Marshal(record)
		if err != nil {
			return nil, err
		}
		packedRecords[i] = data
	}
	result := fdbgrpc.FDBCrudCommand_Records{
		Records: &fdbgrpc.RecordsList{
			Records: packedRecords,
		},
	}
	return &result, nil
}

func marshalKeys(keys []tuple.Tuple) (*fdbgrpc.FDBCrudCommand_Keys, error) {
	packedKeys := make([][]byte, len(keys))
	for i, key := range keys {
		packedKeys[i] = key.Pack()
	}
	result := fdbgrpc.FDBCrudCommand_Keys{
		Keys: &fdbgrpc.KeysList{
			Keys: packedKeys,
		},
	}
	return &result, nil
}

func unmarshalRecords(
	response *fdbgrpc.FDBCrudResponse,
	makeNew func() proto.Message,
) ([]proto.Message, error) {
	records := make([]proto.Message, len(response.GetRecords().Records))
	for i, record := range response.GetRecords().Records {
		holder := makeNew()
		err := proto.Unmarshal(record, holder)
		if err != nil {
			return nil, err
		}
		records[i] = holder
	}
	return records, nil
}

func unmarshalKeys(response *fdbgrpc.FDBCrudResponse) ([]tuple.Tuple, error) {
	keys := make([]tuple.Tuple, len(response.GetKeys().Keys))
	for i, key := range response.GetKeys().Keys {
		tup, err := tuple.Unpack(key)
		if err != nil {
			return nil, err
		}
		keys[i] = tup
	}
	return keys, nil
}

func (fdb *RemoteFDBCrud) execute(
	table string,
	operation fdbgrpc.FDBCrudCommand_OPERATION,
	records []proto.Message,
	keys []tuple.Tuple,
	query *fdbgrpc.BooleanQuery,
) (*fdbgrpc.FDBCrudResponse, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	command := fdbgrpc.FDBCrudCommand{
		Database:  &fdb.Database,
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
		command.Data = &fdbgrpc.FDBCrudCommand_Query{Query: query}
	}
	response, err := fdb.client.Execute(ctx, &command)
	if err != nil {
		return nil, err
	}
	return response, nil
}

func (fdb *RemoteFDBCrud) CreateAll(
	table string,
	records []proto.Message,
) ([]tuple.Tuple, error) {
	response, err := fdb.execute(table, fdbgrpc.FDBCrudCommand_CREATE, records, nil, nil)
	if err != nil {
		return nil, err
	}
	return unmarshalKeys(response)
}

func (fdb *RemoteFDBCrud) LoadAllKeys(
	table string,
	keys []tuple.Tuple,
	makeNew func() proto.Message,
) ([]proto.Message, error) {
	response, err := fdb.execute(table, fdbgrpc.FDBCrudCommand_LOAD, nil, keys, nil)
	if err != nil {
		return nil, err
	}
	return unmarshalRecords(response, makeNew)
}

func (fdb *RemoteFDBCrud) LoadAllQuery(
	table string,
	query *fdbgrpc.BooleanQuery,
	makeNew func() proto.Message,
) ([]proto.Message, error) {
	response, err := fdb.execute(table, fdbgrpc.FDBCrudCommand_LOAD, nil, nil, query)
	if err != nil {
		return nil, err
	}
	return unmarshalRecords(response, makeNew)
}

func (fdb *RemoteFDBCrud) DeleteAllKeys(
	table string,
	keys []tuple.Tuple,
) (bool, error) {
	response, err := fdb.execute(table, fdbgrpc.FDBCrudCommand_DELETE, nil, keys, nil)
	if err != nil {
		return false, err
	}
	return response.GetSuccess(), nil
}

func (fdb *RemoteFDBCrud) DeleteAllQuery(
	table string,
	query *fdbgrpc.BooleanQuery,
) (bool, error) {
	response, err := fdb.execute(table, fdbgrpc.FDBCrudCommand_DELETE, nil, nil, query)
	if err != nil {
		return false, err
	}
	return response.GetSuccess(), nil
}
