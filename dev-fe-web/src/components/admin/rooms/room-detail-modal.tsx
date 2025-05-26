import { useEffect, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
} from "@/components/ui/dialog";
import { RoomService } from "@/services/admin/room.service";

import {
  MapPin,
  Home,
  Layers,
  Building,
  Users,
  Tag,
  DollarSign,
  Image,
  CheckCircle,
  XCircle,
  Loader2,
  Info,
  Camera,
} from "lucide-react";
import { BranchService } from "@/services/admin/branch.service";

interface RoomDetailModalProps {
  roomId: string | null;
  onClose: () => void;
  open: boolean;
}

interface Place {
  id: string;
  code: string;
  name: string;
  placeType: string;
  children: Place[];
}

export default function RoomDetailModal({
  roomId,
  onClose,
  open,
}: RoomDetailModalProps) {
  const [roomDetail, setRoomDetail] = useState<any>(null);
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState<any>(null);
  const [branches, setBranches] = useState<Place[]>([]);
  const [selectedBranchId, setSelectedBranchId] = useState<string | null>(null);
  const [selectedBuildingId, setSelectedBuildingId] = useState<string | null>(
    null
  );
  const [selectedFloorId, setSelectedFloorId] = useState<string | null>(null);

  // Để lưu các cấp lọc sau khi chọn
  const [buildings, setBuildings] = useState<Place[]>([]);
  const [floors, setFloors] = useState<Place[]>([]);

  // Xử lý thay đổi form input
  const handleChange = (field: string, value: any) => {
    setFormData((prev: any) => ({
      ...prev,
      [field]: value,
    }));
  };

  // Xử lý lưu cập nhật (bạn có thể gọi API ở đây)
  const handleSave = () => {
    // Gọi API update, giả sử thành công thì cập nhật roomDetail
    setRoomDetail(formData);
    setEditMode(false);
  };

  // Hủy chỉnh sửa, reset lại dữ liệu ban đầu
  const handleCancel = () => {
    setFormData(roomDetail);
    setEditMode(false);
  };

  // Lấy tên theo id từ danh sách options (branches, buildings, floors)
  function findPlaceById(id: string, places: Place[]): Place | undefined {
    for (const place of places) {
      if (place.id === id) return place;
      const found = findPlaceById(id, place.children);
      if (found) return found;
    }
    return undefined; // cần có return để chắc chắn hàm luôn trả về giá trị
  }

  // Khi chỉnh sửa branch
  const handleBranchChange = (branchId: string) => {
    const branch = branches.find((b) => b.id === branchId) || null;
    setSelectedBranchId(branchId);
    setBuildings(branch?.children || []);
    setSelectedBuildingId(null);
    setFloors([]);
    setSelectedFloorId(null);

    handleChange("branch", branch);
    handleChange("building", null);
    handleChange("floor", null);
  };

  // Khi chỉnh sửa building
  const handleBuildingChange = (buildingId: string) => {
    const building = buildings.find((b) => b.id === buildingId) || null;
    setSelectedBuildingId(buildingId);
    setFloors(building?.children || []);
    setSelectedFloorId(null);

    handleChange("building", building);
    handleChange("floor", null);
  };

  // Khi chỉnh sửa floor
  const handleFloorChange = (floorId: string) => {
    const floor = floors.find((f) => f.id === floorId) || null;
    setSelectedFloorId(floorId);

    handleChange("floor", floor);
  };

  // 1. Lần đầu load modal hoặc khi modal mở, lấy dữ liệu branches
  useEffect(() => {
    (async () => {
      try {
        const service = new BranchService();
        const data = await service.getAllBranchesWithHierarchy();
        setBranches(data);
      } catch (error) {
        console.error("Failed to load branches:", error);
      }
    })();
  }, []);

  // 2. Khi selectedBranchId hoặc branches thay đổi, cập nhật buildings và reset building, floor chọn
  useEffect(() => {
    if (!selectedBranchId) {
      setBuildings([]);
      setSelectedBuildingId(null);
      setFloors([]);
      setSelectedFloorId(null);
      return;
    }

    const branch = branches.find((b) => b.id === selectedBranchId);
    setBuildings(branch?.children || []);
    setSelectedBuildingId(null);
    setFloors([]);
    setSelectedFloorId(null);
  }, [selectedBranchId, branches]);

  // 3. Khi selectedBuildingId hoặc buildings thay đổi, cập nhật floors và reset floor chọn
  useEffect(() => {
    if (!selectedBuildingId) {
      setFloors([]);
      setSelectedFloorId(null);
      return;
    }

    const building = buildings.find((b) => b.id === selectedBuildingId);
    setFloors(building?.children || []);
    setSelectedFloorId(null);
  }, [selectedBuildingId, buildings]);

  // 4. Khi modal mở và có roomId, lấy chi tiết phòng
  useEffect(() => {
    if (!roomId || !open) return;

    (async () => {
      try {
        const roomService = new RoomService();
        const data = await roomService.getDetailRoom(roomId);
        setRoomDetail(data);
        setFormData(data);
        setEditMode(false);
      } catch (error) {
        console.error("Failed to fetch room details:", error);
      }
    })();
  }, [roomId, open]);

  // 5. Khi roomDetail thay đổi, đồng bộ selectedBranchId, selectedBuildingId, selectedFloorId
  useEffect(() => {
    if (!roomDetail) return;
    setSelectedBranchId(roomDetail.branch?.id || null);
    setSelectedBuildingId(roomDetail.building?.id || null);
    setSelectedFloorId(roomDetail.floor?.id || null);
  }, [roomDetail]);

  return (
    <Dialog open={open} onOpenChange={(val) => !val && onClose()}>
      <DialogContent className="sm:max-w-[600px] dark:bg-gray-900 rounded-lg">
        <DialogHeader>
          <DialogTitle>Chi tiết phòng</DialogTitle>
          <DialogDescription>
            Thông tin chi tiết về phòng đang chọn.
          </DialogDescription>
        </DialogHeader>

        {!roomDetail ? (
          <div className="flex flex-col items-center justify-center py-10 text-gray-500 dark:text-gray-400">
            <Loader2 className="animate-spin mb-2" size={24} />
            Đang tải dữ liệu phòng...
          </div>
        ) : (
          <div className="space-y-4 text-gray-800 dark:text-gray-100">
            {/* Ảnh lớn ở trên với ảnh thay thế */}
            <div className="relative w-full h-48 rounded-lg border border-gray-300 dark:border-gray-700 shadow-sm overflow-hidden group">
              {roomDetail.imageUrls?.[0] ? (
                <img
                  src={roomDetail.imageUrls[0]}
                  alt="room-main"
                  className="w-full h-full object-cover"
                />
              ) : (
                <div className="w-full h-full flex items-center justify-center bg-gray-200 dark:bg-gray-800 text-gray-500 dark:text-gray-400 font-medium">
                  Chưa có ảnh
                </div>
              )}

              {/* Overlay icon */}
              <label className="absolute inset-0 bg-black/30 opacity-0 group-hover:opacity-100 transition flex items-center justify-center cursor-pointer">
                <Camera className="text-white w-8 h-8" />
                <input
                  type="file"
                  accept="image/*"
                  className="hidden"
                  onChange={async (e) => {
                    const file = e.target.files?.[0];
                    if (!file || !roomId) return;

                    try {
                      const roomService = new RoomService();

                      await roomService.updateImageRoom(roomId, file);

                      const updated = await roomService.getDetailRoom(roomId);
                      setRoomDetail(updated);
                      setFormData(updated);
                    } catch (err) {
                      console.error("Upload failed", err);
                    }
                  }}
                />
              </label>
            </div>

            {/* Grid thông tin chi tiết */}
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-x-6 gap-y-3 text-sm">
              <EditableInfoItem
                icon={<MapPin className="text-blue-500 dark:text-blue-400" />}
                label="Chi nhánh"
                value={selectedBranchId || ""} // value là id để select chọn đúng option
                disabled={!editMode}
                isSelect={true}
                options={branches.map((b) => ({ id: b.id, name: b.name }))}
                onChange={handleBranchChange}
                displayValue={formData.branch?.name || ""} // thêm prop hiển thị tên khi không edit
              />

              <EditableInfoItem
                icon={<Building className="text-cyan-500 dark:text-cyan-400" />}
                label="Tòa nhà"
                value={selectedBuildingId || ""}
                disabled={!editMode}
                isSelect={true}
                options={buildings.map((b) => ({ id: b.id, name: b.name }))}
                onChange={handleBuildingChange}
                displayValue={formData.building?.name || ""}
              />

              <EditableInfoItem
                icon={
                  <Layers className="text-purple-500 dark:text-purple-400" />
                }
                label="Lầu"
                value={selectedFloorId || ""}
                disabled={!editMode}
                isSelect={true}
                options={floors.map((f) => ({ id: f.id, name: f.name }))}
                onChange={handleFloorChange}
                displayValue={formData.floor?.name || ""}
              />

              <EditableInfoItem
                icon={getStatusIcon(formData.status)}
                label="Trạng thái"
                value={getRoomStatusLabel(formData.status)}
                disabled={true} // status tạm khóa chỉnh sửa
              />
              <EditableInfoItem
                icon={<Users className="text-pink-500 dark:text-pink-400" />}
                label="Sức chứa"
                value={formData.roomClass?.capacity || ""}
                disabled={!editMode}
                onChange={(val) =>
                  handleChange("roomClass", {
                    ...formData.roomClass,
                    capacity: val,
                  })
                }
              />

              <EditableInfoItem
                icon={<DollarSign className="text-red-500 dark:text-red-400" />}
                label="Giá phòng/giờ"
                value={formData.roomClass?.basePrice || ""}
                disabled={!editMode}
                onChange={(val) =>
                  handleChange("roomClass", {
                    ...formData.roomClass,
                    basePrice: val,
                  })
                }
              />
            </div>
            <EditableInfoItem
              icon={<Info className="text-gray-500 dark:text-gray-400" />}
              label="Mô tả"
              value={formData.description || ""}
              disabled={!editMode}
              onChange={(val) => handleChange("description", val)}
              textarea={true} // Dùng textarea cho mô tả
            />

            {/* Grid ảnh nhỏ */}
            {roomDetail.imageUrls?.length > 1 && (
              <div>
                <div className="flex items-center gap-2 mb-2 text-sm font-medium text-gray-700 dark:text-gray-200">
                  <Image className="w-5 h-5 text-gray-500 dark:text-gray-400" />
                  Hình ảnh khác
                </div>
                <div className="grid grid-cols-2 gap-2">
                  {roomDetail.imageUrls
                    .slice(1)
                    .map((url: string, idx: number) => (
                      <img
                        key={idx}
                        src={url}
                        alt={`room-${idx}`}
                        className="rounded-lg h-24 w-full object-cover border border-gray-300 dark:border-gray-700"
                      />
                    ))}
                </div>
              </div>
            )}

            {/* Nút bật/tắt chỉnh sửa */}
            <div className="mt-4 flex justify-end gap-3">
              {editMode ? (
                <>
                  <button
                    className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
                    onClick={handleCancel}
                  >
                    Hủy
                  </button>
                  <button
                    className="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
                    onClick={handleSave}
                  >
                    Lưu
                  </button>
                </>
              ) : (
                <button
                  className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                  onClick={() => setEditMode(true)}
                >
                  Cập nhật
                </button>
              )}
            </div>
          </div>
        )}
      </DialogContent>
    </Dialog>
  );
}

