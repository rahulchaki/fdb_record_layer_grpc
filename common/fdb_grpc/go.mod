module cio/fdb/grpc

go 1.22.3

require (
	cio/fdb/record v0.0.0-unpublished
	google.golang.org/grpc v1.64.0
	google.golang.org/protobuf v1.34.1
)

replace (
	cio/fdb/record v0.0.0-unpublished => ../record_layer
)