import { useEffect, useRef, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { ServiceService } from "@/services/admin/service.service";
import {
  Barcode,
  Info,
  DollarSign,
  Monitor,
  ImagePlus,
  Pencil,
  X,
  Save,
} from "lucide-react";
import { toast } from "sonner";

interface Service {
  id: string;
  name: string;
  serviceCode: string;
  description?: string;
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
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [imageUrl, setImageUrl] = useState<string>("");
  const fileInputRef = useRef<HTMLInputElement>(null);

  const [errors, setErrors] = useState<{
    name?: string;
    serviceCode?: string;
  }>({});

  useEffect(() => {
    const fetchService = async () => {
      try {
        const serviceApi = new ServiceService();
        const data = await serviceApi.getDetailService(id);
        setService(data);
      } catch (error) {
        console.error("Failed to fetch service:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchService();
  }, [id]);

  const validateInputs = () => {
    if (!service) return false;

    const newErrors = {
      name: service.name.trim() ? "" : "Vui lòng nhập tên dịch vụ",
      serviceCode: service.serviceCode.trim()
        ? ""
        : "Vui lòng nhập mã dịch vụ",
    };

    setErrors(newErrors);
    return !Object.values(newErrors).some((e) => e);
  };

  const handleUpdateService = async () => {
    if (!validateInputs() || !service) return;

    try {
      const serviceApi = new ServiceService();
      await serviceApi.updateService(
        id,
        fileInputRef.current?.files?.[0],
        {
          name: service.name,
          description: service.description,
        },
        service.unitPrice
      );

      setIsEditing(false);
      toast.success("Cập nhật dịch vụ thành công");
      setTimeout(() => {
        window.location.reload();
      }, 1000);
    } catch (error) {
      console.error("Lỗi cập nhật dịch vụ:", error);
      toast.error("Lỗi khi cập nhật dịch vụ");
    }
  };

  const defaultTrigger = (
    <button className="px-4 py-2 rounded bg-indigo-600 text-white hover:bg-indigo-700 dark:hover:bg-indigo-500">
      Chi tiết
    </button>
  );

  const inputClass =
    "w-full rounded border px-3 py-2 bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 border-gray-300 dark:border-gray-600 focus:outline-none focus:ring-2 focus:ring-indigo-500";

  const handleImageClick = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    const maxSizeInBytes = 2 * 1024 * 1024;
    if (file.size > maxSizeInBytes) {
      toast.error("Kích thước ảnh vượt quá giới hạn 2MB.");
      return;
    }

    const validTypes = ["image/jpeg", "image/jpg", "image/png"];
    if (!validTypes.includes(file.type)) {
      toast.error("Chỉ chấp nhận ảnh định dạng JPG, JPEG hoặc PNG.");
      return;
    }

    const tempUrl = URL.createObjectURL(file);
    setImageUrl(tempUrl);
  };

  return (
    <Dialog>
      <DialogTrigger asChild>{trigger ?? defaultTrigger}</DialogTrigger>

      <DialogContent className="max-w-2xl rounded-lg shadow-lg p-0 dark:bg-gray-900 flex flex-col max-h-[90vh]">
        <div className="flex items-center justify-between p-4 border-b border-gray-200 dark:border-gray-700 flex-shrink-0">
          <DialogTitle className="text-lg font-semibold text-gray-900 dark:text-gray-100">
            Thông tin dịch vụ
          </DialogTitle>
        </div>

        {loading ? (
          <div className="p-6 text-center text-gray-600 dark:text-gray-400 overflow-auto flex-1">
            Đang tải...
          </div>
        ) : !service ? (
          <div className="p-6 text-center text-red-500 overflow-auto flex-1">
            Không tìm thấy dịch vụ.
          </div>
        ) : (
          <div className="flex-1 overflow-auto">
            <div className="px-6 pt-2 pb-4">
              <label className="flex items-center font-medium gap-2 mb-2 text-gray-700 dark:text-gray-300">
                Hình ảnh dịch vụ
              </label>

              <div
                className="relative w-full max-h-64 cursor-pointer group"
                onClick={handleImageClick}
              >
                <img
                  src={imageUrl || service.imageUrls?.[0] || "/fallback.jpg"}
                  alt="Ảnh dịch vụ"
                  className="w-full max-h-64 object-contain rounded-lg border border-gray-300 dark:border-gray-700"
                />
                {isEditing && (
                  <div className="absolute inset-0 bg-black/30 flex items-center justify-center rounded-lg opacity-0 group-hover:opacity-100 transition">
                    <ImagePlus className="w-10 h-10 text-white" />
                  </div>
                )}
              </div>
              <input
                type="file"
                className="hidden"
                ref={fileInputRef}
                onChange={handleImageChange}
                accept="image/jpeg,image/jpg,image/png"
              />
            </div>

            <div className="px-6 py-6 text-gray-800 dark:text-gray-300 grid grid-cols-1 sm:grid-cols-2 gap-6">
              <div className="space-y-1">
                <label className="flex items-center font-medium gap-2">
                  <Barcode className="text-yellow-500" size={18} />
                  Mã dịch vụ
                </label>
                <input
                  type="text"
                  value={service.serviceCode}
                  disabled={true}
                  className={inputClass}
                  onChange={(e) =>
                    setService({
                      ...service,
                      serviceCode: e.target.value,
                    })
                  }
                />
                {errors.serviceCode && (
                  <p className="text-sm text-red-500 mt-1">
                    {errors.serviceCode}
                  </p>
                )}
              </div>

              <div className="space-y-1">
                <label className="flex items-center font-medium gap-2">
                  <Monitor className="text-indigo-600" size={18} />
                  Tên dịch vụ
                </label>
                <input
                  type="text"
                  value={service.name}
                  disabled={!isEditing}
                  className={inputClass}
                  onChange={(e) =>
                    setService({ ...service, name: e.target.value })
                  }
                />
                {errors.name && (
                  <p className="text-sm text-red-500 mt-1">{errors.name}</p>
                )}
              </div>

              <div className="space-y-1">
                <label className="flex items-center font-medium gap-2">
                  <DollarSign className="text-pink-600" size={18} />
                  Đơn giá/giờ
                </label>
                <input
                  type="number"
                  value={service.unitPrice ?? ""}
                  disabled={!isEditing}
                  min={0}
                  max={5000000}
                  step="0.01"
                  className={inputClass}
                  onChange={(e) => {
                    const value = Math.min(
                      5000000,
                      Math.max(0, Number(e.target.value))
                    );
                    setService({
                      ...service,
                      unitPrice: value,
                    });
                  }}
                />
              </div>

              <div className="sm:col-span-2 space-y-1">
                <label className="flex items-center font-medium gap-2">
                  <Info className="text-gray-700 dark:text-gray-300" size={18} />
                  Mô tả
                </label>
                <textarea
                  value={service.description}
                  disabled={!isEditing}
                  rows={3}
                  className={`${inputClass} resize-none`}
                  onChange={(e) =>
                    setService({
                      ...service,
                      description: e.target.value,
                    })
                  }
                />
              </div>
            </div>
          </div>
        )}

        {/* Footer giữ nguyên */}
        <div className="flex justify-end gap-3 px-6 py-4 border-t border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800 flex-shrink-0">
          {!isEditing ? (
            <button
              onClick={() => setIsEditing(true)}
              className="inline-flex items-center gap-2 px-4 py-2 text-sm bg-indigo-600 text-white rounded hover:bg-indigo-700 dark:hover:bg-indigo-500"
            >
              <Pencil size={16} />
              Chỉnh sửa
            </button>
          ) : (
            <>
              <button
                onClick={() => setIsEditing(false)}
                className="inline-flex items-center gap-2 px-4 py-2 text-sm bg-gray-200 text-gray-800 dark:bg-gray-700 dark:text-gray-200 rounded hover:bg-gray-300 dark:hover:bg-gray-600"
              >
                <X size={16} />
                Huỷ
              </button>
              <button
                onClick={handleUpdateService}
                className="inline-flex items-center gap-2 px-4 py-2 text-sm bg-green-600 text-white rounded hover:bg-green-700 dark:hover:bg-green-500"
              >
                <Save size={16} />
                Lưu
              </button>
            </>
          )}
        </div>
      </DialogContent>
    </Dialog>
  );
}
