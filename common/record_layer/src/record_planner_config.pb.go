//
// record_planner_config.proto
//
// This source file is part of the FoundationDB open source project
//
// Copyright 2015-2024 Apple Inc. and the FoundationDB project authors
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
// source: record_planner_config.proto

package src

import (
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

type PlannerConfiguration_IndexScanPreference int32

const (
	PlannerConfiguration_PREFER_SCAN              PlannerConfiguration_IndexScanPreference = 0
	PlannerConfiguration_PREFER_INDEX             PlannerConfiguration_IndexScanPreference = 1
	PlannerConfiguration_PREFER_PRIMARY_KEY_INDEX PlannerConfiguration_IndexScanPreference = 2
)

// Enum value maps for PlannerConfiguration_IndexScanPreference.
var (
	PlannerConfiguration_IndexScanPreference_name = map[int32]string{
		0: "PREFER_SCAN",
		1: "PREFER_INDEX",
		2: "PREFER_PRIMARY_KEY_INDEX",
	}
	PlannerConfiguration_IndexScanPreference_value = map[string]int32{
		"PREFER_SCAN":              0,
		"PREFER_INDEX":             1,
		"PREFER_PRIMARY_KEY_INDEX": 2,
	}
)

func (x PlannerConfiguration_IndexScanPreference) Enum() *PlannerConfiguration_IndexScanPreference {
	p := new(PlannerConfiguration_IndexScanPreference)
	*p = x
	return p
}

func (x PlannerConfiguration_IndexScanPreference) String() string {
	return protoimpl.X.EnumStringOf(x.Descriptor(), protoreflect.EnumNumber(x))
}

func (PlannerConfiguration_IndexScanPreference) Descriptor() protoreflect.EnumDescriptor {
	return file_record_planner_config_proto_enumTypes[0].Descriptor()
}

func (PlannerConfiguration_IndexScanPreference) Type() protoreflect.EnumType {
	return &file_record_planner_config_proto_enumTypes[0]
}

func (x PlannerConfiguration_IndexScanPreference) Number() protoreflect.EnumNumber {
	return protoreflect.EnumNumber(x)
}

// Deprecated: Do not use.
func (x *PlannerConfiguration_IndexScanPreference) UnmarshalJSON(b []byte) error {
	num, err := protoimpl.X.UnmarshalJSONEnum(x.Descriptor(), b)
	if err != nil {
		return err
	}
	*x = PlannerConfiguration_IndexScanPreference(num)
	return nil
}

// Deprecated: Use PlannerConfiguration_IndexScanPreference.Descriptor instead.
func (PlannerConfiguration_IndexScanPreference) EnumDescriptor() ([]byte, []int) {
	return file_record_planner_config_proto_rawDescGZIP(), []int{0, 0}
}

type PlannerConfiguration_IndexFetchMethod int32

const (
	PlannerConfiguration_SCAN_AND_FETCH                 PlannerConfiguration_IndexFetchMethod = 0
	PlannerConfiguration_USE_REMOTE_FETCH               PlannerConfiguration_IndexFetchMethod = 1
	PlannerConfiguration_USE_REMOTE_FETCH_WITH_FALLBACK PlannerConfiguration_IndexFetchMethod = 2
)

// Enum value maps for PlannerConfiguration_IndexFetchMethod.
var (
	PlannerConfiguration_IndexFetchMethod_name = map[int32]string{
		0: "SCAN_AND_FETCH",
		1: "USE_REMOTE_FETCH",
		2: "USE_REMOTE_FETCH_WITH_FALLBACK",
	}
	PlannerConfiguration_IndexFetchMethod_value = map[string]int32{
		"SCAN_AND_FETCH":                 0,
		"USE_REMOTE_FETCH":               1,
		"USE_REMOTE_FETCH_WITH_FALLBACK": 2,
	}
)

func (x PlannerConfiguration_IndexFetchMethod) Enum() *PlannerConfiguration_IndexFetchMethod {
	p := new(PlannerConfiguration_IndexFetchMethod)
	*p = x
	return p
}

func (x PlannerConfiguration_IndexFetchMethod) String() string {
	return protoimpl.X.EnumStringOf(x.Descriptor(), protoreflect.EnumNumber(x))
}

func (PlannerConfiguration_IndexFetchMethod) Descriptor() protoreflect.EnumDescriptor {
	return file_record_planner_config_proto_enumTypes[1].Descriptor()
}

func (PlannerConfiguration_IndexFetchMethod) Type() protoreflect.EnumType {
	return &file_record_planner_config_proto_enumTypes[1]
}

func (x PlannerConfiguration_IndexFetchMethod) Number() protoreflect.EnumNumber {
	return protoreflect.EnumNumber(x)
}

// Deprecated: Do not use.
func (x *PlannerConfiguration_IndexFetchMethod) UnmarshalJSON(b []byte) error {
	num, err := protoimpl.X.UnmarshalJSONEnum(x.Descriptor(), b)
	if err != nil {
		return err
	}
	*x = PlannerConfiguration_IndexFetchMethod(num)
	return nil
}

// Deprecated: Use PlannerConfiguration_IndexFetchMethod.Descriptor instead.
func (PlannerConfiguration_IndexFetchMethod) EnumDescriptor() ([]byte, []int) {
	return file_record_planner_config_proto_rawDescGZIP(), []int{0, 1}
}

type PlannerConfiguration struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Flags                             *int64                                    `protobuf:"varint,1,opt,name=flags" json:"flags,omitempty"` // used to collect multiple Boolean configuration flags
	IndexScanPreference               *PlannerConfiguration_IndexScanPreference `protobuf:"varint,2,opt,name=indexScanPreference,enum=com.apple.foundationdb.record.PlannerConfiguration_IndexScanPreference" json:"indexScanPreference,omitempty"`
	AttemptFailedInJoinAsUnionMaxSize *int32                                    `protobuf:"varint,3,opt,name=attemptFailedInJoinAsUnionMaxSize" json:"attemptFailedInJoinAsUnionMaxSize,omitempty"`
	ComplexityThreshold               *int32                                    `protobuf:"varint,4,opt,name=complexityThreshold" json:"complexityThreshold,omitempty"`
	MaxTaskQueueSize                  *int32                                    `protobuf:"varint,5,opt,name=maxTaskQueueSize" json:"maxTaskQueueSize,omitempty"`
	MaxTotalTaskCount                 *int32                                    `protobuf:"varint,6,opt,name=maxTotalTaskCount" json:"maxTotalTaskCount,omitempty"`
	MaxNumMatchesPerRuleCall          *int32                                    `protobuf:"varint,7,opt,name=maxNumMatchesPerRuleCall" json:"maxNumMatchesPerRuleCall,omitempty"`
	SortConfiguration                 *PlannerConfiguration_SortConfiguration   `protobuf:"bytes,8,opt,name=sortConfiguration" json:"sortConfiguration,omitempty"`
	DisabledTransformationRules       []string                                  `protobuf:"bytes,9,rep,name=disabledTransformationRules" json:"disabledTransformationRules,omitempty"`
	IndexFetchMethod                  *PlannerConfiguration_IndexFetchMethod    `protobuf:"varint,10,opt,name=indexFetchMethod,enum=com.apple.foundationdb.record.PlannerConfiguration_IndexFetchMethod" json:"indexFetchMethod,omitempty"`
	ValueIndexesOverScanNeeded        []string                                  `protobuf:"bytes,11,rep,name=valueIndexesOverScanNeeded" json:"valueIndexesOverScanNeeded,omitempty"`
	MaxNumReplansForInToJoin          *int32                                    `protobuf:"varint,12,opt,name=maxNumReplansForInToJoin" json:"maxNumReplansForInToJoin,omitempty"`
	OrToUnionMaxNumConjuncts          *int32                                    `protobuf:"varint,13,opt,name=orToUnionMaxNumConjuncts" json:"orToUnionMaxNumConjuncts,omitempty"`
}

