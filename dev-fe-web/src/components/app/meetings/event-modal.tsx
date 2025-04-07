import React from "react";
import { Dialog, DialogContent, DialogTitle, DialogDescription } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";

interface EventModalProps {
  event: any | null;
  onClose: () => void;
}

const EventModal: React.FC<EventModalProps> = ({ event, onClose }) => {
  if (!event) return null; // Không hiển thị nếu không có sự kiện

  // Format thời gian
  const formatTime = (dateString: string) => {
    const date = new Date(dateString);
    return `${date.toLocaleDateString()} ${date.toLocaleTimeString()}`;
  };

  // Đảm bảo roomPrice có giá trị hợp lệ, nếu không trả về giá trị mặc định là 0
  const roomPrice = event.extendedProps.roomPrice ?? 0;

  // Đảm bảo attendees và devices là mảng hợp lệ trước khi gọi map
  const attendees = Array.isArray(event.extendedProps.attendees) ? event.extendedProps.attendees : [];
  const devices = Array.isArray(event.extendedProps.devices) ? event.extendedProps.devices : [];

  return (
    <Dialog open={true} onOpenChange={onClose}>
      <DialogContent className="max-h-[80%] overflow-auto max-w-4xl grid grid-cols-1 md:grid-cols-2 gap-6 p-6">
        <DialogTitle className="col-span-2">{event.title}</DialogTitle>
        <DialogDescription className="col-span-2">
          {/* Thông tin cơ bản */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <div className="font-bold text-lg">Chủ trì:</div>
              <div>{event.extendedProps.host}</div>
            </div>
            <div>
              <div className="font-bold text-lg">Phòng:</div>
              <div>{event.extendedProps.room}</div>
            </div>
            <div>
              <div className="font-bold text-lg">Tầng:</div>
              <div>{event.extendedProps.floor}</div>
            </div>
            <div>
              <div className="font-bold text-lg">Ghi chú:</div>
              <div>{event.extendedProps.note}</div>
            </div>
            <div>
              <div className="font-bold text-lg">Loại lịch:</div>
              <div>{event.extendedProps.scheduleType}</div>
            </div>
            <div>
              <div className="font-bold text-lg">Giá phòng:</div>
              <div>{roomPrice.toLocaleString()} VND</div>
            </div>
            <div>
              <div className="font-bold text-lg">Thời gian bắt đầu:</div>
              <div>{formatTime(event.start)}</div>
            </div>
            <div>
              <div className="font-bold text-lg">Thời gian kết thúc:</div>
              <div>{formatTime(event.end)}</div>
            </div>
          </div>

          {/* Danh sách người tham gia */}
          <div className="mt-4">
            <div className="font-bold text-lg">Người tham gia:</div>
            <div className="flex flex-wrap gap-2 max-w-full max-h-[60vh] overflow-y-auto">
              {attendees.map((attendee: string, index: number) => (
                <span
                  key={index}
                  className="cursor-pointer bg-blue-500 text-white rounded-full px-4 py-2 text-sm"
                >
                  {attendee}
                </span>
              ))}
            </div>
          </div>

          {/* Danh sách thiết bị */}
          <div className="mt-4">
            <div className="font-bold text-lg">Thiết bị:</div>
            <div className="flex flex-wrap gap-2 max-w-full max-h-[60vh] overflow-y-auto">
              {devices.map((device: any, index: number) => (
                <span
                  key={index}
                  className="cursor-pointer bg-green-500 text-white rounded-full px-4 py-2 text-sm"
                >
                  {device.name} - {device.pricePerHour.toLocaleString()} VND/giờ
                </span>
              ))}
            </div>
          </div>

          {/* Thông tin liên hệ */}
          <div className="font-bold text-lg mt-4">Email:</div>
          <div>{event.extendedProps.email}</div>

          <div className="font-bold text-lg mt-2">Chi nhánh:</div>
          <div>{event.extendedProps.branch}</div>

          {/* Nút đóng modal */}
          <div className="mt-4">
            <Button onClick={onClose} variant="outline" size="sm">Đóng</Button>
          </div>
        </DialogDescription>
      </DialogContent>
    </Dialog>
  );
};

export default EventModal;
