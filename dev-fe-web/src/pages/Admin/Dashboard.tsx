import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import { Users, Calendar, CheckCircle, DollarSign } from "lucide-react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer } from "recharts";
import CMSLayout from "@/layouts/cms-layout";

const stats = [
  { title: "Số phòng", value: 15, icon: <Calendar className="w-6 h-6" /> },
  { title: "Lượt đặt hôm nay", value: 8, icon: <CheckCircle className="w-6 h-6" /> },
  { title: "Người dùng", value: 200, icon: <Users className="w-6 h-6" /> },
  { title: "Tổng chi phí", value: "$12,500", icon: <DollarSign className="w-6 h-6" /> },
];

const bookings = [
  { id: 1, room: "Phòng hội nghị A", user: "John Doe", time: "10:00 - 11:00", status: "Đã xác nhận" },
  { id: 2, room: "Phòng họp B", user: "Jane Smith", time: "14:00 - 15:00", status: "Chờ xử lý" },
  { id: 3, room: "Phòng họp C", user: "Mike Johnson", time: "16:00 - 17:00", status: "Đã hủy" },
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
  return (
    <CMSLayout title='Trang chủ'>
      <div className="p-6 space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
          {stats.map((stat, index) => (
            <Card key={index} className="shadow-lg">
              <CardHeader>
                <CardTitle className="flex items-center gap-2">{stat.icon}{stat.title}</CardTitle>
              </CardHeader>
              <CardContent>
                <p className="text-3xl font-bold text-blue-600">{stat.value}</p>
              </CardContent>
            </Card>
          ))}
        </div>

        <Card className="shadow-lg">
          <CardHeader>
            <CardTitle>Đặt phòng gần đây</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>ID</TableHead>
                  <TableHead>Phòng</TableHead>
                  <TableHead>Người dùng</TableHead>
                  <TableHead>Thời gian</TableHead>
                  <TableHead>Trạng thái</TableHead>
                  <TableHead>Hành động</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {bookings.map(booking => (
                  <TableRow key={booking.id}>
                    <TableCell>{booking.id}</TableCell>
                    <TableCell>{booking.room}</TableCell>
                    <TableCell>{booking.user}</TableCell>
                    <TableCell>{booking.time}</TableCell>
                    <TableCell>{booking.status}</TableCell>
                    <TableCell>
                      <Button size="sm" variant="outline">Quản lý</Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>

        <Card className="shadow-lg">
          <CardHeader>
            <CardTitle>Thống kê đặt phòng</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={bookingChartData}>
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Bar dataKey="bookings" fill="#3b82f6" />
              </BarChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>

        <Card className="shadow-lg">
          <CardHeader>
            <CardTitle>Thống kê chi phí</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={revenueChartData}>
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Bar dataKey="revenue" fill="#10b981" />
              </BarChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>
      </div>
    </CMSLayout>
  );
};

export default Dashboard;
