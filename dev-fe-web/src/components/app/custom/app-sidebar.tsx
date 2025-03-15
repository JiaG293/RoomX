"use client";

import * as React from "react";
import {
  CalendarCheck,
  Clock,
  Users,
  Bell,
  User,
  Settings,
  Hourglass,
  LogOut,
} from "lucide-react";
import { NavMain } from "@/components/app/custom/nav-main";
import { Sidebar, SidebarContent, SidebarMenuButton } from "@/components/ui/sidebar";

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  const data = {
    navMain: [
      { title: "Lịch của tôi", url: "/portal/home", icon: CalendarCheck },
      { title: "Đặt lịch", url: "/portal/booking", icon: Clock },
      { title: "Lịch chờ duyệt", url: "/portal/pending", icon: Hourglass },
      { title: "Gợi ý đặt lịch", url: "/portal/suggestions", icon: Bell },
      { title: "Nhóm", url: "/portal/groups", icon: Users },
      { title: "Hồ sơ", url: "/portal/profile", icon: User },
      { title: "Cài đặt", url: "/portal/settings", icon: Settings },
    ],
  };

  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarContent className="mt-16 bg-gradient-to-b from-blue-100 via-purple-100 to-pink-200 text-white shadow-lg h-[calc(100vh-4rem)] font-poppins flex flex-col">
        {/* Các mục menu */}
        <NavMain items={data.navMain} />
        
        {/* Phần Đăng xuất được tách biệt và đẩy xuống cuối cùng */}
        <div className="mt-auto">
          <SidebarMenuButton asChild>
            <a href="/logout" className=" flex items-center space-x-3 p-6 text-red-500 hover:bg-opacity-80 transition rounded-lg bg-red-100">
              <LogOut className="w-6 h-6" />
              <span className="text-sm font-semibold">Đăng xuất</span>
            </a>
          </SidebarMenuButton>
        </div>
      </SidebarContent>
    </Sidebar>
  );
}

export default AppSidebar;
