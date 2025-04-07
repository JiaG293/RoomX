import React from "react";
import {
  Dialog,
  DialogContent,
  DialogTitle,
  DialogDescription,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Calendar, Users, Tag, Monitor, User, DollarSign, Locate } from "lucide-react"; // Đảm bảo bạn nhập đúng tên các icon từ lucid-icons

interface EventModalProps {
  event: any | null;
  onClose: () => void;
}

const EventModal: React.FC<EventModalProps> = ({ event, onClose }) => {
  if (!event) return null;

  const formatTime = (dateString: string) => {
    const date = new Date(dateString);
    return `${date.toLocaleDateString()} ${date.toLocaleTimeString()}`;
  };

  const roomPrice = event.extendedProps.totalPrice ?? 0;

  const attendees = Array.isArray(event.extendedProps.attendees)
    ? event.extendedProps.attendees
    : [];
  const devices = Array.isArray(event.extendedProps.devices)
    ? event.extendedProps.devices
    : [];

  return (
    <Dialog open={true} onOpenChange={onClose}>
      <DialogContent className="max-h-[80%] overflow-auto max-w-4xl p-6">
        <DialogTitle className="text-center text-2xl font-semibold">
          {event.title}
        </DialogTitle>
        <DialogDescription className="space-y-6 mt-4">
          {/* Thông tin cơ bản */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 justify-center items-center">
            <div className="card p-4 border rounded-lg shadow-lg">
              <div className="flex items-center space-x-2">
                <User className="w-6 h-6 text-gray-500" />
                <div className="font-bold text-lg">Chủ trì:</div>
                <div>{event.extendedProps.host || "Không có thông tin"}</div>
              </div>
            </div>

            <div className="card p-4 border rounded-lg shadow-lg">
              <div className="flex items-center space-x-2">
                <Locate className="w-6 h-6 text-gray-500" />
                <div className="font-bold text-lg">Vị trí:</div>
                {/* <div>{roomPrice.toLocaleString()} VND</div> */}
              </div>
            </div>

            <div className="card p-4 border rounded-lg shadow-lg">
              <div className="flex items-center space-x-2">
                <Calendar className="w-6 h-6 text-gray-500" />
                <div className="font-bold text-lg">Thời gian bắt đầu:</div>
              </div>
              <div>{formatTime(event.start)}</div>
            </div>

            <div className="card p-4 border rounded-lg shadow-lg">
              <div className="flex items-center space-x-2">
                <Calendar className="w-6 h-6 text-gray-500" />
                <div className="font-bold text-lg">Thời gian kết thúc:</div>
              </div>
              <div>{formatTime(event.end)}</div>
            </div>
          </div>

          {/* Danh sách người tham gia */}
          <div className="card p-4 border rounded-lg shadow-lg">
            <div className="flex items-center space-x-2">
              <Users className="w-6 h-6 text-gray-500" />
              <div className="font-bold text-lg">Người tham gia:</div>
            </div>
            <div className="flex flex-wrap gap-2">
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
          <div className="card p-4 border rounded-lg shadow-lg">
            <div className="flex items-center space-x-2">
              <Monitor className="w-6 h-6 text-gray-500" />
              <div className="font-bold text-lg">Thiết bị:</div>
            </div>
            <div className="flex flex-wrap gap-2">
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
          <div className="card p-4 border rounded-lg shadow-lg">
            <div className="font-bold text-lg">Email:</div>
            <div>{event.extendedProps.email || "Không có thông tin"}</div>

            <div className="font-bold text-lg mt-2">Chi nhánh:</div>
            <div>{event.extendedProps.branch || "Không có thông tin"}</div>
          </div>

          {/* Nút đóng modal */}
          <div className="mt-4 text-center">
            <Button onClick={onClose} variant="outline" size="sm">
              Đóng
            </Button>
          </div>
        </DialogDescription>
      </DialogContent>
    </Dialog>
  );
};

export default EventModal;
