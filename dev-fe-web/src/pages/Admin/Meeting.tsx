import CMSLayout from "@/layouts/cms-layout";
import React, { useEffect, useState, useCallback } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import "@/styles/calendar-style.css";
import { ScheduleService } from "@/services/admin/schedule.service";
import EventModal from "@/components/admin/meetings/event-modal"; // Import component EventModal
import timeGridPlugin from "@fullcalendar/timegrid";

const Meeting: React.FC = () => {
  const [viewMode, setViewMode] = useState<
    "dayGridMonth" | "listWeek" | "timeGridPlugin"
  >("dayGridMonth");
  const [events, setEvents] = useState([]);
  const [currentMonth, setCurrentMonth] = useState(new Date().getMonth() + 1);
  const [currentYear, setCurrentYear] = useState(new Date().getFullYear());
  const [modalEvent, setModalEvent] = useState<any>(null);

  const loadEvents = useCallback(async (month: number, year: number) => {
    try {
      const scheduleService = new ScheduleService();
      const data = await scheduleService.getAllSchedules(month, year);
  
      const formattedEvents = data
        .map((event: any) => {
          const eventDate = new Date(event.meetingDate);
          if (
            eventDate.getMonth() + 1 !== month || 
            eventDate.getFullYear() !== year
          ) {
            return null; // Loại bỏ sự kiện không thuộc tháng đang xem
          }
  
          const statusClass =
            event.status === "COMPLETED" ? "event-completed" : "event-scheduled";
  
          return {
            id: event.id,
            title: event.title || "Chưa có tiêu đề",
            start: `${event.meetingDate}T${event.meetingStart}`,
            end: `${event.meetingDate}T${event.meetingEnd}`,
            className: statusClass,
          };
        })
        .filter((event: any) => event !== null); // Loại bỏ null
  
      setEvents(formattedEvents);
    } catch (error) {
      console.error("Error loading schedule:", error);
    }
  }, []);
  

  useEffect(() => {
    loadEvents(currentMonth, currentYear);
  }, [currentMonth, currentYear, loadEvents]);

  return (
    <CMSLayout title="Quản lý đặt phòng">
      <div style={{ flex: 0.9 }}>
        <FullCalendar
          locale="vi"
          plugins={[
            dayGridPlugin,
            listPlugin,
            interactionPlugin,
            timeGridPlugin,
          ]}
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
            right: "dayGridMonth,dayGridWeek,timeGridDay",
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
            timeGridDay: {
              // Lịch ngày: không giới hạn số sự kiện
              eventLimit: false,
              moreLinkText: "Xem thêm",
            },
          }}
          moreLinkClick={(info) => {
            // Khi nhấn "Xem thêm", chuyển sang chế độ xem ngày
            info.view.calendar.changeView("timeGridDay", info.date);
          }}
        />
      {/* Chú thích */}
      <div style={{ display: "flex", marginTop: "10px", justifyContent: "flex-start", paddingLeft: "10px" }}>
  <div style={{ display: "flex", alignItems: "center", marginRight: "10px" }}>
    <div style={{ width: "12px", height: "12px", backgroundColor: "#9E9E9E", marginRight: "5px" }}></div>
    <span style={{ fontSize: "12px" }}>Lên lịch</span>
  </div>
  <div style={{ display: "flex", alignItems: "center" }}>
    <div style={{ width: "12px", height: "12px", backgroundColor: "#1E88E5", marginRight: "5px" }}></div>
    <span style={{ fontSize: "12px" }}>Hoàn thành</span>
  </div>
</div>

      </div>

      {/* Sử dụng EventModal */}
      <EventModal event={modalEvent} onClose={() => setModalEvent(null)} />
    </CMSLayout>
  );
};

export default Meeting;
