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
      <DialogContent className="w-full max-w-4xl max-h-[85vh] p-6 overflow-auto bg-background rounded-xl">
        <DialogTitle className="mb-6 text-xl font-semibold border-b pb-3">
          {eventDetails?.title || "Chi tiết sự kiện"}
        </DialogTitle>

        <div className="space-y-4 overflow-y-auto">
            <p>{}</p>
        </div>

        <DialogFooter className="mt-6 px-0 border-t bg-muted flex justify-end">
          <Button variant="secondary" onClick={onClose}>
            Đóng
          </Button>
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
  <div className="bg-card p-4 rounded-xl shadow-sm border space-y-3">
    <h3 className="flex items-center gap-2 text-base font-semibold text-primary mb-3">
      {icon}
      {title}
    </h3>
    <div className="space-y-2">{children}</div>
  </div>
);
