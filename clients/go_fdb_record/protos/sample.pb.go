//
// sample.proto
//
// This source file is part of the FoundationDB open source project
//
// Copyright 2015-2018 Apple Inc. and the FoundationDB project authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// Code generated by protoc-gen-go. DO NOT EDIT.
// versions:
// 	protoc-gen-go v1.34.2
// 	protoc        v4.24.4
// source: sample.proto

package gen

import (
	_ "cio/fdb/record/src"
	protoreflect "google.golang.org/protobuf/reflect/protoreflect"
	protoimpl "google.golang.org/protobuf/runtime/protoimpl"
	reflect "reflect"
	sync "sync"
)

const (
	// Verify that this generated code is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(20 - protoimpl.MinVersion)
	// Verify that runtime/protoimpl is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(protoimpl.MaxVersion - 20)
)

type Vendor struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	// For each top-level message type, a primary key must be declared either in
	// the message type definition or in code via a RecordMetaDataBuilder.
	// Note: the primary key extent is shared between all record types,
	// so each record must have primary key that is unique across all records
	// of each type in each store. (In other words, two records of different
	// types cannot share the same primary key.) For more information, see the
	// FAQ: https://github.com/FoundationDB/fdb-record-layer/blob/main/docs/FAQ.md#are-record-types-tables
	VendorId   *int64  `protobuf:"varint,1,opt,name=vendor_id,json=vendorId" json:"vendor_id,omitempty"`
	VendorName *string `protobuf:"bytes,2,opt,name=vendor_name,json=vendorName" json:"vendor_name,omitempty"`
}

func (x *Vendor) Reset() {
	*x = Vendor{}
	if protoimpl.UnsafeEnabled {
		mi := &file_sample_proto_msgTypes[0]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *Vendor) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*Vendor) ProtoMessage() {}

func (x *Vendor) ProtoReflect() protoreflect.Message {
	mi := &file_sample_proto_msgTypes[0]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use Vendor.ProtoReflect.Descriptor instead.
func (*Vendor) Descriptor() ([]byte, []int) {
	return file_sample_proto_rawDescGZIP(), []int{0}
}

func (x *Vendor) GetVendorId() int64 {
	if x != nil && x.VendorId != nil {
		return *x.VendorId
	}
	return 0
}

func (x *Vendor) GetVendorName() string {
	if x != nil && x.VendorName != nil {
		return *x.VendorName
	}
	return ""
}

type Item struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	ItemId   *int64  `protobuf:"varint,1,opt,name=item_id,json=itemId" json:"item_id,omitempty"`
	ItemName *string `protobuf:"bytes,2,opt,name=item_name,json=itemName" json:"item_name,omitempty"`
	VendorId *int64  `protobuf:"varint,3,opt,name=vendor_id,json=vendorId" json:"vendor_id,omitempty"`
	Category *string `protobuf:"bytes,4,opt,name=category" json:"category,omitempty"`
}

func (x *Item) Reset() {
	*x = Item{}
	if protoimpl.UnsafeEnabled {
		mi := &file_sample_proto_msgTypes[1]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *Item) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*Item) ProtoMessage() {}

