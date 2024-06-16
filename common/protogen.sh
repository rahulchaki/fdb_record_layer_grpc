

export RECORD_LAYER=record_layer
export RECORD_LAYER_PROTOS="$RECORD_LAYER/protos"
export RECORD_LAYER_SRC="$RECORD_LAYER/src"

rm -rf $RECORD_LAYER_SRC
mkdir -p -m 777 $RECORD_LAYER_SRC

protoc --proto_path=$RECORD_LAYER_PROTOS --go_out=$RECORD_LAYER_SRC \
  --go_opt=paths=source_relative $(find $RECORD_LAYER_PROTOS -iname "*.proto")



export FDB_GRPC=fdb_grpc
export FDB_GRPC_PROTOS="$FDB_GRPC/protos"
export FDB_GRPC_SRC="$FDB_GRPC/src/main"
export FDB_GRPC_SRC_GO="$FDB_GRPC_SRC/go"
export FDB_GRPC_SRC_JAVA="$FDB_GRPC_SRC/java"

rm -rf $FDB_GRPC_SRC
mkdir -p -m 777 $FDB_GRPC_SRC_GO
mkdir -p -m 777 $FDB_GRPC_SRC_JAVA

protoc --proto_path=$RECORD_LAYER_PROTOS --proto_path=$FDB_GRPC_PROTOS \
  --go_out=$FDB_GRPC_SRC_GO --go-grpc_out=$FDB_GRPC_SRC_GO \
  --go-grpc_opt=paths=source_relative --go_opt=paths=source_relative $(find $FDB_GRPC_PROTOS -iname "*.proto")


cd fdb_grpc
mvn clean compile install
mvn clean
cd ..

#protoc  --proto_path=$RECORD_LAYER_PROTOS --proto_path=$FDB_GRPC_PROTOS \
#  --java_out=$FDB_GRPC_SRC_JAVA $(find $FDB_GRPC_PROTOS -iname "*.proto")