import CMSLayout from "@/layouts/cms-layout";
import React, { useEffect, useState, useCallback, useRef } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import "@/styles/calendar-style.css";
import { ScheduleService } from "@/services/admin/schedule.service";
import EventModalApproval from "@/components/admin/meetings/event-modal-approval";
import { useTranslation } from "react-i18next";

interface EventType {
  id: string;
  title: string;
  start: string;
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
  const monthYearRef = useRef({
    month: new Date().getMonth() + 1,
    year: new Date().getFullYear(),
  });
  const loadEvents = useCallback(async (month: number, year: number) => {
    try {
      const scheduleService = new ScheduleService();
      const data = await scheduleService.getPendingSchedules(month, year);
      console.log(data);

      const formattedEvents: EventType[] = data.map((event: any) => ({
        id: event.id,
        title: event.title || "Sự kiện",
        start: `${event.updatedAt}`,
       
        extendedProps: {
          status: event.approvalStatus || "Chưa duyệt",
        },
        className:
          event.approvalStatus === "PENDING"
            ? "event-pending"
            : "event-conflict",
      }));

      setEvents(formattedEvents);
    } catch (error) {
      console.error("Error loading schedule:", error);
    }
  }, []);

  useEffect(() => {
    loadEvents(currentMonth, currentYear);
  }, [currentMonth, currentYear, loadEvents]);

   //polling
    useEffect(() => {
      const interval = setInterval(() => {
        loadEvents(monthYearRef.current.month, monthYearRef.current.year);
      }, 5000); // 10 giây
  
      return () => clearInterval(interval); // Cleanup khi component unmount
    }, [loadEvents]);
  

  const { t } = useTranslation();

  return (
  <CMSLayout
      title={t("admin.menu.main.schedule.title")}
      subtitle={t("admin.menu.main.schedule.sub.pending")}
    >
      <div style={{ flex: 0.9 }}>
        <FullCalendar
          locale="vi"
          plugins={[dayGridPlugin, listPlugin, interactionPlugin]}
          initialView={"dayGridMonth"}
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
            right: "dayGridMonth,listWeek,listDay",
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
            info.view.calendar.changeView("listDay", info.date);
          }}
        />
        {/* Chú thích */}
        <div
          style={{
            display: "flex",
            flexWrap: "wrap",
            gap: "12px",
            marginTop: "10px",
            paddingLeft: "10px",
          }}
        >
          {[
            { color: "#1565c0", label: "Hoàn thành" },
            { color: "#2e7d32", label: "Lên lịch" },
            { color: "#f9a825", label: "Chờ duyệt" },
            { color: "#c62828", label: "Xung đột" },
          ].map((item, index) => (
            <div
              key={index}
              style={{ display: "flex", alignItems: "center", gap: "6px" }}
            >
              <div
                style={{
                  width: "12px",
                  height: "12px",
                  backgroundColor: item.color,
                  borderRadius: "2px",
                }}
              ></div>
              <span style={{ fontSize: "12px" }}>{item.label}</span>
            </div>
          ))}
        </div>
      </div>
      <EventModalApproval
        event={modalEvent}
        onClose={() => setModalEvent(null)}
      />
    </CMSLayout>
  );
};

export default MeetingApproval;
