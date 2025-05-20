import { useEffect, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { ServiceService } from "@/services/admin/service.service";

interface Service {
  id: string;
  serviceCode: string;
  name: string;
  description?: string;
  note?: string;
  unitPrice?: number;
  totalPrice?: number | null;
  quantity?: number | null;
  imageUrls: string[];
}

export function ServiceDetail({
  id,
  trigger,
}: {
  id: string;
  trigger?: React.ReactNode;
}) {
  const [service, setService] = useState<Service | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchService = async () => {
      setLoading(true); // Bật loading khi bắt đầu fetch
      try {
        const serviceApi = new ServiceService();
        const data = await serviceApi.getDetailService(id);
        setService(data);
      } catch (error) {
        console.error("Failed to fetch service:", error);
        setService(null);
      } finally {
        setLoading(false); // Tắt loading sau khi fetch xong
      }
    };

    if (id) {
      fetchService();
    }
  }, [id]);

  const defaultTrigger = (
    <button className="px-4 py-2 rounded bg-indigo-600 text-white hover:bg-indigo-700">
      Xem chi tiết dịch vụ
    </button>
  );

  return (
    <Dialog>
      <DialogTrigger asChild>{trigger ?? defaultTrigger}</DialogTrigger>

      <DialogContent className="max-w-xl p-0 overflow-hidden rounded-xl shadow-lg">
        <div className="flex items-center justify-between p-4 border-b border-gray-200 dark:border-gray-700">
          <DialogTitle className="text-lg font-semibold text-gray-900 dark:text-gray-100">
            Thông tin dịch vụ
          </DialogTitle>
        </div>

        {loading ? (
          <div className="p-6 text-center text-gray-600 dark:text-gray-400">
            Đang tải...
          </div>
        ) : !service ? (
          <div className="p-6 text-center text-red-500">
            Không tìm thấy dịch vụ.
          </div>
        ) : (
          <>
            <div
              className="h-32 w-full bg-center bg-cover pointer-events-none"
              style={{
                backgroundImage: `url(${
                  service.imageUrls?.[0] ??
                  "https://via.placeholder.com/800x200?text=No+Image"
                })`,
              }}
            ></div>

            <div className="px-6 pt-4 pb-6 text-gray-800 dark:text-gray-300 space-y-4">
              <dl className="space-y-3">
                {[
                  { label: "Tên dịch vụ", value: service.name },
                  { label: "Mã dịch vụ", value: service.serviceCode },
                  {
                    label: "Mô tả",
                    value: service.description
                      ? service.description.length > 60
                        ? service.description.slice(0, 60) + "…"
                        : service.description
                      : null,
                  },
                  {
                    label: "Đơn giá",
                    value: service.unitPrice
                      ? `${service.unitPrice.toLocaleString()}₫`
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