func (x *PlannerConfiguration) Reset() {
	*x = PlannerConfiguration{}
	if protoimpl.UnsafeEnabled {
		mi := &file_record_planner_config_proto_msgTypes[0]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *PlannerConfiguration) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*PlannerConfiguration) ProtoMessage() {}

func (x *PlannerConfiguration) ProtoReflect() protoreflect.Message {
	mi := &file_record_planner_config_proto_msgTypes[0]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use PlannerConfiguration.ProtoReflect.Descriptor instead.
func (*PlannerConfiguration) Descriptor() ([]byte, []int) {
	return file_record_planner_config_proto_rawDescGZIP(), []int{0}
}

func (x *PlannerConfiguration) GetFlags() int64 {
	if x != nil && x.Flags != nil {
		return *x.Flags
	}
	return 0
}

func (x *PlannerConfiguration) GetIndexScanPreference() PlannerConfiguration_IndexScanPreference {
	if x != nil && x.IndexScanPreference != nil {
		return *x.IndexScanPreference
	}
	return PlannerConfiguration_PREFER_SCAN
}

func (x *PlannerConfiguration) GetAttemptFailedInJoinAsUnionMaxSize() int32 {
	if x != nil && x.AttemptFailedInJoinAsUnionMaxSize != nil {
		return *x.AttemptFailedInJoinAsUnionMaxSize
	}
	return 0
}

func (x *PlannerConfiguration) GetComplexityThreshold() int32 {
	if x != nil && x.ComplexityThreshold != nil {
		return *x.ComplexityThreshold
	}
	return 0
}

func (x *PlannerConfiguration) GetMaxTaskQueueSize() int32 {
	if x != nil && x.MaxTaskQueueSize != nil {
		return *x.MaxTaskQueueSize
	}
	return 0
}

func (x *PlannerConfiguration) GetMaxTotalTaskCount() int32 {
	if x != nil && x.MaxTotalTaskCount != nil {
		return *x.MaxTotalTaskCount
	}
	return 0
}

func (x *PlannerConfiguration) GetMaxNumMatchesPerRuleCall() int32 {
	if x != nil && x.MaxNumMatchesPerRuleCall != nil {
		return *x.MaxNumMatchesPerRuleCall
	}
	return 0
}

func (x *PlannerConfiguration) GetSortConfiguration() *PlannerConfiguration_SortConfiguration {
	if x != nil {
		return x.SortConfiguration
	}
	return nil
}

func (x *PlannerConfiguration) GetDisabledTransformationRules() []string {
	if x != nil {
		return x.DisabledTransformationRules
	}
	return nil
}

func (x *PlannerConfiguration) GetIndexFetchMethod() PlannerConfiguration_IndexFetchMethod {
	if x != nil && x.IndexFetchMethod != nil {
		return *x.IndexFetchMethod
	}
	return PlannerConfiguration_SCAN_AND_FETCH
}

func (x *PlannerConfiguration) GetValueIndexesOverScanNeeded() []string {
	if x != nil {
		return x.ValueIndexesOverScanNeeded
	}
	return nil
}

func (x *PlannerConfiguration) GetMaxNumReplansForInToJoin() int32 {
	if x != nil && x.MaxNumReplansForInToJoin != nil {
		return *x.MaxNumReplansForInToJoin
	}
	return 0
}

func (x *PlannerConfiguration) GetOrToUnionMaxNumConjuncts() int32 {
	if x != nil && x.OrToUnionMaxNumConjuncts != nil {
		return *x.OrToUnionMaxNumConjuncts
	}
	return 0
}

type PlannerConfiguration_SortConfiguration struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	ShouldAllowNonIndexSort *bool `protobuf:"varint,1,opt,name=shouldAllowNonIndexSort" json:"shouldAllowNonIndexSort,omitempty"`
}

