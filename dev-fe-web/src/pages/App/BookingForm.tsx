import { useState, useMemo, useEffect } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import TagSelect, {
  OptionItem,
  SelectedItem,
} from "@/components/app/custom/tag-select";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { TimePicker } from "@/components/app/custom/time-picker";
import { toast } from "sonner";
import { ScheduleService } from "@/services/user/schedule.service";
import CheckingTable from "@/pages/App/checking-table";
import {
  AlertTriangle,
  Calendar,
  Coffee,
  FileClock,
  FileEdit,
  Home,
  Info,
  MapPin,
  MonitorSmartphone,
  Repeat,
  Settings,
  UserPlus,
  Users,
} from "lucide-react";
import { BranchService } from "@/services/admin/branch.service";
import { RoomService } from "@/services/admin/room.service";
import { UserService } from "@/services/admin/user.service";
import { ServiceService } from "@/services/admin/service.service";
import { EquipmentService } from "@/services/admin/equipment.service";

interface BookingModalProps {
  eventDate?: string;
  onClose: () => void;
}

export type SelectedPerson = {
  name: string;
};

export const BookingModal: React.FC<BookingModalProps> = ({
  eventDate,
  onClose,
}) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [capacity, setCapacity] = useState(20);
  const [startDate, setStartDate] = useState<Date | null>(
    eventDate ? new Date(eventDate) : new Date()
  );
  const [endDate, setEndDate] = useState<Date | null>(
    eventDate ? new Date(eventDate) : new Date()
  );
  const [startTime, setStartTime] = useState(() => getClosestTime());
  const [endTime, setEndTime] = useState(() => {
    const [hour, minute] = getClosestTime().split(":").map(Number);
    const end = new Date();
    end.setHours(hour);
    end.setMinutes(minute + 30);
    const h = end.getHours().toString().padStart(2, "0");
    const m = end.getMinutes().toString().padStart(2, "0");
    return `${h}:${m}`;
  });
  const [repeatType, setRepeatType] = useState("");
  const [selectedServices, setSelectedServices] = useState<SelectedItem[]>([]);
  const [selectedParticipants, setSelectedParticipants] = useState<
    SelectedPerson[]
  >([]);
  const [selectedEquipments, setSelectedEquipments] = useState<SelectedItem[]>(
    []
  );
  const [scheduleResult, setScheduleResult] = useState([]); //lưu mảng sau khi kiểm tra lịch
  const [branch, setBranch] = useState<string>("");
  const [room, setRoom] = useState("");
  const isOpen = useMemo(() => !!eventDate, [eventDate]);

  //danh sách chi nhánh
  const [branches, setBranches] = useState<
    { branchId: string; branchCode: string; name: string }[]
  >([]);
  //danh sách phòng
  const [rooms, setRooms] = useState<
    { roomId: string; roomCode: string; roomName: string }[]
  >([]);
  //danh sách người tham gia
  const [participants, setParticipants] = useState<OptionItem[]>([]);

  //danh sách dịch vụ
  const [services, setServices] = useState<OptionItem[]>([]);

  //danh sách thiết bị
  const [equipments, setEquipments] = useState<OptionItem[]>([]);

  // rerender lại lấy ngày đã chọn cho chính xác
  useEffect(() => {
    if (eventDate) {
      const parsedDate = new Date(eventDate);
      setStartDate(parsedDate);
      setEndDate(parsedDate);
    }
  }, [eventDate]);

  // Lấy chi nhánh
  const fetchBranches = async () => {
    try {
      const branchService = new BranchService();
      const branchList = await branchService.getAllBranches();
      const dataBranch = branchList.map((branch: any) => ({
        branchId: branch.id,
        branchCode: branch.code,
        name: branch.name,
      }));
      setBranches(dataBranch);
    } catch (error) {
      console.error("Lỗi khi lấy danh sách chi nhánh:", error);
      toast.error("Không thể tải danh sách chi nhánh.");
    }
  };

  // lấy danh sách người tham gia
  const fetchParticipants = async (keyword: string): Promise<OptionItem[]> => {
    const userService = new UserService();
    const data = await userService.getListUsers(0, 100, keyword);
    return data.content.map((user: any) => ({
      id: user.id, // hoặc user.email, tùy bạn muốn dùng gì làm id
      name: user.email,
    }));
  };

  // lấy danh sách dịch vụ
  const fetchServices = async (keyword: string): Promise<OptionItem[]> => {
    const serviceService = new ServiceService();
    const data = await serviceService.getListServices(0, 100, keyword);
    return data.content.map((service: any) => ({
      id: service.id,
      name: service.name,
    }));
  };

  // lấy danh sách thiết bị
  const fetchEquipments = async (keyword: string): Promise<OptionItem[]> => {
    const equipmentService = new EquipmentService();
    const data = await equipmentService.getListEquipments(0, 100, keyword);
    return data.content.map((equipment: any) => ({
      id: equipment.id,
      name: equipment.name,
    }));
  };

  useEffect(() => {
    fetchBranches();
  }, []);

  // lấy danh sách phòng
  const fetchRooms = async () => {
    try {
      const roomService = new RoomService();
      const data = await roomService.getListRoomsByBranchId(branch);
      console.log(data);
      const dataRoom = data.content.map((room: any) => ({
        roomId: room.id,
        roomCode: room.roomCode,
      }));
      setRooms(dataRoom);
    } catch (error) {
      console.error("Error fetching rooms:", error);
    }
  };

  useEffect(() => {
    fetchRooms();
  }, [branch]);

  function getClosestTime(): string {
    const now = new Date();
    let h = now.getHours();
    let m = now.getMinutes();

    const remainder = m % 15;
    if (remainder !== 0) {
      m += 15 - remainder;
    }

    if (m === 60) {
      m = 0;
      h += 1;
    }

    h = h % 24;

    return `${h.toString().padStart(2, "0")}:${m.toString().padStart(2, "0")}`;
  }

  // Kiểm tra input hợp lệ
  const checkValid = async () => {
    if (!startDate || !endDate || !startTime || !endTime) {
      alert("Vui lòng chọn đầy đủ ngày và giờ.");
      return false;
    }

    const now = new Date();
    const start = new Date(
      `${startDate.toISOString().split("T")[0]}T${startTime}`
    );
    const end = new Date(`${endDate.toISOString().split("T")[0]}T${endTime}`);

    const today = new Date(now.toDateString());
    const startDay = new Date(start.toDateString());

    if (!title.trim()) {
      toast.error("Vui lòng nhập tiêu đề.");
      return false;
    }

    if (!description.trim()) {
      toast.error("Vui lòng nhập mô tả.");
      return false;
    }

    if (!branch) {
      toast.error("Vui lòng chọn chi nhánh.");
      return false;
    }

    // Bắt buộc chọn 1 trong 2: phòng hoặc số lượng
    if (!room && capacity <= 5) {
      toast.error("Nếu không chọn phòng, vui lòng nhập số lượng lớn hơn 5.");
      return false;
    }

    if (!repeatType) {
      toast.error("Vui lòng chọn loại lịch.");
      return false;
    }

    if (startDay < today) {
      console.log(startDay);
      console.log(today);
      toast.error("Ngày bắt đầu không được ở trong quá khứ.");
      return false;
    }

    if (startDay.getDay() === today.getTime() && start <= now) {
      toast.error("Giờ bắt đầu hôm nay phải sau thời gian hiện tại.");
      return false;
    }

    if (end <= start) {
      toast.error("Thời gian kết thúc phải sau thời gian bắt đầu.");
      return false;
    }

    const durationMinutes = (end.getTime() - start.getTime()) / (1000 * 60);
    if (durationMinutes < 30) {
      toast.error("Lịch họp phải kéo dài ít nhất 30 phút.");
      return false;
    }

    if (selectedParticipants.length < 2) {
      toast.error("Vui lòng chọn ít nhất 2 người tham gia.");
      return false;
    }

    // ✅ In ra dữ liệu khi hợp lệ
    console.log("===== Booking Information =====");
    console.log("Tiêu đề:", title);
    console.log("Mô tả:", description);
    console.log("Ngày bắt đầu:", startDate.toLocaleDateString());
    console.log("Giờ bắt đầu:", startTime);
    console.log("Ngày kết thúc:", endDate.toLocaleDateString());
    console.log("Giờ kết thúc:", endTime);
    console.log("Chi nhánh:", branch);
    console.log("Loại lịch:", repeatType);
    console.log("Người tham gia:", selectedParticipants);
    console.log("Dịch vụ:", selectedServices);
    console.log("Thiết bị:", selectedEquipments);

    return true;
  };

  // check lịch
  const checkSchedule = async () => {
    const isValid = await checkValid();
    if (!isValid) return;

    const formatDateOnly = (date: Date) => date.toISOString().split("T")[0];
    const storedExceptions = localStorage.getItem("dateRequestExceptions");
    const parsedExceptions = storedExceptions
      ? JSON.parse(storedExceptions)
      : [];

    const scheduleData = {
      title,
      description,
      recurrenceType: "DAILY",
      capacity,
      startDate: formatDateOnly(new Date(startDate!)),
      endDate: formatDateOnly(new Date(endDate!)),
      startTime: startTime,
      endTime: endTime,
      daysOfWeek: ["MO", "TU", "WE", "TH", "FR", "SA", "SU"].join(","),
      participants: ["user004.roomx@gmail.com", "user005.roomx@gmail.com"],
      dateRequestExceptions: parsedExceptions,
    };

    const scheduleService = new ScheduleService();
    try {
      const response = await scheduleService.checkSchedule(scheduleData);
      console.log(response.result);
      setScheduleResult(response.result); // Cập nhật state
      const hasConflict = response.result.some((item: any) => item.hasConflict);

      if (hasConflict) {
        toast.warning("Một số ngày bị trùng lịch. Vui lòng kiểm tra lại! 🕒");
      } else {
        toast.success("Lịch hợp lệ! ✅");
      }
    } catch (error) {
      console.error(error);
      toast.error("Có lỗi xảy ra khi kiểm tra lịch.");
    }
  };

  // Đặt lịch
  const handleBook = async () => {
    const isValid = await checkValid();
    if (!isValid) return;

    const formatDateOnly = (date: Date) => date.toISOString().split("T")[0];
    const storedExceptions = localStorage.getItem("dateRequestExceptions");
    const parsedExceptions = storedExceptions
      ? JSON.parse(storedExceptions)
      : [];

    const scheduleData = {
      title,
      description,
      recurrenceType: repeatType,
      branchId: branch,
      roomId: room,
      capacity,
      startDate: formatDateOnly(new Date(startDate!)),
      endDate: formatDateOnly(new Date(endDate!)),
      startTime: startTime,
      endTime: endTime,
      daysOfWeek: ["MO", "TU", "WE", "TH", "FR", "SA", "SU"].join(","),
      participants: selectedParticipants.map((p) => p.name),
      services: selectedServices.map((s) => ({
        serviceId: s.id,
        quantity: s.quantity,
      })),
      equipments: selectedEquipments.map((e) => ({
        equipmentId: e.id,
        quantity: e.quantity,
      })),
      dateRequestExceptions: parsedExceptions,
    };

    console.log(scheduleData);

    const scheduleService = new ScheduleService();
    try {
      await scheduleService.createSchedule(scheduleData);
      handleClose();
    } catch (error: any) {
      console.error(error);

      if (error?.response?.data?.code === 1000) {
        toast.warning("Có xung đột thời gian. Vui lòng chọn khung giờ khác.");
        const conflictData = error.response.data.result;
        console.log(conflictData);
        setScheduleResult(conflictData);
      } else {
        toast.error("Có lỗi xảy ra khi đặt lịch.");
      }
    }
  };

  // reset clear dữ liệu
  const resetForm = () => {
    setTitle("");
    setDescription("");
    setCapacity(20);
    setStartDate(null);
    setEndDate(null);
    setStartTime(getClosestTime());
    setEndTime(getClosestTime());
    setBranch("");
    setSelectedServices([]); // Xóa tất cầu dịch vụ
    setSelectedEquipments([]); // Xóa tất cầu thiết bị
    setSelectedParticipants([]); // Xóa tất cầu người tham gia
    setScheduleResult([]);
    setRepeatType("");
    setRoom("");
    localStorage.removeItem("dateRequestExceptions");
  };

  const handleClose = () => {
    resetForm();
    onClose();
  };

  return (
    <Dialog open={isOpen} onOpenChange={handleClose}>
      <DialogContent className="sm:max-w-4xl max-h-[95vh] overflow-hidden">
        <DialogHeader>
          <DialogTitle>Đặt phòng họp</DialogTitle>
          <DialogDescription>
            Chọn ngày, giờ và thiết lập lịch hẹn phòng họp.
          </DialogDescription>
        </DialogHeader>
        <div className="flex flex-col gap-6 overflow-y-auto max-h-[75vh] pr-2">
          {/* Thông tin lịch hẹn */}
          <Card className="w-full bg-background shadow-xl rounded-2xl border">
            <CardHeader className="border-b px-4 py-3 bg-muted/40">
              <CardTitle className="text-xl font-semibold text-primary flex items-center gap-2">
                <Calendar className="w-4 h-4 text-blue-500" /> Thông tin lịch
                hẹn
              </CardTitle>
            </CardHeader>

            <CardContent className="px-4 py-4">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {/* Tiêu đề */}
                <div>
                  <Label
                    htmlFor="title"
                    className="mb-1 text-sm text-muted-foreground flex items-center gap-1"
                  >
                    <FileEdit className="w-4 h-4 text-pink-500" />
                    Tiêu đề
                  </Label>
                  <Input
                    id="title"
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    placeholder="Nhập tiêu đề buổi họp"
                    className="h-9 text-sm"
                  />
                </div>

                {/* Mô tả */}
                <div>
                  <Label
                    htmlFor="description"
                    className="mb-1 text-sm text-muted-foreground flex items-center gap-1"
                  >
                    <Info className="w-4 h-4 text-yellow-500" />
                    Mô tả
                  </Label>
                  <Input
                    id="description"
                    type="text"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    placeholder="Nhập mô tả chi tiết"
                    className="h-9 text-sm"
                  />
                </div>

                {/* Chi nhánh */}
                <div>
                  <Label
                    htmlFor="branch"
                    className="mb-1 text-sm text-muted-foreground flex items-center gap-1"
                  >
                    <MapPin className="w-4 h-4 text-green-500" />
                    Chi nhánh
                  </Label>
                  <Select value={branch} onValueChange={setBranch}>
                    <SelectTrigger className="h-9 text-sm">
                      <SelectValue placeholder="Chọn chi nhánh" />
                    </SelectTrigger>
                    <SelectContent>
                      {branches.map((b) => (
                        <SelectItem
                          key={b.branchId}
                          value={b.branchId}
                          className="text-sm"
                        >
                          {b.name}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>

                {/* Phòng họp */}
                <div>
                  <Label
                    htmlFor="room"
                    className="mb-1 text-sm text-muted-foreground flex items-center gap-1"
                  >
                    <Home className="w-4 h-4 text-indigo-500" />
                    Phòng họp
                  </Label>
                  <Select value={room} onValueChange={setRoom}>
                    <SelectTrigger className="h-9 text-sm">
                      <SelectValue placeholder="Chọn phòng họp" />
                    </SelectTrigger>
                    <SelectContent>
                      {rooms.map((r) => (
                        <SelectItem
                          key={r.roomId}
                          value={r.roomId}
                          className="text-sm"
                        >
                          {r.roomCode}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>

                {/* Loại lịch */}
                <div>
                  <Label className="mb-1 text-sm text-muted-foreground flex items-center gap-1">
                    <Repeat className="w-4 h-4 text-rose-500" />
                    Loại lịch
                  </Label>
                  <Select value={repeatType} onValueChange={setRepeatType}>
                    <SelectTrigger className="h-9 text-sm">
                      <SelectValue placeholder="Chọn loại lịch" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="DAILY" className="text-sm">
                        Ngày
                      </SelectItem>
                      <SelectItem value="WEEKLY" className="text-sm">
                        Hằng tuần
                      </SelectItem>
                      <SelectItem value="MONTHLY" className="text-sm">
                        Hằng tháng
                      </SelectItem>
                      {/* <SelectItem value="YEARLY" className="text-sm">
                        Hằng năm
                      </SelectItem> */}
                      {/* <SelectItem value="CUSTOM" className="text-sm">
                        Tuỳ chọn
                      </SelectItem> */}
                    </SelectContent>
                  </Select>
                </div>

                {/* Sức chứa */}
                <div>
                  <Label
                    htmlFor="capacity"
                    className="mb-1 text-sm text-muted-foreground flex items-center gap-1"
                  >
                    <Users className="w-4 h-4 text-cyan-500" />
                    Sức chứa
                  </Label>
                  <Input
                    id="capacity"
                    type="number"
                    value={capacity}
                    onChange={(e) => {
                      const value = e.target.value;

                      // Chỉ chấp nhận số nguyên dương
                      if (/^\d*$/.test(value)) {
                        setCapacity(Number(value));
                      }
                    }}
                    placeholder="Nhập số lượng sức chứa"
                    className="h-9 text-sm"
                    min={0}
                    step={1}
                  />
                </div>

                {/* Ngày bắt đầu */}
                <div className="flex items-center gap-3">
                  <Label className="w-24 text-sm text-muted-foreground flex items-center gap-1">
                    <Calendar className="w-4 h-4 text-purple-500" /> Bắt đầu
                  </Label>
                  <DatePicker
                    selected={startDate}
                    onChange={setStartDate}
                    dateFormat="dd-MM-yyyy"
                    className="w-full h-9 px-3 py-2 text-sm border rounded-md bg-background shadow-sm focus:outline-none focus:ring-2 focus:ring-primary focus:border-primary transition"
                  />
                </div>

                {/* Ngày kết thúc */}
                <div className="flex items-center gap-3">
                  <Label className=" w-24 text-sm text-muted-foreground flex items-center gap-1">
                    <Calendar className="w-4 h-4 text-purple-500" /> Kết thúc
                  </Label>
                  <DatePicker
                    className="w-full h-9 px-3 py-2 text-sm border rounded-md bg-background shadow-sm focus:outline-none focus:ring-2 focus:ring-primary focus:border-primary transition"
                    selected={endDate}
                    onChange={setEndDate}
                    dateFormat="dd-MM-yyyy"
                  />
                </div>

                {/* Giờ bắt đầu */}
                <div className="flex items-center gap-3">
                  <Label className="w-30 text-sm text-muted-foreground flex items-center gap-1">
                    <FileClock className="w-4 h-4 text-orange-500" /> Giờ bắt
                    đầu
                  </Label>
                  <TimePicker value={startTime} onChange={setStartTime} />
                </div>

                {/* Giờ kết thúc */}
                <div className="flex items-center gap-3">
                  <Label className="w-30 text-sm text-muted-foreground flex items-center gap-1">
                    <FileClock className="w-4 h-4 text-orange-500" /> Giờ kết
                    thúc
                  </Label>
                  <TimePicker value={endTime} onChange={setEndTime} />
                </div>
              </div>
            </CardContent>
          </Card>

          {/* Thành phần liên quan */}
          <Card className="w-full">
            <CardHeader className="px-4 py-3 border-b bg-muted/40">
              <CardTitle className="text-base font-semibold text-primary flex items-center gap-2">
                <Users className="w-5 h-5 text-blue-500" /> Thành phần liên quan
              </CardTitle>
            </CardHeader>
            <CardContent className="p-4">
              <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-3">
                {/* Người tham gia */}
                <TagSelect
                  title={
                    <span className="flex items-center gap-1 text-sm font-medium">
                      <UserPlus className="w-4 h-4 text-green-500" />
                      Người tham gia
                    </span>
                  }
                  placeholder="Tìm người tham gia..."
                  data={participants}
                  variant="people"
                  onChange={setSelectedParticipants}
                  onSearch={fetchParticipants}
                />

                {/* Dịch vụ */}
                <TagSelect
                  title={
                    <span className="flex items-center gap-1 text-sm font-medium">
                      <Coffee className="w-4 h-4 text-orange-500" />
                      Dịch vụ
                    </span>
                  }
                  placeholder="Tìm dịch vụ..."
                  data={services}
                  onChange={setSelectedServices}
                  onSearch={fetchServices}
                />
                {/* Thiết bị */}
                <TagSelect
                  title={
                    <span className="flex items-center gap-1 text-sm font-medium">
                      <MonitorSmartphone className="w-4 h-4 text-purple-500" />
                      Thiết bị
                    </span>
                  }
                  placeholder="Tìm thiết bị..."
                  data={equipments}
                  onChange={setSelectedEquipments}
                  onSearch={fetchEquipments}
                />
              </div>
            </CardContent>
          </Card>

          <Card className="rounded-2xl shadow-sm border bg-white">
            <CardHeader className="px-6 py-4 border-b bg-muted/50 rounded-t-2xl">
              <CardTitle className="text-base font-semibold text-yellow-600 flex items-center gap-2">
                <AlertTriangle className="w-5 h-5" />
                Cập nhật xung đột
              </CardTitle>
            </CardHeader>

            <CardContent className="p-6 space-y-4">
              {/* Nếu có dữ liệu mới hiển thị bảng */}
              {scheduleResult.length > 0 ? (
                <CheckingTable data={scheduleResult} />
              ) : (
                <p className="text-sm text-muted-foreground">
                  Không có xung đột nào được ghi nhận.
                </p>
              )}
            </CardContent>
          </Card>
        </div>

        {/* Buttons */}
        <div className="flex justify-end gap-2 ">
          <Button variant="outline" onClick={onClose}>
            Hủy
          </Button>
          <Button onClick={handleBook} className="bg-blue-600 text-white">
            📅 Đặt lịch ngay
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};
