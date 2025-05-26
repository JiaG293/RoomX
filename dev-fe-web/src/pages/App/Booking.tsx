import React, { useCallback, useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import PortalLayout from "@/layouts/portal-layout";
import listPlugin from "@fullcalendar/list";
import { ScheduleService } from "@/services/admin/schedule.service";
import { BookingModal } from "@/pages/App/BookingForm";
import EventModal from "@/components/app/meetings/event-modal";
import timeGridPlugin from "@fullcalendar/timegrid";

const Booking: React.FC = () => {
  const [viewMode, setViewMode] = useState<"dayGridMonth" | "listWeek" | "timeGridDay">(
    "dayGridMonth"
  );
  const [events, setEvents] = useState<any[]>([]);
  const [currentMonth, setCurrentMonth] = useState(new Date().getMonth() + 1);
  const [currentYear, setCurrentYear] = useState(new Date().getFullYear());
  const [modalEvent, setModalEvent] = useState<any>(null);
  const [modalDay, setModalDay] = useState<any>(null);

  const loadEvents = useCallback(async (month: number, year: number) => {
    try {
      const scheduleService = new ScheduleService();
      const data = await scheduleService.getAllSchedulesUser(month, year);
      console.log(data);
      const formattedEvents = data.map((event: any) => {
        const startDateTime = new Date(
          `${event.meetingDate}T${event.meetingStart}`
        );
        const endDateTime = new Date(
          `${event.meetingDate}T${event.meetingEnd}`
        );
        return {
          id: event.id,
          title: event.title || "Sự kiện",
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
      <div style={{ flex: 1 }}>
        <FullCalendar
          locale="vi"
          plugins={[dayGridPlugin, listPlugin, interactionPlugin, timeGridPlugin]}
          initialView={viewMode}
          events={events}
          dateClick={(info) => {
            console.log("Clicked date:", info.dateStr);

            if (info.date.getMonth() + 1 === currentMonth) {
              setModalDay({ start: info.dateStr });
            }
          }}
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
            setCurrentMonth(info.view.currentStart.getMonth() + 1);
            setCurrentYear(info.view.currentStart.getFullYear());
          }}
          dayCellClassNames={(arg) =>
            arg.date.getMonth() + 1 !== currentMonth ? "disabled-day" : ""
          }
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
      </div>
      <BookingModal
        eventDate={modalDay?.start}
        onClose={() => setModalDay(null)}
      />
      <EventModal event={modalEvent} onClose={() => setModalEvent(null)} />
          
      {/* Chú thích màu sắc */}
      <div className="mt-4 flex flex-col gap-2">
        {/* <div className="flex gap-4">
          <div className="flex items-center gap-2">
            <span className="w-4 h-4 bg-yellow-400 rounded-sm"></span>
            <span>Lịch của tôi</span>
          </div>
          <div className="flex items-center gap-2">
            <span className="w-4 h-4 bg-blue-400 rounded-sm"></span>
            <span>Lịch họp khác</span>
          </div>
        </div> */}
        <p className="text-sm text-gray-600 italic">
          * Chọn ngày để mở hộp thoại đặt lịch *
        </p>
      </div>
    </PortalLayout>
  );
};

export default Booking;
