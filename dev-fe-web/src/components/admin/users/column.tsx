import { AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { User } from "@/types/UserType";
import { getShortName } from "@/utils/string.util";
import { Avatar } from "@radix-ui/react-avatar";
import { ColumnDef } from "@tanstack/react-table";
import { Link } from "react-router-dom";

export const columns: ColumnDef<User>[] = [
  {
    id: "stt",
    header: "STT",
    cell: ({ row }) => row.index + 1,
  },
  {
    id: "avatar",
    header: "Ảnh",
    cell: ({ row }) => {
      return (
        <div className="flex items-center w-full">
          <Avatar className="w-8 h-8 border border-gray-300 rounded-full">
            <AvatarImage
              className="w-8 h-8 rounded-full cursor-pointer"
              src={row.original.avatarImage || ""}
            />
            <AvatarFallback>
              {getShortName(row.original.lastName || "")}
            </AvatarFallback>
          </Avatar>
        </div>
      );
    },
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


  // {
  //   accessorKey: "userType",
  //   header: "Loại người dùng",
  // },
  {
    id: "actions",
    cell: ({ row }) => {
      return (
        <Button
          asChild
          className="px-3 py-2 bg-transparent text-[var(--view-button-text)] hover:bg-[var(--view-button-bg-hover)] hover:shadow-lg transform transition-transform duration-200 ease-in-out rounded-[var(--view-button-border-radius)] shadow-[var(--view-button-box-shadow)]"
        >
          <Link
            to={`/admin/users/${row.original.id}`}
            className="w-full h-full flex justify-center items-center text-[var(--view-button-text)] font-medium text-xs"
          >
            Xem chi tiết
          </Link>
        </Button>
      );
    },
  },
];
