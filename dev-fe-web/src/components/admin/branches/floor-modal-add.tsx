"use client";

import { useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Check, X } from "lucide-react";

interface AddFloorModalProps {
  open: boolean;
  onClose: () => void;
  buildingId: string | null;
  onSubmit: (floorData: { code: string; buildingId: string }) => void;
}

const AddFloorModal: React.FC<AddFloorModalProps> = ({
  open,
  onClose,
  buildingId,
  onSubmit,
}) => {
  const [code, setCode] = useState("");

  const handleConfirm = () => {
    if (code.trim() && buildingId) {
      onSubmit({ code, buildingId });
      setCode("");
    }
  };

  const handleCancel = () => {
    setCode("");
    onClose();
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="bg-white dark:bg-zinc-900 rounded-2xl shadow-xl border border-zinc-200 dark:border-zinc-700 p-6">
        <DialogHeader>
          <DialogTitle className="text-lg font-semibold text-zinc-800 dark:text-zinc-100">
            Thêm tầng
          </DialogTitle>
        </DialogHeader>
        <Input
          placeholder="Tên tầng"
          value={code}
          onChange={(e) => setCode(e.target.value)}
          className="mb-4 text-sm placeholder:text-zinc-400 dark:placeholder:text-zinc-500 focus-visible:ring-2 focus-visible:ring-primary transition"
        />
        <div className="flex justify-end gap-2">
          <Button
            variant="outline"
            onClick={handleCancel}
            className="text-zinc-700 dark:text-zinc-200 border-zinc-300 dark:border-zinc-600 hover:bg-zinc-100 dark:hover:bg-zinc-800"
          >
            <X className="w-4 h-4 mr-1" />
            Huỷ
          </Button>
          <Button
            onClick={handleConfirm}
            disabled={!buildingId || !code.trim()}
            className="bg-blue-600 text-white hover:bg-primary/90 disabled:opacity-50"
          >
            <Check className="w-4 h-4 mr-1" />
            Xác nhận
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default AddFloorModal;
