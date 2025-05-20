import React, { useEffect, useState } from "react";
import {
  XCircle,
  Check,
  FileText,
  Calendar,
  Clock,
  RefreshCcw,
  Users,
  Info,
  Building2,
  DoorOpen,
  AlignVerticalSpaceAround,
  CalendarCheck,
  Star,
  CalendarClock,
  Flame,
  CalendarDays,
  MapPin,
  History,
} from "lucide-react";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { ScheduleService } from "@/services/admin/schedule.service";
import { toast } from "sonner";
import { PlaceService } from "@/services/admin/place.service";

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

interface PlaceDetail {
  id: string;
  parentId: string | null;
  code: string;
  name: string;
  layout: string;
  placeType: string;
}

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

const EventModalApproval: React.FC<EventModalApprovalProps> = ({
  event,
  onClose,
}) => {
  const [detail, setDetail] = useState<any>(null);
  const [place, setPlace] = useState(null);
  const [building, setBuilding] = useState(null);
  const [floor, setFloor] = useState(null);

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
  // const placeId = detail?.room?.placeId;

  // useEffect(() => {
  //   if (!placeId) return;

  //   const fetchPlace = async () => {
  //     const placeService = new PlaceService();
  //     try {
  //       const data = await placeService.getDetailPlaces(placeId);
  //       if (data?.building) setBuilding(data.building);
  //       if (data?.floor) setFloor(data.floor);
  //     } catch (error) {
  //       // error đã được handle trong service
  //     }
  //   };

  //   fetchPlace();
  // }, [placeId]);

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

  const handleReject = async () => {
    try {
      const service = new ScheduleService();
      await service.rejectSchedules(event.id);
      toast.success("Từ chối lịch họp thành công!");
      onClose();
      setTimeout(() => window.location.reload(), 1000);
    } catch (error) {
      console.error("Lỗi duyệt lịch:", error);
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
    recurrenceInterval,
    capacity,
    priority,
    branch,
    room,
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
    <Dialog open={!!event} onOpenChange={(open) => !open && onClose()}>
      <DialogContent className="max-w-2xl p-4">
        <DialogHeader>
          <DialogTitle className="text-xl font-bold">{title}</DialogTitle>
        </DialogHeader>
        <div className="grid gap-3 max-h-[70vh] overflow-y-auto mt-2 text-sm grid-cols-1 md:grid-cols-2">
          {/* Trạng thái & Ưu tiên */}
          <Card className="bg-gradient-to-r from-green-100 to-blue-100 dark:from-green-900 dark:to-blue-900 border col-span-1 md:col-span-2">
            <CardContent className="py-3 text-gray-800 dark:text-gray-200 grid grid-cols-2 gap-4">
              <div className="flex items-center gap-2">
                {approvalStatus === "APPROVED" ? (
                  <Check className="w-4 h-4 text-green-600 dark:text-green-400" />
                ) : (
                  <XCircle className="w-4 h-4 text-red-600 dark:text-red-400" />
                )}
                <strong>Trạng thái:</strong> {approvalStatus}
              </div>

              <div className="flex items-center gap-2">
                <Flame className="w-4 h-4 text-orange-500 dark:text-orange-400" />
                <strong>Ưu tiên:</strong> {priority}
              </div>

              <div className="flex items-center gap-2">
                <CalendarDays className="w-4 h-4 text-slate-500 dark:text-slate-400" />
                <strong>Ngày tạo:</strong> {formatDateTime(createdAt)}
              </div>

              <div className="flex items-center gap-2">
                <History className="w-4 h-4 text-slate-500 dark:text-slate-400" />
                <strong>Ngày cập nhật:</strong> {formatDateTime(updatedAt)}
              </div>
            </CardContent>
          </Card>

          {/* Thời gian & lặp lại */}
          <Card className="bg-emerald-100 dark:bg-emerald-900 border">
            <CardContent className="py-3 space-y-2 text-emerald-800 dark:text-emerald-200">
              <h3 className="font-semibold text-base">Thông tin lịch</h3>
              <div className="space-y-2">
                <div>
                  <div className="flex items-center gap-4 mt-1">
                    <div className="flex items-center gap-2">
                      <Calendar className="w-4 h-4 text-teal-600" />
                      <span>{formatDate(startDate)}</span>
                    </div>
                    <span>-</span>
                    <div className="flex items-center gap-2">
                      <Calendar className="w-4 h-4 text-teal-600" />
                      <span>{formatDate(endDate)}</span>
                    </div>
                  </div>
                </div>

                <div>
                  <span className="font-semibold flex items-center gap-4">
                    <Clock className="w-4 h-4 text-teal-600" />
                    <div className="flex items-center gap-4 mt-1">
                      <div className="flex items-center gap-2">
                        <span>{formatTime(startTime)}</span>
                      </div>
                      <span>-</span>
                      <div className="flex items-center gap-2">
                        <span>{formatTime(endTime)}</span>
                      </div>
                    </div>
                  </span>
                </div>

                <div className="flex items-center gap-2">
                  <RefreshCcw className="w-4 h-4 text-purple-600" />
                  <strong>Lặp:</strong>{" "}
                  {recurrenceMap[recurrenceType] || recurrenceType}
                </div>
              </div>
            </CardContent>
          </Card>

          {/* Địa điểm */}
          <Card className="bg-amber-100 dark:bg-amber-900 border">
            <CardContent className="py-3 space-y-2 text-amber-800 dark:text-amber-200">
              <h3 className="font-semibold text-base">Địa điểm</h3>
              <div className="space-y-2">
                {branch && (
                  <div className="flex items-center gap-2">
                    <MapPin className="w-4 h-4 text-amber-600" />
                    <strong>Chi nhánh:</strong> {branch.name}
                  </div>
                )}
                {room && (
                  <div className="flex items-center gap-2">
                    <DoorOpen className="w-4 h-4 text-amber-600" />
                    <strong>Phòng:</strong> {room.roomCode}
                  </div>
                )}
              </div>
            </CardContent>
          </Card>

          {/* Người đặt lịch */}
          {requester && (
            <Card className="bg-indigo-100 dark:bg-indigo-900 border col-span-1 md:col-span-2">
              <CardContent className="py-3 text-indigo-800 dark:text-indigo-200">
                <h3 className="font-semibold text-base mb-4">Người đặt lịch</h3>

                <div className="flex items-center gap-3 mb-4">
                  <img
                    src={requester.avatarImage}
                    alt={`${requester.firstName} ${requester.lastName}`}
                    className="w-10 h-10 rounded-full object-cover"
                  />
                  <div>
                    <p className="font-semibold">
                      {requester.firstName} {requester.lastName}
                    </p>
                    <p className="text-xs">{requester.userType}</p>
                  </div>
                </div>

                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <strong>Email:</strong>{" "}
                    <a href={`mailto:${requester.email}`} className="underline">
                      {requester.email}
                    </a>
                  </div>
                  <div>
                    <strong>SĐT:</strong>{" "}
                    <a
                      href={`tel:${requester.phoneNumber}`}
                      className="underline"
                    >
                      {requester.phoneNumber}
                    </a>
                  </div>
                  <div>
                    <strong>Kích hoạt:</strong>{" "}
                    {requester.enable ? "Đang hoạt động" : "Đã khoá"}
                  </div>
                  <div>
                    <strong>Mã nhân viên:</strong> {requester.userCode}
                  </div>
                </div>
              </CardContent>
            </Card>
          )}

          {/* Mô tả */}
          {description && (
            <Card className="bg-gray-100 dark:bg-gray-800 border col-span-1 md:col-span-2">
              <CardContent className="py-3 space-y-2 text-gray-800 dark:text-gray-200">
                <div className="flex items-center gap-2">
                  <FileText className="w-4 h-4 text-gray-600" />
                  <strong>Mô tả:</strong>
                  <span>{description}</span>
                </div>
              </CardContent>
            </Card>
          )}

          {/* Dịch vụ */}
          {services?.length > 0 && (
            <Card className="bg-blue-100 dark:bg-blue-900 border">
              <CardContent className="py-3 text-blue-800 dark:text-blue-200">
                <h3 className="font-semibold text-base">Dịch vụ</h3>
                <ul className="list-disc list-inside">
                  {services.map((svc: any) => (
                    <li key={svc.id}>
                      {svc.name} - SL: {svc.quantity}
                    </li>
                  ))}
                </ul>
              </CardContent>
            </Card>
          )}

          {/* Thiết bị */}
          {equipments?.length > 0 && (
            <Card className="bg-pink-100 dark:bg-pink-900 border">
              <CardContent className="py-3 text-pink-800 dark:text-pink-200">
                <h3 className="font-semibold text-base">Thiết bị</h3>
                <ul className="list-disc list-inside">
                  {equipments.map((eq: any) => (
                    <li key={eq.id}>
                      {eq.name} - SL: {eq.quantity}
                    </li>
                  ))}
                </ul>
              </CardContent>
            </Card>
          )}

          {/* Người tham gia */}
          {participants?.length > 0 && (
            <Card className="bg-yellow-100 dark:bg-yellow-900 border col-span-full">
              <CardContent className="py-3 text-yellow-800 dark:text-yellow-200">
                <h3 className="font-semibold text-base">Người tham gia</h3>
                <ul className="list-disc list-inside">
                  {participants.map((email: string, idx: number) => (
                    <li key={idx}>{email}</li>
                  ))}
                </ul>
              </CardContent>
            </Card>
          )}
        </div>
        <div className="flex justify-end gap-3 mt-4">
          <Button
            variant="destructive"
            onClick={handleReject}
            className="flex items-center gap-2"
          >
            <XCircle className="w-4 h-4" />
            Từ chối
          </Button>
          <Button
            className="bg-indigo-600 hover:bg-indigo-700 flex items-center gap-2"
            variant="default"
            onClick={handleApprove}
          >
            <Check className="w-4 h-4" />
            Duyệt lịch
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default EventModalApproval;
