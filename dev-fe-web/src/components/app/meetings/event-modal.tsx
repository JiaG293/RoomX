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
  Home,
  Phone,
  Mail,
  Info,
  CheckCircle,
  Building2,
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
      <DialogContent
        className="max-w-[900px] w-[90vw] max-h-[90vh] p-6 font-sans bg-white rounded-lg flex flex-col"
        style={{ backdropFilter: "blur(8px)" }}
      >
        <DialogTitle className="text-2xl font-semibold text-gray-900 mb-6 flex-shrink-0">
          Chi tiết sự kiện
        </DialogTitle>

        {/* Phần body cuộn */}
        <DialogDescription className="flex-grow overflow-auto">
          {eventDetails ? (
            <div className="space-y-8 min-w-[300px]">
              {/* Event Info */}
              <Card title="Thông tin sự kiện" gradientFrom="from-blue-200" gradientTo="to-blue-50">
                <InfoGrid>
                  <InfoRow
                    icon={<Clock className="text-blue-600" />}
                    label="Mã đặt chỗ"
                    value={eventDetails.bookingCode}
                  />
                  <InfoRow
                    icon={<Calendar className="text-green-600" />}
                    label="Ngày họp"
                    value={eventDetails.meetingDate}
                  />
                  <InfoRow
                    icon={<Clock className="text-purple-600" />}
                    label="Giờ bắt đầu"
                    value={eventDetails.meetingStart}
                  />
                  <InfoRow
                    icon={<Clock className="text-pink-600" />}
                    label="Giờ kết thúc"
                    value={eventDetails.meetingEnd}
                  />
                </InfoGrid>
              </Card>

              {/* Status */}
              <Card title="Tình trạng" gradientFrom="from-green-200" gradientTo="to-green-50">
                <p className="text-gray-700 text-base flex items-center gap-2">
                  <CheckCircle className="text-green-700" size={20} />
                  <strong>Trạng thái:</strong> {eventDetails.status}
                </p>
              </Card>

              {/* Room Info */}
              <Card title="Thông tin phòng" gradientFrom="from-yellow-200" gradientTo="to-yellow-50">
                <InfoGrid>
                  <InfoRow
                    icon={<Home className="text-yellow-700" />}
                    label="Phòng"
                    value={eventDetails.room?.roomCode || "Chưa phân phòng"}
                  />
                  <InfoRow
                    icon={<Info className="text-indigo-700" />}
                    label="Mô tả phòng"
                    value={eventDetails.room?.description || "Không có mô tả"}
                  />
                  <InfoRow
                    icon={<Building2 className="text-red-700" />}
                    label="Tình trạng phòng"
                    value={eventDetails.room?.status || "Chưa cập nhật"}
                  />
                </InfoGrid>
              </Card>

              {/* Requester */}
              <Card title="Người yêu cầu" gradientFrom="from-teal-200" gradientTo="to-teal-50">
                <InfoGrid>
                  <InfoRow
                    icon={<User className="text-teal-700" />}
                    label="Họ tên"
                    value={`${eventDetails.requester?.firstName} ${eventDetails.requester?.lastName}`}
                  />
                  <InfoRow
                    icon={<Phone className="text-cyan-700" />}
                    label="Số điện thoại"
                    value={eventDetails.requester?.phoneNumber}
                  />
                  <InfoRow
                    icon={<Mail className="text-purple-700" />}
                    label="Email"
                    value={eventDetails.requester?.email}
                  />
                </InfoGrid>
              </Card>

              {/* Participants */}
              <Card title="Danh sách tham gia" gradientFrom="from-pink-200" gradientTo="to-pink-50">
                {eventDetails.participants?.length ? (
                  <ul className="list-disc pl-5 space-y-1 text-gray-700 text-base max-h-[250px] overflow-auto rounded-md">
                    {eventDetails.participants.map((p: any) => (
                      <li key={p.participantId}>
                        {p.userCode} - {p.email}
                      </li>
                    ))}
                  </ul>
                ) : (
                  <p className="text-gray-500 text-base">Không có người tham gia</p>
                )}
              </Card>
            </div>
          ) : (
            <p className="text-gray-500 text-base">Đang tải thông tin sự kiện...</p>
          )}
        </DialogDescription>

        <DialogFooter className="mt-6 flex-shrink-0 flex justify-end">
          <Button variant="secondary" onClick={onClose} className="px-6 py-2 text-base">
            Đóng
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

const Card = ({
  children,
  title,
  gradientFrom,
  gradientTo,
}: {
  children: React.ReactNode;
  title: string;
  gradientFrom: string;
  gradientTo: string;
}) => {
  return (
    <div
      className={`p-5 rounded-xl shadow-lg border border-gray-200 bg-gradient-to-r ${gradientFrom} ${gradientTo} transition-transform hover:scale-[1.02]`}
      style={{ backdropFilter: "blur(8px)" }}
    >
      <h3 className="text-lg font-semibold mb-4 text-gray-900">{title}</h3>
      {children}
    </div>
  );
};

const InfoGrid = ({ children }: { children: React.ReactNode }) => (
  <div className="grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 gap-6">{children}</div>
);

const InfoRow = ({
  icon,
  label,
  value,
}: {
  icon?: React.ReactNode;
  label: string;
  value: string;
}) => (
  <div className="flex items-center space-x-3">
    {icon && <div className="text-xl">{icon}</div>}
    <p className="text-base text-gray-800 leading-tight">
      <strong>{label}:</strong> {value}
    </p>
  </div>
);

export default EventModal;
