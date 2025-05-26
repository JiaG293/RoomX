import React, { useEffect, useState } from "react";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogTitle,
  DialogFooter,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { ScrollArea } from "@/components/ui/scroll-area";
import {
  Calendar,
  Clock,
  User,
  Phone,
  Mail,
  Home,
  DollarSign,
  MapPin,
  Building,
  Layers,
  Users,
  XCircle,
  Barcode,
  IdCard,
  Hash,
} from "lucide-react";
import { ScheduleService } from "@/services/admin/schedule.service";

interface EventModalProps {
  event: any | null;
  onClose: () => void;
}

const EventModal: React.FC<EventModalProps> = ({ event, onClose }) => {
  const [eventDetails, setEventDetails] = useState<any>(null);

  useEffect(() => {
    if (!event) return;

    const fetchData = async () => {
      try {
        const service = new ScheduleService();
        const data = await service.getDetailSchedule(event.id);
        console.log(data);
        setEventDetails(data);
      } catch (error) {
        console.error("Error fetching event details:", error);
      }
    };

    fetchData();
  }, [event]);

  if (!eventDetails) return null;

  const {
    bookingCode,
    bookingRequestId,
    branch,
    building,
    floor,
    meeetingDate,
    participants,
    requester,
    roomCode,
    roomId,
    startTime,
    endTime,
    status,
    title,
    totalPrice,
    roomImage,
  } = eventDetails;

  return (
    <Dialog open={!!event} onOpenChange={(open) => !open && onClose()}>
      <DialogTrigger />
      <DialogContent className="w-full max-w-4xl max-h-[85vh] p-0 bg-background rounded-md flex flex-col">
        {/* Header cố định */}
        <div className="flex items-center justify-between px-6 py-4 border-b bg-muted sticky top-0 z-20">
          <DialogTitle className="text-xl font-semibold">
            {title || "Chi tiết sự kiện"}
          </DialogTitle>
        </div>

        {/* Body cuộn */}
        <ScrollArea className="flex-1 p-6 space-y-6 overflow-y-auto">
          {/* Thông tin chung booking */}
          {roomImage ? (
            <img
              src={roomImage}
              alt="Room"
              className="w-full h-64 object-cover rounded-md border"
            />
          ) : (
            <div className="w-full h-64 bg-gray-200 rounded-md flex items-center justify-center text-gray-500 text-sm border">
              No image available
            </div>
          )}

          <SectionCard
            title="Thông tin đặt lịch"
            icon={<Calendar className="text-blue-500 w-4 h-4" />}
          >
            <div className="grid grid-cols-2 gap-x-8 gap-y-3">
              <InfoItem
                icon={<Barcode className="text-pink-500" />}
                label="Mã đặt lịch"
                value={bookingCode || "-"}
              />
              <InfoItem
                icon={<DollarSign className="text-green-600" />}
                label="Tổng tiền"
                value={`${totalPrice?.toLocaleString()} VNĐ`}
              />
              <InfoItem
                icon={<Calendar className="text-blue-500" />}
                label="Ngày họp"
                value={meeetingDate || "-"}
              />
              <InfoItem
                icon={<Clock className="text-purple-500" />}
                label="Thời gian"
                value={`${startTime || "-"} - ${endTime || "-"}`}
              />
            </div>
          </SectionCard>

          {/* Thông tin địa điểm */}
          <SectionCard
            title="Địa điểm"
            icon={<MapPin className="text-green-500  w-4 h-4" />}
          >
            <div className="grid grid-cols-2 gap-x-8 gap-y-3">
              <InfoItem
                icon={<Home className="text-red-500" />}
                label="Chi nhánh"
                value={branch?.name || "-"}
              />
              <InfoItem
                icon={<Building className="text-orange-500" />}
                label="Tòa nhà"
                value={building?.name || "-"}
              />
              <InfoItem
                icon={<Layers className="text-indigo-500" />}
                label="Tầng"
                value={floor?.name || "-"}
              />
              <InfoItem
                icon={<Home className="text-yellow-600" />}
                label="Phòng"
                value={roomCode?.match(/\d+$/)?.[0] || "-"}
              />
            </div>
          </SectionCard>

          {/* Thông tin người yêu cầu */}
          <SectionCard
            title="Người đặt lịch"
            icon={<User className="text-teal-500 w-4 h-4" />}
          >
            <div className="grid grid-cols-2 gap-x-8 gap-y-3 text-sm">
              <InfoItem
                icon={<Hash className="text-emerald-600" />}
                label="Mã nhân viên"
                value={requester?.userCode || "-"}
              />
              <InfoItem
                icon={<User className="text-teal-500" />}
                label="Họ tên"
                value={`${requester?.firstName || ""} ${
                  requester?.lastName || ""
                }`}
              />
              <InfoItem
                icon={<Mail className="text-cyan-600" />}
                label="Email"
                value={requester?.email || "-"}
              />
              <InfoItem
                icon={<Phone className="text-lime-600" />}
                label="SĐT"
                value={requester?.phoneNumber || "-"}
              />
            </div>
          </SectionCard>

          {/* Danh sách người tham gia */}
          <SectionCard
            title={`Danh sách người tham gia (${participants?.length || 0})`}
            icon={<Users className="text-purple-600 w-4 h-4" />}
          >
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 max-h-48 overflow-y-auto">
              {participants?.map((p: any) => (
                <div
                  key={p.participantId}
                  className="flex items-center gap-3 p-2 bg-muted rounded-md"
                >
                  <User className="w-5 h-5 text-gray-500" />
                  <div className="text-sm">
                    <p className="font-semibold">{p.userCode || "N/A"}</p>
                    <p className="truncate max-w-xs">{p.email || "-"}</p>
                  </div>
                </div>
              ))}
            </div>
          </SectionCard>
        </ScrollArea>

        {/* Footer nút đóng */}
        <DialogFooter className="px-6 py-3 border-t bg-muted flex justify-end">
          <Button
            variant="secondary"
            className="border border-gray-800"
            onClick={onClose}
          >
            Đóng
          </Button>
          {/* <Button
            variant="destructive"
            size="sm"
            className="flex items-center gap-1"
            onClick={() => alert("Chưa gán sự kiện")}
          >
            <XCircle className="w-5 h-5" />
            Huỷ đặt lịch
          </Button> */}
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default EventModal;

const InfoItem = ({
  icon,
  label,
  value,
}: {
  icon?: React.ReactNode;
  label: string;
  value: string;
}) => (
  <div className="flex items-center gap-2 text-sm text-foreground">
    {icon && <span className="mt-0.5">{icon}</span>}
    <p>
      <strong>{label}:</strong> {value}
    </p>
  </div>
);

const SectionCard = ({
  title,
  icon,
  children,
}: {
  title: string;
  icon?: React.ReactNode;
  children: React.ReactNode;
}) => (
  <div className="bg-card p-4 rounded-md border border-border space-y-3">
    <h3 className="flex items-center gap-2 text-base font-semibold text-primary mb-3">
      {icon}
      {title}
    </h3>
    <div className="space-y-2">{children}</div>
  </div>
);
