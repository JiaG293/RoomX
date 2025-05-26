import React, { useEffect, useState } from "react";
import {
  Calendar,
  Clock,
  MapPin,
  Home,
  Building2,
  Users,
  Info,
  FileText,
  Check,
  XCircle,
  UserRound,
  CalendarDays,
  CalendarClock,
  Star,
  RefreshCcw,
  Flame,
  History,
  ListChecks,
  Settings,
} from "lucide-react";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { ScrollArea } from "@/components/ui/scroll-area";
import { toast } from "sonner";
import { Button } from "@/components/ui/button";
import { ScheduleService } from "@/services/admin/schedule.service";

interface EventModalApprovalProps {
  event: any;
  onClose: () => void;
}

const recurrenceMap: Record<string, string> = {
  DAILY: "Hằng ngày",
  WEEKLY: "Hằng tuần",
  MONTHLY: "Hằng tháng",
  YEARLY: "Hằng năm",
};

const daysMap: Record<string, string> = {
  MO: "Thứ 2",
  TU: "Thứ 3",
  WE: "Thứ 4",
  TH: "Thứ 5",
  FR: "Thứ 6",
  SA: "Thứ 7",
  SU: "Chủ nhật",
};

const formatDate = (str: string) =>
  new Date(str).toLocaleDateString("vi-VN", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });

const formatTime = (time: string) => time?.slice(0, 5);

const formatDateTime = (str: string) =>
  new Date(str).toLocaleString("vi-VN", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });

const InfoItem = ({
  icon,
  label,
  value,
}: {
  icon: React.ReactNode;
  label: string;
  value: string | number;
}) => (
  <div className="flex items-start gap-2">
    <div className="mt-1">{icon}</div>
    <div>
      <div className="text-sm font-medium text-muted-foreground">{label}</div>
      <div className="text-base">{value}</div>
    </div>
  </div>
);

const SectionCard = ({
  title,
  icon,
  children,
}: {
  title: string;
  icon: React.ReactNode;
  children: React.ReactNode;
}) => (
  <div className="border rounded-lg p-4 space-y-4">
    <div className="flex items-center gap-2 text-lg font-semibold">
      {icon}
      {title}
    </div>
    {children}
  </div>
);

