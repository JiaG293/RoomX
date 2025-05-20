import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { EquipmentDetail } from "@/pages/Admin/Equipment/EquipmentDetail";
import { ServiceDetail } from "@/pages/Admin/Service/ServiceDetail";
import { Dialog, DialogContent, DialogTrigger } from "@radix-ui/react-dialog";
import { ColumnDef } from "@tanstack/react-table";
import { MoreHorizontal } from "lucide-react";
import { useState } from "react";
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
    const [open, setOpen] = useState(false);
    return (
      <Dialog open={open} onOpenChange={setOpen}>
        <DialogTrigger asChild>
          <div>
            <ServiceDetail id={row.original.id} />
          </div>
        </DialogTrigger>
        <DialogContent className="max-w-md p-0 overflow-hidden rounded-lg shadow-lg"></DialogContent>
      </Dialog>
    );
  },
}

];
