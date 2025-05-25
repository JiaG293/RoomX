import { useEffect, useRef, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { EquipmentService } from "@/services/admin/equipment.service";
import {
  Barcode,
  Tag,
  Info,
  DollarSign,
  Pencil,
  X,
  Save,
  Monitor,
  ImagePlus,
} from "lucide-react";
import { toast } from "sonner";

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
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [imageUrl, setImageUrl] = useState<string>(""); // để hiển thị ảnh tạm thời
  const fileInputRef = useRef<HTMLInputElement>(null);

  const [errors, setErrors] = useState<{
    name?: string;
    brand?: string;
    equipmentCode?: string;
  }>({});

  useEffect(() => {
    const fetchEquipment = async () => {
      try {
        const service = new EquipmentService();
        const data = await service.getDetailEquipment(id);
        setEquipment(data);
        console.log(data);
      } catch (error) {
        console.error("Failed to fetch equipment:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchEquipment();
  }, [id]);

  const validateInputs = () => {
    if (!equipment) return false;

    const newErrors = {
      name: equipment.name.trim() ? "" : "Vui lòng nhập tên thiết bị",
      brand: equipment.brand.trim() ? "" : "Vui lòng nhập thương hiệu",
      equipmentCode: equipment.equipmentCode.trim()
        ? ""
        : "Vui lòng nhập mã thiết bị",
    };

    setErrors(newErrors);
    return !Object.values(newErrors).some((e) => e);
  };

  const handleUpdateEquipment = async () => {
    if (!validateInputs() || !equipment) return;
    console.log(equipment.description)
    try {
      const service = new EquipmentService();
      console.log(fileInputRef.current?.files);
      await service.updateEquipment(
        id,
        fileInputRef.current?.files?.[0],
        {
          name: equipment.name,
          brand: equipment.brand,
          description: equipment.description,
        },
        equipment.unitPrice
      );

      setIsEditing(false);
      toast.success("Cập nhật thiết bị thành công");
      setTimeout(() => {
        window.location.reload();
      }, 1000);
    } catch (error) {
      console.error("Lỗi cập nhật thiết bị:", error);
      toast.error("Lỗi khi cập nhật thiết bị");
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

    // Kiểm tra dung lượng file (tối đa 2MB)
    const maxSizeInBytes = 2 * 1024 * 1024; // 2MB
    if (file.size > maxSizeInBytes) {
      toast.error("Kích thước ảnh vượt quá giới hạn 2MB.");
      return;
    }

    // Kiểm tra định dạng hợp lệ
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
        {/* Header cố định */}
        <div className="flex items-center justify-between p-4 border-b border-gray-200 dark:border-gray-700 flex-shrink-0">
          <DialogTitle className="text-lg font-semibold text-gray-900 dark:text-gray-100">
            Thông tin thiết bị
          </DialogTitle>
        </div>

        {/* Body cuộn */}
        {loading ? (
          <div className="p-6 text-center text-gray-600 dark:text-gray-400 overflow-auto flex-1">
            Đang tải...
          </div>
        ) : !equipment ? (
          <div className="p-6 text-center text-red-500 overflow-auto flex-1">
            Không tìm thấy thiết bị.
          </div>
        ) : (
          <div className="flex-1 overflow-auto">
            <div className="px-6 pt-2 pb-4">
              <label className="flex items-center font-medium gap-2 mb-2 text-gray-700 dark:text-gray-300">
                Hình ảnh thiết bị
              </label>

              <div
                className="relative w-full max-h-64 cursor-pointer group"
                onClick={handleImageClick}
              >
                <img
                  src={imageUrl || equipment.imageUrls?.[0] || "/fallback.jpg"}
                  alt="Ảnh thiết bị"
                  className="w-full max-h-64 object-contain rounded-lg border border-gray-300 dark:border-gray-700"
                />

                {/* Hover icon */}
                {isEditing && (
                  <div className="absolute inset-0 bg-black/30 flex items-center justify-center rounded-lg opacity-0 group-hover:opacity-100 transition">
                    <ImagePlus className="w-10 h-10 text-white" />
                  </div>
                )}
              </div>

              {/* Hidden file input */}
              <input
                type="file"
                className="hidden"
                ref={fileInputRef}
                onChange={handleImageChange}
                accept="image/jpeg,image/jpg,image/png"
              />
            </div>

            <div className="px-6 py-6 text-gray-800 dark:text-gray-300 grid grid-cols-1 sm:grid-cols-2 gap-6">
              {/* Mã thiết bị */}
              <div className="space-y-1">
                <label className="flex items-center font-medium gap-2">
                  <Barcode className="text-yellow-500" size={18} />
                  Mã thiết bị
                </label>
                <input
                  type="text"
                  value={equipment.equipmentCode}
                  disabled={true}
                  className={inputClass}
                  onChange={(e) =>
                    setEquipment({
                      ...equipment,
                      equipmentCode: e.target.value,
                    })
                  }
                />
                {errors.equipmentCode && (
                  <p className="text-sm text-red-500 mt-1">
                    {errors.equipmentCode}
                  </p>
                )}
              </div>

              {/* Tên thiết bị */}
              <div className="space-y-1">
                <label className="flex items-center font-medium gap-2">
                  <Monitor className="text-indigo-600" size={18} />
                  Tên thiết bị
                </label>
                <input
                  type="text"
                  value={equipment.name}
                  disabled={!isEditing}
                  className={inputClass}
                  onChange={(e) =>
                    setEquipment({ ...equipment, name: e.target.value })
                  }
                />
                {errors.name && (
                  <p className="text-sm text-red-500 mt-1">{errors.name}</p>
                )}
              </div>

              {/* Đơn giá/giờ */}
              <div className="space-y-1">
                <label className="flex items-center font-medium gap-2">
                  <DollarSign className="text-pink-600" size={18} />
                  Đơn giá/giờ
                </label>
                <input
                  type="number"
                  value={equipment.unitPrice ?? ""}
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
                    setEquipment({
                      ...equipment,
                      unitPrice: value,
                    });
                  }}
                />
              </div>

              {/* Thương hiệu */}
              <div className="space-y-1">
                <label className="flex items-center font-medium gap-2">
                  <Tag className="text-emerald-600" size={18} />
                  Thương hiệu
                </label>
                <input
                  type="text"
                  value={equipment.brand}
                  disabled={!isEditing}
                  className={inputClass}
                  onChange={(e) =>
                    setEquipment({ ...equipment, brand: e.target.value })
                  }
                />
                {errors.brand && (
                  <p className="text-sm text-red-500 mt-1">{errors.brand}</p>
                )}
              </div>

              {/* Mô tả */}
              <div className="sm:col-span-2 space-y-1">
                <label className="flex items-center font-medium gap-2">
                  <Info
                    className="text-gray-700 dark:text-gray-300"
                    size={18}
                  />
                  Mô tả
                </label>
                <textarea
                  value={equipment.description}
                  disabled={!isEditing}
                  rows={3}
                  className={`${inputClass} resize-none`}
                  onChange={(e) =>
                    setEquipment({
                      ...equipment,
                      description: e.target.value,
                    })
                  }
                />
              </div>
            </div>
          </div>
        )}

        {/* Footer cố định */}
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
                onClick={handleUpdateEquipment}
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
