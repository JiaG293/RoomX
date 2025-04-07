import DisableUserDialog from "@/components/admin/users/user-disable";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { User } from "@/types/UserType";
import { ColumnDef } from "@tanstack/react-table";
import { MoreHorizontal } from "lucide-react"; // Import icon từ lucide-react
import { Link } from "react-router-dom";

export const columns: ColumnDef<User>[] = [
  {
    id: "stt",
    header: "STT",
    cell: ({ row }) => row.index + 1,
  },
  {
    accessorKey: "userCode",
    header: "Mã nhân viên",
  },
  {
    accessorKey: "email",
    header: "Email",
  },
  {
    accessorKey: "firstName",
    header: "Họ",
    cell: ({ row }) => row.original.firstName || "N/A",
  },
  {
    accessorKey: "lastName",
    header: "Tên",
    cell: ({ row }) => row.original.lastName || "N/A",
  },
  {
    accessorKey: "phoneNumber",
    header: "Số điện thoại",
    cell: ({ row }) => row.original.phoneNumber || "N/A",
  },
  {
    accessorKey: "userType",
    header: "Loại người dùng",
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const isEven = row.index % 2 === 0;
      return (
        <Button
          asChild
          variant={isEven ? "secondary" : "outline"}
          className={`px-4 py-2 text-sm font-medium ${
            isEven
              ? "bg-gray-100 hover:bg-gray-200"
              : "bg-blue-100 hover:bg-blue-200"
          }`}
        >
          <Link
            to={`/admin/users/${row.original.userId}`}
          >
            Xem chi tiết
          </Link>
        </Button>
      );
    },
  },
];
