package main

import (
	fdbgrpc "cio/fdb/grpc/src/main/go"
	"context"
	"google.golang.org/protobuf/reflect/protodesc"
	"google.golang.org/protobuf/reflect/protoreflect"
	"time"
)

type FdbMetadataManager struct {
	client fdbgrpc.FDBMetadataManagerClient
}

func (this *FdbMetadataManager) CreateOrOpen(
	database string,
	descriptor protoreflect.FileDescriptor,
) (*fdbgrpc.FDBDatabaseMetadata, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	command := fdbgrpc.FDBCreateOrOpenRequest{
		Database: database,
		Schema:   protodesc.ToFileDescriptorProto(descriptor),
	}
	response, err := this.client.CreateOrOpen(ctx, &command)
	if err != nil {
		return nil, err
	}
	return response.GetMetadata(), nil
}
