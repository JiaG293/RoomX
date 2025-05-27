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

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { TimePicker } from "@/components/app/custom/time-picker";
import { toast } from "sonner";
import { ScheduleService } from "@/services/user/schedule.service";
import CheckingTable from "@/pages/App/checking-table";
import {
  AlertTriangle,
  Calendar,
  CalendarHeart,
  FileClock,
  FileEdit,
  Hourglass,
  Info,
  MapPin,
  Repeat,
  Users,
} from "lucide-react";
import { BranchService } from "@/services/admin/branch.service";
import { ToggleGroup, ToggleGroupItem } from "@/components/ui/toggle-group";
import { MemberSelectionPanel } from "@/pages/App/MemberSelectionPanel";
import { calculateEndTime, isBookingTimeValid } from "@/utils/date.util";
import { ResourceSelectionPanel } from "@/pages/App/ResourceSelectionPanel";
import { RoomService } from "@/services/admin/room.service";

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
  const [capacity, setCapacity] = useState(0);
  const [startDate, setStartDate] = useState<Date | null>(
    eventDate ? new Date(eventDate) : new Date()
  );
  const [endDate, setEndDate] = useState<Date | null>(
    eventDate ? new Date(eventDate) : new Date()
  );
  const [startTime, setStartTime] = useState(() => getClosestTime());
  const [duration, setDuration] = useState(30);
  const durationOptions = [
    30,
    45, // Ngắn
    60,
    75,
    90, // Vừa
    105,
    120, // Dài
    135,
    150,
    165,
    180,
  ];

  const [repeatType, setRepeatType] = useState("");
  const [selectedServices, setSelectedServices] = useState<any[]>([]);
  const [selectedParticipants, setSelectedParticipants] = useState<string[]>(
    []
  );
  const [selectedEquipments, setSelectedEquipments] = useState<any[]>([]);
  const [scheduleResult, setScheduleResult] = useState([]); //lưu mảng sau khi kiểm tra lịch
  const [branch, setBranch] = useState<string>("");
  const isOpen = useMemo(() => !!eventDate, [eventDate]);
  const daysOfWeek = [
    { value: "MO", label: "T2" },
    { value: "TU", label: "T3" },
    { value: "WE", label: "T4" },
    { value: "TH", label: "T5" },
    { value: "FR", label: "T6" },
    { value: "SA", label: "T7" },
    { value: "SU", label: "CN" },
  ];
  const [selectedDays, setSelectedDays] = useState<string[]>([]);
  //danh sách chi nhánh
  const [branches, setBranches] = useState<
    { branchId: string; branchCode: string; name: string }[]
  >([]);
  const [loading, setLoading] = useState(false);

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
      const branchList = await branchService.getAllBranchesWithHierarchy();
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

  useEffect(() => {
    fetchBranches();
  }, []);

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
    const endTime = calculateEndTime(startTime, duration);

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

    if (!branch) {
      toast.error("Vui lòng chọn chi nhánh.");
      return false;
    }

    const svc = new RoomService();
    const max = await svc.getLimitRoom();

    if (capacity > max) {
      toast.error("Vượt sức chứa phòng lớn nhất.");
      return false;
    }

    if (selectedParticipants.length < 3 && capacity < 3) {
      toast.error("Cuộc họp cần tối thiểu 3 thành viên.");
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

    // ----- Thêm kiểm tra giờ hành chính (ví dụ 7:00 - 22:00) -----
    const officeStartTime = "07:00";
    const officeEndTime = "22:00";

    if (
      !isBookingTimeValid(startTime, endTime, officeStartTime, officeEndTime)
    ) {
      toast.error(
        `Chỉ cho phép đặt phòng từ ${officeStartTime} đến ${officeEndTime}.`
      );
      return false;
    }

    return true;
  };

  // Đặt lịch
  const handleBook = async () => {
    // console.log(loading)
    if (loading) return; // tránh gọi nhiều lần
    setLoading(true);

    const isValid = await checkValid();
    if (!isValid) {
      setLoading(false);
      return;
    }

    const formatDateOnly = (date: Date) => date.toISOString().split("T")[0];
    const storedExceptions = localStorage.getItem("dateRequestExceptions");
    const parsedExceptions = storedExceptions
      ? JSON.parse(storedExceptions)
      : [];
    console.log(branch);
    const endTime = calculateEndTime(startTime, duration);

    console.log(endTime);
    const scheduleData = {
      title,
      description,
      recurrenceType: repeatType,
      branchId: branch,
      capacity,
      startDate: formatDateOnly(new Date(startDate!)),
      endDate: formatDateOnly(new Date(endDate!)),
      startTime: startTime,
      endTime: endTime,
      daysOfWeek:
        repeatType === "DAILY" ? "MO,TU,WE,TH,FR,SA,SU" : daysOfWeek.join(","),
      participants: selectedParticipants,
      services: selectedServices.map((s) => ({
        serviceId: s.serviceId,
        quantity: s.quantity,
      })),
      equipments: selectedEquipments.map((e) => ({
        equipmentId: e.equipmentId,
        quantity: e.quantity,
      })),
      dateRequestExceptions: parsedExceptions,
    };

    console.log(scheduleData);
    // localStorage.removeItem("dateRequestExceptions");

    const scheduleService = new ScheduleService();
    try {
      await scheduleService.createSchedule(scheduleData);
      handleClose(); // ✅ Thành công thì đóng modal
    } catch (error: any) {
      console.error(error);

      if (error?.response?.data?.code === 1000) {
        toast.warning("Có xung đột thời gian. Vui lòng chọn khung giờ khác.");
        if (error.response.data.message === "BranchId:  không tồn tại") {
          toast.error("Chi nhánh không có phòng khả dụng.");
          setLoading(false); // 🔁 reset lại
          return;
        }

        const conflictData =
          error &&
          error.response &&
          error.response.data &&
          error.response.data.result
            ? error.response.data.result
            : [];
        console.log(conflictData);
        setScheduleResult(conflictData);
      } else {
        toast.error("Không còn phòng khả dụng, vui lòng chọn khung giờ khác.");
      }
    } finally {
      setLoading(false); // 🔁 đảm bảo luôn reset sau khi chạy xong
    }
  };

  // reset clear dữ liệu
  const resetForm = () => {
    setTitle("");
    setDescription("");
    setCapacity(0);
    setStartDate(null);
    setEndDate(null);
    setStartTime(getClosestTime());
    setDuration(30);
    setBranch("");
    setSelectedServices([]); // Xóa tất cầu dịch vụ
    setSelectedEquipments([]); // Xóa tất cầu thiết bị
    setSelectedParticipants([]); // Xóa tất cầu người tham gia
    setScheduleResult([]);
    setRepeatType("");
    localStorage.removeItem("dateRequestExceptions");
    setSelectedDays([]);
    setLoading(false);
  };

  const handleClose = () => {
    resetForm();
    onClose();
  };

  return (
    <Dialog open={isOpen} onOpenChange={handleClose}>
      <DialogContent className="bg-muted sm:max-w-7xl max-h-[95vh] overflow-hidden">
        <DialogHeader>
          <DialogTitle>Đặt phòng họp</DialogTitle>
          <DialogDescription>
            Chọn ngày, giờ và thiết lập lịch hẹn phòng họp.
          </DialogDescription>
        </DialogHeader>
        <div className="bg-muted flex flex-col gap-6 overflow-y-auto max-h-[75vh] pr-2">
          <div className="flex gap-4">
            {/* Thông tin lịch hẹn */}
            <Card className="flex-[3.5] border-gray-400 dark:border-gray-600 bg-background rounded-md border">
              <CardHeader className="border-b px-4 py-3 bg-muted/40">
                <CardTitle className="text-xl font-semibold text-primary flex items-center gap-2">
                  <Calendar className="w-5 h-5 text-green-500" /> Thông tin lịch
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

                  {/* Sức chứa */}
                  <div>
                    <Label
                      htmlFor="capacity"
                      className="mb-1 text-sm text-muted-foreground flex items-center gap-1"
                    >
                      <Users className="w-4 h-4 text-cyan-500" />
                      Thành viên dự kiến {`(tuỳ chọn)`}
                    </Label>
                    <Input
                      id="capacity"
                      type="number"
                      value={capacity}
                      onChange={(e) => {
                        const value = e.target.value;
                        // Chỉ chấp nhận số nguyên dương hoặc rỗng (để dễ xoá)
                        if (/^\d*$/.test(value)) {
                          // Chuyển thành số (hoặc 0 nếu rỗng)
                          let num = value === "" ? 0 : Number(value);
                          // Giới hạn tối đa 1000
                          if (num > 1000) num = 1000;
                          setCapacity(num);
                        }
                      }}
                      placeholder="Nhập số lượng sức chứa"
                      className="h-9 text-sm"
                      min={0}
                      step={1}
                    />
                  </div>

                  {/* Loại lịch */}
                  <div className="transition-all col-span-1">
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
                      </SelectContent>
                    </Select>
                  </div>

                  {repeatType !== "DAILY" && repeatType !== "" && (
                    <div className="flex flex-col gap-1 col-span-full">
                      <Label className="text-sm text-muted-foreground flex items-center gap-1">
                        <CalendarHeart className="w-4 h-4 text-indigo-500" />
                        Ngày trong tuần
                      </Label>
                      <ToggleGroup
                        type="multiple"
                        value={selectedDays}
                        onValueChange={(value) => {
                          setSelectedDays(value);
                          console.log(value);
                        }}
                        className="grid grid-cols-7 gap-2"
                        aria-label="Chọn ngày trong tuần"
                      >
                        {daysOfWeek.map((day) => (
                          <ToggleGroupItem
                            key={day.value}
                            value={day.value}
                            className="aspect-square text-xs font-medium rounded-full border border-gray-300 dark:border-gray-600 cursor-pointer flex items-center justify-center transition-colors data-[state=on]:bg-blue-500 data-[state=on]:text-white"
                            aria-label={day.label}
                          >
                            {day.label}
                          </ToggleGroupItem>
                        ))}
                      </ToggleGroup>
                    </div>
                  )}

                  {/* Ngày bắt đầu */}
                  <div className="flex items-center gap-3">
                    <Label className="w-24 text-sm text-muted-foreground flex items-center gap-1">
                      <Calendar className="w-4 h-4 text-purple-500" /> Bắt đầu
                    </Label>
                    <DatePicker
                      selected={startDate}
                      onChange={setStartDate}
                      dateFormat="dd-MM-yyyy"
                      className="w-full h-9 px-3 py-2 text-sm border rounded-md bg-background focus:outline-none focus:ring-2 focus:ring-primary focus:border-primary transition"
                    />
                  </div>

                  {/* Ngày kết thúc */}
                  <div className="flex items-center gap-3">
                    <Label className=" w-24 text-sm text-muted-foreground flex items-center gap-1">
                      <Calendar className="w-4 h-4 text-purple-500" /> Kết thúc
                    </Label>
                    <DatePicker
                      className="w-full h-9 px-3 py-2 text-sm border rounded-md bg-background focus:outline-none focus:ring-2 focus:ring-primary focus:border-primary transition"
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

                  {/* Thời lượng */}
                  <div className="flex items-center gap-3">
                    <Label className="w-30 text-sm text-muted-foreground flex items-center gap-1">
                      <Hourglass className="w-4 h-4 text-blue-500" /> Thời lượng
                    </Label>
                    <select
                      value={duration}
                      onChange={(e) => setDuration(parseInt(e.target.value))}
                      className="bg-transparent border rounded px-2 py-1 max-h-32 overflow-y-auto"
                      size={1} // giữ dạng dropdown
                    >
                      {durationOptions.map((d) => (
                        <option key={d} value={d}>
                          {d >= 60
                            ? `${Math.floor(d / 60)} giờ${
                                d % 60 !== 0 ? " " + (d % 60) + " phút" : ""
                              }`
                            : `${d} phút`}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                {/* Mô tả nằm riêng, chiếm full chiều ngang */}
                <div className="mt-4">
                  <Label
                    htmlFor="description"
                    className="mb-1 text-sm text-muted-foreground flex items-center gap-1"
                  >
                    <Info className="w-4 h-4 text-yellow-500" />
                    Mô tả
                  </Label>
                  <textarea
                    id="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    placeholder="Nhập mô tả chi tiết"
                    className="bg-transparent w-full h-24 p-2 border rounded-md text-sm resize-none"
                  />
                </div>
              </CardContent>
            </Card>

            {/* Thành viên tham dự */}
            <MemberSelectionPanel
              onSelectedEmailsChange={(emails) =>
                setSelectedParticipants(emails)
              }
            />
          </div>

          <ResourceSelectionPanel
            onSelectedDeviceIdsChange={(ids) => setSelectedEquipments(ids)}
            onSelectedServiceIdsChange={(ids) => setSelectedServices(ids)}
          />

          <Card className=" border-gray-400 dark:border-gray-600 bg-background rounded-md border">
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
          <Button
            onClick={handleBook}
            disabled={loading}
            className="bg-blue-600 text-white"
          >
            {loading ? "⏳ Đang xử lý..." : "📅 Đặt lịch ngay"}
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};
