package main

import (
	pb "cio/fdb/grpc/client/protos"
	"fmt"
	"github.com/apple/foundationdb/bindings/go/src/fdb"
	"github.com/apple/foundationdb/bindings/go/src/fdb/tuple"
	"google.golang.org/protobuf/encoding/protojson"
	"google.golang.org/protobuf/proto"
	"log"
)

func newVendor(ids ...int32) []proto.Message {
	protos := make([]proto.Message, len(ids))
	for i, id := range ids {
		vendorId := int64(id)
		vendorName := fmt.Sprintf("%s%d", "N_vendor_", id)
		protos[i] = &pb.Vendor{VendorId: &vendorId, VendorName: &vendorName}
	}
	return protos
}
func newItems(ids ...int32) []proto.Message {
	protos := make([]proto.Message, len(ids))
	for i, id := range ids {
		itemId := int64(id)
		vendorId := int64(id - 10)
		itemName := fmt.Sprintf("%s%d", "N_item_", id)
		protos[i] = &pb.Item{
			ItemId:   &itemId,
			ItemName: &itemName,
			VendorId: &vendorId,
		}
	}
	return protos
}
func main() {
	if err := fdb.APIVersion(710); err != nil {
		log.Fatalf("Error loading fdb: %v", err)
	}

	vendors := newVendor(1, 2, 3, 4, 5)
	log.Println("Vendors Generated")
	for _, vendor := range vendors {
		log.Printf(protojson.Format(vendor))
	}
	vendorBuilder := func() proto.Message { return &pb.Vendor{} }
	items := newItems(11, 12, 13, 14, 15)
	log.Println("Items Generated")
	for _, item := range items {
		log.Printf(protojson.Format(item))
	}
	itemsBuilder := func() proto.Message { return &pb.Item{} }

	keySpace := []string{"environment", "demo", "application", "ecommerce"}
	fdbCRUD, err := NewCRUDClient()
	if err != nil {
		log.Fatalf("Could not create CRUD client: %v", err)
	}
	metadata, err := fdbCRUD.LoadMetadata(keySpace)
	if err != nil {
		log.Fatalf("Could not load metadata: %v", err)
	}
	if metadata == nil {
		log.Println(" Metadata not found. Registering Schema : =========")
		registered, err := fdbCRUD.RegisterSchema(keySpace, pb.File_sample_proto)
		if err != nil {
			log.Fatalf("Could not register schema: %v", err)
		}
		metadata = registered
		log.Println("  Registering Schema done: =========")
	}
	log.Println(" Metadata found : =========")
	log.Println(protojson.Format(metadata))
	log.Println(" Metadata found : =========")

	vendorKeys, err := fdbCRUD.CreateAll("Vendor", vendors)
	if err != nil {
		log.Fatalf("Could not create vendors: %v", err)
	}
	log.Println(" Vendors created with Keys  : =========")
	for _, vendor := range vendorKeys {
		log.Println(" VendorKey : ", vendor.String())
	}
	log.Println(" Vendors created with Keys  : =========")

	itemKeys, err := fdbCRUD.CreateAll("Item", items)
	if err != nil {
		log.Fatalf("Could not create items: %v", err)
	}
	log.Println(" Items created with Keys  : =========")
	for _, item := range itemKeys {
		log.Println(" ItemKey : ", item.String())
	}
	log.Println(" Items created with Keys  : =========")

	vendorsFiltered, err := fdbCRUD.LoadAllKeys(
		"Vendor",
		[]tuple.Tuple{
			[]tuple.TupleElement{int64(1)},
			[]tuple.TupleElement{int64(2)},
			[]tuple.TupleElement{int64(3)},
		},
		vendorBuilder,
	)
	if err != nil {
		log.Fatalf("Could not filter vendors: %v", err)
	}
	log.Println(" Vendors found: =========")
	for _, vendor := range vendorsFiltered {
		log.Println(" Vendor : ", protojson.Format(vendor))
	}
	log.Println(" Vendors found: =========")

	itemsFiltered, err := fdbCRUD.LoadAllQuery(
		"Item",
		Field_Equals_Long("vendor_id", 1),
		itemsBuilder,
	)
	if err != nil {
		log.Fatalf("Could not filter items: %v", err)
	}
	log.Println(" Items found: =========")
	for _, item := range itemsFiltered {
		log.Println(" Item : ", protojson.Format(item))
	}
	log.Println(" Items found: =========")
}
