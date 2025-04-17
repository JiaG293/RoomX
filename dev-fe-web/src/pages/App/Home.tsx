import React, { useCallback, useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid"; // Để hiển thị lịch theo dạng Grid
import interactionPlugin from "@fullcalendar/interaction"; // Để có thể tương tác với sự kiện
import PortalLayout from "@/layouts/portal-layout"; // Import layout của bạn
import listPlugin from "@fullcalendar/list";
import { ScheduleService } from "@/services/user/schedule.service";
import EventModal from "@/components/app/meetings/event-modal";

const Home: React.FC = () => {
  const [viewMode, setViewMode] = useState<"dayGridMonth" | "listWeek">(
    "dayGridMonth"
  );
  const [events, setEvents] = useState([]);
  const [currentMonth, setCurrentMonth] = useState(new Date().getMonth() + 1);
  const [currentYear, setCurrentYear] = useState(new Date().getFullYear());
  const [modalEvent, setModalEvent] = useState<any>(null);

  const loadEvents = useCallback(async (month: number, year: number) => {
    try {
      const scheduleService = new ScheduleService();
      const data = await scheduleService.getAllSchedules(month, year);
      console.log(data.result.content);
      const formattedEvents = data.result.content.map((event: any) => {
        const startDateTime = new Date(`${event.meetingDate}T${event.meetingStart}`);
        const endDateTime = new Date(`${event.meetingDate}T${event.meetingEnd}`);
        return {
          id: event.id,
          title: event.title || "Không có tiêu đề",
          start: startDateTime.toISOString(),
          end: endDateTime.toISOString(),
          extendedProps: {
            room: event.room?.name || "Chưa có phòng",
            floor: event.floor?.name || "Chưa có tầng",
            note: event.description || "Chưa có ghi chú",
            attendees: [], // Giữ nguyên mảng trống nếu chưa có
            devices: [],
            roomPrice: 0,
            scheduleType: event.status || "Unknown",
            email: event.email || "N/A",
            branch: event.branch?.name || "Unknown",
            host: event.name || "Chưa có chủ trì",
          },
        };
      });
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
          views={{
            dayGridMonth: {
              // Lịch tháng: giới hạn tối đa 2 dòng sự kiện mỗi ngày
              dayMaxEvents: 2,
              moreLinkText: "Xem thêm",

            },
            dayGridWeek: {
              // Lịch tuần: giới hạn tối đa 10 sự kiện mỗi ngày
              dayMaxEvents: 10,
              moreLinkText: "Xem thêm",

            },
            dayGridDay: {
              // Lịch ngày: không giới hạn số sự kiện
              eventLimit: false, 
              moreLinkText: "Xem thêm",
            }
          }}
          moreLinkClick={(info) => {
            // Khi nhấn "Xem thêm", chuyển sang chế độ xem ngày
            info.view.calendar.changeView('dayGridDay', info.date);
          }}
        />
      </div>
      <EventModal event={modalEvent} onClose={() => setModalEvent(null)} />

    </PortalLayout>
  );
};

export default Home;
