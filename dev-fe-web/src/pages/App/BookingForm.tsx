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
import TagSelect from "@/components/app/custom/tag-select";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { TimePicker } from "@/components/app/custom/time-picker";
import { toast } from "sonner";
import { ScheduleService } from "@/services/user/schedule.service";
import CheckingTable from "@/pages/App/checking-table";

interface BookingModalProps {
  eventDate?: string;
  onClose: () => void;
}
export type SelectedItem = {
  name: string;
  quantity: number;
};

export type SelectedPerson = {
  name: string;
};

export const BookingModal: React.FC<BookingModalProps> = ({
  eventDate,
  onClose,
}) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [capacity, setCapacity] = useState(10);
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
    end.setMinutes(minute + 30); // Tăng 30 phút so với giờ bắt đầu

    const h = end.getHours().toString().padStart(2, "0");
    const m = end.getMinutes().toString().padStart(2, "0");
    return `${h}:${m}`;
  });
  const [branch, setBranch] = useState("");
  const [repeatType, setRepeatType] = useState("one-time");
  const [selectedServices, setSelectedServices] = useState<SelectedItem[]>([]);
  const [selectedParticipants, setSelectedParticipants] = useState<
    SelectedPerson[]
  >([]);
  const [selectedDevices, setSelectedDevices] = useState<SelectedItem[]>([]);

  //lưu mảng sau khi kiểm tra lịch
  const [scheduleResult, setScheduleResult] = useState([]);

  const isOpen = useMemo(() => !!eventDate, [eventDate]);

  // rerender lại lấy ngày đã chọn cho chính xác
  useEffect(() => {
    if (eventDate) {
      const parsedDate = new Date(eventDate);
      setStartDate(parsedDate);
      setEndDate(parsedDate);
    }
  }, [eventDate]);

  const servicesList = [
    "Nước khoáng",
    "Bánh ngọt",
    "Trái cây",
    "Cà phê",
    "Trà",
    "Bánh mì",
  ];
  const participantsList = [
    "user004.roomx@gmail.com",
    "user005.roomx@gmail.com",
  ];
  const devicesList = [
    "Máy chiếu",
    "Micro",
    "Bảng trắng",
    "Điều hòa",
    "Laptop",
  ];

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

    if (selectedParticipants.length === 1) {
      toast.error("Vui lòng chọn ít nhất 2 người tham gia.");
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
    console.log("Thiết bị:", selectedDevices);

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
      toast.success("Lịch hợp lệ!");
    } catch (error) {
      console.error(error);
      toast.error("Có lỗi xảy ra khi kiểm tra lịch.");
    }
  };

  // reset clear dữ liệu
  const resetForm = () => {
    setTitle("");
    setDescription("");
    setCapacity(10);
    setStartDate(null);
    setEndDate(null);
    setStartTime(getClosestTime());
    setEndTime(getClosestTime());
    setBranch("Nha Trang");
    setRepeatType("one-time");
    setSelectedServices([]); // Xóa tất cầu dịch vụ
    setSelectedDevices([]); // Xóa tất cầu thiết bị
    setSelectedParticipants([]); // Xóa tất cầu người tham gia
    setScheduleResult([]);
    localStorage.removeItem("dateRequestExceptions");
  };

  const handleClose = () => {
    resetForm();
    onClose();
  };

  return (
    <Dialog open={isOpen} onOpenChange={handleClose}>
      <DialogContent className="sm:max-w-4xl max-h-[90vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle>Đặt phòng họp</DialogTitle>
          <DialogDescription>
            Chọn ngày, giờ và thiết lập lịch hẹn phòng họp.
          </DialogDescription>
        </DialogHeader>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-4">
          <Card className="w-full">
            <CardHeader>
              <CardTitle>Thông tin lịch hẹn</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="space-y-2">
                  <Label htmlFor="title">Tiêu đề</Label>
                  <Input
                    id="title"
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    placeholder="Nhập tiêu đề buổi họp"
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="description">Mô tả</Label>
                  <Input
                    id="description"
                    type="text"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    placeholder="Nhập mô tả chi tiết"
                  />
                </div>

                <div className="space-y-2">
                  <Label>Loại lịch</Label>
                  <Select value={repeatType} onValueChange={setRepeatType}>
                    <SelectTrigger className="w-full">
                      <SelectValue />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="daily">Ngày</SelectItem>
                      <SelectItem value="weekly">Hằng tuần</SelectItem>
                      <SelectItem value="monthly">Hằng tháng</SelectItem>
                      <SelectItem value="yearly">Hằng năm</SelectItem>
                      <SelectItem value="custom">Tuỳ chọn</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="capacity">Sức chứa</Label>
                  <Input
                    id="capacity"
                    type="number"
                    value={capacity}
                    onChange={(e) => setCapacity(Number(e.target.value))}
                    placeholder="Nhập số lượng sức chứa"
                  />
                </div>

                {/* Ngày và giờ bắt đầu */}
                <div className="flex flex-col md:flex-row gap-4">
                  <div className="flex-1 space-y-2">
                    <Label>Ngày bắt đầu</Label>
                    <DatePicker
                      selected={startDate}
                      onChange={(date) => setStartDate(date)}
                      dateFormat="dd-MM-yyyy"
                      className="w-full border px-3 py-2 rounded-md bg-transparent"
                    />
                  </div>
                  <div className="flex-1 space-y-2">
                    <Label>Giờ bắt đầu</Label>
                    <TimePicker value={startTime} onChange={setStartTime} />
                  </div>
                </div>

                {/* Ngày và giờ kết thúc */}
                <div className="flex flex-col md:flex-row gap-4">
                  <div className="flex-1 space-y-2">
                    <Label>Ngày kết thúc</Label>
                    <DatePicker
                      selected={endDate}
                      onChange={(date) => setEndDate(date)}
                      dateFormat="dd-MM-yyyy"
                      className="w-full border px-3 py-2 rounded-md bg-transparent"
                    />
                  </div>
                  <div className="flex-1 space-y-2">
                    <Label>Giờ kết thúc</Label>
                    <TimePicker value={endTime} onChange={setEndTime} />
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>

          <div className="flex flex-col gap-4 max-h-[90vh] overflow-y-auto">
            <TagSelect
              title="Người tham gia"
              placeholder="Tìm người tham gia..."
              data={participantsList}
              variant="people"
              onChange={setSelectedParticipants}
            />
            <TagSelect
              title="Dịch vụ"
              placeholder="Tìm dịch vụ..."
              data={servicesList}
              onChange={setSelectedServices}
            />
            <TagSelect
              title="Thiết bị"
              placeholder="Tìm thiết bị..."
              data={devicesList}
              onChange={setSelectedDevices}
            />
          </div>
        </div>
        {scheduleResult && scheduleResult.length > 0 && (
          <>
            <CheckingTable data={scheduleResult} />

            <div className="mt-6 flex justify-end">
              <button className="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-xl shadow-md transition-all duration-300">
                📅 Đặt lịch ngay
              </button>
            </div>
          </>
        )}

        <div className="flex justify-end gap-2 mt-6">
          <Button variant="outline" onClick={onClose}>
            Hủy
          </Button>
          <Button onClick={checkSchedule} className="bg-blue-600">Kiểm tra</Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};
