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

func NewCRUDClient(conn *grpc.ClientConn, database string) *RemoteFDBCrud {
	return &RemoteFDBCrud{
		client:   fdbgrpc.NewFDBCrudClient(conn),
		Database: database,
	}
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
