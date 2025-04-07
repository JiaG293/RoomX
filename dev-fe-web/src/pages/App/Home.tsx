import React, { useCallback, useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid"; // Để hiển thị lịch theo dạng Grid
import interactionPlugin from "@fullcalendar/interaction"; // Để có thể tương tác với sự kiện
import PortalLayout from "@/layouts/portal-layout"; // Import layout của bạn
import listPlugin from "@fullcalendar/list";
import { ScheduleService } from "@/services/user/schedule.service";
import { useAuth } from "@/context/AuthProvider";
import EventModal from "@/components/admin/meetings/event-modal";

const Home: React.FC = () => {
  const [viewMode, setViewMode] = useState<"dayGridMonth" | "listWeek">(
    "dayGridMonth"
  );
  const [events, setEvents] = useState([]);
  const [currentMonth, setCurrentMonth] = useState(new Date().getMonth() + 1);
  const [currentYear, setCurrentYear] = useState(new Date().getFullYear());
  const [modalEvent, setModalEvent] = useState<any>(null);
  const { getUserInfo } = useAuth();


  const loadEvents = useCallback(async (month: number, year: number) => {
    try {
      const scheduleService = new ScheduleService();
      const email = getUserInfo()?.email+"";
      const data = await scheduleService.getAllSchedules(month, year, email);
      console.log(data);
      const formattedEvents = data.map((event: any) => ({
        id: event._id,
        title: event.note,
        start: event.startTime,
        end: event.endTime,
        extendedProps: {
          room: event.room || "Chưa có phòng", // Phòng mặc định
          floor: event.floor || "Chưa có tầng", // Tầng mặc định
          note: event.note || "Chưa có ghi chú", // Ghi chú mặc định
          attendees: event.attendees || [], // Mảng người tham gia mặc định
          devices: event.devices || [], // Mảng thiết bị mặc định
          roomPrice: event.roomPrice || 0, // Giá phòng mặc định là 0
          scheduleType: event.scheduleType || "Unknown", // Mặc định loại lịch
          email: event.email || "N/A", // Email mặc định
          branch: event.branch || "Unknown", // Chi nhánh mặc định
          host: event.name || "Chưa có chủ trì", // Thêm chủ trì (dùng `name` làm chủ trì)
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
    <PortalLayout>
      <div style={{ flex: 0.9 }}>
        <FullCalendar
          locale="vi"
          plugins={[dayGridPlugin, listPlugin, interactionPlugin]}
          initialView={viewMode}
          events={events}
          eventClick={(info) => {
            const event = info.event;
            setModalEvent(event);
          }}
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
            const newMonth = info.view.currentStart.getMonth() + 1;
            const newYear = info.view.currentStart.getFullYear();
            if (newMonth !== currentMonth || newYear !== currentYear) {
              setCurrentMonth(newMonth);
              setCurrentYear(newYear);
            }
          }}
        />
      </div>
      <EventModal event={modalEvent} onClose={() => setModalEvent(null)} />

    </PortalLayout>
  );
};

export default Home;