interface EditableInfoItemProps {
  icon: React.ReactNode;
  label: string;
  value: string | number;
  disabled: boolean;
  onChange?: (value: string) => void;
  valueClassName?: string;
  textarea?: boolean;
  options?: { id: string; name: string }[]; // thêm prop options cho select
  isSelect?: boolean; // đánh dấu nếu là select
}

interface EditableInfoItemProps {
  icon: React.ReactNode;
  label: string;
  value: string | number;
  disabled: boolean;
  onChange?: (value: string) => void;
  valueClassName?: string;
  textarea?: boolean;
  options?: { id: string; name: string }[];
  isSelect?: boolean;
  displayValue?: string; // thêm prop này để hiển thị tên khi disabled
}

function EditableInfoItem({
  icon,
  label,
  value,
  disabled,
  onChange,
  valueClassName,
  textarea = false,
  options = [],
  isSelect = false,
  displayValue,
}: EditableInfoItemProps) {
  return (
    <div className="flex items-center gap-2">
      <div className="flex-shrink-0">{icon}</div>
      <div className="flex-grow text-sm">
        <label className="font-semibold mr-1">{label}:</label>
        {textarea ? (
          <textarea
            className={`w-full rounded border border-gray-300 dark:border-gray-700 px-2 py-1 text-gray-900 dark:text-gray-100 bg-white dark:bg-gray-900 resize-y ${
              disabled ? "cursor-not-allowed opacity-60" : "cursor-text"
            } ${valueClassName || ""}`}
            value={value}
            disabled={disabled}
            rows={4}
            onChange={(e) => onChange && onChange(e.target.value)}
          />
        ) : isSelect ? (
          disabled ? (
            // Khi disable, hiển thị tên (displayValue) thay vì select
            <div
              className={`px-2 py-1 border border-transparent ${
                valueClassName || ""
              }`}
            >
              {displayValue || "-"}
            </div>
          ) : (
            <select
              className={`w-full rounded border border-gray-300 dark:border-gray-700 px-2 py-1 text-gray-900 dark:text-gray-100 bg-white dark:bg-gray-900 ${
                valueClassName || ""
              }`}
              value={value}
              onChange={(e) => onChange && onChange(e.target.value)}
            >
              <option value="">-- Chọn --</option>
              {options.map((opt) => (
                <option key={opt.id} value={opt.id}>
                  {opt.name}
                </option>
              ))}
            </select>
          )
        ) : (
          <input
            type="text"
            className={`w-full rounded border border-gray-300 dark:border-gray-700 px-2 py-1 text-gray-900 dark:text-gray-100 bg-white dark:bg-gray-900 ${
              disabled ? "cursor-not-allowed opacity-60" : ""
            } ${valueClassName || ""}`}
            value={value}
            disabled={disabled}
            onChange={(e) => onChange && onChange(e.target.value)}
          />
        )}
      </div>
    </div>
  );
}

function getRoomStatusLabel(status: string): string {
  switch (status) {
    case "AVAILABLE":
      return "Còn trống";
    case "RESERVED":
      return "Đặt trước";
    case "BLOCKED":
      return "Bảo trì";
    case "CLEANED":
      return "Vệ sinh";
    default:
      return "Không rõ";
  }
}

function getStatusIcon(status: string) {
  switch (status) {
    case "AVAILABLE":
      return (
        <CheckCircle className="text-green-500 dark:text-green-400 w-5 h-5" />
      );
    case "RESERVED":
      return <Tag className="text-yellow-500 dark:text-yellow-400 w-5 h-5" />;
    case "BLOCKED":
      return <XCircle className="text-red-500 dark:text-red-400 w-5 h-5" />;
    case "CLEANED":
      return (
        <Loader2 className="animate-spin text-blue-500 dark:text-blue-400 w-5 h-5" />
      );
    default:
      return <Tag className="text-gray-400 w-5 h-5" />;
  }
}
