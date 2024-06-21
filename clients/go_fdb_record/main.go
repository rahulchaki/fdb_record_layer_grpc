package main

import (
	"fmt"
	"github.com/apple/foundationdb/bindings/go/src/fdb"
	"github.com/apple/foundationdb/bindings/go/src/fdb/tuple"
	"google.golang.org/protobuf/encoding/protojson"
	"google.golang.org/protobuf/proto"
	pb "io/fdb/grpc/client/protos"
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

	conn, err := NewGrpcConnection()
	if err != nil {
		log.Fatalf("Error connecting to fdb: %v", err)
	}
	database := "workspace_1"

	fdbMetadataManager := NewFDbMetadataManager(conn)
	//fdbCRUD := NewCRUDClient(conn, database)

	metadata, err := fdbMetadataManager.CreateOrOpen(database, pb.File_sample_proto)
	if err != nil {
		log.Fatalf("Could not load metadata: %v", err)
	}
	log.Println(" Metadata found : =========")
	log.Println(protojson.Format(metadata))
	log.Println(" Metadata found : =========")

	NewRemoteSession(conn, func(session *FDBSessionContext) error {
		rs := session.RecordStore(database)
		rs.CreateAll("Vendor", vendors, func(vendorKeys []tuple.Tuple, err error) {
			if err != nil {
				log.Fatalf("Could not create vendors: %v", err)
			}
			log.Println(" Vendors created with Keys  : =========")
			for _, vendor := range vendorKeys {
				log.Println(" VendorKey : ", vendor.String())
			}
			log.Println(" Vendors created with Keys  : =========")
		})
		rs.CreateAll("Item", items, func(itemKeys []tuple.Tuple, err error) {
			if err != nil {
				log.Fatalf("Could not create items: %v", err)
			}
			log.Println(" Items created with Keys  : =========")
			for _, item := range itemKeys {
				log.Println(" ItemKey : ", item.String())
			}
			log.Println(" Items created with Keys  : =========")
		})
		keysToLoad := []tuple.Tuple{
			[]tuple.TupleElement{int64(1)},
			[]tuple.TupleElement{int64(2)},
			[]tuple.TupleElement{int64(3)},
		}
		rs.LoadAllKeys(
			"Vendor", keysToLoad, vendorBuilder,
			func(vendorsFiltered []proto.Message, err error) {
				if err != nil {
					log.Fatalf("Could not filter vendors: %v", err)
				}
				log.Println(" Vendors found: =========")
				for _, vendor := range vendorsFiltered {
					log.Println(" Vendor : ", protojson.Format(vendor))
				}
				log.Println(" Vendors found: =========")
			})
		rs.LoadAllQuery(
			"Item",
			Field_Equals_Long("vendor_id", 1), itemsBuilder,
			func(itemsFiltered []proto.Message, err error) {
				if err != nil {
					log.Fatalf("Could not filter items: %v", err)
				}
				log.Println(" Items found: =========")
				for _, item := range itemsFiltered {
					log.Println(" Item : ", protojson.Format(item))
				}
				log.Println(" Items found: =========")
			})

		err := session.Done()
		if err != nil {
			return err
		}
		return session.Wait()
	})

	//vendorKeys, err := fdbCRUD.CreateAll("Vendor", vendors)
	//itemKeys, err := fdbCRUD.CreateAll("Item", items)
	//
	//vendorsFiltered, err := fdbCRUD.LoadAllKeys(
	//	"Vendor",
	//	[]tuple.Tuple{
	//		[]tuple.TupleElement{int64(1)},
	//		[]tuple.TupleElement{int64(2)},
	//		[]tuple.TupleElement{int64(3)},
	//	},
	//	vendorBuilder,
	//)
	//
	//itemsFiltered, err := fdbCRUD.LoadAllQuery(
	//	"Item",
	//	Field_Equals_Long("vendor_id", 1),
	//	itemsBuilder,
	//)

}
