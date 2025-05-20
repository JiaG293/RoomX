import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { EquipmentDetail } from "@/pages/Admin/Equipment/EquipmentDetail";
import { Dialog, DialogContent, DialogTrigger } from "@radix-ui/react-dialog";
import { ColumnDef } from "@tanstack/react-table";
import { MoreHorizontal } from "lucide-react";
import { useState } from "react";
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
  {
    id: "actions",
    cell: ({ row }) => {
      const [open, setOpen] = useState(false);
      return (
        <Dialog open={open} onOpenChange={setOpen}>
          <DialogTrigger asChild>
            <div >
              <EquipmentDetail  id={row.original.id} />{" "}
            </div>
          </DialogTrigger>
          <DialogContent className="max-w-md p-0 overflow-hidden rounded-lg shadow-lg"></DialogContent>
        </Dialog>
      );
    },
  },
];
