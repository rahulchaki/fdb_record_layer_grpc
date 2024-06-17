module cio/fdb/grpc/client

go 1.22.3

require (
	cio/fdb/grpc v0.0.0-unpublished
	cio/fdb/record v0.0.0-unpublished
	google.golang.org/grpc v1.64.0
	google.golang.org/protobuf v1.34.2
)

require (
	github.com/apple/foundationdb/bindings/go v0.0.0-20240412182139-38384edc16db // indirect
	golang.org/x/net v0.22.0 // indirect
	golang.org/x/sys v0.18.0 // indirect
	golang.org/x/text v0.14.0 // indirect
	google.golang.org/genproto/googleapis/rpc v0.0.0-20240318140521-94a12d6c2237 // indirect
)

replace (
	cio/fdb/grpc v0.0.0-unpublished => ../../common/fdb_grpc
	cio/fdb/record v0.0.0-unpublished => ../../common/record_layer
)
