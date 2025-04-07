"use client";

import { useState } from "react";
import { ToggleGroup, ToggleGroupItem } from "@/components/ui/toggle-group";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
// import GroupUserItem from "./group-user-item"; // Component hiển thị thông tin 1 nhóm


export type GroupUser = {
  groupId: string;
  groupName: string;
  details: string;
  type: string;
  memberCount: number;
}

const groups: GroupUser[] = [
  {
    groupId: "1",
    groupName: "Nhóm A",
    details: "Chi tiết nhóm A",
    type: "nhom",
    memberCount: 5,
  },
  {
    groupId: "2",
    groupName: "Nhóm B",
    details: "Chi tiết nhóm B",
    type: "nhom",
    memberCount: 8,
  },
  {
    groupId: "3",
    groupName: "Nhóm C",
    details: "Chi tiết nhóm C",
    type: "nhom",
    memberCount: 12,
  },
  // Thêm các nhóm khác nếu cần
];

const GroupUserList: React.FC = () => {
  const [viewMode, setViewMode] = useState<"table">("table");
  const [searchTerm, setSearchTerm] = useState("");
  const [filterType, setFilterType] = useState("all");

  const filteredGroups = groups.filter((group) => {
    const matchesSearch =
      group.groupName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      group.details.toLowerCase().includes(searchTerm.toLowerCase());
    
    const matchesFilter = filterType === "all" || group.type === filterType;
    
    return matchesSearch && matchesFilter;
  });

  console.log(filteredGroups)

  return (
    <div className="flex-1">
      {/* Thanh tìm kiếm và bộ lọc */}
      <div className="mb-4 flex flex-col md:flex-row gap-4 items-center">
        <Input
          placeholder="Tìm theo tên nhóm hoặc chi tiết..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full md:w-1/3"
        />
        <Select value={filterType} onValueChange={setFilterType} defaultValue="all">
          <SelectTrigger className="w-full md:w-1/4">
            <SelectValue placeholder="Lọc theo loại" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="all">Tất cả</SelectItem>
            <SelectItem value="nhom">Nhóm</SelectItem>
            {/* Có thể thêm các loại khác nếu cần */}
          </SelectContent>
        </Select>
        <ToggleGroup
          type="single"
          value={viewMode}
          onValueChange={(value) => value && setViewMode(value as "table")}
        >
          <ToggleGroupItem value="table">Table</ToggleGroupItem>
          {/* <ToggleGroupItem value="card">Card</ToggleGroupItem> */}
        </ToggleGroup>
      </div>

      {viewMode === "table" ? (
        <div className="overflow-auto max-h-[75vh]">
          {/* <DataTable columns={columns} data={filteredGroups} /> */}
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
          {/* {filteredGroups.map((group) => (
            // <GroupUserItem key={group.groupId} group={group} />
            <></>
          ))} */}
        </div>
      )}
    </div>
  );
};

export default GroupUserList;
