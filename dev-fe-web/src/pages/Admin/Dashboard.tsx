import { useEffect, useMemo, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import { Users, Calendar, CheckCircle, DollarSign, Home } from "lucide-react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  LineChart,
  Line,
} from "recharts";
import CMSLayout from "@/layouts/cms-layout";
import { useTranslation } from "react-i18next";
import { ScheduleService } from "@/services/admin/schedule.service";
import { toast } from "sonner";
import { AuthService } from "@/services/auth.service";

const stats = [
  {
    title: "common.so_phong_hop",
    value: 13,
    icon: <Home className="w-6 h-6" />,
  },
  {
    title: "common.so_nguoi_dung",
    value: 22,
    icon: <Users className="w-6 h-6" />,
  },
  {
    title: "common.luot_dat_phong_thang",
    value: 25,
    icon: <CheckCircle className="w-6 h-6" />,
  },
  {
    title: "common.chi_phi_thang",
    value: "12,500,000 VNĐ",
    icon: <DollarSign className="w-6 h-6" />,
  },
];

const Dashboard: React.FC = () => {
  const { t } = useTranslation();
  const [pendingBookings, setPendingBookings] = useState<any[]>([]);

  const bookingChartData = useMemo(
    () => [
      { name: t("common.month_labels.1"), bookings: 30 },
      { name: t("common.month_labels.2"), bookings: 45 },
      { name: t("common.month_labels.3"), bookings: 60 },
      { name: t("common.month_labels.4"), bookings: 50 },
      { name: t("common.month_labels.5"), bookings: 70 },
      { name: t("common.month_labels.6"), bookings: 90 },
    ],
    [t]
  );

  const revenueChartData = useMemo(
    () => [
      { name: t("common.month_labels.1"), revenue: 3000 },
      { name: t("common.month_labels.2"), revenue: 4500 },
      { name: t("common.month_labels.3"), revenue: 6000 },
      { name: t("common.month_labels.4"), revenue: 5000 },
      { name: t("common.month_labels.5"), revenue: 7000 },
      { name: t("common.month_labels.6"), revenue: 9000 },
    ],
    [t]
  );

  useEffect(() => {
    const fetchData = async () => {
      try {
        const bookingService = new ScheduleService();
        const today = new Date();
        const month = today.getMonth() + 1;
        const year = today.getFullYear();
        const result = await bookingService.getPendingSchedules(month, year);
        setPendingBookings(result);
      } catch (error) {
        toast.error("Không thể tải dữ liệu lịch cần duyệt");
      }
    };

    fetchData();
  }, []);

  return (
    <CMSLayout title={t("admin.menu.main.home")}>
      <div className="p-4 space-y-4 h-full flex flex-col">
        {/* Tổng quan nhanh */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
          {stats.map((stat, index) => (
            <Card
              key={index}
              className="shadow-xl rounded-lg hover:scale-105 transition-all"
            >
              <CardHeader>
                <CardTitle className="flex items-center gap-2 text-xl font-semibold text-card-foreground">
                  {stat.icon}
                  {t(stat.title)}
                </CardTitle>
              </CardHeader>
              <CardContent>
                <p className="text-xl font-bold text-blue-400">{stat.value}</p>
              </CardContent>
            </Card>
          ))}
        </div>

        {/* Nội dung chính */}
        <div className="flex flex-col lg:flex-row gap-6 flex-1 min-h-0">
          {/* Bảng đặt phòng */}
          <Card className="flex-1 shadow-xl flex flex-col min-h-0 overflow-hidden rounded-lg">
            <CardHeader>
              <CardTitle className="text-lg font-medium text-foreground">
                {t("common.luot_dat_gan_day")}
              </CardTitle>
            </CardHeader>
            <CardContent className="flex-1 overflow-auto p-0">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead className="text-sm text-card-foreground">
                      ID
                    </TableHead>
                    <TableHead className="text-sm text-card-foreground">
                      {t("common.trang_thai")}
                    </TableHead>
                    <TableHead className="text-sm text-card-foreground">
                      {t("common.ngay_dat")}
                    </TableHead>
                    <TableHead className="text-sm text-card-foreground">
                      {t("common.tieu_de")}
                    </TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {pendingBookings.slice(0, 5).map((booking, index) => {
                    const statusMap: {
                      [key: string]: { label: string; color: string };
                    } = {
                      PENDING: {
                        label: "Chờ duyệt",
                        color:
                          "bg-yellow-200 text-yellow-800 dark:bg-yellow-300 dark:text-yellow-900",
                      },
                      APPROVED: {
                        label: "Đã duyệt",
                        color:
                          "bg-emerald-200 text-emerald-800 dark:bg-emerald-300 dark:text-emerald-900",
                      },
                      CANCELLED: {
                        label: "Đã hủy",
                        color:
                          "bg-rose-200 text-rose-800 dark:bg-rose-300 dark:text-rose-900",
                      },
                    };

                    const status = statusMap[booking.approvalStatus] || {
                      label: booking.approvalStatus,
                      color:
                        "bg-gray-200 text-gray-800 dark:bg-gray-300 dark:text-gray-900",
                    };

                    return (
                      <TableRow key={booking.id}>
                        <TableCell className="text-sm text-card-foreground">
                          {index + 1}
                        </TableCell>
                        <TableCell className="text-sm text-card-foreground">
                          <span
                            className={`px-2 py-1 rounded-md text-sm font-medium ${status.color}`}
                          >
                            {status.label}
                          </span>
                        </TableCell>
                        <TableCell className="text-sm text-card-foreground">
                          {booking?.updatedAt
                            ? new Date(booking.updatedAt).toLocaleString(
                                "vi-VN",
                                {
                                  day: "2-digit",
                                  month: "2-digit",
                                  year: "numeric",
                                  hour: "2-digit",
                                  minute: "2-digit",
                                }
                              )
                            : ""}
                        </TableCell>
                        <TableCell className="text-sm text-card-foreground">
                          {booking.title}
                        </TableCell>
                      </TableRow>
                    );
                  })}
                </TableBody>
              </Table>
            </CardContent>
          </Card>

          {/* Biểu đồ */}
          <div className="flex-1 flex flex-col gap-6 min-h-0">
            <Card className="shadow-xl flex-1 flex flex-col min-h-0 rounded-lg">
              <CardHeader>
                <CardTitle className="text-lg font-medium text-card-foreground">
                  {t("common.dat_phong_theo_thang")}
                </CardTitle>
              </CardHeader>
              <CardContent className="flex-1 min-h-0">
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart data={bookingChartData}>
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Bar dataKey="bookings" fill="#3b82f6" />
                  </BarChart>
                </ResponsiveContainer>
              </CardContent>
            </Card>

            <Card className="shadow-xl flex-1 flex flex-col min-h-0 rounded-lg">
              <CardHeader>
                <CardTitle className="text-lg font-medium text-card-foreground">
                  {t("common.chi_phi_theo_thang")}
                </CardTitle>
              </CardHeader>
              <CardContent className="flex-1 min-h-0">
                <ResponsiveContainer width="100%" height="100%">
                  <LineChart data={revenueChartData}>
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip />
                    <Line
                      type="monotone"
                      dataKey="revenue"
                      stroke="#10b981"
                      strokeWidth={2}
                    />
                  </LineChart>
                </ResponsiveContainer>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </CMSLayout>
  );
};

export default Dashboard;
