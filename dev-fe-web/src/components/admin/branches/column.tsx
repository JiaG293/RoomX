import { ColumnDef } from "@tanstack/react-table";
import {  MoreHorizontal } from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";

export type Branch = {
  branchId: string;
  branchCode: string;
  name: string;
  email: string;
  phoneNumber: string;
  address: string;
};

export const columns: ColumnDef<Branch>[] = [
  {
    id: "stt",
    header: "STT",
    cell: ({ row }) => row.index + 1,
  },
  {
    accessorKey: "branchCode",
    header: "Mã chi nhánh",
  },
  {
    accessorKey: "name",
    header: "Chi nhánh",
  },
  {
    accessorKey: "email",
    header: "Email",
  },
  {
    accessorKey: "phoneNumber",
    header: "Số điện thoại",
  },
  {
    accessorKey: "address",
    header: "Địa chỉ",
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
            to={`/admin/branches/${row.original.branchId}`}
          >
            Xem chi tiết
          </Link>
        </Button>
      );
    },
  },
];
