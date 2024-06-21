package main

import (
	"context"
	"errors"
	"github.com/apple/foundationdb/bindings/go/src/fdb/tuple"
	"google.golang.org/protobuf/proto"
	"io"
	fdbgrpc "io/fdb/grpc/src/main/go"
	"math/rand/v2"
	"sync"
	"time"
)

type ResponseHandler func(*fdbgrpc.FDBCrudResponse, error)

type FDBSessionContext struct {
	stream fdbgrpc.FDBStreamingSession_ExecuteClient

	responseHandlers sync.Map
	doneCh           chan error
}

func (session *FDBSessionContext) finishHandlers() {
	session.responseHandlers.Range(
		func(key, value interface{}) bool {
			handler := value.(ResponseHandler)
			handler(nil, errors.New(" No response from server"))
			return true
		},
	)
}

func (session *FDBSessionContext) Done() error {
	return session.stream.CloseSend()
}

func (session *FDBSessionContext) Wait() error {
	return <-session.doneCh
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
	command := fdbgrpc.FDBStreamingSessionComand{
		CommandId: rand.Int64(),
		Command:   &execCommand,
	}
	session.responseHandlers.Store(command.CommandId, onComplete)
	return session.stream.Send(&command)
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

func (this *RemoteFdbSession) Run() (*FDBSessionContext, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	stream, err := this.client.Execute(ctx)
	if err != nil {
		return nil, err
	}
	fdbContext := FDBSessionContext{
		stream:           stream,
		responseHandlers: sync.Map{},
	}
	go func() {
		defer func() {
			fdbContext.finishHandlers()
		}()
		for {
			response, err := stream.Recv()
			if err == io.EOF {
				fdbContext.doneCh <- nil
				return
			} else if err != nil {
				// log
			} else {
				responseHandler, loaded := fdbContext.responseHandlers.LoadAndDelete(response.GetCommandId())
				if loaded {
					responseHandler.(ResponseHandler)(response.GetResponse(), nil)
				}
			}
		}
	}()
	return &fdbContext, err
}
