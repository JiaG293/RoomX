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

export interface EquipmentType {
  id: string;
  equipmentCode: string;
  name: string;
  brand: string;
  description: string;
  unitPrice: number;
  createdAt: string;
  updatedAt: string;
}

export const columns: ColumnDef<EquipmentType>[] = [
  {
    id: "stt",
    header: "STT",
    cell: ({ row }) => row.index + 1,
  },
  {
    accessorKey: "equipmentCode",
    header: "Mã thiết bị",
  },
  {
    accessorKey: "name",
    header: "Tên thiết bị",
  },
  {
    accessorKey: "brand",
    header: "Thương hiệu",
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
  //   header: "Giá thiết bị",
  //   cell: ({ row }) => `${row?.original?.unitPrice?.toLocaleString()} VND`,
  // },
  // {
  //   id: "actions",
  //   cell: ({ row }) => {
  //     return (
  //       <DropdownMenu>
  //         <DropdownMenuTrigger asChild>
  //           <Button variant="ghost" className="h-8 w-8 p-0 bg-transparent">
  //             <MoreHorizontal className="h-4 w-4" />
  //           </Button>
  //         </DropdownMenuTrigger>
  //         <DropdownMenuContent align="end">
  //           <DropdownMenuLabel>Hành động</DropdownMenuLabel>
  //           <DropdownMenuItem>
  //             <Link to={`/admin/equipments/${row.original.id}`}>
  //               Xem chi tiết
  //             </Link>
  //           </DropdownMenuItem>
  //           <DropdownMenuItem
  //             onClick={(e) => {
  //               e.stopPropagation();
  //               e.preventDefault();
  //               alert(`Chỉnh sửa thiết bị: ${row.original.name}`);
  //             }}
  //           >
  //             Cập nhật
  //           </DropdownMenuItem>
  //         </DropdownMenuContent>
  //       </DropdownMenu>
  //     );
  //   },
  // },
];
