import { useState, useEffect } from "react";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogTitle,
  DialogDescription,
  DialogClose,
} from "@/components/ui/dialog";
import { RoomService } from "@/services/admin/room.service";
import { BranchService } from "@/services/admin/branch.service";
import { toast } from "sonner";
import { RoomValidator } from "@/validators/room.validators";

export interface Room {
  roomCode: string;
  placeId: string;
  roomClassId: string;
  description: string;
  status: string;
  buildingId?: string; // Thêm buildingId vào Room interface
  floorId?: string;
}

interface Branch {
  id: string;
  name: string;
}

interface Building {
  id: string;
  name: string;
}

interface RoomAddModalProps {
  onAddSuccess: () => void;
}

const RoomAddModal: React.FC<RoomAddModalProps> = ({ onAddSuccess }) => {
  const [roomCode, setRoomCode] = useState("");
  const [placeId, setPlaceId] = useState("");
  const [roomClassId, setRoomClassId] = useState("");
  const [description, setDescription] = useState("");
  const [status, setStatus] = useState("AVAILABLE");
  const [branches, setBranches] = useState<Branch[]>([]);
  const [buildings, setBuildings] = useState<Building[]>([]);
  const [buildingId, setBuildingId] = useState("");
  const [floors, setFloors] = useState<{ id: string; name: string }[]>([]);
  const [floorId, setFloorId] = useState("");
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const roomService = new RoomService();
  const branchService = new BranchService();

  useEffect(() => {
    const fetchBranches = async () => {
      try {
        const branchList = await branchService.getAllBranches();
        setBranches(branchList);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách chi nhánh:", error);
        toast.error("Không thể tải danh sách chi nhánh.");
      }
    };

    fetchBranches();
  }, []);

  // Gọi API lấy danh sách tòa nhà khi chọn chi nhánh
  useEffect(() => {
    const fetchBuildings = async () => {
      if (!placeId) {
        setBuildings([]);
        setBuildingId("");
        return;
      }

      try {
        const response = await roomService.getBuildingByBranch(placeId);
        console.log("API response:", response);

        if (Array.isArray(response)) {
          setBuildings(
            response.map((name, index) => ({ id: String(index), name }))
          );
        } else {
          console.error("Dữ liệu không đúng định dạng mong đợi:", response);
          setBuildings([]);
        }
      } catch (error) {
        console.error("Lỗi khi lấy danh sách tòa nhà:", error);
        toast.error("Không thể tải danh sách tòa nhà.");
      }
    };

    fetchBuildings();
  }, [placeId]);

  useEffect(() => {
    const fetchFloors = async () => {
        if (!placeId || !buildingId) {
          setFloors([]);
          setFloorId("");
          return;
        }
      
        try {
          const buildingName = buildings.find(b => b.id === buildingId)?.name; // Lấy name thay vì id
      
          if (!buildingName) {
            console.error("Không tìm thấy tên tòa nhà phù hợp.");
            return;
          }
      
          const response = await roomService.getFloorByBranchAndBuilding(placeId, buildingName); // Gửi name thay vì id
          if (Array.isArray(response)) {
            setFloors(response.map((floor) => ({ id: floor, name: `Tầng ${floor}` })));
          } else {
            console.error("Dữ liệu không đúng định dạng mong đợi:", response);
            setFloors([]);
          }
        } catch (error) {
          console.error("Lỗi khi lấy danh sách tầng:", error);
          toast.error("Không thể tải danh sách tầng.");
        }
      };
      
  
    fetchFloors();
  }, [placeId, buildingId]);
  

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

     // Kiểm tra validation
     if (!RoomValidator.isNotEmpty(roomCode)) {
      toast.error("Mã phòng không được để trống!");
      return;
    }
    // if (!RoomValidator.isNotEmpty(placeId)) {
    //   toast.error("Chưa chọn địa điểm!");
    //   return;
    // }
    if (!RoomValidator.isNotEmpty(roomClassId)) {
      toast.error("Mã loại phòng không được để trống!");
      return;
    }
    if (!RoomValidator.isNotEmpty(description)) {
      toast.error("Mô tả không được để trống!");
      return;
    }
    if (!RoomValidator.isNotEmpty(status)) {
      toast.error("Trạng thái không được để trống!");
      return;
    }
    // if (!RoomValidator.isNotEmpty(buildingId)) {
    //   toast.error("Mã tòa nhà không được để trống!");
    //   return;
    // }

    // if (!roomCode || !placeId || !roomClassId || !description || !status) {
    //   toast.error("Vui lòng điền đầy đủ thông tin!");
    //   return;
    // }

    const newRoom: Room = {
      roomCode,
      placeId,
      roomClassId,
      description,
      status,
      buildingId, // Thêm buildingId vào dữ liệu gửi đi
    };

    try {
      await roomService.createRoom(newRoom);
      toast.success("Phòng đã được thêm thành công!", {
        description: <strong>{roomCode} đã được tạo.</strong>,
      });

      setIsDialogOpen(false);
      setRoomCode("");
      setPlaceId("");
      setRoomClassId("");
      setDescription("");
      setStatus("AVAILABLE");
      setBuildingId("");
      onAddSuccess();
    } catch (error) {
      console.error("Lỗi khi thêm phòng:", error);
      toast.error("Đã có lỗi xảy ra khi thêm phòng.");
    }
  };

  return (
    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
      <DialogTrigger asChild>
        <button className="py-2 px-4 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors">
          Thêm phòng
        </button>
      </DialogTrigger>

      <DialogContent className="p-6 bg-white rounded-lg shadow-lg max-w-lg mx-auto">
        <DialogTitle className="text-xl font-semibold mb-4">
          Thêm phòng mới
        </DialogTitle>
        <DialogDescription className="text-sm mb-6">
          Nhập thông tin chi tiết của phòng
        </DialogDescription>

        <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">
          <div className="col-span-1">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Mã phòng
            </label>
            <input
              type="text"
              value={roomCode}
              onChange={(e) => setRoomCode(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
              placeholder="Nhập mã phòng"
            />
          </div>

          {/* Combobox Chọn Chi Nhánh */}
          <div className="col-span-1">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Chi nhánh
            </label>
            <select
              value={placeId}
              onChange={(e) => setPlaceId(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
            >
              <option value="">Chọn chi nhánh</option>
              {branches.map((branch) => (
                <option key={branch.id} value={branch.id}>
                  {branch.name}
                </option>
              ))}
            </select>
          </div>

          {/* Hiển thị Combobox tòa nhà khi đã chọn chi nhánh */}
          {buildings.length > 0 && (
            <div className="col-span-1">
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Tòa nhà
              </label>
              <select
                value={buildingId}
                onChange={(e) => setBuildingId(e.target.value)}
                className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
              >
                <option value="">Chọn tòa nhà</option>
                {buildings.map((building) => (
                  <option key={building.id} value={building.id}>
                    {building.name}
                  </option>
                ))}
              </select>
            </div>
          )}

          {/* Hiển thị Combobox tầng khi đã chọn chi nhánh và tòa nhà */}
          {floors.length > 0 && (
            <div className="col-span-1">
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Tầng
              </label>
              <select
                value={floorId}
                onChange={(e) => setFloorId(e.target.value)}
                className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
              >
                <option value="">Chọn tầng</option>
                {floors.map((floor) => (
                  <option key={floor.id} value={floor.id}>
                    {floor.name}
                  </option>
                ))}
              </select>
            </div>
          )}

          <div className="col-span-2">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Mã loại phòng
            </label>
            <input
              type="text"
              value={roomClassId}
              onChange={(e) => setRoomClassId(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
              placeholder="Nhập mã loại phòng"
            />
          </div>

          <div className="col-span-2">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Mô tả
            </label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
              placeholder="Nhập mô tả"
              rows={3}
            />
          </div>

          <div className="col-span-2">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Trạng thái
            </label>
            <select
              value={status}
              onChange={(e) => setStatus(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
            >
              <option value="AVAILABLE">Có sẵn</option>
              <option value="BOOKED">Đã đặt</option>
              <option value="UNDER_MAINTENANCE">Đang bảo trì</option>
            </select>
          </div>

          <div className="col-span-2 flex justify-between items-center mt-4">
            <DialogClose asChild>
              <button
                type="button"
                className="py-2 px-4 bg-gray-300 text-gray-800 rounded-md hover:bg-gray-400 transition-colors"
              >
                Huỷ
              </button>
            </DialogClose>

            <button
              type="submit"
              className="py-2 px-4 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
            >
              Thêm phòng
            </button>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
};

export default RoomAddModal;
