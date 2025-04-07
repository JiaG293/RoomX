import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { ColumnDef } from "@tanstack/react-table";
import { MoreHorizontal } from "lucide-react";
import { Link } from "react-router-dom";

export interface ServiceType {
  id: string;
  serviceCode: string;
  name: string;
  description: string;
  note: string;
  unitPrice: number;
  createdAt: string;
  updatedAt: string;
}

export const columns: ColumnDef<ServiceType>[] = [
  {
    id: "stt",
    header: "STT",
    cell: ({ row }) => row.index + 1,
  },
  {
    accessorKey: "serviceCode",
    header: "Mã dịch vụ",
  },
  {
    accessorKey: "name",
    header: "Tên dịch vụ",
  },
  {
    accessorKey: "description",
    header: "Mô tả",
    cell: ({ row }) =>
      row.original.description.length > 50
        ? row.original.description.substring(0, 50) + "..."
        : row.original.description,
  },
  // {
  //   accessorKey: "unitPrice",
  //   header: "Giá dịch vụ",
  //   cell: ({ row }) => `${row.original.unitPrice.toLocaleString()} VND`,
  // },
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
          <Link to={`/admin/services/${row.original.id}`}>Xem chi tiết</Link>
        </Button>
      );
    },
  },
];
