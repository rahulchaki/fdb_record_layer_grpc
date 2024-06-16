//
// string_interning_data.proto
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
// source: string_interning_data.proto

package src

import (
	protoreflect "google.golang.org/protobuf/reflect/protoreflect"
	protoimpl "google.golang.org/protobuf/runtime/protoimpl"
	_ "google.golang.org/protobuf/types/descriptorpb"
	reflect "reflect"
	sync "sync"
)

const (
	// Verify that this generated code is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(20 - protoimpl.MinVersion)
	// Verify that runtime/protoimpl is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(protoimpl.MaxVersion - 20)
)

type Data struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	InternedValue *uint64 `protobuf:"varint,1,opt,name=internedValue" json:"internedValue,omitempty"`
	Metadata      []byte  `protobuf:"bytes,2,opt,name=metadata" json:"metadata,omitempty"`
}

func (x *Data) Reset() {
	*x = Data{}
	if protoimpl.UnsafeEnabled {
		mi := &file_string_interning_data_proto_msgTypes[0]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *Data) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*Data) ProtoMessage() {}

func (x *Data) ProtoReflect() protoreflect.Message {
	mi := &file_string_interning_data_proto_msgTypes[0]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use Data.ProtoReflect.Descriptor instead.
func (*Data) Descriptor() ([]byte, []int) {
	return file_string_interning_data_proto_rawDescGZIP(), []int{0}
}

func (x *Data) GetInternedValue() uint64 {
	if x != nil && x.InternedValue != nil {
		return *x.InternedValue
	}
	return 0
}

func (x *Data) GetMetadata() []byte {
	if x != nil {
		return x.Metadata
	}
	return nil
}

var File_string_interning_data_proto protoreflect.FileDescriptor

var file_string_interning_data_proto_rawDesc = []byte{
	0x0a, 0x1b, 0x73, 0x74, 0x72, 0x69, 0x6e, 0x67, 0x5f, 0x69, 0x6e, 0x74, 0x65, 0x72, 0x6e, 0x69,
	0x6e, 0x67, 0x5f, 0x64, 0x61, 0x74, 0x61, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x12, 0x1d, 0x63,
	0x6f, 0x6d, 0x2e, 0x61, 0x70, 0x70, 0x6c, 0x65, 0x2e, 0x66, 0x6f, 0x75, 0x6e, 0x64, 0x61, 0x74,
	0x69, 0x6f, 0x6e, 0x64, 0x62, 0x2e, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x1a, 0x20, 0x67, 0x6f,
	0x6f, 0x67, 0x6c, 0x65, 0x2f, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x62, 0x75, 0x66, 0x2f, 0x64, 0x65,
	0x73, 0x63, 0x72, 0x69, 0x70, 0x74, 0x6f, 0x72, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x22, 0x48,
	0x0a, 0x04, 0x44, 0x61, 0x74, 0x61, 0x12, 0x24, 0x0a, 0x0d, 0x69, 0x6e, 0x74, 0x65, 0x72, 0x6e,
	0x65, 0x64, 0x56, 0x61, 0x6c, 0x75, 0x65, 0x18, 0x01, 0x20, 0x01, 0x28, 0x04, 0x52, 0x0d, 0x69,
	0x6e, 0x74, 0x65, 0x72, 0x6e, 0x65, 0x64, 0x56, 0x61, 0x6c, 0x75, 0x65, 0x12, 0x1a, 0x0a, 0x08,
	0x6d, 0x65, 0x74, 0x61, 0x64, 0x61, 0x74, 0x61, 0x18, 0x02, 0x20, 0x01, 0x28, 0x0c, 0x52, 0x08,
	0x6d, 0x65, 0x74, 0x61, 0x64, 0x61, 0x74, 0x61, 0x42, 0x2a, 0x42, 0x14, 0x53, 0x74, 0x72, 0x69,
	0x6e, 0x67, 0x49, 0x6e, 0x74, 0x65, 0x72, 0x6e, 0x69, 0x6e, 0x67, 0x50, 0x72, 0x6f, 0x74, 0x6f,
	0x5a, 0x12, 0x63, 0x69, 0x6f, 0x2f, 0x66, 0x64, 0x62, 0x2f, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64,
	0x2f, 0x73, 0x72, 0x63,
}

var (
	file_string_interning_data_proto_rawDescOnce sync.Once
	file_string_interning_data_proto_rawDescData = file_string_interning_data_proto_rawDesc
)

func file_string_interning_data_proto_rawDescGZIP() []byte {
	file_string_interning_data_proto_rawDescOnce.Do(func() {
		file_string_interning_data_proto_rawDescData = protoimpl.X.CompressGZIP(file_string_interning_data_proto_rawDescData)
	})
	return file_string_interning_data_proto_rawDescData
}

var file_string_interning_data_proto_msgTypes = make([]protoimpl.MessageInfo, 1)
var file_string_interning_data_proto_goTypes = []any{
	(*Data)(nil), // 0: com.apple.foundationdb.record.Data
}
var file_string_interning_data_proto_depIdxs = []int32{
	0, // [0:0] is the sub-list for method output_type
	0, // [0:0] is the sub-list for method input_type
	0, // [0:0] is the sub-list for extension type_name
	0, // [0:0] is the sub-list for extension extendee
	0, // [0:0] is the sub-list for field type_name
}

func init() { file_string_interning_data_proto_init() }
func file_string_interning_data_proto_init() {
	if File_string_interning_data_proto != nil {
		return
	}
	if !protoimpl.UnsafeEnabled {
		file_string_interning_data_proto_msgTypes[0].Exporter = func(v any, i int) any {
			switch v := v.(*Data); i {
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
			RawDescriptor: file_string_interning_data_proto_rawDesc,
			NumEnums:      0,
			NumMessages:   1,
			NumExtensions: 0,
			NumServices:   0,
		},
		GoTypes:           file_string_interning_data_proto_goTypes,
		DependencyIndexes: file_string_interning_data_proto_depIdxs,
		MessageInfos:      file_string_interning_data_proto_msgTypes,
	}.Build()
	File_string_interning_data_proto = out.File
	file_string_interning_data_proto_rawDesc = nil
	file_string_interning_data_proto_goTypes = nil
	file_string_interning_data_proto_depIdxs = nil
}
