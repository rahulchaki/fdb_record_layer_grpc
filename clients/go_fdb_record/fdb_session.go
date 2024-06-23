package main

import (
	"context"
	"github.com/apple/foundationdb/bindings/go/src/fdb/tuple"
	"google.golang.org/protobuf/proto"
	fdbgrpc "io/fdb/grpc/src/main/go"
	"math/rand/v2"
	"time"
)

type ResponseHandler func(*fdbgrpc.FDBCrudResponse, error)

type FDBSessionContext struct {
	GrpcStreamer[fdbgrpc.FDBStreamingSessionComand, fdbgrpc.FDBStreamingSessionResponse]
}

func (session *FDBSessionContext) Done() error {
	return session.GrpcStreamer.Done()
}

func (session *FDBSessionContext) Wait() error {
	return session.GrpcStreamer.Wait()
}

func (session *FDBSessionContext) execute(
	database string,
	table string,
	operation fdbgrpc.FDBCrudCommand_OPERATION,
	records []proto.Message,
	keys []tuple.Tuple,
	query *fdbgrpc.BooleanQuery,
	onComplete ResponseHandler,
) error {

	execCommand := fdbgrpc.FDBCrudCommand{
		Database:  &database,
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
	command := &fdbgrpc.FDBStreamingSessionComand{
		CommandId: rand.Int64(),
		Database:  database,
		Command:   &execCommand,
	}
	handler := func(res *fdbgrpc.FDBStreamingSessionResponse, err error) error {
		onComplete(res.GetResponse(), err)
		return nil
	}
	return session.Send(command.CommandId, command, handler)
}

func (session *FDBSessionContext) RecordStore(database string) *FDBRecordStore {
	return &FDBRecordStore{
		Session:  session,
		Database: database,
	}
}

type FDBRecordStore struct {
	Session *FDBSessionContext

	Database string
}

func (rs *FDBRecordStore) CreateAll(
	table string,
	records []proto.Message,
	onComplete func([]tuple.Tuple, error)) error {
	responseHandler := func(resp *fdbgrpc.FDBCrudResponse, err error) {
		keys, rErr := unmarshalKeys(resp)
		if rErr != nil {
			onComplete(nil, rErr)
		} else {
			onComplete(keys, err)
		}
	}
	err := rs.Session.execute(rs.Database, table, fdbgrpc.FDBCrudCommand_CREATE, records, nil, nil, responseHandler)
	return err
}
func (rs *FDBRecordStore) LoadAllKeys(
	table string,
	keys []tuple.Tuple,
	makeNew func() proto.Message,
	onComplete func([]proto.Message, error)) error {
	responseHandler := func(resp *fdbgrpc.FDBCrudResponse, err error) {
		records, rErr := unmarshalRecords(resp, makeNew)
		if rErr != nil {
			onComplete(nil, rErr)
		} else {
			onComplete(records, err)
		}
	}
	err := rs.Session.execute(rs.Database, table, fdbgrpc.FDBCrudCommand_LOAD, nil, keys, nil, responseHandler)
	return err
}
func (rs *FDBRecordStore) LoadAllQuery(
	table string,
	query *fdbgrpc.BooleanQuery,
	makeNew func() proto.Message,
	onComplete func([]proto.Message, error)) error {
	responseHandler := func(resp *fdbgrpc.FDBCrudResponse, err error) {
		records, rErr := unmarshalRecords(resp, makeNew)
		if rErr != nil {
			onComplete(nil, rErr)
		} else {
			onComplete(records, err)
		}
	}
	err := rs.Session.execute(rs.Database, table, fdbgrpc.FDBCrudCommand_LOAD, nil, nil, query, responseHandler)
	return err
}
func (rs *FDBRecordStore) DeleteAllKeys(
	table string,
	keys []tuple.Tuple,
	onComplete func(bool, error)) error {
	responseHandler := func(resp *fdbgrpc.FDBCrudResponse, err error) {
		onComplete(resp.GetSuccess(), err)
	}
	err := rs.Session.execute(rs.Database, table, fdbgrpc.FDBCrudCommand_DELETE, nil, keys, nil, responseHandler)
	return err
}
func (rs *FDBRecordStore) DeleteAllQuery(
	table string,
	query *fdbgrpc.BooleanQuery,
	onComplete func(bool, error)) error {
	responseHandler := func(resp *fdbgrpc.FDBCrudResponse, err error) {
		onComplete(resp.GetSuccess(), err)
	}
	err := rs.Session.execute(rs.Database, table, fdbgrpc.FDBCrudCommand_DELETE, nil, nil, query, responseHandler)
	return err
}

type RemoteFdbSession struct {
	client fdbgrpc.FDBStreamingSessionClient
}

func (this *RemoteFdbSession) NewSession() (*FDBSessionContext, error) {
	ctx, _ := context.WithTimeout(context.Background(), 10*time.Second)

	stream, err := this.client.Execute(ctx)
	if err != nil {
		return nil, err
	}
	streamer := RunGrpcSession[fdbgrpc.FDBStreamingSessionComand, fdbgrpc.FDBStreamingSessionResponse](
		stream,
		func(rs *fdbgrpc.FDBStreamingSessionResponse) int64 {
			return rs.GetCommandId()
		})

	return &FDBSessionContext{streamer}, nil
}
