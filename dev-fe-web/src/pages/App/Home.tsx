import React, { useEffect, useState } from "react";
import { format } from "date-fns";
import {
  CalendarDays,
  CheckCircle2,
  Clock,
} from "lucide-react";
import { cn } from "@/lib/utils";
import PortalLayout from "@/layouts/portal-layout";

const Home: React.FC = () => {
  const [events, setEvents] = useState<any[]>([]);

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

  return (
    <PortalLayout>
      <div className="min-h-screen p-4 space-y-8">
        {/* Tổng quan */}
        <section className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div className="bg-white dark:bg-gray-900 rounded-xl p-5 shadow hover:shadow-lg transition">
            <div className="flex items-center space-x-4">
              <CalendarDays className="text-blue-500 w-6 h-6" />
              <div>
                <p className="text-sm text-gray-500">Tổng số sự kiện</p>
                <p className="text-xl font-semibold">{events.length}</p>
              </div>
            </div>
          </div>
          <div className="bg-white dark:bg-gray-900 rounded-xl p-5 shadow hover:shadow-lg transition">
            <div className="flex items-center space-x-4">
              <Clock className="text-yellow-500 w-6 h-6" />
              <div>
                <p className="text-sm text-gray-500">Sắp diễn ra</p>
                <p className="text-xl font-semibold">{upcomingEvents.length}</p>
              </div>
            </div>
          </div>
          <div className="bg-white dark:bg-gray-900 rounded-xl p-5 shadow hover:shadow-lg transition">
            <div className="flex items-center space-x-4">
              <CheckCircle2 className="text-green-500 w-6 h-6" />
              <div>
                <p className="text-sm text-gray-500">Đã hoàn thành</p>
                <p className="text-xl font-semibold">{completedEvents.length}</p>
              </div>
            </div>
          </div>
        </section>

        {/* Sự kiện sắp diễn ra */}
        <section className="bg-white dark:bg-gray-900 rounded-xl p-5 shadow">
          <h2 className="text-lg font-semibold text-gray-800 dark:text-white mb-4">
            Sự kiện sắp diễn ra
          </h2>

          {upcomingEvents.length > 0 ? (
            <ul className="grid gap-3">
              {upcomingEvents.map((event) => (
                <li
                  key={event.id}
                  className="border rounded-lg p-4 bg-gray-50 dark:bg-gray-800 hover:bg-blue-50 dark:hover:bg-gray-700 transition cursor-pointer"
                >
                  <p className="text-blue-600 font-medium">{event.title}</p>
                  <p className="text-sm text-gray-600 dark:text-gray-400">
                    {format(new Date(event.start), "dd/MM/yyyy HH:mm")} -{" "}
                    {format(new Date(event.end), "HH:mm")}
                  </p>
                  {event.description && (
                    <p className="text-sm text-gray-500 dark:text-gray-300 mt-1">
                      {event.description}
                    </p>
                  )}
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-500 dark:text-gray-400">
              Không có sự kiện sắp tới.
            </p>
          )}
        </section>
      </div>
    </PortalLayout>
  );
};

export default Home;