func (x *Item) ProtoReflect() protoreflect.Message {
	mi := &file_sample_proto_msgTypes[1]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use Item.ProtoReflect.Descriptor instead.
func (*Item) Descriptor() ([]byte, []int) {
	return file_sample_proto_rawDescGZIP(), []int{1}
}

func (x *Item) GetItemId() int64 {
	if x != nil && x.ItemId != nil {
		return *x.ItemId
	}
	return 0
}

func (x *Item) GetItemName() string {
	if x != nil && x.ItemName != nil {
		return *x.ItemName
	}
	return ""
}

func (x *Item) GetVendorId() int64 {
	if x != nil && x.VendorId != nil {
		return *x.VendorId
	}
	return 0
}

func (x *Item) GetCategory() string {
	if x != nil && x.Category != nil {
		return *x.Category
	}
	return ""
}

type Customer struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	CustomerId    *int64   `protobuf:"varint,1,opt,name=customer_id,json=customerId" json:"customer_id,omitempty"`
	FirstName     *string  `protobuf:"bytes,2,opt,name=first_name,json=firstName" json:"first_name,omitempty"`
	LastName      *string  `protobuf:"bytes,3,opt,name=last_name,json=lastName" json:"last_name,omitempty"`
	EmailAddress  []string `protobuf:"bytes,4,rep,name=email_address,json=emailAddress" json:"email_address,omitempty"`
	PreferenceTag []string `protobuf:"bytes,5,rep,name=preference_tag,json=preferenceTag" json:"preference_tag,omitempty"`
	PhoneNumber   *string  `protobuf:"bytes,6,opt,name=phone_number,json=phoneNumber" json:"phone_number,omitempty"`
	Order         []*Order `protobuf:"bytes,7,rep,name=order" json:"order,omitempty"`
}

func (x *Customer) Reset() {
	*x = Customer{}
	if protoimpl.UnsafeEnabled {
		mi := &file_sample_proto_msgTypes[2]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *Customer) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*Customer) ProtoMessage() {}

func (x *Customer) ProtoReflect() protoreflect.Message {
	mi := &file_sample_proto_msgTypes[2]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use Customer.ProtoReflect.Descriptor instead.
func (*Customer) Descriptor() ([]byte, []int) {
	return file_sample_proto_rawDescGZIP(), []int{2}
}

func (x *Customer) GetCustomerId() int64 {
	if x != nil && x.CustomerId != nil {
		return *x.CustomerId
	}
	return 0
}

func (x *Customer) GetFirstName() string {
	if x != nil && x.FirstName != nil {
		return *x.FirstName
	}
	return ""
}

func (x *Customer) GetLastName() string {
	if x != nil && x.LastName != nil {
		return *x.LastName
	}
	return ""
}

func (x *Customer) GetEmailAddress() []string {
	if x != nil {
		return x.EmailAddress
	}
	return nil
}

func (x *Customer) GetPreferenceTag() []string {
	if x != nil {
		return x.PreferenceTag
	}
	return nil
}

func (x *Customer) GetPhoneNumber() string {
	if x != nil && x.PhoneNumber != nil {
		return *x.PhoneNumber
	}
	return ""
}

func (x *Customer) GetOrder() []*Order {
	if x != nil {
		return x.Order
	}
	return nil
}

type Order struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	// This is not a top-level message, so it does not have a primary key
	// defined.
	OrderId  *int64 `protobuf:"varint,1,opt,name=order_id,json=orderId" json:"order_id,omitempty"`
	ItemId   *int64 `protobuf:"varint,2,opt,name=item_id,json=itemId" json:"item_id,omitempty"`
	Quantity *int32 `protobuf:"varint,3,opt,name=quantity" json:"quantity,omitempty"`
}

func (x *Order) Reset() {
	*x = Order{}
	if protoimpl.UnsafeEnabled {
		mi := &file_sample_proto_msgTypes[3]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *Order) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*Order) ProtoMessage() {}

func (x *Order) ProtoReflect() protoreflect.Message {
	mi := &file_sample_proto_msgTypes[3]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use Order.ProtoReflect.Descriptor instead.
func (*Order) Descriptor() ([]byte, []int) {
	return file_sample_proto_rawDescGZIP(), []int{3}
}

func (x *Order) GetOrderId() int64 {
	if x != nil && x.OrderId != nil {
		return *x.OrderId
	}
	return 0
}

func (x *Order) GetItemId() int64 {
	if x != nil && x.ItemId != nil {
		return *x.ItemId
	}
	return 0
}

func (x *Order) GetQuantity() int32 {
	if x != nil && x.Quantity != nil {
		return *x.Quantity
	}
	return 0
}

// Required boilerplate listing all top-level message types in the Record Store.
type RecordTypeUnion struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	XItem     *Item     `protobuf:"bytes,1,opt,name=_Item,json=Item" json:"_Item,omitempty"`
	XVendor   *Vendor   `protobuf:"bytes,2,opt,name=_Vendor,json=Vendor" json:"_Vendor,omitempty"`
	XCustomer *Customer `protobuf:"bytes,3,opt,name=_Customer,json=Customer" json:"_Customer,omitempty"`
}