func (x *PlannerConfiguration_SortConfiguration) Reset() {
	*x = PlannerConfiguration_SortConfiguration{}
	if protoimpl.UnsafeEnabled {
		mi := &file_record_planner_config_proto_msgTypes[1]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *PlannerConfiguration_SortConfiguration) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*PlannerConfiguration_SortConfiguration) ProtoMessage() {}

func (x *PlannerConfiguration_SortConfiguration) ProtoReflect() protoreflect.Message {
	mi := &file_record_planner_config_proto_msgTypes[1]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use PlannerConfiguration_SortConfiguration.ProtoReflect.Descriptor instead.
func (*PlannerConfiguration_SortConfiguration) Descriptor() ([]byte, []int) {
	return file_record_planner_config_proto_rawDescGZIP(), []int{0, 0}
}

func (x *PlannerConfiguration_SortConfiguration) GetShouldAllowNonIndexSort() bool {
	if x != nil && x.ShouldAllowNonIndexSort != nil {
		return *x.ShouldAllowNonIndexSort
	}
	return false
}

var File_record_planner_config_proto protoreflect.FileDescriptor

var file_record_planner_config_proto_rawDesc = []byte{
	0x0a, 0x1b, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x5f, 0x70, 0x6c, 0x61, 0x6e, 0x6e, 0x65, 0x72,
	0x5f, 0x63, 0x6f, 0x6e, 0x66, 0x69, 0x67, 0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x12, 0x1d, 0x63,
	0x6f, 0x6d, 0x2e, 0x61, 0x70, 0x70, 0x6c, 0x65, 0x2e, 0x66, 0x6f, 0x75, 0x6e, 0x64, 0x61, 0x74,
	0x69, 0x6f, 0x6e, 0x64, 0x62, 0x2e, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x22, 0xa7, 0x09, 0x0a,
	0x14, 0x50, 0x6c, 0x61, 0x6e, 0x6e, 0x65, 0x72, 0x43, 0x6f, 0x6e, 0x66, 0x69, 0x67, 0x75, 0x72,
	0x61, 0x74, 0x69, 0x6f, 0x6e, 0x12, 0x14, 0x0a, 0x05, 0x66, 0x6c, 0x61, 0x67, 0x73, 0x18, 0x01,
	0x20, 0x01, 0x28, 0x03, 0x52, 0x05, 0x66, 0x6c, 0x61, 0x67, 0x73, 0x12, 0x79, 0x0a, 0x13, 0x69,
	0x6e, 0x64, 0x65, 0x78, 0x53, 0x63, 0x61, 0x6e, 0x50, 0x72, 0x65, 0x66, 0x65, 0x72, 0x65, 0x6e,
	0x63, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x0e, 0x32, 0x47, 0x2e, 0x63, 0x6f, 0x6d, 0x2e, 0x61,
	0x70, 0x70, 0x6c, 0x65, 0x2e, 0x66, 0x6f, 0x75, 0x6e, 0x64, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x64,
	0x62, 0x2e, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x2e, 0x50, 0x6c, 0x61, 0x6e, 0x6e, 0x65, 0x72,
	0x43, 0x6f, 0x6e, 0x66, 0x69, 0x67, 0x75, 0x72, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x2e, 0x49, 0x6e,
	0x64, 0x65, 0x78, 0x53, 0x63, 0x61, 0x6e, 0x50, 0x72, 0x65, 0x66, 0x65, 0x72, 0x65, 0x6e, 0x63,
	0x65, 0x52, 0x13, 0x69, 0x6e, 0x64, 0x65, 0x78, 0x53, 0x63, 0x61, 0x6e, 0x50, 0x72, 0x65, 0x66,
	0x65, 0x72, 0x65, 0x6e, 0x63, 0x65, 0x12, 0x4c, 0x0a, 0x21, 0x61, 0x74, 0x74, 0x65, 0x6d, 0x70,
	0x74, 0x46, 0x61, 0x69, 0x6c, 0x65, 0x64, 0x49, 0x6e, 0x4a, 0x6f, 0x69, 0x6e, 0x41, 0x73, 0x55,
	0x6e, 0x69, 0x6f, 0x6e, 0x4d, 0x61, 0x78, 0x53, 0x69, 0x7a, 0x65, 0x18, 0x03, 0x20, 0x01, 0x28,
	0x05, 0x52, 0x21, 0x61, 0x74, 0x74, 0x65, 0x6d, 0x70, 0x74, 0x46, 0x61, 0x69, 0x6c, 0x65, 0x64,
	0x49, 0x6e, 0x4a, 0x6f, 0x69, 0x6e, 0x41, 0x73, 0x55, 0x6e, 0x69, 0x6f, 0x6e, 0x4d, 0x61, 0x78,
	0x53, 0x69, 0x7a, 0x65, 0x12, 0x30, 0x0a, 0x13, 0x63, 0x6f, 0x6d, 0x70, 0x6c, 0x65, 0x78, 0x69,
	0x74, 0x79, 0x54, 0x68, 0x72, 0x65, 0x73, 0x68, 0x6f, 0x6c, 0x64, 0x18, 0x04, 0x20, 0x01, 0x28,
	0x05, 0x52, 0x13, 0x63, 0x6f, 0x6d, 0x70, 0x6c, 0x65, 0x78, 0x69, 0x74, 0x79, 0x54, 0x68, 0x72,
	0x65, 0x73, 0x68, 0x6f, 0x6c, 0x64, 0x12, 0x2a, 0x0a, 0x10, 0x6d, 0x61, 0x78, 0x54, 0x61, 0x73,
	0x6b, 0x51, 0x75, 0x65, 0x75, 0x65, 0x53, 0x69, 0x7a, 0x65, 0x18, 0x05, 0x20, 0x01, 0x28, 0x05,
	0x52, 0x10, 0x6d, 0x61, 0x78, 0x54, 0x61, 0x73, 0x6b, 0x51, 0x75, 0x65, 0x75, 0x65, 0x53, 0x69,
	0x7a, 0x65, 0x12, 0x2c, 0x0a, 0x11, 0x6d, 0x61, 0x78, 0x54, 0x6f, 0x74, 0x61, 0x6c, 0x54, 0x61,
	0x73, 0x6b, 0x43, 0x6f, 0x75, 0x6e, 0x74, 0x18, 0x06, 0x20, 0x01, 0x28, 0x05, 0x52, 0x11, 0x6d,
	0x61, 0x78, 0x54, 0x6f, 0x74, 0x61, 0x6c, 0x54, 0x61, 0x73, 0x6b, 0x43, 0x6f, 0x75, 0x6e, 0x74,
	0x12, 0x3a, 0x0a, 0x18, 0x6d, 0x61, 0x78, 0x4e, 0x75, 0x6d, 0x4d, 0x61, 0x74, 0x63, 0x68, 0x65,
	0x73, 0x50, 0x65, 0x72, 0x52, 0x75, 0x6c, 0x65, 0x43, 0x61, 0x6c, 0x6c, 0x18, 0x07, 0x20, 0x01,
	0x28, 0x05, 0x52, 0x18, 0x6d, 0x61, 0x78, 0x4e, 0x75, 0x6d, 0x4d, 0x61, 0x74, 0x63, 0x68, 0x65,
	0x73, 0x50, 0x65, 0x72, 0x52, 0x75, 0x6c, 0x65, 0x43, 0x61, 0x6c, 0x6c, 0x12, 0x73, 0x0a, 0x11,
	0x73, 0x6f, 0x72, 0x74, 0x43, 0x6f, 0x6e, 0x66, 0x69, 0x67, 0x75, 0x72, 0x61, 0x74, 0x69, 0x6f,
	0x6e, 0x18, 0x08, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x45, 0x2e, 0x63, 0x6f, 0x6d, 0x2e, 0x61, 0x70,
	0x70, 0x6c, 0x65, 0x2e, 0x66, 0x6f, 0x75, 0x6e, 0x64, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x64, 0x62,
	0x2e, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x2e, 0x50, 0x6c, 0x61, 0x6e, 0x6e, 0x65, 0x72, 0x43,
	0x6f, 0x6e, 0x66, 0x69, 0x67, 0x75, 0x72, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x2e, 0x53, 0x6f, 0x72,
	0x74, 0x43, 0x6f, 0x6e, 0x66, 0x69, 0x67, 0x75, 0x72, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x52, 0x11,
	0x73, 0x6f, 0x72, 0x74, 0x43, 0x6f, 0x6e, 0x66, 0x69, 0x67, 0x75, 0x72, 0x61, 0x74, 0x69, 0x6f,
	0x6e, 0x12, 0x40, 0x0a, 0x1b, 0x64, 0x69, 0x73, 0x61, 0x62, 0x6c, 0x65, 0x64, 0x54, 0x72, 0x61,
	0x6e, 0x73, 0x66, 0x6f, 0x72, 0x6d, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x52, 0x75, 0x6c, 0x65, 0x73,
	0x18, 0x09, 0x20, 0x03, 0x28, 0x09, 0x52, 0x1b, 0x64, 0x69, 0x73, 0x61, 0x62, 0x6c, 0x65, 0x64,
	0x54, 0x72, 0x61, 0x6e, 0x73, 0x66, 0x6f, 0x72, 0x6d, 0x61, 0x74, 0x69, 0x6f, 0x6e, 0x52, 0x75,
	0x6c, 0x65, 0x73, 0x12, 0x70, 0x0a, 0x10, 0x69, 0x6e, 0x64, 0x65, 0x78, 0x46, 0x65, 0x74, 0x63,
	0x68, 0x4d, 0x65, 0x74, 0x68, 0x6f, 0x64, 0x18, 0x0a, 0x20, 0x01, 0x28, 0x0e, 0x32, 0x44, 0x2e,
	0x63, 0x6f, 0x6d, 0x2e, 0x61, 0x70, 0x70, 0x6c, 0x65, 0x2e, 0x66, 0x6f, 0x75, 0x6e, 0x64, 0x61,
	0x74, 0x69, 0x6f, 0x6e, 0x64, 0x62, 0x2e, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x2e, 0x50, 0x6c,
	0x61, 0x6e, 0x6e, 0x65, 0x72, 0x43, 0x6f, 0x6e, 0x66, 0x69, 0x67, 0x75, 0x72, 0x61, 0x74, 0x69,
	0x6f, 0x6e, 0x2e, 0x49, 0x6e, 0x64, 0x65, 0x78, 0x46, 0x65, 0x74, 0x63, 0x68, 0x4d, 0x65, 0x74,
	0x68, 0x6f, 0x64, 0x52, 0x10, 0x69, 0x6e, 0x64, 0x65, 0x78, 0x46, 0x65, 0x74, 0x63, 0x68, 0x4d,
	0x65, 0x74, 0x68, 0x6f, 0x64, 0x12, 0x3e, 0x0a, 0x1a, 0x76, 0x61, 0x6c, 0x75, 0x65, 0x49, 0x6e,
	0x64, 0x65, 0x78, 0x65, 0x73, 0x4f, 0x76, 0x65, 0x72, 0x53, 0x63, 0x61, 0x6e, 0x4e, 0x65, 0x65,
	0x64, 0x65, 0x64, 0x18, 0x0b, 0x20, 0x03, 0x28, 0x09, 0x52, 0x1a, 0x76, 0x61, 0x6c, 0x75, 0x65,
	0x49, 0x6e, 0x64, 0x65, 0x78, 0x65, 0x73, 0x4f, 0x76, 0x65, 0x72, 0x53, 0x63, 0x61, 0x6e, 0x4e,
	0x65, 0x65, 0x64, 0x65, 0x64, 0x12, 0x3a, 0x0a, 0x18, 0x6d, 0x61, 0x78, 0x4e, 0x75, 0x6d, 0x52,
	0x65, 0x70, 0x6c, 0x61, 0x6e, 0x73, 0x46, 0x6f, 0x72, 0x49, 0x6e, 0x54, 0x6f, 0x4a, 0x6f, 0x69,
	0x6e, 0x18, 0x0c, 0x20, 0x01, 0x28, 0x05, 0x52, 0x18, 0x6d, 0x61, 0x78, 0x4e, 0x75, 0x6d, 0x52,
	0x65, 0x70, 0x6c, 0x61, 0x6e, 0x73, 0x46, 0x6f, 0x72, 0x49, 0x6e, 0x54, 0x6f, 0x4a, 0x6f, 0x69,
	0x6e, 0x12, 0x3a, 0x0a, 0x18, 0x6f, 0x72, 0x54, 0x6f, 0x55, 0x6e, 0x69, 0x6f, 0x6e, 0x4d, 0x61,
	0x78, 0x4e, 0x75, 0x6d, 0x43, 0x6f, 0x6e, 0x6a, 0x75, 0x6e, 0x63, 0x74, 0x73, 0x18, 0x0d, 0x20,
	0x01, 0x28, 0x05, 0x52, 0x18, 0x6f, 0x72, 0x54, 0x6f, 0x55, 0x6e, 0x69, 0x6f, 0x6e, 0x4d, 0x61,
	0x78, 0x4e, 0x75, 0x6d, 0x43, 0x6f, 0x6e, 0x6a, 0x75, 0x6e, 0x63, 0x74, 0x73, 0x1a, 0x4d, 0x0a,
	0x11, 0x53, 0x6f, 0x72, 0x74, 0x43, 0x6f, 0x6e, 0x66, 0x69, 0x67, 0x75, 0x72, 0x61, 0x74, 0x69,
	0x6f, 0x6e, 0x12, 0x38, 0x0a, 0x17, 0x73, 0x68, 0x6f, 0x75, 0x6c, 0x64, 0x41, 0x6c, 0x6c, 0x6f,
	0x77, 0x4e, 0x6f, 0x6e, 0x49, 0x6e, 0x64, 0x65, 0x78, 0x53, 0x6f, 0x72, 0x74, 0x18, 0x01, 0x20,
	0x01, 0x28, 0x08, 0x52, 0x17, 0x73, 0x68, 0x6f, 0x75, 0x6c, 0x64, 0x41, 0x6c, 0x6c, 0x6f, 0x77,
	0x4e, 0x6f, 0x6e, 0x49, 0x6e, 0x64, 0x65, 0x78, 0x53, 0x6f, 0x72, 0x74, 0x22, 0x56, 0x0a, 0x13,
	0x49, 0x6e, 0x64, 0x65, 0x78, 0x53, 0x63, 0x61, 0x6e, 0x50, 0x72, 0x65, 0x66, 0x65, 0x72, 0x65,
	0x6e, 0x63, 0x65, 0x12, 0x0f, 0x0a, 0x0b, 0x50, 0x52, 0x45, 0x46, 0x45, 0x52, 0x5f, 0x53, 0x43,
	0x41, 0x4e, 0x10, 0x00, 0x12, 0x10, 0x0a, 0x0c, 0x50, 0x52, 0x45, 0x46, 0x45, 0x52, 0x5f, 0x49,
	0x4e, 0x44, 0x45, 0x58, 0x10, 0x01, 0x12, 0x1c, 0x0a, 0x18, 0x50, 0x52, 0x45, 0x46, 0x45, 0x52,
	0x5f, 0x50, 0x52, 0x49, 0x4d, 0x41, 0x52, 0x59, 0x5f, 0x4b, 0x45, 0x59, 0x5f, 0x49, 0x4e, 0x44,
	0x45, 0x58, 0x10, 0x02, 0x22, 0x60, 0x0a, 0x10, 0x49, 0x6e, 0x64, 0x65, 0x78, 0x46, 0x65, 0x74,
	0x63, 0x68, 0x4d, 0x65, 0x74, 0x68, 0x6f, 0x64, 0x12, 0x12, 0x0a, 0x0e, 0x53, 0x43, 0x41, 0x4e,
	0x5f, 0x41, 0x4e, 0x44, 0x5f, 0x46, 0x45, 0x54, 0x43, 0x48, 0x10, 0x00, 0x12, 0x14, 0x0a, 0x10,
	0x55, 0x53, 0x45, 0x5f, 0x52, 0x45, 0x4d, 0x4f, 0x54, 0x45, 0x5f, 0x46, 0x45, 0x54, 0x43, 0x48,
	0x10, 0x01, 0x12, 0x22, 0x0a, 0x1e, 0x55, 0x53, 0x45, 0x5f, 0x52, 0x45, 0x4d, 0x4f, 0x54, 0x45,
	0x5f, 0x46, 0x45, 0x54, 0x43, 0x48, 0x5f, 0x57, 0x49, 0x54, 0x48, 0x5f, 0x46, 0x41, 0x4c, 0x4c,
	0x42, 0x41, 0x43, 0x4b, 0x10, 0x02, 0x42, 0x35, 0x42, 0x1f, 0x52, 0x65, 0x63, 0x6f, 0x72, 0x64,
	0x50, 0x6c, 0x61, 0x6e, 0x6e, 0x65, 0x72, 0x43, 0x6f, 0x6e, 0x66, 0x69, 0x67, 0x75, 0x72, 0x61,
	0x74, 0x69, 0x6f, 0x6e, 0x50, 0x72, 0x6f, 0x74, 0x6f, 0x5a, 0x12, 0x63, 0x69, 0x6f, 0x2f, 0x66,
	0x64, 0x62, 0x2f, 0x72, 0x65, 0x63, 0x6f, 0x72, 0x64, 0x2f, 0x73, 0x72, 0x63,
}

var (
	file_record_planner_config_proto_rawDescOnce sync.Once
	file_record_planner_config_proto_rawDescData = file_record_planner_config_proto_rawDesc
)

func file_record_planner_config_proto_rawDescGZIP() []byte {
	file_record_planner_config_proto_rawDescOnce.Do(func() {
		file_record_planner_config_proto_rawDescData = protoimpl.X.CompressGZIP(file_record_planner_config_proto_rawDescData)
	})
	return file_record_planner_config_proto_rawDescData
}

var file_record_planner_config_proto_enumTypes = make([]protoimpl.EnumInfo, 2)
var file_record_planner_config_proto_msgTypes = make([]protoimpl.MessageInfo, 2)
var file_record_planner_config_proto_goTypes = []any{
	(PlannerConfiguration_IndexScanPreference)(0),  // 0: com.apple.foundationdb.record.PlannerConfiguration.IndexScanPreference
	(PlannerConfiguration_IndexFetchMethod)(0),     // 1: com.apple.foundationdb.record.PlannerConfiguration.IndexFetchMethod
	(*PlannerConfiguration)(nil),                   // 2: com.apple.foundationdb.record.PlannerConfiguration
	(*PlannerConfiguration_SortConfiguration)(nil), // 3: com.apple.foundationdb.record.PlannerConfiguration.SortConfiguration
}
var file_record_planner_config_proto_depIdxs = []int32{
	0, // 0: com.apple.foundationdb.record.PlannerConfiguration.indexScanPreference:type_name -> com.apple.foundationdb.record.PlannerConfiguration.IndexScanPreference
	3, // 1: com.apple.foundationdb.record.PlannerConfiguration.sortConfiguration:type_name -> com.apple.foundationdb.record.PlannerConfiguration.SortConfiguration
	1, // 2: com.apple.foundationdb.record.PlannerConfiguration.indexFetchMethod:type_name -> com.apple.foundationdb.record.PlannerConfiguration.IndexFetchMethod
	3, // [3:3] is the sub-list for method output_type
	3, // [3:3] is the sub-list for method input_type
	3, // [3:3] is the sub-list for extension type_name
	3, // [3:3] is the sub-list for extension extendee
	0, // [0:3] is the sub-list for field type_name
}

func init() { file_record_planner_config_proto_init() }
func file_record_planner_config_proto_init() {
	if File_record_planner_config_proto != nil {
		return
	}
	if !protoimpl.UnsafeEnabled {
		file_record_planner_config_proto_msgTypes[0].Exporter = func(v any, i int) any {
			switch v := v.(*PlannerConfiguration); i {
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
		file_record_planner_config_proto_msgTypes[1].Exporter = func(v any, i int) any {
			switch v := v.(*PlannerConfiguration_SortConfiguration); i {
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
			RawDescriptor: file_record_planner_config_proto_rawDesc,
			NumEnums:      2,
			NumMessages:   2,
			NumExtensions: 0,
			NumServices:   0,
		},
		GoTypes:           file_record_planner_config_proto_goTypes,
		DependencyIndexes: file_record_planner_config_proto_depIdxs,
		EnumInfos:         file_record_planner_config_proto_enumTypes,
		MessageInfos:      file_record_planner_config_proto_msgTypes,
	}.Build()
	File_record_planner_config_proto = out.File
	file_record_planner_config_proto_rawDesc = nil
	file_record_planner_config_proto_goTypes = nil
	file_record_planner_config_proto_depIdxs = nil
}