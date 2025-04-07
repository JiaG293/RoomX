import CMSLayout from "@/layouts/cms-layout";
import React, { useEffect, useState, useCallback } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import "@/styles/calendar-style.css";
import { ScheduleService } from "@/services/admin/schedule.service";
import EventModal from "@/components/admin/meetings/event-modal"; // Import component EventModal

const Meeting: React.FC = () => {
  const [viewMode, setViewMode] = useState<
    "dayGridMonth" | "listWeek" | "dayGridDay"
  >("dayGridMonth");
  const [events, setEvents] = useState([]);
  const [currentMonth, setCurrentMonth] = useState(new Date().getMonth() + 1);
  const [currentYear, setCurrentYear] = useState(new Date().getFullYear());
  const [modalEvent, setModalEvent] = useState<any>(null);

  const loadEvents = useCallback(async (month: number, year: number) => {
    try {
      const scheduleService = new ScheduleService();
      const data = await scheduleService.getAllSchedules(month, year);
      console.log(data);
      const formattedEvents = data.map((event: any) => ({
        id: event.id,
        title: event.bookingCode, // Booking code sẽ là tiêu đề sự kiện
        start: `${event.meetingDate}T${event.meetingStart}`, // Định dạng thời gian bắt đầu
        end: `${event.meetingDate}T${event.meetingEnd}`, // Định dạng thời gian kết thúc
        extendedProps: {
          room: event.roomId || "Chưa có phòng", // ID phòng họp (có thể không có)
          totalPrice: event.totalPrice || 0, // Giá tổng (nếu có)
          status: event.status || "Unknown", // Trạng thái (ví dụ: "COMPLETED")
          count: event.count || 0, // Số lượng người tham gia
          bookingRequestId: event.bookingRequestId || "Unknown", // ID yêu cầu đặt phòng
          createdAt: event.createdAt, // Ngày tạo
          updatedAt: event.updatedAt, // Ngày cập nhật
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
    <CMSLayout title="Quản lý đặt phòng">
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

      {/* Sử dụng EventModal */}
      <EventModal event={modalEvent} onClose={() => setModalEvent(null)} />
    </CMSLayout>
  );
};

export default Meeting;
