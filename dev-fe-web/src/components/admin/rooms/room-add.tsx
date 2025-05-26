import { useState, useEffect, useRef } from "react";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
  DialogFooter,
  DialogClose,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import { toast } from "sonner";
import { PlusCircle, FileText, Camera } from "lucide-react";
import { RoomService } from "@/services/admin/room.service";
import { BranchService } from "@/services/admin/branch.service";

const RoomAddModal: React.FC = () => {
  const [roomCode, setRoomCode] = useState("");
  const [description, setDescription] = useState("");
  const [status, setStatus] = useState("AVAILABLE");
  const [placeId, setPlaceId] = useState("");
  const [buildingId, setBuildingId] = useState("");
  const [floorId, setFloorId] = useState("");
  const [capacity, setCapacity] = useState(0);
  const [price, setPrice] = useState(0);
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [previewUrl, setPreviewUrl] = useState<string>("");

  const [branches, setBranches] = useState<any[]>([]);
  const [buildings, setBuildings] = useState<any[]>([]);
  const [floors, setFloors] = useState<any[]>([]);
  const [open, setOpen] = useState(false);

  const fileInputRef = useRef<HTMLInputElement>(null);

  const roomService = new RoomService();
  const branchService = new BranchService();

  const fetchBranches = async () => {
    try {
      const data = await branchService.getAllBranchesWithHierarchy();
      setBranches(data);
    } catch (error) {
      toast.error("Lỗi khi tải danh sách chi nhánh.");
    }
  };

  useEffect(() => {
    fetchBranches();
  }, []);

  useEffect(() => {
    if (!placeId) return;
    const selectedBranch = branches.find((b) => b.id === placeId);
    if (selectedBranch?.children) {
      setBuildings(selectedBranch.children);
      setBuildingId("");
      setFloorId("");
      setFloors([]);
    }
  }, [placeId, branches]);

  useEffect(() => {
    if (!buildingId) return;
    const selectedBuilding = buildings.find((b) => b.id === buildingId);
    if (selectedBuilding?.children) {
      setFloors(selectedBuilding.children);
      setFloorId("");
    }
  }, [buildingId, buildings]);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setImageFile(file);
      setPreviewUrl(URL.createObjectURL(file));
    }
  };

  const handleSubmit = async () => {
    // Check các trường bắt buộc
    if (
      !roomCode ||
      !placeId ||
      !buildingId ||
      !floorId ||
      !capacity ||
      !price
    ) {
      toast.error("Vui lòng điền đầy đủ thông tin.");
      return;
    }

    // Check roomCode hợp lệ theo regex
    const roomCodeRegex = /^[a-zA-Z0-9_-]+$/;
    if (!roomCodeRegex.test(roomCode)) {
      toast.error(
        "Mã phòng không hợp lệ. Chỉ được chứa chữ, số, dấu gạch dưới (_) và dấu gạch ngang (-)."
      );
      return;
    }

    // Kiểm tra capacity: số nguyên dương, từ 1 đến 1000 (ví dụ)
    if (!Number.isInteger(capacity) || capacity < 1 || capacity > 1000) {
      toast.error("Sức chứa phải là số nguyên từ 1 đến 1000.");
      return;
    }

    // Kiểm tra price: số nguyên dương, từ 1 đến 10,000,000 (ví dụ)
    if (!Number.isInteger(price) || price < 1 || price > 10000000) {
      toast.error("Giá tiền phải là số nguyên từ 1 đến 10,000,000 VND.");
      return;
    }

    try {
      const payload = {
        roomCode,
        description,
        floorId,
        capacity,
        price: price,
        file: imageFile || null,
      };
      await roomService.createRoom(payload);
      toast.success("Thêm phòng thành công!");
      setOpen(false);
      // reset form nếu muốn
      setRoomCode("");
      setDescription("");
      setStatus("AVAILABLE");
      setPlaceId("");
      setBuildingId("");
      setFloorId("");
      setCapacity(0);
      setPrice(0);
      setImageFile(null);
      setPreviewUrl("");
    } catch (error) {
      toast.error("Lỗi khi thêm phòng.");
    }
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button
          variant="default"
          className="flex items-center gap-2 bg-green-600 hover:bg-green-700 text-white dark:text-black"
        >
          <PlusCircle className="w-5 h-5" />
          Thêm phòng
        </Button>
      </DialogTrigger>

      <DialogContent className="max-h-[90vh] max-w-3xl dark:bg-gray-900 flex flex-col">
        <DialogHeader className="flex-shrink-0">
          <DialogTitle className="flex items-center gap-2">
            <FileText className="text-blue-600 dark:text-blue-400" /> Thêm phòng
            mới
          </DialogTitle>
          <DialogDescription>
            Vui lòng nhập thông tin chi tiết của phòng.
          </DialogDescription>
        </DialogHeader>

        <div className="flex-grow overflow-auto mt-4 mb-4 p-4 pr-2 ">
          {/* Chọn ảnh bằng cách click ảnh */}
          <div
            className="relative flex items-center justify-center w-full h-60 bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden mb-4 group cursor-pointer"
            onClick={() => fileInputRef.current?.click()}
          >
            {previewUrl ? (
              <img
                src={previewUrl}
                alt="Preview"
                className="object-cover w-full h-full"
              />
            ) : (
              <p className="text-gray-500 dark:text-gray-400">Chọn ảnh</p>
            )}
            <div className="absolute inset-0 bg-black bg-opacity-40 opacity-0 group-hover:opacity-100 flex items-center justify-center transition">
              <Camera className="w-10 h-10 text-white" />
            </div>
            <input
              ref={fileInputRef}
              type="file"
              accept="image/*"
              onChange={handleImageChange}
              className="hidden"
            />
          </div>

          {/* Form chia grid 2 cột */}
          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label>Mã phòng</Label>
              <Input
                value={roomCode}
                onChange={(e) => setRoomCode(e.target.value)}
              />
            </div>

            <div>
              <Label>Chi nhánh</Label>
              <Select value={placeId} onValueChange={setPlaceId}>
                <SelectTrigger>
                  <SelectValue placeholder="Chọn chi nhánh" />
                </SelectTrigger>
                <SelectContent>
                  {branches.map((b) => (
                    <SelectItem key={b.id} value={b.id}>
                      {b.name}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div>
              <Label>Tòa nhà</Label>
              <Select
                value={buildingId}
                onValueChange={setBuildingId}
                disabled={!buildings.length}
              >
                <SelectTrigger>
                  <SelectValue placeholder="Chọn tòa nhà" />
                </SelectTrigger>
                <SelectContent>
                  {buildings.map((b) => (
                    <SelectItem key={b.id} value={b.id}>
                      {b.name}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div>
              <Label>Tầng</Label>
              <Select
                value={floorId}
                onValueChange={setFloorId}
                disabled={!floors.length}
              >
                <SelectTrigger>
                  <SelectValue placeholder="Chọn tầng" />
                </SelectTrigger>
                <SelectContent>
                  {floors.map((f) => (
                    <SelectItem key={f.id} value={f.id}>
                      {f.name}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div>
              <Label>Sức chứa</Label>
              <Input
                type="number"
                value={capacity}
                onChange={(e) => setCapacity(+e.target.value)}
              />
            </div>

            <div>
              <Label>Giá tiền (VND)</Label>
              <Input
                type="number"
                value={price}
                onChange={(e) => setPrice(+e.target.value)}
              />
            </div>

            <div className="col-span-2">
              <Label>
                <span className="text-gray-600 font-medium">Mô tả</span>
              </Label>
              <textarea
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                className="w-full p-2 mt-1 border rounded-md bg-white dark:bg-gray-800 dark:text-white resize-none h-24"
                placeholder="Nhập mô tả chi tiết về phòng..."
              />
            </div>
          </div>
        </div>

        <DialogFooter className="flex-shrink-0 pt-4">
          <DialogClose asChild>
            <Button variant="outline">Hủy</Button>
          </DialogClose>
          <Button
            onClick={handleSubmit}
            className="bg-blue-600 text-white hover:bg-blue-700"
          >
            Lưu
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default RoomAddModal;
