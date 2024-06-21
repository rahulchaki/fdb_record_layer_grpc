module io/fdb/grpc

go 1.22.3

require (
	io/fdb/record v0.0.0-unpublished
	google.golang.org/grpc v1.64.0
	google.golang.org/protobuf v1.34.1
)

replace (
	io/fdb/record v0.0.0-unpublished => ../record_layer
)