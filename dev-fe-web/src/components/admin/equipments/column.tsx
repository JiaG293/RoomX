import { ImagePreviewModal } from "@/components/admin/rooms/ImagePreviewModal";
import { EquipmentDetail } from "@/pages/Admin/Equipment/EquipmentDetail";
import { Dialog, DialogContent, DialogTrigger } from "@radix-ui/react-dialog";
import { ColumnDef } from "@tanstack/react-table";
import { useState } from "react";

export interface EquipmentType {
  id: string;
  equipmentCode: string;
  name: string;
  brand: string;
  description: string;
  imageUrls: string[];
  price: number;
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
    header: "Tên thiết bị",
  },
  {
    accessorKey: "brand",
    header: "Thương hiệu",
  },
  {
    accessorKey: "equipmentCode",
    header: "Mã thiết bị",
  },
  {
    accessorKey: "description",
    header: "Mô tả",
    cell: ({ row }) => (
      <div className="max-w-[200px] truncate text-ellipsis whitespace-nowrap">
        {row.original.description}
      </div>
    ),
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const [open, setOpen] = useState(false);
      return (
        <Dialog open={open} onOpenChange={setOpen}>
          <DialogTrigger asChild>
            <EquipmentDetail id={row.original.id}></EquipmentDetail>
          </DialogTrigger>
          <DialogContent className="max-w-3xl">
            <EquipmentDetail id={row.original.id} />
          </DialogContent>
        </Dialog>
      );
    },
  },
];