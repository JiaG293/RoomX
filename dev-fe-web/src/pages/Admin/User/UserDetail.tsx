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
  Mail,
  Pencil,
  Phone,
  UserX,
  User,
  MoreHorizontal,
  RefreshCcw,
  CalendarDays,
  CheckCircle,
  ShieldCheck,
  VenetianMask,
  UserPlus,
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
        setUser(response.result);
      } catch (error) {
        console.error("Error fetching user details:", error);
      }
    };
    fetchUser();
  }, [userId]);

  const formatDate = (iso: string) => {
    return new Date(iso).toLocaleDateString("vi-VN", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    });
  };

  return (
    <CMSLayout title="Chi tiết người dùng">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {/* Left Column */}
        <div className="space-y-6">
          <Card className="shadow-xl rounded-2xl border border-gray-300 dark:border-gray-700">
            <CardHeader className="bg-gradient-to-r from-purple-500 to-purple-300 dark:from-purple-700 dark:to-purple-900 text-white p-6 text-center rounded-t-2xl">
              <Avatar className="w-28 h-28 mx-auto border-4 border-white dark:border-gray-800">
                <AvatarImage
                  src={user?.avatarImage || "/placeholder-avatar.png"}
                  alt="User Avatar"
                  className="rounded-full"
                />
                <AvatarFallback className="bg-background text-foreground">
                  {getShortName(user?.firstName || "")}
                </AvatarFallback>
              </Avatar>
              <CardTitle className="mt-4 text-xl font-semibold">
                {user?.firstName} {user?.lastName}
              </CardTitle>
            </CardHeader>
            <CardContent className="p-6 space-y-4 bg-gray-100 dark:bg-gray-800 rounded-b-2xl text-gray-800 dark:text-gray-100 max-h-[500px] overflow-auto">
              <div className="flex items-center">
                <User className="w-5 h-5 mr-3 text-yellow-500" />
                <span className="font-medium w-36">Mã nhân viên:</span>
                <span>{user?.userCode}</span>
              </div>
              <div className="flex items-center">
                <Mail className="w-5 h-5 mr-3 text-blue-500" />
                <span className="font-medium w-36">Email:</span>
                <span>{user?.email}</span>
              </div>
              <div className="flex items-center">
                <Phone className="w-5 h-5 mr-3 text-green-500" />
                <span className="font-medium w-36">Số điện thoại:</span>
                <span>{user?.phoneNumber || "Chưa có"}</span>
              </div>
              <div className="flex items-center">
                <VenetianMask className="w-5 h-5 mr-3 text-pink-500" />
                <span className="font-medium w-36">Giới tính:</span>
                <span>{user?.gender ? "Nam" : "Nữ"}</span>
              </div>
              <div className="flex items-center">
                <ShieldCheck className="w-5 h-5 mr-3 text-purple-500" />
                <span className="font-medium w-36">Vai trò:</span>
                <div className="flex flex-wrap gap-2">
                  {user?.roles?.length > 0
                    ? user?.roles?.map((role: string) => {
                        switch (role) {
                          case "USER":
                            return (
                              <span
                                key={role}
                                className="px-3 py-1 text-sm text-white bg-blue-500 rounded-full dark:bg-blue-700"
                              >
                                Người dùng
                              </span>
                            );
                          case "ADMIN":
                            return (
                              <span
                                key={role}
                                className="px-3 py-1 text-sm text-white bg-red-500 rounded-full dark:bg-red-700"
                              >
                                Quản trị viên
                              </span>
                            );
                          case "APPROVER":
                            return (
                              <span
                                key={role}
                                className="px-3 py-1 text-sm text-white bg-green-500 rounded-full dark:bg-green-700"
                              >
                                Kiểm duyệt viên
                              </span>
                            );
                          default:
                            return null; /* (
                              <span
                                key={role}
                                className="px-3 py-1 text-sm text-white bg-gray-500 rounded-full dark:bg-gray-700"
                              >
                                Không xác định
                              </span>
                            ); */
                        }
                      })
                    : "Không xác định"}
                </div>
              </div>

              <div className="flex items-center">
                <CheckCircle
                  className={`w-5 h-5 mr-3 ${
                    user?.enable ? "text-green-500" : "text-red-500"
                  }`}
                />
                <span className="font-medium w-36">Trạng thái:</span>
                <span
                  className={
                    user?.enable
                      ? "text-green-600 dark:text-green-400"
                      : "text-red-600 dark:text-red-400"
                  }
                >
                  {user?.enable ? "Đang hoạt động" : "Bị vô hiệu hóa"}
                </span>
              </div>
              <div className="flex items-center">
                <CalendarDays className="w-5 h-5 mr-3 text-gray-500 dark:text-gray-400" />
                <span className="font-medium w-36">Ngày tạo:</span>
                <span>{formatDate(user?.createdAt)}</span>
              </div>
              <div className="flex items-center">
                <RefreshCcw className="w-5 h-5 mr-3 text-gray-500 dark:text-gray-400" />
                <span className="font-medium w-36">Ngày cập nhật:</span>
                <span>{formatDate(user?.updatedAt)}</span>
              </div>
              <Separator />
              <div className="flex flex-wrap justify-between gap-2 pt-4">
                {/* Group Update and Add to Group */}
                <div className="flex gap-2">
                  {/* <Button
                    size="sm"
                    variant="default"
                    className="bg-blue-600 hover:bg-blue-700 text-white"
                  >
                    <Pencil className="w-4 h-4 mr-1" /> Cập nhật
                  </Button>
                  <Button
                    size="sm"
                    variant="outline"
                    className="border-gray-400 text-gray-700 dark:text-white"
                  >
                    <UserPlus className="w-4 h-4 mr-1" /> Thêm nhóm
                  </Button> */}
                </div>

                {/* Group Deactivate */}
                {!user?.roles?.includes("ADMIN") && (
                  <div className="flex gap-2">
                    {user?.enable ? (
                      <Button
                        size="sm"
                        variant="destructive"
                        className="bg-red-600 hover:bg-red-700 text-white"
                        onClick={() => {
                          const userService = new UserService();
                          userService.deactivateUser(user.id).then(() => {
                            setUser({ ...user, enable: false });
                          });
                        }}
                      >
                        <UserX className="w-4 h-4 mr-1" /> Vô hiệu
                      </Button>
                    ) : (
                      <Button
                        size="sm"
                        variant="default"
                        className="bg-green-600 hover:bg-green-700 text-white"
                        onClick={() => {
                          const userService = new UserService();
                          userService.activateUser(user.id).then(() => {
                            setUser({ ...user, enable: true });
                          });
                        }}
                      >
                        <CheckCircle className="w-4 h-4 mr-1" /> Kích hoạt
                      </Button>
                    )}
                  </div>
                )}
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Right Column */}
        <div className="space-y-6">
          <Card className="shadow-xl rounded-2xl border border-gray-300 dark:border-gray-700">
            <CardHeader className="bg-gray-100 dark:bg-gray-800 p-4 rounded-t-2xl">
              <CardTitle className="font-semibold dark:text-white">
                Nhóm của người dùng
              </CardTitle>
            </CardHeader>
            <CardContent className="p-4 flex flex-wrap gap-2">
              {userGroups.map((group, idx) => (
                <Badge
                  key={idx}
                  className="bg-blue-500 text-white rounded-lg px-4 py-1 text-sm"
                >
                  {group}
                </Badge>
              ))}
            </CardContent>
          </Card>

          <Card className="shadow-xl rounded-2xl border border-gray-300 dark:border-gray-700">
            <CardHeader className="bg-gray-100 dark:bg-gray-800 p-4 rounded-t-2xl">
              <CardTitle className="font-semibold dark:text-white">
                Lịch họp sắp tới
              </CardTitle>
            </CardHeader>
            <CardContent className="p-4 space-y-3">
              <div className="h-64 overflow-auto space-y-3 pr-1">
                {recentBookings.slice(0, 3).map((booking) => (
                  <div
                    key={booking.id}
                    className="bg-white dark:bg-gray-700 border border-gray-200 dark:border-gray-600 rounded-xl p-4 shadow-md"
                  >
                    <p className="font-medium dark:text-white">
                      {booking.title}
                    </p>
                    <p className="text-sm text-gray-500 dark:text-gray-300">
                      {booking.date}
                    </p>
                  </div>
                ))}
              </div>
              {recentBookings.length > 3 && (
                <Button
                  variant="outline"
                  className="w-full flex items-center justify-center mt-2 dark:text-white"
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