func (x *RecordTypeUnion) Reset() {
	*x = RecordTypeUnion{}
	if protoimpl.UnsafeEnabled {
		mi := &file_sample_proto_msgTypes[4]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *RecordTypeUnion) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*RecordTypeUnion) ProtoMessage() {}

func (x *RecordTypeUnion) ProtoReflect() protoreflect.Message {
	mi := &file_sample_proto_msgTypes[4]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use RecordTypeUnion.ProtoReflect.Descriptor instead.
func (*RecordTypeUnion) Descriptor() ([]byte, []int) {
	return file_sample_proto_rawDescGZIP(), []int{4}
}

func (x *RecordTypeUnion) GetXItem() *Item {
	if x != nil {
		return x.XItem
	}
	return nil
}

func (x *RecordTypeUnion) GetXVendor() *Vendor {
	if x != nil {
		return x.XVendor
	}
	return nil
}

func (x *RecordTypeUnion) GetXCustomer() *Customer {
	if x != nil {
		return x.XCustomer
	}
	return nil
}

var File_sample_proto protoreflect.FileDescriptor

var file_sample_proto_rawDesc = []byte{
	0x0a, 0x0c, 0x73, 0x61, 0x6d, 0x70, 0x6c, 0x65, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x12, 0x17,
	0x63, 0x6f, 0x6d, 0x2e, 0x63, 0x69, 0x6f, 0x2e, 0x66, 0x64, 0x62, 0x2e, 0x72, 0x65, 0x63, 0x6f,
	0x72, 0x64, 0x2e, 0x67, 0x72, 0x70, 0x63, 0x1a, 0x1d, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x5f,
	0x6d, 0x65, 0x74, 0x61, 0x64, 0x61, 0x74, 0x61, 0x5f, 0x6f, 0x70, 0x74, 0x69, 0x6f, 0x6e, 0x73,
	0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x22, 0x4d, 0x0a, 0x06, 0x56, 0x65, 0x6e, 0x64, 0x6f, 0x72,
	0x12, 0x22, 0x0a, 0x09, 0x76, 0x65, 0x6e, 0x64, 0x6f, 0x72, 0x5f, 0x69, 0x64, 0x18, 0x01, 0x20,
	0x01, 0x28, 0x03, 0x42, 0x05, 0x8a, 0x4d, 0x02, 0x10, 0x01, 0x52, 0x08, 0x76, 0x65, 0x6e, 0x64,
	0x6f, 0x72, 0x49, 0x64, 0x12, 0x1f, 0x0a, 0x0b, 0x76, 0x65, 0x6e, 0x64, 0x6f, 0x72, 0x5f, 0x6e,
	0x61, 0x6d, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52, 0x0a, 0x76, 0x65, 0x6e, 0x64, 0x6f,
	0x72, 0x4e, 0x61, 0x6d, 0x65, 0x22, 0x83, 0x01, 0x0a, 0x04, 0x49, 0x74, 0x65, 0x6d, 0x12, 0x1e,
	0x0a, 0x07, 0x69, 0x74, 0x65, 0x6d, 0x5f, 0x69, 0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x03, 0x42,
	0x05, 0x8a, 0x4d, 0x02, 0x10, 0x01, 0x52, 0x06, 0x69, 0x74, 0x65, 0x6d, 0x49, 0x64, 0x12, 0x1b,
	0x0a, 0x09, 0x69, 0x74, 0x65, 0x6d, 0x5f, 0x6e, 0x61, 0x6d, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28,
	0x09, 0x52, 0x08, 0x69, 0x74, 0x65, 0x6d, 0x4e, 0x61, 0x6d, 0x65, 0x12, 0x22, 0x0a, 0x09, 0x76,
	0x65, 0x6e, 0x64, 0x6f, 0x72, 0x5f, 0x69, 0x64, 0x18, 0x03, 0x20, 0x01, 0x28, 0x03, 0x42, 0x05,
	0x8a, 0x4d, 0x02, 0x1a, 0x00, 0x52, 0x08, 0x76, 0x65, 0x6e, 0x64, 0x6f, 0x72, 0x49, 0x64, 0x12,
	0x1a, 0x0a, 0x08, 0x63, 0x61, 0x74, 0x65, 0x67, 0x6f, 0x72, 0x79, 0x18, 0x04, 0x20, 0x01, 0x28,
	0x09, 0x52, 0x08, 0x63, 0x61, 0x74, 0x65, 0x67, 0x6f, 0x72, 0x79, 0x22, 0x9a, 0x02, 0x0a, 0x08,
	0x43, 0x75, 0x73, 0x74, 0x6f, 0x6d, 0x65, 0x72, 0x12, 0x26, 0x0a, 0x0b, 0x63, 0x75, 0x73, 0x74,
	0x6f, 0x6d, 0x65, 0x72, 0x5f, 0x69, 0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x03, 0x42, 0x05, 0x8a,
	0x4d, 0x02, 0x10, 0x01, 0x52, 0x0a, 0x63, 0x75, 0x73, 0x74, 0x6f, 0x6d, 0x65, 0x72, 0x49, 0x64,
	0x12, 0x1d, 0x0a, 0x0a, 0x66, 0x69, 0x72, 0x73, 0x74, 0x5f, 0x6e, 0x61, 0x6d, 0x65, 0x18, 0x02,
	0x20, 0x01, 0x28, 0x09, 0x52, 0x09, 0x66, 0x69, 0x72, 0x73, 0x74, 0x4e, 0x61, 0x6d, 0x65, 0x12,
	0x1b, 0x0a, 0x09, 0x6c, 0x61, 0x73, 0x74, 0x5f, 0x6e, 0x61, 0x6d, 0x65, 0x18, 0x03, 0x20, 0x01,
	0x28, 0x09, 0x52, 0x08, 0x6c, 0x61, 0x73, 0x74, 0x4e, 0x61, 0x6d, 0x65, 0x12, 0x23, 0x0a, 0x0d,
	0x65, 0x6d, 0x61, 0x69, 0x6c, 0x5f, 0x61, 0x64, 0x64, 0x72, 0x65, 0x73, 0x73, 0x18, 0x04, 0x20,
	0x03, 0x28, 0x09, 0x52, 0x0c, 0x65, 0x6d, 0x61, 0x69, 0x6c, 0x41, 0x64, 0x64, 0x72, 0x65, 0x73,
	0x73, 0x12, 0x25, 0x0a, 0x0e, 0x70, 0x72, 0x65, 0x66, 0x65, 0x72, 0x65, 0x6e, 0x63, 0x65, 0x5f,
	0x74, 0x61, 0x67, 0x18, 0x05, 0x20, 0x03, 0x28, 0x09, 0x52, 0x0d, 0x70, 0x72, 0x65, 0x66, 0x65,
	0x72, 0x65, 0x6e, 0x63, 0x65, 0x54, 0x61, 0x67, 0x12, 0x28, 0x0a, 0x0c, 0x70, 0x68, 0x6f, 0x6e,
	0x65, 0x5f, 0x6e, 0x75, 0x6d, 0x62, 0x65, 0x72, 0x18, 0x06, 0x20, 0x01, 0x28, 0x09, 0x42, 0x05,
	0x8a, 0x4d, 0x02, 0x1a, 0x00, 0x52, 0x0b, 0x70, 0x68, 0x6f, 0x6e, 0x65, 0x4e, 0x75, 0x6d, 0x62,
	0x65, 0x72, 0x12, 0x34, 0x0a, 0x05, 0x6f, 0x72, 0x64, 0x65, 0x72, 0x18, 0x07, 0x20, 0x03, 0x28,
	0x0b, 0x32, 0x1e, 0x2e, 0x63, 0x6f, 0x6d, 0x2e, 0x63, 0x69, 0x6f, 0x2e, 0x66, 0x64, 0x62, 0x2e,
	0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x2e, 0x67, 0x72, 0x70, 0x63, 0x2e, 0x4f, 0x72, 0x64, 0x65,
	0x72, 0x52, 0x05, 0x6f, 0x72, 0x64, 0x65, 0x72, 0x22, 0x57, 0x0a, 0x05, 0x4f, 0x72, 0x64, 0x65,
	0x72, 0x12, 0x19, 0x0a, 0x08, 0x6f, 0x72, 0x64, 0x65, 0x72, 0x5f, 0x69, 0x64, 0x18, 0x01, 0x20,
	0x01, 0x28, 0x03, 0x52, 0x07, 0x6f, 0x72, 0x64, 0x65, 0x72, 0x49, 0x64, 0x12, 0x17, 0x0a, 0x07,
	0x69, 0x74, 0x65, 0x6d, 0x5f, 0x69, 0x64, 0x18, 0x02, 0x20, 0x01, 0x28, 0x03, 0x52, 0x06, 0x69,
	0x74, 0x65, 0x6d, 0x49, 0x64, 0x12, 0x1a, 0x0a, 0x08, 0x71, 0x75, 0x61, 0x6e, 0x74, 0x69, 0x74,
	0x79, 0x18, 0x03, 0x20, 0x01, 0x28, 0x05, 0x52, 0x08, 0x71, 0x75, 0x61, 0x6e, 0x74, 0x69, 0x74,
	0x79, 0x22, 0xc6, 0x01, 0x0a, 0x0f, 0x52, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x54, 0x79, 0x70, 0x65,
	0x55, 0x6e, 0x69, 0x6f, 0x6e, 0x12, 0x32, 0x0a, 0x05, 0x5f, 0x49, 0x74, 0x65, 0x6d, 0x18, 0x01,
	0x20, 0x01, 0x28, 0x0b, 0x32, 0x1d, 0x2e, 0x63, 0x6f, 0x6d, 0x2e, 0x63, 0x69, 0x6f, 0x2e, 0x66,
	0x64, 0x62, 0x2e, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x2e, 0x67, 0x72, 0x70, 0x63, 0x2e, 0x49,
	0x74, 0x65, 0x6d, 0x52, 0x04, 0x49, 0x74, 0x65, 0x6d, 0x12, 0x38, 0x0a, 0x07, 0x5f, 0x56, 0x65,
	0x6e, 0x64, 0x6f, 0x72, 0x18, 0x02, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x1f, 0x2e, 0x63, 0x6f, 0x6d,
	0x2e, 0x63, 0x69, 0x6f, 0x2e, 0x66, 0x64, 0x62, 0x2e, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x2e,
	0x67, 0x72, 0x70, 0x63, 0x2e, 0x56, 0x65, 0x6e, 0x64, 0x6f, 0x72, 0x52, 0x06, 0x56, 0x65, 0x6e,
	0x64, 0x6f, 0x72, 0x12, 0x3e, 0x0a, 0x09, 0x5f, 0x43, 0x75, 0x73, 0x74, 0x6f, 0x6d, 0x65, 0x72,
	0x18, 0x03, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x21, 0x2e, 0x63, 0x6f, 0x6d, 0x2e, 0x63, 0x69, 0x6f,
	0x2e, 0x66, 0x64, 0x62, 0x2e, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x2e, 0x67, 0x72, 0x70, 0x63,
	0x2e, 0x43, 0x75, 0x73, 0x74, 0x6f, 0x6d, 0x65, 0x72, 0x52, 0x08, 0x43, 0x75, 0x73, 0x74, 0x6f,
	0x6d, 0x65, 0x72, 0x3a, 0x05, 0x8a, 0x4d, 0x02, 0x08, 0x02, 0x42, 0x11, 0x8a, 0x4d, 0x02, 0x18,
	0x01, 0x5a, 0x0a, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x73, 0x2f, 0x67, 0x65, 0x6e,
}

var (
	file_sample_proto_rawDescOnce sync.Once
	file_sample_proto_rawDescData = file_sample_proto_rawDesc
)

func file_sample_proto_rawDescGZIP() []byte {
	file_sample_proto_rawDescOnce.Do(func() {
		file_sample_proto_rawDescData = protoimpl.X.CompressGZIP(file_sample_proto_rawDescData)
	})
	return file_sample_proto_rawDescData
}

var file_sample_proto_msgTypes = make([]protoimpl.MessageInfo, 5)
var file_sample_proto_goTypes = []any{
	(*Vendor)(nil),          // 0: com.cio.fdb.record.grpc.Vendor
	(*Item)(nil),            // 1: com.cio.fdb.record.grpc.Item
	(*Customer)(nil),        // 2: com.cio.fdb.record.grpc.Customer
	(*Order)(nil),           // 3: com.cio.fdb.record.grpc.Order
	(*RecordTypeUnion)(nil), // 4: com.cio.fdb.record.grpc.RecordTypeUnion
}
var file_sample_proto_depIdxs = []int32{
	3, // 0: com.cio.fdb.record.grpc.Customer.order:type_name -> com.cio.fdb.record.grpc.Order
	1, // 1: com.cio.fdb.record.grpc.RecordTypeUnion._Item:type_name -> com.cio.fdb.record.grpc.Item
	0, // 2: com.cio.fdb.record.grpc.RecordTypeUnion._Vendor:type_name -> com.cio.fdb.record.grpc.Vendor
	2, // 3: com.cio.fdb.record.grpc.RecordTypeUnion._Customer:type_name -> com.cio.fdb.record.grpc.Customer
	4, // [4:4] is the sub-list for method output_type
	4, // [4:4] is the sub-list for method input_type
	4, // [4:4] is the sub-list for extension type_name
	4, // [4:4] is the sub-list for extension extendee
	0, // [0:4] is the sub-list for field type_name
}

func init() { file_sample_proto_init() }
func file_sample_proto_init() {
	if File_sample_proto != nil {
		return
	}
	if !protoimpl.UnsafeEnabled {
		file_sample_proto_msgTypes[0].Exporter = func(v any, i int) any {
			switch v := v.(*Vendor); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_sample_proto_msgTypes[1].Exporter = func(v any, i int) any {
			switch v := v.(*Item); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_sample_proto_msgTypes[2].Exporter = func(v any, i int) any {
			switch v := v.(*Customer); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_sample_proto_msgTypes[3].Exporter = func(v any, i int) any {
			switch v := v.(*Order); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_sample_proto_msgTypes[4].Exporter = func(v any, i int) any {
			switch v := v.(*RecordTypeUnion); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
	}
	type x struct{}
	out := protoimpl.TypeBuilder{
		File: protoimpl.DescBuilder{
			GoPackagePath: reflect.TypeOf(x{}).PkgPath(),
			RawDescriptor: file_sample_proto_rawDesc,
			NumEnums:      0,
			NumMessages:   5,
			NumExtensions: 0,
			NumServices:   0,
		},
		GoTypes:           file_sample_proto_goTypes,
		DependencyIndexes: file_sample_proto_depIdxs,
		MessageInfos:      file_sample_proto_msgTypes,
	}.Build()
	File_sample_proto = out.File
	file_sample_proto_rawDesc = nil
	file_sample_proto_goTypes = nil
	file_sample_proto_depIdxs = nil
}
