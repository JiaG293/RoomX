import React, { useEffect, useState } from "react";
import { format } from "date-fns";
import { CalendarDays, CheckCircle2, Clock } from "lucide-react";
import PortalLayout from "@/layouts/portal-layout";

// Import Recharts
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  LineChart,
  Line,
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
  Legend,
} from "recharts";

const Home: React.FC = () => {
  const [events, setEvents] = useState<any[]>([]);
  const COLORS = ["#FFA500", "#32CD32", "#FF6347"]; // cam, xanh lá, đỏ tomato

  useEffect(() => {
    const fakeData = [
      {
        id: "1",
        title: "Họp nhóm dự án",
        start: new Date().toISOString(),
        end: new Date(new Date().getTime() + 60 * 60 * 1000).toISOString(),
        description: "Thảo luận về tiến độ dự án web",
      },
      {
        id: "2",
        title: "Phỏng vấn thực tập",
        start: new Date(Date.now() + 86400000).toISOString(),
        end: new Date(Date.now() + 86400000 + 3600000).toISOString(),
        description: "Phỏng vấn vị trí front-end intern",
      },
      {
        id: "3",
        title: "Họp mentor",
        start: new Date(Date.now() + 2 * 86400000).toISOString(),
        end: new Date(Date.now() + 2 * 86400000 + 3600000).toISOString(),
        description: "Họp với mentor trao đổi học tập",
      },
      {
        id: "4",
        title: "Làm bài kiểm tra giữa kỳ",
        start: new Date(Date.now() - 3 * 86400000).toISOString(),
        end: new Date(Date.now() - 3 * 86400000 + 2 * 3600000).toISOString(),
        description: "Môn Phát triển Web",
      },
      {
        id: "5",
        title: "Tham gia hội thảo AI",
        start: new Date(Date.now() + 5 * 86400000).toISOString(),
        end: new Date(Date.now() + 5 * 86400000 + 2 * 3600000).toISOString(),
        description: "Ứng dụng AI trong phát triển phần mềm",
      },
    ];
    setEvents(fakeData);
  }, []);

  const upcomingEvents = events
    .filter((e) => new Date(e.start) > new Date())
    .sort((a, b) => new Date(a.start).getTime() - new Date(b.start).getTime())
    .slice(0, 5);

  const completedEvents = events.filter((e) => new Date(e.end) < new Date());

  // Data cho biểu đồ 1 - ví dụ số sự kiện theo ngày
  const chartData1 = [
    { date: "20/05", events: 1 },
    { date: "21/05", events: 2 },
    { date: "22/05", events: 1 },
    { date: "23/05", events: 0 },
    { date: "24/05", events: 3 },
    { date: "25/05", events: 1 },
  ];

  // Data cho biểu đồ 2 - ví dụ số sự kiện sắp diễn ra và đã hoàn thành
  const chartData2 = [
    { name: "Chờ duyệt", value: 8 },
    { name: "Lên lịch", value: 5 },
    { name: "Đã hủy", value: 2 },
  ];

  return (
    <PortalLayout>
      <div
        className="p-4 bg-gray-100 dark:bg-gray-900 font-sans flex flex-col"
        style={{ height: "calc(100vh - 64px)" }}
      >
        {/* Overview section */}
        <section className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
          <div className="rounded-md p-4 border-2 border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 hover:opacity-90 cursor-pointer">
            <div className="flex items-center space-x-3">
              <CalendarDays className="text-blue-600 dark:text-blue-300 w-5 h-5" />
              <div>
                <p className="text-sm font-medium text-gray-800 dark:text-gray-200">
                  Tổng số sự kiện
                </p>
                <p className="text-xl font-bold text-gray-900 dark:text-white">
                  {events.length}
                </p>
              </div>
            </div>
          </div>

          <div className="rounded-md p-4 border-2 border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 hover:opacity-90 cursor-pointer">
            <div className="flex items-center space-x-3">
              <Clock className="text-yellow-600 dark:text-yellow-300 w-5 h-5" />
              <div>
                <p className="text-sm font-medium text-gray-800 dark:text-gray-200">
                  Sắp diễn ra
                </p>
                <p className="text-xl font-bold text-gray-900 dark:text-white">
                  {upcomingEvents.length}
                </p>
              </div>
            </div>
          </div>

          <div className="rounded-md p-4 border-2 border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 hover:opacity-90 cursor-pointer">
            <div className="flex items-center space-x-3">
              <CheckCircle2 className="text-green-600 dark:text-green-300 w-5 h-5" />
              <div>
                <p className="text-sm font-medium text-gray-800 dark:text-gray-200">
                  Đã hoàn thành
                </p>
                <p className="text-xl font-bold text-gray-900 dark:text-white">
                  {completedEvents.length}
                </p>
              </div>
            </div>
          </div>
        </section>

        {/* Main content section */}
        <section className="flex-1 grid grid-cols-1 md:grid-cols-3 gap-4">
          {/* Upcoming Events List */}
          <div className="md:col-span-2 flex flex-col rounded-md p-6 border border-gray-300 dark:border-gray-700 bg-white dark:bg-[#1f2937] overflow-auto">
            <h2 className="text-xl font-semibold text-gray-900 dark:text-gray-100 mb-5">
              Sự kiện sắp diễn ra
            </h2>

            {upcomingEvents.length > 0 ? (
              <ul className="space-y-4 flex-1 overflow-auto pr-2">
                {upcomingEvents.map((event) => (
                  <li
                    key={event.id}
                    className="rounded-lg p-4 cursor-pointer bg-gradient-to-tr from-blue-200 via-blue-300 to-blue-400 dark:from-blue-800 dark:via-blue-700 dark:to-blue-600 border border-blue-300 dark:border-blue-600 hover:opacity-90 transition"
                  >
                    <p className="text-blue-900 dark:text-blue-100 font-semibold text-lg">
                      {event.title}
                    </p>
                    <p className="text-sm text-blue-800 dark:text-blue-200 mt-1">
                      {format(new Date(event.start), "dd/MM/yyyy HH:mm")} -{" "}
                      {format(new Date(event.end), "HH:mm")}
                    </p>
                    {event.description && (
                      <p className="text-sm text-blue-700 dark:text-blue-200 mt-2 leading-snug">
                        {event.description}
                      </p>
                    )}
                  </li>
                ))}
              </ul>
            ) : (
              <p className="text-gray-600 dark:text-gray-400 text-base">
                Không có sự kiện sắp tới.
              </p>
            )}
          </div>

          {/* Charts */}
          <div className="flex flex-col gap-5 h-full">
            {/* Bar Chart */}
            <div className="flex-1 rounded-md p-4 border bg-white dark:bg-gray-900 border-gray-300 dark:border-gray-700 flex flex-col">
              <h3 className="text-md font-semibold mb-3 text-gray-900 dark:text-gray-200">
                Biểu đồ số sự kiện theo ngày
              </h3>
              <ResponsiveContainer width="100%" height={150}>
                <BarChart data={chartData1}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="date" />
                  <YAxis allowDecimals={false} />
                  <Tooltip />
                  <Bar dataKey="events" fill="#3b82f6" />
                </BarChart>
              </ResponsiveContainer>
            </div>

            {/* Pie Chart */}
            <div className="flex-1 rounded-md p-4 border bg-white dark:bg-gray-900 border-gray-300 dark:border-gray-700 flex flex-col">
              <h3 className="text-md font-semibold mb-3 text-gray-900 dark:text-gray-200">
                Tổng sự kiện theo trạng thái
              </h3>
              <ResponsiveContainer width="100%" height={150}>
                <PieChart>
                  <Pie
                    data={chartData2}
                    dataKey="value"
                    nameKey="name"
                    cx="50%"
                    cy="50%"
                    outerRadius={60}
                    labelLine={false}
                    label={false}
                  >
                    {chartData2.map((entry, index) => (
                      <Cell
                        key={`cell-${index}`}
                        fill={COLORS[index % COLORS.length]}
                      />
                    ))}
                  </Pie>
                  <Tooltip />
                </PieChart>
              </ResponsiveContainer>
              {/* Legend */}
              <div className="flex justify-center mt-2 gap-4">
                {chartData2.map((entry, index) => (
                  <div
                    key={`legend-${index}`}
                    className="flex items-center gap-2"
                  >
                    <div
                      style={{
                        width: 14,
                        height: 14,
                        backgroundColor: COLORS[index % COLORS.length],
                        borderRadius: 4,
                      }}
                    />
                    <span className="text-sm text-black">{entry.name}</span>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </section>
      </div>
    </PortalLayout>
  );
};

export default Home;
