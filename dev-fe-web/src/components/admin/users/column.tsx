import { Button } from "@/components/ui/button";
import { User } from "@/types/UserType";
import { ColumnDef } from "@tanstack/react-table";
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
      return (
        <Button
          asChild
          className="px-3 py-2 bg-[var(--view-button-bg)] text-[var(--view-button-text)] hover:bg-[var(--view-button-bg-hover)] hover:shadow-lg transform transition-transform duration-200 ease-in-out rounded-[var(--view-button-border-radius)] shadow-[var(--view-button-box-shadow)]"
        >
          <Link
            to={`/admin/users/${row.original.userId}`}
            className="w-full h-full flex justify-center items-center text-[var(--view-button-text)] font-medium text-xs"
          >
            Xem chi tiết
          </Link>
        </Button>
      );
    },
  },
];