const EventModalApprovalUser: React.FC<EventModalApprovalProps> = ({
  event,
  onClose,
}) => {
  const [detail, setDetail] = useState<any>(null);

  useEffect(() => {
    if (event?.id) {
      const fetchDetail = async () => {
        try {
          const service = new ScheduleService();
          const res = await service.getDetailPendingSchedule(event.id);
          setDetail(res);
        } catch (error) {
          console.error("Lỗi lấy chi tiết lịch:", error);
        }
      };
      fetchDetail();
    }
  }, [event]);

  const handleApprove = async () => {
    try {
      const service = new ScheduleService();
      await service.approveSchedules(event.id);
      toast.success("Duyệt lịch họp thành công!");
      onClose();
      setTimeout(() => window.location.reload(), 1000);
    } catch (error) {
      console.error("Lỗi duyệt lịch:", error);
    }
  };

  // State cho modal nhập lý do huỷ
  const [isRejectModalOpen, setIsRejectModalOpen] = useState(false);
  const [cancelReason, setCancelReason] = useState("");

  // Hàm gọi API từ chối với lý do (vẫn giữ tên handleReject)
  const handleReject = async () => {
    if (!cancelReason.trim()) {
      toast.error("Vui lòng nhập lý do huỷ!");
      return;
    }
    try {
      const service = new ScheduleService();
      // Giả sử API rejectSchedules có thể nhận thêm lý do
      await service.rejectSchedules(event.id, cancelReason);
      toast.success("Huỷ lịch họp thành công!");
      setCancelReason("");
      setIsRejectModalOpen(false);
      onClose();
      setTimeout(() => window.location.reload(), 1000);
    } catch (error) {
      console.error("Lỗi từ chối lịch:", error);
      toast.error("Có lỗi xảy ra khi từ chối lịch họp.");
    }
  };

  if (!event || !detail) return null;

  const {
    title,
    description,
    startDate,
    endDate,
    startTime,
    endTime,
    daysOfWeek,
    recurrenceType,
    capacity,
    priority,
    branch,
    approvalStatus,
    createdAt,
    updatedAt,
    requester,
    services,
    equipments,
    participants,
  } = detail;

  const formattedDays =
    daysOfWeek &&
    daysOfWeek
      .split(",")
      .map((d: string) => daysMap[d] || d)
      .join(", ");

  return (
    <>
      <Dialog open={!!event} onOpenChange={(open) => !open && onClose()}>
        <DialogContent className="w-full max-w-4xl max-h-[85vh] p-0 bg-background rounded-md flex flex-col">
          {/* Header */}
          <div className="flex items-center justify-between px-6 py-4 border-b bg-muted sticky top-0 z-20">
            <DialogTitle className="text-xl font-semibold">
              {title || "Chi tiết sự kiện"}
            </DialogTitle>
          </div>

          {/* Body */}
          <ScrollArea className="flex-1 p-6 space-y-6 overflow-y-auto">
            {/* Thông tin lịch */}
            <SectionCard
              title="Thông tin lịch họp"
              icon={<CalendarDays className="text-blue-500 w-4 h-4" />}
            >
              <div className="grid grid-cols-2 gap-x-8 gap-y-4">
                <InfoItem
                  icon={<Calendar className="text-blue-600" />}
                  label="Từ ngày"
                  value={formatDate(startDate)}
                />
                <InfoItem
                  icon={<Calendar className="text-blue-600" />}
                  label="Đến ngày"
                  value={formatDate(endDate)}
                />
                <InfoItem
                  icon={<Clock className="text-purple-600" />}
                  label="Giờ"
                  value={`${formatTime(startTime)} - ${formatTime(endTime)}`}
                />
                <InfoItem
                  icon={<RefreshCcw className="text-orange-600" />}
                  label="Lặp lại"
                  value={recurrenceMap[recurrenceType] || "Không"}
                />
                <InfoItem
                  icon={<Flame className="text-red-500" />}
                  label="Ưu tiên"
                  value={priority === 0 ? "Thấp" : "Cao"}
                />
                <InfoItem
                  icon={<Users className="text-green-500" />}
                  label="Sức chứa"
                  value={`${capacity} người`}
                />
                <InfoItem
                  icon={<CalendarClock className="text-gray-700" />}
                  label="Các ngày"
                  value={formattedDays}
                />
              </div>
            </SectionCard>

            {/* Người yêu cầu */}
            <SectionCard
              title="Người yêu cầu"
              icon={<UserRound className="text-indigo-500" />}
            >
              <div className="grid grid-cols-2 gap-x-8 gap-y-4">
                <InfoItem
                  icon={<UserRound />}
                  label="Tên"
                  value={`${requester?.firstName} ${requester?.lastName}`}
                />
                <InfoItem
                  icon={<FileText />}
                  label="Email"
                  value={requester?.email}
                />
                <InfoItem
                  icon={<Info />}
                  label="SĐT"
                  value={requester?.phoneNumber}
                />
              </div>
            </SectionCard>

            {/* Địa điểm */}
            <SectionCard
              title="Địa điểm"
              icon={<MapPin className="text-pink-500" />}
            >
              <div className="grid grid-cols-2 gap-x-8 gap-y-4">
                <InfoItem
                  icon={<Home />}
                  label="Chi nhánh"
                  value={branch?.name || "-"}
                />
              </div>
            </SectionCard>

            {/* Thiết bị */}
            {equipments?.length > 0 && (
              <SectionCard
                title="Thiết bị yêu cầu"
                icon={<Settings className="text-amber-600" />}
              >
                <ul className="list-disc pl-6 space-y-1">
                  {equipments.map((item: any) => (
                    <li key={item.id}>
                      {item.name} x{item.quantity}
                    </li>
                  ))}
                </ul>
              </SectionCard>
            )}

            {/* Dịch vụ */}
            {services?.length > 0 && (
              <SectionCard
                title="Dịch vụ yêu cầu"
                icon={<ListChecks className="text-emerald-600" />}
              >
                <ul className="list-disc pl-6 space-y-1">
                  {services.map((item: any) => (
                    <li key={item.id}>
                      {item.name} x{item.quantity}
                    </li>
                  ))}
                </ul>
              </SectionCard>
            )}

            {/* Người tham gia */}
            {participants?.length > 0 && (
              <SectionCard
                title="Người tham gia"
                icon={<Users className="text-cyan-600" />}
              >
                <ul className="list-disc pl-6 space-y-1">
                  {participants.map((email: string, index: number) => (
                    <li key={index}>{email}</li>
                  ))}
                </ul>
              </SectionCard>
            )}

            {/* Tạo & cập nhật */}
            <SectionCard
              title="Lịch sử"
              icon={<History className="text-gray-500" />}
            >
              <div className="grid grid-cols-2 gap-x-8 gap-y-4">
                <InfoItem
                  icon={<Clock />}
                  label="Tạo lúc"
                  value={formatDateTime(createdAt)}
                />
                <InfoItem
                  icon={<Clock />}
                  label="Cập nhật lúc"
                  value={formatDateTime(updatedAt)}
                />
                <InfoItem
                  icon={<Info />}
                  label="Trạng thái"
                  value={approvalStatus}
                />
              </div>
            </SectionCard>
          </ScrollArea>

          {/* Footer */}
          <div className="flex justify-end gap-4 px-6 py-4 border-t bg-muted">
            <Button
              variant="destructive"
              onClick={() => setIsRejectModalOpen(true)} // Mở modal nhập lý do thay vì gọi handleReject trực tiếp
              className="gap-2"
            >
              <XCircle size={16} />
              Huỷ đặt phòng
            </Button>

            <Button onClick={handleApprove} className="bg-green-500 gap-2">
              <Check size={16} />
              Duyệt
            </Button>
          </div>
        </DialogContent>
      </Dialog>

      <Dialog
        open={isRejectModalOpen}
        onOpenChange={(open) => setIsRejectModalOpen(open)}
      >
        <DialogContent className="w-full max-w-[800px] p-6 bg-background rounded-md flex flex-col gap-6">
          <DialogHeader>
            <DialogTitle>Nhập lý do huỷ lịch họp</DialogTitle>
          </DialogHeader>

          {/* Container nút chọn */}
          <div className="grid grid-cols-2 gap-3">
            {cancelReasons.map((reason) => (
              <button
                key={reason}
                type="button"
                onClick={() => setCancelReason(reason)}
                className={`
            text-sm font-medium px-4 py-2 rounded-md border transition-colors
            ${
              cancelReason === reason
                ? "bg-blue-600 border-blue-600 text-white"
                : "bg-white border-gray-300 text-gray-700 hover:bg-gray-100"
            }
            focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-offset-1
          `}
              >
                {reason}
              </button>
            ))}
          </div>

          <textarea
            rows={4}
            value={cancelReason}
            onChange={(e) => setCancelReason(e.target.value)}
            placeholder="Nhập lý do huỷ lịch họp..."
            className="bg-white w-full p-3 border border-gray-300 rounded-md resize-none placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />

          <div className="flex justify-end gap-3">
            <Button
              variant="outline"
              onClick={() => {
                setCancelReason("");
                setIsRejectModalOpen(false);
              }}
            >
              Huỷ
            </Button>
            <Button
              onClick={handleReject}
              className="bg-red-600 hover:bg-red-700 text-white"
            >
              Xác nhận huỷ
            </Button>
          </div>
        </DialogContent>
      </Dialog>
    </>
  );
};

const cancelReasons = [
  "Tôi bị trùng lịch vào thời điểm này",
  "Tôi có việc cá nhân đột xuất",
  "Tôi không còn nhu cầu tham gia cuộc họp này",
  "Không đủ thành viên tham gia",
  "Tôi cần dời lịch sang thời gian khác",
  "Tôi cần điều chỉnh thông tin lịch",
  "Tôi gặp vấn đề kỹ thuật (thiết bị, mạng, v.v.)",
  "Tôi nhận được thông báo thay đổi từ ban tổ chức",
];

export default EventModalApprovalUser;
