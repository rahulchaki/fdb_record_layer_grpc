package main

import (
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	fdbgrpc "io/fdb/grpc/src/main/go"
)

func NewGrpcConnection() (*grpc.ClientConn, error) {
	return grpc.NewClient(
		"localhost:8080",
		grpc.WithTransportCredentials(insecure.NewCredentials()),
	)
}

func NewCRUDClient(conn *grpc.ClientConn, database string) *RemoteFDBCrud {
	return &RemoteFDBCrud{
		client:   fdbgrpc.NewFDBCrudClient(conn),
		Database: database,
	}
}
func NewRemoteSession(conn *grpc.ClientConn, handler func(ctx *FDBSessionContext) error) error {
	fdbRemote := &RemoteFdbSession{
		client: fdbgrpc.NewFDBStreamingSessionClient(conn),
	}
	session, err := fdbRemote.Run()
	if err != nil {
		return err
	}
	return handler(session)
}

func NewFDbMetadataManager(conn *grpc.ClientConn) *FdbMetadataManager {
	return &FdbMetadataManager{
		client: fdbgrpc.NewFDBMetadataManagerClient(conn),
	}
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
