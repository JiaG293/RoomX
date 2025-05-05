import React, { useEffect, useState } from "react";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogTitle,
  DialogDescription,
  DialogFooter,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { ScheduleService } from "@/services/admin/schedule.service";
import {
  Calendar,
  Clock,
  User,
  DollarSign,
  Home,
  Phone,
  Mail,
} from "lucide-react";

interface EventModalProps {
  event: any | null;
  onClose: () => void;
}

const EventModal: React.FC<EventModalProps> = ({ event, onClose }) => {
  const [eventDetails, setEventDetails] = useState<any>(null);

  useEffect(() => {
    if (!event) return;

    const fetchEventDetails = async () => {
      try {
        const scheduleService = new ScheduleService();
        const data = await scheduleService.getDetailSchedule(event.id);
        console.log("chi tiết:", data);
        setEventDetails(data);
      } catch (error) {
        console.error("Error fetching event details:", error);
      }
    };

    fetchEventDetails();
  }, [event]);

  if (!event) return null;

  return (
    <Dialog open={!!event} onOpenChange={(open) => !open && onClose()}>
      <DialogTrigger />
      <DialogContent className="w-full max-w-[1000px] max-h-[80vh] p-6 overflow-auto bg-white shadow-xl">
      <DialogTitle className="text-xl font-semibold text-blue-600">
          Chi tiết sự kiện
        </DialogTitle>
        <DialogDescription>
          {eventDetails ? (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
              {/* Event Info Card */}
              <div className="p-4 border border-blue-200 rounded-lg shadow-sm bg-blue-50">
                <div className="flex items-center space-x-2 text-blue-600">
                  <Clock size={20} />
                  <p>
                    <strong>Mã đặt chỗ:</strong> {eventDetails.bookingCode}
                  </p>
                </div>
                <div className="flex items-center space-x-2 text-blue-600">
                  <Calendar size={20} />
                  <p>
                    <strong>Ngày họp:</strong> {eventDetails.meetingDate}
                  </p>
                </div>
                <div className="flex items-center space-x-2 text-blue-600">
                  <Clock size={20} />
                  <p>
                    <strong>Giờ bắt đầu:</strong> {eventDetails.meetingStart}
                  </p>
                </div>
                <div className="flex items-center space-x-2 text-blue-600">
                  <Clock size={20} />
                  <p>
                    <strong>Giờ kết thúc:</strong> {eventDetails.meetingEnd}
                  </p>
                </div>
              </div>

              {/* Room Info Card */}
              <div className="p-4 border border-green-200 rounded-lg shadow-sm bg-green-50">
                <div className="flex items-center space-x-2 text-green-600">
                  <Home size={20} />
                  <p>
                    <strong>Phòng:</strong>{" "}
                    {eventDetails.room?.roomCode || "Chưa phân phòng"}
                  </p>
                </div>
                <div className="flex items-center space-x-2 text-green-600">
                  <p>
                    <strong>Mô tả phòng:</strong>{" "}
                    {eventDetails.room?.description || "Không có mô tả"}
                  </p>
                </div>
                <div className="flex items-center space-x-2 text-green-600">
                  <p>
                    <strong>Tình trạng phòng:</strong>{" "}
                    {eventDetails.room?.status || "Chưa cập nhật"}
                  </p>
                </div>
              </div>

              {/* Participant Info Card */}
              <div className="md:col-span-2 p-4 border border-yellow-200 rounded-lg shadow-sm bg-yellow-50">
                <div className="flex items-center space-x-2 text-yellow-600">
                  <User size={20} />
                  <p>
                    <strong>Thành viên tham gia:</strong>
                  </p>
                </div>
                <ul className="list-disc pl-6 space-y-2 mt-2">
                  {eventDetails.participants?.map((participant: any) => (
                    <li key={participant.participantId} className="text-yellow-600">
                      {participant.userCode} - {participant.email}
                    </li>
                  ))}
                </ul>
              </div>

              {/* Requester Info Card */}
              <div className="p-4 border border-purple-200 rounded-lg shadow-sm bg-purple-50">
                <div className="flex items-center space-x-2 text-purple-600">
                  <User size={20} />
                  <p>
                    <strong>Yêu cầu bởi:</strong>{" "}
                    {eventDetails.requester?.firstName}{" "}
                    {eventDetails.requester?.lastName}
                  </p>
                </div>
                <div className="flex items-center space-x-2 text-purple-600">
                  <Phone size={20} />
                  <p>
                    <strong>Số điện thoại:</strong>{" "}
                    {eventDetails.requester?.phoneNumber}
                  </p>
                </div>
                <div className="flex items-center space-x-2 text-purple-600">
                  <Mail size={20} />
                  <p>
                    <strong>Email:</strong> {eventDetails.requester?.email}
                  </p>
                </div>
              </div>

              {/* Price Info Card */}
              <div className="p-4 border border-teal-200 rounded-lg shadow-sm bg-teal-50">
                <div className="flex items-center space-x-2 text-teal-600">
                  <DollarSign size={20} />
                  <p>
                    <strong>Tổng giá trị:</strong>{" "}
                    {eventDetails.totalPrice
                      ? `${eventDetails.totalPrice} VND`
                      : "Chưa cập nhật"}
                  </p>
                </div>
              </div>

              {/* Status Info Card */}
              <div className="md:col-span-2 p-4 border border-red-200 rounded-lg shadow-sm bg-red-50">
                <div className="flex items-center space-x-2 text-red-600">
                  <p>
                    <strong>Tình trạng sự kiện:</strong> {eventDetails.status}
                  </p>
                </div>
              </div>
            </div>
          ) : (
            <p>Đang tải thông tin sự kiện...</p>
          )}
        </DialogDescription>
        <DialogFooter>
          <Button variant="secondary" onClick={onClose}>
            Đóng
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default EventModal;
