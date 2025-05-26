import CMSLayout from "@/layouts/cms-layout";
import React, { useEffect, useState, useCallback, useRef } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import "@/styles/calendar-style.css";
import { ScheduleService } from "@/services/admin/schedule.service";
import EventModal from "@/components/admin/meetings/event-modal"; // Import component EventModal
import timeGridPlugin from "@fullcalendar/timegrid";
import { useTranslation } from "react-i18next";
import { useScheduleStore } from "@/store/useScheduleStore";
import EventModalApproval from "@/components/admin/meetings/event-modal-approval";

const Meeting: React.FC = () => {
  const [viewMode, setViewMode] = useState<
    "dayGridMonth" | "listWeek" | "dayGridDay"
  >("dayGridMonth");
  const { events, setEvents } = useScheduleStore();
  // const [currentMonth, setCurrentMonth] = useState(new Date().getMonth() + 1);
  // const [currentYear, setCurrentYear] = useState(new Date().getFullYear());
  const [modalEvent, setModalEvent] = useState<any>(null);
  const monthYearRef = useRef({
    month: new Date().getMonth() + 1,
    year: new Date().getFullYear(),
  });
  const calendarRef = useRef<any>(null);

  const loadEvents = useCallback(async (month: number, year: number) => {
    try {
      const scheduleService = new ScheduleService();
      const data = await scheduleService.getAllSchedules(month, year);
      const formattedEvents = data
        .map((event: any) => {
          const eventDate = new Date(event.meetingDate);
          // Loại bỏ sự kiện không thuộc tháng đang xem
          if (
            eventDate.getMonth() + 1 !== month ||
            eventDate.getFullYear() !== year
          ) {
            return null;
          }
          const eventEnd = new Date(`${event.meetingDate}T${event.meetingEnd}`);
          const now = new Date();

          // Xác định class theo trạng thái
          const statusClass = (() => {
            if (event.status === "SCHEDULED" && eventEnd < now) {
              // Nếu sự kiện đã qua, coi như COMPLETED
              return "event-completed";
            }
            switch (event.status) {
              case "COMPLETED":
                return "event-completed";
              case "SCHEDULED":
                return "event-scheduled";
              case "PENDING":
                return "event-pending";
              case "REJECT":
                return "event-conflict";
              default:
                return "";
            }
          })();
          if (event.status === "REJECT" || event.status === "PENDING") {
            console.log(event);
            return {
              id: event.bookingRequestId,
              title:
                event.title ||
                (event.status === "PENDING"
                  ? "Sự kiện chờ duyệt"
                  : "Sự kiện xung đột"),
              start: event.updatedAt,
              end: event.updatedAt, // Không có thời gian kết thúc
              className: statusClass,
            };
          } else {
            // Bình thường: dùng meetingDate + giờ
            return {
              id: event.id,
              title: event.title || "Sự kiện",
              start: `${event.meetingDate}T${event.meetingStart}`,
              end: `${event.meetingDate}T${event.meetingEnd}`,
              className: statusClass,
            };
          }
        })
        .filter((event: any) => event !== null);

      setEvents(formattedEvents);
    } catch (error) {
      console.error("Error loading schedule:", error);
    }
  }, []);

  useEffect(() => {
    loadEvents(monthYearRef.current.month, monthYearRef.current.year);
  }, [loadEvents]);

  const { t } = useTranslation();

  //polling
  useEffect(() => {
    const interval = setInterval(() => {
      loadEvents(monthYearRef.current.month, monthYearRef.current.year);
    }, 5000); // 10 giây

    return () => clearInterval(interval); // Cleanup khi component unmount
  }, [loadEvents]);

  return (
    <CMSLayout
      title={t("admin.menu.main.schedule.title")}
      subtitle={t("admin.menu.main.schedule.sub.overview")}
    >
      <div style={{ flex: 0.9 }}>
        <FullCalendar
          ref={calendarRef}
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
          dateClick={(info) => {
            // Khi nhấn "Xem thêm", chuyển sang chế độ xem ngày
            info.view.calendar.changeView("listDay", info.date);
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
            right: "dayGridMonth,dayGridWeek,listDay",
          }}
          datesSet={(info) => {
            const newMonth = info.view.currentStart.getMonth() + 1;
            const newYear = info.view.currentStart.getFullYear();
            if (
              newMonth !== monthYearRef.current.month ||
              newYear !== monthYearRef.current.year
            ) {
              monthYearRef.current = { month: newMonth, year: newYear };
              // Nếu bạn muốn load lại sự kiện mỗi khi tháng năm thay đổi:
              loadEvents(newMonth, newYear);
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
            listDay: {
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

      {/* Sử dụng EventModal */}
      {modalEvent &&
        (modalEvent.classNames?.includes("event-pending", "event-conflict") ||
        modalEvent.classNames?.includes("event-conflict") ? (
          <EventModalApproval
            event={modalEvent}
            onClose={() => setModalEvent(null)}
          />
        ) : (
          <EventModal event={modalEvent} onClose={() => setModalEvent(null)} />
        ))}
    </CMSLayout>
  );
};

export default Meeting;
