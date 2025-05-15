import React, { useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react";
import listPlugin from "@fullcalendar/list";
import interactionPlugin from "@fullcalendar/interaction";
import { format } from "date-fns";
import { CalendarDays, CheckCircle2, Clock, BarChart2, PieChart } from "lucide-react";
import { cn } from "@/lib/utils";
import { Pie, Bar } from "react-chartjs-2"; // Add Chart.js for the charts
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, ArcElement } from "chart.js"; // Ensure all required components are imported
import PortalLayout from "@/layouts/portal-layout";

// Register the necessary components
ChartJS.register(CategoryScale, LinearScale, BarElement, ArcElement, Title, Tooltip, Legend);

const Home: React.FC = () => {
  const [events, setEvents] = useState<any[]>([]);
  const [selectedEvent, setSelectedEvent] = useState<any>(null);

  // Fake data
  useEffect(() => {
    const fakeData = [
      { id: "1", title: "Họp nhóm dự án", start: new Date().toISOString(), end: new Date(new Date().getTime() + 60 * 60 * 1000).toISOString(), description: "Thảo luận về tiến độ dự án web" },
      { id: "2", title: "Phỏng vấn thực tập", start: new Date(new Date().getTime() + 86400000).toISOString(), end: new Date(new Date().getTime() + 86400000 + 3600000).toISOString(), description: "Phỏng vấn vị trí front-end intern" },
      { id: "3", title: "Họp mentor", start: new Date(new Date().getTime() + 2 * 86400000).toISOString(), end: new Date(new Date().getTime() + 2 * 86400000 + 3600000).toISOString(), description: "Họp với mentor trao đổi học tập" },
      { id: "4", title: "Làm bài kiểm tra giữa kỳ", start: new Date(new Date().getTime() - 3 * 86400000).toISOString(), end: new Date(new Date().getTime() - 3 * 86400000 + 2 * 3600000).toISOString(), description: "Môn Phát triển Web" },
      { id: "5", title: "Tham gia hội thảo AI", start: new Date(new Date().getTime() + 5 * 86400000).toISOString(), end: new Date(new Date().getTime() + 5 * 86400000 + 2 * 3600000).toISOString(), description: "Thảo luận ứng dụng AI trong phát triển phần mềm" },
    ];
    setEvents(fakeData);
  }, []);

  const handleEventClick = (info: any) => {
    setSelectedEvent(info.event.extendedProps);
  };

  const upcomingEvents = events.filter((e) => new Date(e.start) > new Date()).sort((a, b) => new Date(a.start).getTime() - new Date(b.start).getTime()).slice(0, 5);

  const chartData = {
    labels: ["Hoàn thành", "Sắp diễn ra", "Chưa hoàn thành"],
    datasets: [
      {
        label: "Sự kiện",
        data: [
          events.filter((e) => new Date(e.end) < new Date()).length,
          upcomingEvents.length,
          events.length - upcomingEvents.length - events.filter((e) => new Date(e.end) < new Date()).length,
        ],
        backgroundColor: ["#4caf50", "#ff9800", "#f44336"],
      },
    ],
  };

  const barChartData = {
    labels: ["Sự kiện này", "Sự kiện tiếp theo", "Lịch sử sự kiện"],
    datasets: [
      {
        label: "Sự kiện đã hoàn thành",
        data: [5, 10, 12], // Adjust data as needed
        backgroundColor: "#4caf50",
      },
      {
        label: "Sự kiện sắp diễn ra",
        data: [2, 8, 5], // Adjust data as needed
        backgroundColor: "#ff9800",
      },
    ],
  };

  return (
    <PortalLayout>
      <div className="min-h-screen flex flex-col p-4 space-y-6">
        {/* Dashboard stats */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div className="bg-white dark:bg-gray-900 shadow-xl rounded-xl p-5 flex items-center gap-4 transition-transform transform hover:scale-105">
            <CalendarDays className="text-blue-500" />
            <div>
              <p className="text-gray-500 text-sm">Tổng số sự kiện</p>
              <p className="text-xl font-semibold">{events.length}</p>
            </div>
          </div>
          <div className="bg-white dark:bg-gray-900 shadow-xl rounded-xl p-5 flex items-center gap-4 transition-transform transform hover:scale-105">
            <Clock className="text-yellow-500" />
            <div>
              <p className="text-gray-500 text-sm">Sắp diễn ra</p>
              <p className="text-xl font-semibold">{upcomingEvents.length}</p>
            </div>
          </div>
          <div className="bg-white dark:bg-gray-900 shadow-xl rounded-xl p-5 flex items-center gap-4 transition-transform transform hover:scale-105">
            <CheckCircle2 className="text-green-500" />
            <div>
              <p className="text-gray-500 text-sm">Đã hoàn thành</p>
              <p className="text-xl font-semibold">{events.filter((e) => new Date(e.end) < new Date()).length}</p>
            </div>
          </div>
        </div>

        {/* Main content split into two vertical parts */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 flex-grow overflow-y-auto">
          {/* Upcoming events */}
          <div className="bg-white dark:bg-gray-900 shadow-md rounded-xl p-5">
            <h2 className="text-lg font-semibold text-gray-800 dark:text-gray-100 mb-4">Sự kiện sắp diễn ra</h2>
            {upcomingEvents.length > 0 ? (
              <ul className="space-y-3">
                {upcomingEvents.map((event) => (
                  <li
                    key={event.id}
                    onClick={() => handleEventClick({ event })}
                    className={cn("p-3 rounded-lg border cursor-pointer hover:bg-gray-50 dark:hover:bg-gray-800 transition", "flex flex-col")}
                  >
                    <span className="font-medium text-blue-600">{event.title}</span>
                    <span className="text-sm text-gray-500 dark:text-gray-400">{format(new Date(event.start), "dd/MM/yyyy HH:mm")} - {format(new Date(event.end), "HH:mm")}</span>
                  </li>
                ))}
              </ul>
            ) : (
              <p className="text-gray-500 dark:text-gray-400">Không có sự kiện sắp tới.</p>
            )}
          </div>

          {/* List View Calendar */}
          <div className="bg-white dark:bg-gray-900 shadow-md rounded-xl p-5">
            <h2 className="text-lg font-semibold text-gray-800 dark:text-gray-100 mb-4">Lịch theo danh sách</h2>
            <FullCalendar
              plugins={[listPlugin, interactionPlugin]}
              initialView="listWeek"
              events={events}
              eventClick={handleEventClick}
              height="auto"
              locale="vi"
            />
          </div>
        </div>

        
      </div>
    </PortalLayout>
  );
};

export default Home;
