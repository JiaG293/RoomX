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

export interface RoomType {
  id: string;
  roomCode: string;
  imageUrls: string[] | null;
  description: string;
  status: string;
  floorPlaceId: string;
  buildingPlaceId: string | null;
  branchPlaceId: string;
  roomClassId: string;
  roomClassCode: string;
  capacity: number;
  equipments: any[];
  services: any[];
}

export const columns: ColumnDef<RoomType>[] = [
  {
    id: "stt",
    header: "STT",
    cell: ({ row }) => row.index + 1,
  },
  {
    accessorKey: "roomCode",
    header: "Mã phòng",
  },
  {
    accessorKey: "roomClassCode",
    header: "Loại phòng",
  },
  {
    accessorKey: "capacity",
    header: "Sức chứa",
    cell: ({ row }) => `${row.original.capacity} người`,
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
    accessorKey: "status",
    header: "Trạng thái",
  },
  {
    id: "actions",
    header: "Thao tác",
    cell: ({ row }) => {
      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="h-8 w-8 p-0 bg-transparent">
              <MoreHorizontal className="h-4 w-4" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Hành động</DropdownMenuLabel>
            <DropdownMenuItem>
              <Link to={`/admin/rooms/${row.original.id}`}>Xem chi tiết</Link>
            </DropdownMenuItem>
            <DropdownMenuItem
              onClick={(e) => {
                e.stopPropagation();
                e.preventDefault();
                alert(`Chỉnh sửa phòng: ${row.original.roomCode}`);
              }}
            >
              Cập nhật
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      );
    },
  },
];
