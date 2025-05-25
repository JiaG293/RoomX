import { ImagePreviewModal } from "@/components/admin/rooms/ImagePreviewModal";
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
  imageUrls: string[];
}

export const columns: ColumnDef<ServiceType>[] = [
  {
    id: "stt",
    header: "STT",
    cell: ({ row }) => row.index + 1,
  },
  {
      accessorKey: "imageUrls",
      header: "Hình ảnh",
      cell: ({ row }) => {
        const imageUrl = row.original.imageUrls?.[0];
        console.log(imageUrl);
        if (!imageUrl) return "";
  
        return (
          <ImagePreviewModal
            imageUrl={imageUrl}
            trigger={
              <img
                src={imageUrl}
                alt="ảnh"
                className="bg-transparent w-16 h-16 object-cover rounded cursor-pointer hover:scale-105 transition"
              />
            }
          />
        );
      },
    },
  {
    accessorKey: "name",
    header: "Tên dịch vụ",
  },
  {
    accessorKey: "serviceCode",
    header: "Mã dịch vụ",
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
            <ServiceDetail id={row.original.id}></ServiceDetail>
          </DialogTrigger>
          <DialogContent className="max-w-3xl">
            <EquipmentDetail id={row.original.id} />
          </DialogContent>
        </Dialog>
      );
    },
  },

];
