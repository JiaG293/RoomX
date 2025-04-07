import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Separator } from "@/components/ui/separator";
import { useParams } from "react-router-dom";
import CMSLayout from "@/layouts/cms-layout";
import { useEffect, useState } from "react";
import { UserService } from "@/services/admin/user.service";
import { Button } from "@/components/ui/button";
import {
  Hash,
  Mail,
  Pencil,
  Phone,
  UserX,
  User,
  MoreHorizontal,
} from "lucide-react";
import { getShortName } from "@/utils/string.util";

export default function UserDetail() {
  const { userId } = useParams();
  const [user, setUser] = useState<any>(null);
  const userService = new UserService();

  const userGroups = ["Phòng nhân sự", "Dự án RoomX", "Đối tác IUH"];
  const recentBookings = [
    { id: 1, title: "Họp nhóm dự án", date: "2024-03-10" },
    { id: 2, title: "Tư vấn khách hàng", date: "2024-03-12" },
    { id: 3, title: "Tham gia hội thảo", date: "2024-03-15" },
    { id: 4, title: "Gặp gỡ đối tác", date: "2024-03-18" },
  ];

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await userService.getUserDetails(userId || "");
        setUser(response.result.content[0]);
      } catch (error) {
        console.error("Error fetching user details:", error);
      }
    };
    fetchUser();
  }, [userId]);

  return (
    <CMSLayout title="Chi tiết người dùng">
      <div className="grid grid-cols-3 gap-6 p-6">
        {/* User Info */}
        <div className="col-span-2">
          <Card className="w-full shadow-xl rounded-3xl overflow-hidden">
            <CardHeader className="flex flex-col items-center bg-blue-300 text-white py-6">
              <Avatar className="w-24 h-24 border-4 border-white">
                <AvatarImage
                  src={user?.avatar || "/placeholder-avatar.png"}
                  alt="User Avatar"
                />
                <AvatarFallback className="bg-gray-300 text-gray-700 text-2xl">
                  {getShortName(user?.firstName || "UK")}
                </AvatarFallback>
              </Avatar>
              <CardTitle className="mt-4 text-2xl font-bold">
                {user?.firstName || "Unknown"} {user?.lastName || "User"}
              </CardTitle>
              <Badge className="mt-2 px-3 py-1 bg-white text-blue-600 text-sm font-semibold rounded-full">
                {user?.userType}
              </Badge>
            </CardHeader>
            <CardContent className="p-6">
              <Separator className="my-4" />
              <div className="space-y-4 text-gray-700 text-lg">
                <p className="flex items-center">
                  <Hash className="w-5 h-5 mr-3 text-red-500" />
                  <strong className="w-36">ID người dùng:</strong>
                  <span>{user?.userId}</span>
                </p>
                <p className="flex items-center">
                  <User className="w-5 h-5 mr-3 text-yellow-500" />
                  <strong className="w-36">Mã nhân viên:</strong>
                  <span>{user?.userCode}</span>
                </p>
                <p className="flex items-center">
                  <Mail className="w-5 h-5 mr-3 text-blue-500" />
                  <strong className="w-36">Email:</strong>
                  <span>{user?.email}</span>
                </p>
                <p className="flex items-center">
                  <Phone className="w-5 h-5 mr-3 text-green-500" />
                  <strong className="w-36">Số điện thoại:</strong>
                  <span>{user?.phoneNumber || "Chưa có"}</span>
                </p>
              </div>
              <div className="flex justify-between mt-6">
                <Button variant="outline" className="flex items-center">
                  <Pencil className="w-4 h-4 mr-2" /> Cập nhật
                </Button>
                <Button variant="destructive" className="flex items-center">
                  <UserX className="w-4 h-4 mr-2" /> Vô hiệu hoá
                </Button>
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Right Column */}
        <div className="flex flex-col space-y-6">
          {/* User Groups */}
          <Card className="w-full shadow-md rounded-3xl">
            <CardHeader className="bg-gray-100 p-4">
              <CardTitle className="text-lg font-semibold">
                Nhóm của người dùng
              </CardTitle>
            </CardHeader>
            <CardContent className="p-6 space-y-2 space-x-2 cursor-pointer">
              {userGroups.map((group, index) => (
                <Badge
                  key={index}
                  className="px-4 py-2 text-sm font-semibold bg-blue-500 text-white rounded-lg"
                >
                  {group}
                </Badge>
              ))}
            </CardContent>
          </Card>

          {/* Recent Bookings */}
          <Card className="w-full shadow-md rounded-3xl">
            <CardHeader className="bg-gray-100 p-4">
              <CardTitle className="text-lg font-semibold">
                Lịch họp sắp tới
              </CardTitle>
            </CardHeader>
            <CardContent className="p-6 max-h-56 overflow-auto space-y-3">
              {recentBookings.slice(0, 3).map((booking) => (
                <Card
                  key={booking.id}
                  className="p-4 shadow-sm border border-gray-200 rounded-xl"
                >
                  <p className="font-semibold">{booking.title}</p>
                  <p className="text-sm text-gray-500">{booking.date}</p>
                </Card>
              ))}
              {recentBookings.length > 3 && (
                <Button
                  variant="outline"
                  className="mt-4 w-full flex items-center justify-center"
                >
                  <MoreHorizontal className="w-4 h-4 mr-2" /> Xem thêm
                </Button>
              )}
            </CardContent>
          </Card>
        </div>
      </div>
    </CMSLayout>
  );
}
