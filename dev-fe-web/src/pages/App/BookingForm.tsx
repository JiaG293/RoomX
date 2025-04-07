import { useEffect, useState, useMemo } from "react";
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
import { format, parse } from "date-fns";
import TagSelect from "@/components/app/custom/tag-select";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

interface BookingModalProps {
  eventDate?: string;
  onClose: () => void;
}

export const BookingModal: React.FC<BookingModalProps> = ({
  eventDate,
  onClose,
}) => {
  const parseDate = (dateString?: string) => {
    if (!dateString) return "";
    try {
      const parsed = parse(
        dateString,
        "yyyy-MM-dd'T'HH:mm:ss.SSSX",
        new Date()
      );
      return format(parsed, "yyyy-MM-dd");
    } catch {
      return "";
    }
  };

  const [date, setDate] = useState(parseDate(eventDate));
  const [duration, setDuration] = useState("60"); // Thời gian phòng họp
  const [room, setRoom] = useState(""); // Phòng họp
  const [repeatType, setRepeatType] = useState("one-time"); // Loại lịch
  const [hour, setHour] = useState("");
  const [minute, setMinute] = useState("");

  useEffect(() => {
    setDate(parseDate(eventDate));
  }, [eventDate]);

  const isOpen = useMemo(() => !!eventDate, [eventDate]);

  const generateHourOptions = (): string[] => {
    return Array.from({ length: 24 }, (_, i) => i.toString().padStart(2, "0"));
  };

  const minuteOptions = ["00", "15", "30", "45"];

  const roomList = ["Phòng họp lớn", "Phòng họp nhỏ", "Phòng hội nghị"];

  const servicesList = [
    "Nước khoáng",
    "Bánh ngọt",
    "Trái cây",
    "Cà phê",
    "Trà",
    "Bánh mì",
  ];

  const participantsList = [
    "Người tham gia 1",
    "Người tham gia 2",
    "Người tham gia 3",
    "Người tham gia 4",
  ];

  const devicesList = [
    "Máy chiếu",
    "Micro",
    "Bảng trắng",
    "Điều hòa",
    "Laptop",
  ];

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
<DialogContent className="sm:max-w-4xl max-h-[90vh] overflow-y-auto">
  <DialogHeader>
    <DialogTitle>Đặt phòng họp</DialogTitle>
    <DialogDescription>
      Chọn ngày, giờ và thiết lập lịch hẹn phòng họp.
    </DialogDescription>
  </DialogHeader>

  <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-4">
    {/* Cột trái: Form chọn ngày, giờ, phòng họp... */}
    <Card className="w-full">
      <CardHeader>
        <CardTitle>Thông tin lịch hẹn</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          {/* Ngày */}
          <div className="space-y-2">
            <Label htmlFor="date">Ngày</Label>
            <Input
              id="date"
              type="date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              className="w-full"
            />
          </div>

          {/* Giờ */}
          <div className="flex gap-2">
            <div className="w-1/2">
              <Label>Giờ</Label>
              <Select value={hour} onValueChange={setHour}>
                <SelectTrigger className="w-full">
                  <SelectValue placeholder="Chọn giờ" />
                </SelectTrigger>
                <SelectContent>
                  {generateHourOptions().map((h) => (
                    <SelectItem key={h} value={h}>
                      {h}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div className="w-1/2">
              <Label>Phút</Label>
              <Select value={minute} onValueChange={setMinute}>
                <SelectTrigger className="w-full">
                  <SelectValue placeholder="Chọn phút" />
                </SelectTrigger>
                <SelectContent>
                  {minuteOptions.map((m) => (
                    <SelectItem key={m} value={m}>
                      {m}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>
          </div>

          {/* Thời lượng */}
          <div className="space-y-2">
            <Label>Thời lượng</Label>
            <Select value={duration} onValueChange={setDuration}>
              <SelectTrigger className="w-full">
                <SelectValue placeholder="Chọn thời lượng" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="30">30 phút</SelectItem>
                <SelectItem value="60">60 phút</SelectItem>
                <SelectItem value="90">90 phút</SelectItem>
              </SelectContent>
            </Select>
          </div>

          {/* Phòng họp */}
          <div className="space-y-2">
            <Label>Phòng họp</Label>
            <Select value={room} onValueChange={setRoom}>
              <SelectTrigger className="w-full">
                <SelectValue placeholder="Chọn phòng họp" />
              </SelectTrigger>
              <SelectContent>
                {roomList.map((r) => (
                  <SelectItem key={r} value={r}>
                    {r}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          {/* Loại lịch */}
          <div className="space-y-2">
            <Label>Loại lịch</Label>
            <Select value={repeatType} onValueChange={setRepeatType}>
              <SelectTrigger className="w-full">
                <SelectValue placeholder="Chọn loại lịch" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="one-time">Một lần</SelectItem>
                <SelectItem value="daily">Hằng ngày</SelectItem>
                <SelectItem value="weekly">Hằng tuần</SelectItem>
                <SelectItem value="monthly">Hằng tháng</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>
      </CardContent>
    </Card>

    {/* Cột phải: TagSelect (Dịch vụ phòng họp) */}
    <div className="flex-1 overflow-y-auto max-h-[90vh]">
      {/* Người tham gia */}
      <TagSelect
        title="Người tham gia"
        placeholder="Tìm người tham gia..."
        data={participantsList}
      />
      <TagSelect
        title="Dịch vụ"
        placeholder="Tìm dịch vụ..."
        data={servicesList}
      />
      {/* Thiết bị */}
      <TagSelect
        title="Thiết bị"
        placeholder="Tìm thiết bị..."
        data={devicesList}
      />
      
    </div>
  </div>

  {/* Nút hành động */}
  <div className="flex justify-end gap-2 mt-6">
    <Button variant="outline" onClick={onClose}>
      Hủy
    </Button>
    <Button>Kiểm tra</Button>
  </div>
</DialogContent>

    </Dialog>
  );
};
