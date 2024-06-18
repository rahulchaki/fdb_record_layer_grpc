package main

import (
	fdbgrpc "cio/fdb/grpc/src/main/go"
)

type RemoteFdbSession struct {
	client fdbgrpc.FDBStreamingSessionClient
}

//func (this *RemoteFdbSession) Execute() {
//	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
//	defer cancel()
//	stream, err := this.client.Execute(ctx)
//
//}
