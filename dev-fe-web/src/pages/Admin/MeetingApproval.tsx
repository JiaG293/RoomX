import CMSLayout from "@/layouts/cms-layout";
import React, { useEffect, useState, useCallback } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import { Check, X } from "lucide-react"; // Sử dụng Lucid Icon
import "@/styles/calendar-style.css";
import { ScheduleService } from "@/services/admin/schedule.service";
import { toast } from "sonner";
import { dA } from "node_modules/@fullcalendar/core/internal-common";
import EventModalApproval from "@/components/admin/meetings/event-modal-approval";

interface EventType {
  id: string;
  title: string;
  start: string;
  end: string;
  extendedProps: {
    status: string;
    approvalStatus: string;
    capacity: number;
    startDate: string;
    endDate: string;
    startTime: string;
    endTime: string;
    recurrenceType: string;
    recurrenceInterval: number;
    daysOfWeek: string;
    createdAt: string;
    updatedAt: string;
  };
}

const MeetingApproval: React.FC = () => {
  const [events, setEvents] = useState<EventType[]>([]);
  const [currentMonth, setCurrentMonth] = useState(new Date().getMonth() + 1);
  const [currentYear, setCurrentYear] = useState(new Date().getFullYear());
  const [modalEvent, setModalEvent] = useState<any>(null);

  const loadEvents = useCallback(async (month: number, year: number) => {
    try {
      const scheduleService = new ScheduleService();
      const data = await scheduleService.getPendingSchedules(month, year);
      console.log(data);

      const formattedEvents: EventType[] = data.map((event: any) => ({
        id: event.id,
        title: event.title || "Chưa có tiêu đề",
        start: `${event.startDate}T${event.startTime}`,
        end: `${event.endDate}T${event.endTime}`,
        extendedProps: {
          status: event.approvalStatus || "Chưa duyệt",
          approvalStatus: event.approvalStatus || "PENDING",
          capacity: event.capacity || 0,
          startDate: event.startDate,
          endDate: event.endDate,
          startTime: event.startTime,
          endTime: event.endTime,
          recurrenceType: event.recurrenceType || "Không xác định",
          recurrenceInterval: event.recurrenceInterval || 0,
          daysOfWeek: event.daysOfWeek || "Không có lịch",
          createdAt: event.createdAt || "",
          updatedAt: event.updatedAt || "",
          description: event.description || "Không có mô tả",
          services: event.services || [],
          equipments: event.equipments || [],
          priority: event.priority ?? 0,
          branchId: event.branchId || null,
          roomId: event.roomId || null,
        },
      }));
      

      setEvents(formattedEvents);
    } catch (error) {
      console.error("Error loading schedule:", error);
    }
  }, []);

  useEffect(() => {
    loadEvents(currentMonth, currentYear);
  }, [currentMonth, currentYear, loadEvents]);

  return (
    <CMSLayout title="Phê duyệt lịch họp">
      <div style={{ flex: 0.9 }}>
        <FullCalendar
          locale="vi"
          plugins={[dayGridPlugin, listPlugin, interactionPlugin]}
          initialView={"listWeek"}
          events={events}
          eventClick={(info) => {
            setModalEvent(info.event);
          }}
          eventContent={(eventInfo) => (
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <span>{eventInfo.event.title}</span>
            </div>
          )}
          height="100%"
          buttonText={{
            today: "Hôm nay",
            month: "Tháng",
            week: "Tuần",
            day: "Ngày",
          }}
          headerToolbar={{
            left: "prev,next today",
            center: "title",
            right: "listMonth,listWeek,listDay",
          }}
          datesSet={(info) => {
            const newMonth = info.view.currentStart.getMonth() + 1;
            const newYear = info.view.currentStart.getFullYear();
            if (newMonth !== currentMonth || newYear !== currentYear) {
              setCurrentMonth(newMonth);
              setCurrentYear(newYear);
            }
          }}
        />
      </div>
      <EventModalApproval event={modalEvent} onClose={() => setModalEvent(null)} />

    </CMSLayout>
  );
};

export default MeetingApproval;
