import React, { useEffect, useState } from "react";
import { Dialog, DialogTrigger, DialogContent, DialogTitle, DialogDescription, DialogFooter } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button"; // Assuming Button is a custom UI component
import { ScheduleService } from "@/services/admin/schedule.service";
import { Calendar, Clock, User, DollarSign, Home, Phone, Mail } from "lucide-react"; // Importing Lucid Icons

interface EventModalProps {
  event: any | null;
  onClose: () => void;
}

const EventModal: React.FC<EventModalProps> = ({ event, onClose }) => {
  const [eventDetails, setEventDetails] = useState<any>(null); // State to store event details

  // Fetch event details when the event is set or changes
  useEffect(() => {
    if (!event) {
      return; // Return early if there's no event
    }

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

  if (!event) {
    return null; 
  }

  return (
    <Dialog open={!!event} onOpenChange={(open) => !open && onClose()}>
      <DialogTrigger />
      <DialogContent className="w-[800px] max-h-[80vh] p-6 space-y-6 overflow-auto">
        <DialogTitle className="text-xl font-semibold">Chi tiết sự kiện</DialogTitle>
        <DialogDescription>
          {eventDetails ? (
            <>
              {/* Event Info Card */}
              <div className="space-y-4">
                <div className="p-4 border border-gray-200 rounded-lg shadow-sm">
                  <div className="flex items-center space-x-2">
                    <Calendar size={20} className="text-gray-500" />
                    <p><strong>Mã sự kiện:</strong> {eventDetails.id}</p>
                  </div>
                  <div className="flex items-center space-x-2">
                    <Clock size={20} className="text-gray-500" />
                    <p><strong>Mã đặt chỗ:</strong> {eventDetails.bookingCode}</p>
                  </div>
                  <div className="flex items-center space-x-2">
                    <Calendar size={20} className="text-gray-500" />
                    <p><strong>Ngày họp:</strong> {eventDetails.meetingDate}</p>
                  </div>
                  <div className="flex items-center space-x-2">
                    <Clock size={20} className="text-gray-500" />
                    <p><strong>Giờ bắt đầu:</strong> {eventDetails.meetingStart}</p>
                  </div>
                  <div className="flex items-center space-x-2">
                    <Clock size={20} className="text-gray-500" />
                    <p><strong>Giờ kết thúc:</strong> {eventDetails.meetingEnd}</p>
                  </div>
                </div>

                {/* Room Info Card */}
                <div className="p-4 border border-gray-200 rounded-lg shadow-sm">
                  <div className="flex items-center space-x-2">
                    <Home size={20} className="text-gray-500" />
                    <p><strong>Phòng:</strong> {eventDetails.room?.roomCode || "Chưa phân phòng"}</p>
                  </div>
                  <div className="flex items-center space-x-2">
                    <p><strong>Mô tả phòng:</strong> {eventDetails.room?.description || "Không có mô tả"}</p>
                  </div>
                  <div className="flex items-center space-x-2">
                    <p><strong>Tình trạng phòng:</strong> {eventDetails.room?.status || "Chưa cập nhật"}</p>
                  </div>
                </div>

                {/* Participant Info Card */}
                <div className="p-4 border border-gray-200 rounded-lg shadow-sm">
                  <div className="flex items-center space-x-2">
                    <User size={20} className="text-gray-500" />
                    <p><strong>Thành viên tham gia:</strong></p>
                  </div>
                  <ul className="list-disc pl-6 space-y-2">
                    {eventDetails.participants?.map((participant: any) => (
                      <li key={participant.participantId}>
                        {participant.userCode} - {participant.email}
                      </li>
                    ))}
                  </ul>
                </div>

                {/* Requester Info Card */}
                <div className="p-4 border border-gray-200 rounded-lg shadow-sm">
                  <div className="flex items-center space-x-2">
                    <User size={20} className="text-gray-500" />
                    <p><strong>Yêu cầu bởi:</strong> {eventDetails.requester?.firstName} {eventDetails.requester?.lastName}</p>
                  </div>
                  <div className="flex items-center space-x-2">
                    <Phone size={20} className="text-gray-500" />
                    <p><strong>Số điện thoại:</strong> {eventDetails.requester?.phoneNumber}</p>
                  </div>
                  <div className="flex items-center space-x-2">
                    <Mail size={20} className="text-gray-500" />
                    <p><strong>Email:</strong> {eventDetails.requester?.email}</p>
                  </div>
                </div>

                {/* Price Info Card */}
                <div className="p-4 border border-gray-200 rounded-lg shadow-sm">
                  <div className="flex items-center space-x-2">
                    <DollarSign size={20} className="text-gray-500" />
                    <p><strong>Tổng giá trị:</strong> {eventDetails.totalPrice ? `${eventDetails.totalPrice} VND` : "Chưa cập nhật"}</p>
                  </div>
                </div>

                {/* Status Info Card */}
                <div className="p-4 border border-gray-200 rounded-lg shadow-sm">
                  <div className="flex items-center space-x-2">
                    <p><strong>Tình trạng sự kiện:</strong> {eventDetails.status}</p>
                  </div>
                </div>
              </div>
            </>
          ) : (
            <p>Đang tải thông tin sự kiện...</p>
          )}
        </DialogDescription>
        <DialogFooter>
          <Button variant="secondary" onClick={onClose}>Đóng</Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default EventModal;
