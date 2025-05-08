import React from "react";
import {
  X,
  Calendar,
  Clock,
  RefreshCcw,
  Users,
  Info,
  Check,
  XCircle,
  FileText,
  Building2,
  DoorOpen,
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

interface EventModalApprovalProps {
  event: any;
  onClose: () => void;
}

// Mapping hiển thị tiếng Việt cho loại lặp
const recurrenceMap: Record<string, string> = {
  DAILY: "Hằng ngày",
  WEEKLY: "Hằng tuần",
  MONTHLY: "Hằng tháng",
  YEARLY: "Hằng năm",
};

const EventModalApproval: React.FC<EventModalApprovalProps> = ({
  event,
  onClose,
}) => {
  if (!event) return null;

  const {
    id,
    title,
    startStr,
    endStr,
    extendedProps: {
      status,
      capacity,
      startDate,
      endDate,
      startTime,
      endTime,
      recurrenceType,
      recurrenceInterval,
      daysOfWeek,
      createdAt,
      updatedAt,
      description,
      branchId,
      roomId,
    },
  } = event;

  const formatDate = (dateStr: string) =>
    new Date(dateStr).toLocaleDateString("vi-VN", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    });

  const formatTime = (time: string) => time?.slice(0, 5); // Giờ:Phút

  const formatDateTime = (str: string) =>
    new Date(str).toLocaleString("vi-VN", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });

  const formattedDays = daysOfWeek
    ?.split(",")
    .map((d: string) => d.trim())
    .join(", ");

  // Duyệt lịch
  const handleApprove = async () => {
    try {
      const scheduleService = new ScheduleService();
      await scheduleService.approveSchedules(id);
      onClose();
    } catch (error) {
      console.error("Error approving schedule:", error);
    }
  };

  return (
    <Dialog open={!!event} onOpenChange={(open) => !open && onClose()}>
      <DialogContent className="max-w-2xl p-6">
        <DialogHeader>
          <div className="flex justify-between items-center">
            <DialogTitle className="text-2xl font-bold">{title}</DialogTitle>
          </div>
        </DialogHeader>

        <div className="space-y-4 mt-4">
          {/* Mô tả */}
          {description && (
            <Card className="bg-muted border">
              <CardContent className="py-4 space-y-2 text-sm">
                <div className="flex items-center gap-2">
                  <FileText className="w-4 h-4 text-muted-foreground" />
                  <strong>Mô tả:</strong> <span>{description}</span>
                </div>
              </CardContent>
            </Card>
          )}

          {/* Trạng thái */}
          <Card className="bg-blue-100 dark:bg-blue-900 border">
            <CardContent className="py-4 text-sm flex items-center gap-2 text-blue-800 dark:text-blue-200">
              <Info className="w-4 h-4" />
              <strong>Trạng thái:</strong> <span>{status}</span>
            </CardContent>
          </Card>

          {/* Thời gian */}
          <Card className="bg-emerald-100 dark:bg-emerald-900 border">
            <CardContent className="py-4 grid grid-cols-2 gap-4 text-sm text-emerald-800 dark:text-emerald-200">
              <div className="flex items-center gap-2">
                <Calendar className="w-4 h-4" />
                <strong>Ngày bắt đầu:</strong>
                <span>{startStr.split("T")[0]}</span>
              </div>
              <div className="flex items-center gap-2">
                <Calendar className="w-4 h-4" />
                <strong>Ngày kết thúc:</strong>
                <span>{endStr.split("T")[0]}</span>
              </div>
              <div className="flex items-center gap-2">
                <Clock className="w-4 h-4" />
                <strong>Bắt đầu:</strong>
                <span>{formatTime(startTime)}</span>
              </div>
              <div className="flex items-center gap-2">
                <Clock className="w-4 h-4" />
                <strong>Kết thúc:</strong>
                <span>{formatTime(endTime)}</span>
              </div>
            </CardContent>
          </Card>

          {/* Lặp */}
          <Card className="bg-yellow-100 dark:bg-yellow-900 border">
            <CardContent className="py-4 grid grid-cols-2 gap-4 text-sm text-yellow-800 dark:text-yellow-100">
              <div className="flex items-center gap-2">
                <RefreshCcw className="w-4 h-4" />
                <strong>Lặp:</strong>
                <span>{recurrenceMap[recurrenceType] || recurrenceType}</span>
              </div>
              {recurrenceInterval !== undefined && (
                <div className="flex items-center gap-2">
                  <strong>Khoảng cách:</strong> <span>{recurrenceInterval}</span>
                </div>
              )}
              <div className="flex items-center gap-2">
                <Calendar className="w-4 h-4" />
                <strong>Từ ngày:</strong>
                <span>{formatDate(startDate)}</span>
              </div>
              <div className="flex items-center gap-2">
                <Calendar className="w-4 h-4" />
                <strong>Đến ngày:</strong>
                <span>{formatDate(endDate)}</span>
              </div>
              {daysOfWeek && (
                <div className="flex items-center gap-2 col-span-2">
                  <strong>Ngày trong tuần:</strong> <span>{formattedDays}</span>
                </div>
              )}
            </CardContent>
          </Card>

          {/* Khác */}
          <Card className="bg-muted border">
            <CardContent className="py-4 grid grid-cols-2 gap-4 text-sm text-muted-foreground">
              <div className="flex items-center gap-2">
                <Users className="w-4 h-4" />
                <strong>Sức chứa:</strong>
                <span>{capacity || "Không xác định"}</span>
              </div>
              {branchId && (
                <div className="flex items-center gap-2">
                  <Building2 className="w-4 h-4" />
                  <strong>Chi nhánh:</strong>
                </div>
              )}
              {roomId && (
                <div className="flex items-center gap-2">
                  <DoorOpen className="w-4 h-4" />
                  <strong>Phòng:</strong>
                  <span>{roomId}</span>
                </div>
              )}
              <div className="text-xs col-span-2 mt-2">
                <p>Ngày tạo: {formatDateTime(createdAt)}</p>
                <p>Cập nhật: {formatDateTime(updatedAt)}</p>
              </div>
            </CardContent>
          </Card>

          {/* Hành động */}
          <div className="flex justify-end gap-2 pt-4 border-t pt-6">
            <Button variant="destructive" className="flex items-center gap-1">
              <XCircle className="w-4 h-4" /> Huỷ
            </Button>
            <Button
              variant="default"
              className="bg-green-600 hover:bg-green-700 text-white flex items-center gap-1"
              onClick={handleApprove}
            >
              <Check className="w-4 h-4" /> Duyệt
            </Button>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default EventModalApproval;
