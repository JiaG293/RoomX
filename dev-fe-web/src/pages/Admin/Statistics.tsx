import React from "react";
import CMSLayout from "@/layouts/cms-layout";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer } from "recharts";
import { TrendingUp, Users, Calendar } from "lucide-react";

const bookingData = [
  { name: "Jan", bookings: 120 },
  { name: "Feb", bookings: 150 },
  { name: "Mar", bookings: 180 },
  { name: "Apr", bookings: 160 },
  { name: "May", bookings: 200 },
  { name: "Jun", bookings: 250 },
];

const roomTypeUsage = [
  { name: "Phòng họp nhỏ", usage: 100 },
  { name: "Phòng họp trung bình", usage: 150 },
  { name: "Phòng họp lớn", usage: 200 },
];

const topUsers = [
  { id: 1, name: "Nguyễn Văn A", totalBookings: 15 },
  { id: 2, name: "Trần Thị B", totalBookings: 12 },
  { id: 3, name: "Lê Minh C", totalBookings: 10 },
];

const Statistics: React.FC = () => {
  return (
    <CMSLayout title="Thống kê">
      <div className="p-6 space-y-6">
        {/* Summary Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <Card className="shadow-lg">
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <TrendingUp className="w-6 h-6 text-green-500" />
                Tổng lượt đặt phòng
              </CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-3xl font-bold text-green-600">960</p>
              <p className="text-sm text-gray-500">+15% so với tháng trước</p>
            </CardContent>
          </Card>

          <Card className="shadow-lg">
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Calendar className="w-6 h-6 text-blue-500" />
                Lượt đặt trong tháng
              </CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-3xl font-bold text-blue-600">250</p>
              <p className="text-sm text-gray-500">+10% so với tháng trước</p>
            </CardContent>
          </Card>

          <Card className="shadow-lg">
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Users className="w-6 h-6 text-purple-500" />
                Người dùng thường xuyên
              </CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-3xl font-bold text-purple-600">75</p>
              <p className="text-sm text-gray-500">30% tổng số người dùng</p>
            </CardContent>
          </Card>
        </div>

        {/* Booking Chart */}
        <Card className="shadow-lg">
          <CardHeader>
            <CardTitle>Lượt đặt phòng theo tháng</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={bookingData}>
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Bar dataKey="bookings" fill="#10b981" />
              </BarChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>

        {/* Room Type Usage */}
        <Card className="shadow-lg">
          <CardHeader>
            <CardTitle>Tần suất sử dụng theo loại phòng</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Loại phòng</TableHead>
                  <TableHead>Số lượt đặt</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {roomTypeUsage.map((room, index) => (
                  <TableRow key={index}>
                    <TableCell>{room.name}</TableCell>
                    <TableCell>{room.usage}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>

        {/* Top Users */}
        <Card className="shadow-lg">
          <CardHeader>
            <CardTitle>Người dùng đặt phòng nhiều nhất</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>ID</TableHead>
                  <TableHead>Tên</TableHead>
                  <TableHead>Số lượt đặt</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {topUsers.map((user) => (
                  <TableRow key={user.id}>
                    <TableCell>{user.id}</TableCell>
                    <TableCell>{user.name}</TableCell>
                    <TableCell>{user.totalBookings}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>
    </CMSLayout>
  );
};

export default Statistics;