package main

import (
	fdbgrpc "cio/fdb/grpc/src/main/go"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
)

func NewGrpcConnection() (*grpc.ClientConn, error) {
	return grpc.NewClient(
		"localhost:8080",
		grpc.WithTransportCredentials(insecure.NewCredentials()),
	)
}

func NewCRUDClient() (*RemoteFDBCRUD, error) {
	conn, err := NewGrpcConnection()
	if err != nil {
		return nil, err
	}
	return &RemoteFDBCRUD{
		Client: fdbgrpc.NewFDBRemoteCRUDClient(conn),
	}, nil
}

func NewSessionClient() (*RemoteFDBSession, error) {
	conn, err := NewGrpcConnection()
	if err != nil {
		return nil, err
	}
	return &RemoteFDBSession{
		Client: fdbgrpc.NewFDBRemoteInteractiveSessionClient(conn),
	}, nil
}

func NewFDBKeySpace(name, value string, further ...string) *fdbgrpc.FDBDirectory {
	dir := fdbgrpc.FDBDirectory{
		Name:  name,
		Value: value,
	}
	if len(further) > 0 && len(further)%2 == 0 {
		dirIter := &dir
		for i := 0; i < len(further); i = i + 2 {
			dirIter.SubDirectory = &fdbgrpc.FDBDirectory{
				Name:         further[i],
				Value:        further[i+1],
				SubDirectory: &fdbgrpc.FDBDirectory{},
			}
		}
	}
	return &dir
}

func Field_Equals_Long(field string, value int64) *fdbgrpc.BooleanQuery {
	return &fdbgrpc.BooleanQuery{
		Expressions: []*fdbgrpc.BooleanExpression{
			{
				Operator: &fdbgrpc.Operator{
					Operation: fdbgrpc.Operator_EQ,
				},
				Field: &fdbgrpc.Field{
					Name: field,
				},
				Value: &fdbgrpc.Operand{
					Expression: &fdbgrpc.Operand_IntValue{
						IntValue: value,
					},
				},
			},
		},
	}
}
