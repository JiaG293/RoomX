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
import { useTranslation } from "react-i18next";

const UserList: React.FC = () => {
  const { t } = useTranslation();
  const [viewMode, setViewMode] = useState<"table" | "card">("table");
  const [searchTerm, setSearchTerm] = useState("");
  const [filterType, setFilterType] = useState("all");
  const [loading, setLoading] = useState(true);
  const [users, setUsers] = useState<User[]>([]);
  const [pageIndex, setPageIndex] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  console.log(loading);
  // Hàm fetch dữ liệu người dùng
  const fetchUsers = async (keyword: string = "") => {
    setLoading(true);
    try {
      const userService = new UserService();
      const data = await userService.getListUsers(pageIndex, 10, keyword);
      setUsers(data.content || []);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error("Lỗi lấy dữ liệu người dùng.", error);
    }
    setLoading(false);
  };

  // Debounce tìm kiếm
  useEffect(() => {
    const delayDebounce = setTimeout(() => {
      fetchUsers(searchTerm);
    }, 500);

    return () => clearTimeout(delayDebounce);
  }, [searchTerm, pageIndex]);

  // Hàm gọi khi thêm người dùng thành công
  const onAddSuccess = async () => {
    // Kiểm tra xem người dùng có phải là người dùng cuối cùng của trang không
    if (users.length >= 10) {
      setPageIndex(pageIndex + 1);
    } else {
      await fetchUsers();
    }
  };

  // rerender khi fetch dữ liệu người dùng
  useEffect(() => {
    fetchUsers();
  }, [pageIndex]);

  // Reset pageIndex khi tìm kiếm
  useEffect(() => {
    setPageIndex(0);
  }, [searchTerm]);

  return (
    <div className="flex-1">
      {/* Thanh tìm kiếm, bộ lọc và nút Thêm */}
      <div className="mb-4 flex flex-col md:flex-row gap-4 items-center">
        <Input
          placeholder={t("common.search")}
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full md:w-1/3"
        />
        {/* <Select
          value={filterType}
          onValueChange={setFilterType}
          defaultValue="all"
        >
          <SelectTrigger className="w-full md:w-1/4">
            <SelectValue placeholder="Filter by role" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="active">{t("admin.users.active")}</SelectItem>
            <SelectItem value="inactive">
              {t("admin.users.inactive")}
            </SelectItem>
          </SelectContent>
        </Select> */}
        <ToggleGroup
          type="single"
          value={viewMode}
          onValueChange={(value) =>
            value && setViewMode(value as "table" | "card")
          }
        >
          <ToggleGroupItem value="table">
            {t("common.view.table")}
          </ToggleGroupItem>
          <ToggleGroupItem value="card">{t("common.view.card")}</ToggleGroupItem>
        </ToggleGroup>

        {/* Nút Thêm User */}
        <UserAddModal onAddSuccess={onAddSuccess} />
      </div>

      {viewMode === "table" ? (
        <div className="flex-1 min-h-[80vh] ">
          <DataTable
            columns={columns}
            data={users}
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
          {users.map((user) => (
            <UserItem key={user.id} user={user} />
          ))}
        </div>
      )}
    </div>
  );
};

export default UserList;
