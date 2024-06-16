

fdb_record_grpc

	protos
		.env
		record_layer
		fdb_grpc
		taskfile.yml
		lib
			go_fdb_grpc
			java_fdb_grpc
	server
	clients
		go_fdb_record
	examples
		sidecar
	taskfile.yml



cd record_layer


protoc --proto_path=record_layer/protos --go_out=record_layer/src --go_opt=paths=source_relative $(find record_layer/protos -iname "*.proto")

protoc --go_out=. --go_opt=paths=source_relative --go-grpc_out=. --go-grpc_opt=paths=source_relative ./*.proto