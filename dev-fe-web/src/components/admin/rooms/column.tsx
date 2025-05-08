import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { BranchService } from "@/services/admin/branch.service";
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
  totalPrice: number;
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
  // {
  //   accessorKey: "roomClassCode",
  //   header: "Loại phòng",
  // },
  {
    accessorKey: "capacity",
    header: "Sức chứa",
    cell: ({ row }) => `${row.original.capacity} người`,
  },
  {
    accessorKey: "totalPrice",
    header: "Giá hiện tại",
    cell: ({ row }) => `${row.original.totalPrice.toLocaleString()} VNĐ`,
  },

  // {
  //   accessorKey: "status",
  //   header: "Trạng thái",
  // },
  {
    id: "actions",
    header: "Thao tác",
    cell: ({ row }) => {
      return (
        <Button
          asChild
          className="px-3 py-2 bg-transparent text-[var(--view-button-text)] hover:bg-[var(--view-button-bg-hover)] hover:shadow-lg transform transition-transform duration-200 ease-in-out rounded-[var(--view-button-border-radius)] shadow-[var(--view-button-box-shadow)]"
        >
          <Link to={`/admin/rooms/${row.original.id}`}
          
          >Xem chi tiết</Link>
        </Button>
      );
    },
  },
];
