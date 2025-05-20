import { useEffect, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { EquipmentService } from "@/services/admin/equipment.service";

interface Equipment {
  id: string;
  name: string;
  brand: string;
  equipmentCode: string;
  description?: string;
  unitPrice?: number;
  totalPrice?: number | null;
  quantity?: number | null;
  imageUrls: string[];
}

export function EquipmentDetail({
  id,
  trigger,
}: {
  id: string;
  trigger?: React.ReactNode;
}) {
  const [equipment, setEquipment] = useState<Equipment | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchEquipment = async () => {
      try {
        const service = new EquipmentService();
        const data = await service.getDetailEquipment(id);
        setEquipment(data);
      } catch (error) {
        console.error("Failed to fetch equipment:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchEquipment();
  }, [id]);

  const defaultTrigger = (
    <button className="px-4 py-2 rounded bg-indigo-600 text-white hover:bg-indigo-700">
      Xem chi tiết thiết bị
    </button>
  );

  return (
    <Dialog>
      <DialogTrigger asChild>{trigger ?? defaultTrigger}</DialogTrigger>

      <DialogContent className="max-w-md p-0 overflow-hidden rounded-lg shadow-lg">
        <div className="flex items-center justify-between p-4 border-b border-gray-200 dark:border-gray-700">
          <DialogTitle className="text-lg font-semibold text-gray-900 dark:text-gray-100">
            Thông tin thiết bị
          </DialogTitle>
        </div>

        {loading ? (
          <div className="p-6 text-center text-gray-600 dark:text-gray-400">
            Đang tải...
          </div>
        ) : !equipment ? (
          <div className="p-6 text-center text-red-500">
            Không tìm thấy thiết bị.
          </div>
        ) : (
          <>
            <div
              className="h-32 w-full bg-center bg-cover pointer-events-none"
              style={{
                backgroundImage: `url(${
                  equipment.imageUrls?.[0] ??
                  "https://via.placeholder.com/800x200?text=No+Image"
                })`,
              }}
            ></div>

            <div className="px-6 pt-4 pb-6 text-gray-800 dark:text-gray-300 space-y-4">
              <dl className="space-y-3">
                {[
                  { label: "Tên thiết bị", value: equipment.name },
                  { label: "Thương hiệu", value: equipment.brand },
                  { label: "Mã thiết bị", value: equipment.equipmentCode },
                  { label: "Mô tả", value: equipment.description },
                  {
                    label: "Đơn giá",
                    value: equipment.unitPrice
                      ? `${equipment.unitPrice.toLocaleString()}₫`
                      : null,
                  },
                ]
                  .filter(
                    (item) => item.value !== null && item.value !== undefined
                  )
                  .map((item, index) => (
                    <div
                      key={index}
                      className="flex justify-between items-center gap-4"
                    >
                      <dt className="font-medium">{item.label}</dt>
                      <dd className="text-right flex-1">{item.value}</dd>
                    </div>
                  ))}
              </dl>
            </div>
          </>
        )}
      </DialogContent>
    </Dialog>
  );
}
