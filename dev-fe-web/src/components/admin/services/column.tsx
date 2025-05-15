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
  {
    id: "actions",
    cell: ({ row }) => {
      return (
        <Button
          asChild
          className="px-3 py-2 bg-transparent text-[var(--view-button-text)] hover:bg-[var(--view-button-bg-hover)] hover:shadow-lg transform transition-transform duration-200 ease-in-out rounded-[var(--view-button-border-radius)] shadow-[var(--view-button-box-shadow)]"
        >
          <Link to={`/admin/services/${row.original.id}`}>Xem chi tiết</Link>
        </Button>
      );
    },
  },
];
