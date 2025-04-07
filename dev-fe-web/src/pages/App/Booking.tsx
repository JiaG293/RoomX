import React, { useCallback, useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import PortalLayout from "@/layouts/portal-layout";
import listPlugin from "@fullcalendar/list";
import { ScheduleService } from "@/services/admin/schedule.service";
import { useAuth } from "@/context/AuthProvider";
import { BookingModal } from "@/pages/App/BookingForm";

const Booking: React.FC = () => {
  const [viewMode, setViewMode] = useState<"dayGridMonth" | "listWeek">("dayGridMonth");
  const [events, setEvents] = useState<any[]>([]);
  const [currentMonth, setCurrentMonth] = useState(new Date().getMonth() + 1);
  const [currentYear, setCurrentYear] = useState(new Date().getFullYear());
  const [modalEvent, setModalEvent] = useState<any>(null);
  const { getUserInfo } = useAuth();

  const loadEvents = useCallback(async (month: number, year: number) => {
    try {
      const email = getUserInfo()?.email + "";
      const scheduleService = new ScheduleService();
      const data = await scheduleService.getAllSchedules(month, year);
      console.log(data);

      // Format lại dữ liệu các sự kiện
      const formattedEvents = data.map((event: any) => ({
        id: event._id,
        title: event.name,
        start: event.startTime,
        end: event.endTime,
        extendedProps: {
          room: event.room || "Chưa có phòng", 
          floor: event.floor || "Chưa có tầng", 
          note: event.note || "Chưa có ghi chú", 
          attendees: event.attendees || [], 
          devices: event.devices || [],
          roomPrice: event.roomPrice || 0,
          scheduleType: event.scheduleType || "Unknown", 
          email: event.email || "N/A", 
          branch: event.branch || "Unknown", 
          host: event.name || "Chưa có chủ trì", 
        },
        classNames: event.email === email ? 'highlight-event' : '',  // Nếu email trùng khớp thì thêm class 'highlight-event'
      }));
      setEvents(formattedEvents);
    } catch (error) {
      console.error("Error loading schedule:", error);
    }
  }, [getUserInfo]);

  useEffect(() => {
    loadEvents(currentMonth, currentYear);
  }, [currentMonth, currentYear, loadEvents]);

  return (
    <PortalLayout>
      <div style={{ flex: 0.9 }}>
      <FullCalendar
  locale="vi"
  plugins={[dayGridPlugin, listPlugin, interactionPlugin]}
  initialView={viewMode}
  events={events}
  dateClick={(info) => {
    if (info.date.getMonth() + 1 === currentMonth) {
      setModalEvent({ start: info.date.toISOString() });
    }
  }}
  eventClick={(info) => setModalEvent(info.event)}
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
    right: "dayGridMonth,dayGridWeek,dayGridDay",
  }}
  datesSet={(info) => {
    setCurrentMonth(info.view.currentStart.getMonth() + 1);
    setCurrentYear(info.view.currentStart.getFullYear());
  }}
  dayCellClassNames={(arg) =>
    arg.date.getMonth() + 1 !== currentMonth ? "disabled-day" : ""
  }
/>

      </div>
      <BookingModal eventDate={modalEvent?.start} onClose={() => setModalEvent(null)} />
              {/* Chú thích màu sắc */}
      <div className="mt-4 flex gap-4">
        <div className="flex items-center gap-2">
          <span className="w-4 h-4 bg-yellow-400 rounded-sm"></span>
          <span>Lịch của tôi</span>
        </div>
        <div className="flex items-center gap-2">
          <span className="w-4 h-4 bg-blue-400 rounded-sm"></span>
          <span>Lịch họp khác</span>
        </div>
      </div>
      </PortalLayout>
  );
};

export default Booking;
