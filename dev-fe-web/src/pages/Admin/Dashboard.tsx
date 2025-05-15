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
import { Users, Calendar, CheckCircle, DollarSign } from "lucide-react";
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

const stats = [
  {
    title: "trang_chu_so_phong",
    value: 12,
    icon: <Calendar className="w-6 h-6" />,
  },

  {
    title: "trang_chu_so_nguoi",
    value: 16,
    icon: <Users className="w-6 h-6" />,
  },
  {
    title: "trang_chu_luot_dat_phong",
    value: 8,
    icon: <CheckCircle className="w-6 h-6" />,
  },
  {
    title: "trang_chu_chi_phi",
    value: "12,500,000 VNĐ",
    icon: <DollarSign className="w-6 h-6" />,
  },
];

const bookings = [
  {
    id: 1,
    room: "Phòng hội nghị A",
    user: "John Doe",
    time: "10:00 - 11:00",
    status: "trang_thai_da_duyet",
  },
  {
    id: 2,
    room: "Phòng họp B",
    user: "Jane Smith",
    time: "14:00 - 15:00",
    status: "trang_thai_cho_duyet",
  },
  {
    id: 3,
    room: "Phòng họp C",
    user: "Mike Johnson",
    time: "16:00 - 17:00",
    status: "trang_thai_huy",
  },
];

const bookingChartData = [
  { name: "Tháng 1", bookings: 30 },
  { name: "Tháng 2", bookings: 45 },
  { name: "Tháng 3", bookings: 60 },
  { name: "Tháng 4", bookings: 50 },
  { name: "Tháng 5", bookings: 70 },
  { name: "Tháng 6", bookings: 90 },
];

const revenueChartData = [
  { name: "Tháng 1", revenue: 3000 },
  { name: "Tháng 2", revenue: 4500 },
  { name: "Tháng 3", revenue: 6000 },
  { name: "Tháng 4", revenue: 5000 },
  { name: "Tháng 5", revenue: 7000 },
  { name: "Tháng 6", revenue: 9000 },
];

const Dashboard: React.FC = () => {
  const { t } = useTranslation();
  return (
    <CMSLayout title={t("menu_trang_chu")}>
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
                {t("trang_chu_luot_dat_phong")}
              </CardTitle>
            </CardHeader>
            <CardContent className="flex-1 overflow-auto p-0">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead className="text-sm text-card-foreground">
                      {t("trang_chu_dat_phong_cot_id")}
                    </TableHead>
                    <TableHead className="text-sm text-card-foreground">
                      {t("trang_chu_dat_phong_cot_phong_hop")}
                    </TableHead>
                    <TableHead className="text-sm text-card-foreground">
                      {t("trang_chu_dat_phong_cot_nguoi_dung")}
                    </TableHead>
                    <TableHead className="text-sm text-card-foreground">
                      {t("trang_chu_dat_phong_cot_thoi_gian")}
                    </TableHead>
                    <TableHead className="text-sm text-card-foreground">
                      {t("trang_chu_dat_phong_cot_trang_thai")}
                    </TableHead>
                    <TableHead className="text-sm text-card-foreground">
                      {t("trang_chu_dat_phong_cot_hanh_dong")}
                    </TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {bookings.map((booking) => (
                    <TableRow key={booking.id}>
                      <TableCell className="text-sm text-card-foreground">
                        {booking.id}
                      </TableCell>
                      <TableCell className="text-sm text-card-foreground">
                        {booking.room}
                      </TableCell>
                      <TableCell className="text-sm text-card-foreground">
                        {booking.user}
                      </TableCell>
                      <TableCell className="text-sm text-card-foreground">
                        {booking.time}
                      </TableCell>
                      <TableCell className="text-sm w-32">
                        <span
                          className="px-2 py-1 rounded-full text-white text-xs"
                          style={{
                            backgroundColor: `hsl(var(--${
                              booking.status === "trang_thai_da_duyet"
                                ? "status-confirmed"
                                : booking.status === "trang_thai_huy"
                                ? "status-cancelled"
                                : "status-pending"
                            }))`,
                          }}
                        >
                          {t(booking.status)}
                        </span>
                      </TableCell>
                      <TableCell>
                        <Button
                          size="sm"
                          variant="outline"
                          className="text-card-foreground hover:bg-card-foreground/10"
                        >
                          {t("trang_chu_dat_phong_hanh_dong_xem")}
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </CardContent>
          </Card>

          {/* Biểu đồ */}
          <div className="flex-1 flex flex-col gap-6 min-h-0">
            <Card className="shadow-xl flex-1 flex flex-col min-h-0 rounded-lg">
              <CardHeader>
                <CardTitle className="text-lg font-medium text-card-foreground">
                  {t("trang_chu_thong_ke_dat_phong")}
                </CardTitle>
              </CardHeader>
              <CardContent className="flex-1 min-h-0">
                <ResponsiveContainer width="100%" height="100%">
                  <BarChart data={bookingChartData}>
                    <XAxis dataKey="name" tickFormatter={(value) => t(`chart_labels.${value}`)} />
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
                  {t("trang_chu_thong_ke_chi_phi")}
                </CardTitle>
              </CardHeader>
              <CardContent className="flex-1 min-h-0">
                <ResponsiveContainer width="100%" height="100%">
                  <LineChart data={revenueChartData}>
                    <XAxis
                      dataKey="name"
                      tickFormatter={(value) => t(`chart_labels.${value}`)}
                    />
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
