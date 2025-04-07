"use client";

import { useEffect, useState } from "react";
import { ToggleGroup, ToggleGroupItem } from "@/components/ui/toggle-group";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import UserItem from "./user-item";
import UserAddModal from "./user-add";
import { UserService } from "@/services/admin/user.service";
import { columns } from "@/components/admin/users/column";
import { DataTable } from "@/components/admin/custom/data-table";
import { User } from "@/types/UserType";

const UserList: React.FC = () => {
  const [viewMode, setViewMode] = useState<"table" | "card">("table");
  const [searchTerm, setSearchTerm] = useState("");
  const [filterType, setFilterType] = useState("all");
  const [loading, setLoading] = useState(true);
  const [users, setUsers] = useState<User[]>([]);

  // Thêm state để quản lý phân trang
  const [pageIndex, setPageIndex] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
console.log(loading)
  // Hàm fetch dữ liệu người dùng
  const fetchUsers = async () => {
    setLoading(true);
    try {
      const userService = new UserService();
      const data = await userService.getListUsers(pageIndex, 10);
      console.log(data);
      setUsers(data.content || []);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
    setLoading(false);
  };

  // Hàm gọi khi thêm người dùng thành công
  const onAddSuccess = async () => {
    // Kiểm tra xem người dùng có phải là người dùng cuối cùng của trang không
    if (users.length >= 10) {
      setPageIndex(pageIndex + 1);
    } else {
      await fetchUsers();
    }
  };

  // // Hàm gọi khi chỉnh sửa người dùng thành công
  // const onEditSuccess = async () => {
  //   await fetchUsers();
  // };

  useEffect(() => {
    fetchUsers();
  }, [pageIndex]);

  const filteredUsers = users.filter((user) => {
    const matchesSearch =
      user.firstName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      user.lastName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      user.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
      user.userCode.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesFilter = filterType === "all" || user.userType === filterType;
    return matchesSearch && matchesFilter;
  });

  return (
    <div className="flex-1">
      {/* Thanh tìm kiếm, bộ lọc và nút Thêm */}
      <div className="mb-4 flex flex-col md:flex-row gap-4 items-center">
        <Input
          placeholder="Tìm kiếm theo tên, email, mã nhân viên..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full md:w-1/3"
        />
        <Select
          value={filterType}
          onValueChange={setFilterType}
          defaultValue="all"
        >
          <SelectTrigger className="w-full md:w-1/4">
            <SelectValue placeholder="Filter by role" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="all">Tất cả</SelectItem>
            <SelectItem value="nhan_vien">Nhân Viên</SelectItem>
            <SelectItem value="apporver">Kiểm duyệt viên</SelectItem>
          </SelectContent>
        </Select>
        <ToggleGroup
          type="single"
          value={viewMode}
          onValueChange={(value) =>
            value && setViewMode(value as "table" | "card")
          }
        >
          <ToggleGroupItem value="table">Bảng</ToggleGroupItem>
          <ToggleGroupItem value="card">Thẻ</ToggleGroupItem>
        </ToggleGroup>

        {/* Nút Thêm User */}
        <UserAddModal onAddSuccess={onAddSuccess} />
      </div>

      {viewMode === "table" ? (
        <div className="flex-1 min-h-[80vh] ">
          <DataTable
            columns={columns}
            data={filteredUsers}
            pageIndex={pageIndex}
            pageSize={10}
            pageCount={totalPages}
            onPageChange={(newPage) => {
              if (newPage >= 0 && newPage < totalPages) {
                setPageIndex(newPage);
              }
            }}
          />
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 p-4">
          {filteredUsers.map((user) => (
            <UserItem key={user.userId} user={user} />
          ))}
        </div>
      )}
    </div>
  );
};

export default UserList;
