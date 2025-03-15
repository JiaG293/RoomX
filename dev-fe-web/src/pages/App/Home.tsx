import React, { useState } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid"; // Để hiển thị lịch theo dạng Grid
import interactionPlugin from "@fullcalendar/interaction"; // Để có thể tương tác với sự kiện
import PortalLayout from "@/layouts/portal-layout"; // Import layout của bạn
import { ToggleGroup, ToggleGroupItem } from "@/components/ui/toggle-group";
import listPlugin from "@fullcalendar/list";

const Home: React.FC = () => {
  // Các sự kiện mẫu với thông tin phòng
  const [events, setEvents] = useState([
    {
      title: "Họp nhóm",
      start: "2025-03-20T10:00:00", // Ngày giờ của sự kiện
      end: "2025-03-20T12:00:00",
      room: "Phòng A1", // Thêm thông tin phòng
    },
    {
      title: "Phỏng vấn ứng viên",
      start: "2025-03-21T14:00:00", // Ngày giờ của sự kiện
      end: "2025-03-21T16:00:00",
      room: "Phòng B2", // Thêm thông tin phòng
    },
  ]);

  const [viewMode, setViewMode] = useState<"dayGridMonth" | "listWeek">(
    "dayGridMonth"
  );

  return (
    <PortalLayout>
      <div style={{ flex: 0.9 }}>
        <FullCalendar
          locale="vi"
          plugins={[dayGridPlugin, listPlugin, interactionPlugin]}
          initialView={viewMode}
          eventTextColor="white" // Màu chữ trắng
          eventBackgroundColor="#E4D1B9" // Màu nền sáng như phong cách Ghibli
          eventBorderColor="#4F6D7A" // Màu viền đậm và nhẹ nhàng
          key={viewMode}
          events={events}
          eventClick={(info) => {
            const event = info.event;
            alert(`Sự kiện: ${event.title}\nPhòng: ${event.extendedProps.room}`);
          }}
          dateClick={(info) => {
            alert(`Ngày chọn: ${info.dateStr}`);
          }}
          height="100%"
          buttonText={{
            today: "Hôm nay",
            month: "Tháng",
            week: "Tuần",
            day: "Ngày",
          }}
          headerToolbar={{
            left: "prev,next today", // Các nút điều hướng
            center: "title", // Tiêu đề của tháng
            right: "dayGridMonth,dayGridWeek,dayGridDay", // Các chế độ xem (tháng, tuần, ngày)
          }}
        />
      </div>
    </PortalLayout>
  );
};

export default Home;
