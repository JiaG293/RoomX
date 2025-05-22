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
import {
  Sidebar,
  SidebarContent,
  SidebarMenuButton,
} from "@/components/ui/sidebar";

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  const data = {
    navMain: [
      { title: "Trang chủ", url: "/portal/home", icon: CalendarCheck },
      { title: "Đặt lịch", url: "/portal/booking", icon: Clock },
      { title: "Yêu cầu", url: "/portal/pending", icon: Hourglass },
      // { title: "Nhóm", url: "/portal/groups", icon: Users },
      { title: "Hồ sơ", url: "/portal/profile", icon: User },
      // { title: "Cài đặt", url: "/portal/settings", icon: Settings },
    ],
  };

  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarContent className="mt-16 bg-blue-50 border-r border-gray-200 h-[calc(100vh-4rem)] text-gray-700 font-sans flex flex-col">
        {/* Các mục menu */}
        <NavMain items={data.navMain} />

        {/* Phần Đăng xuất */}
        <div className="mt-auto">
          <SidebarMenuButton asChild>
            <p
              className="cursor-pointer flex items-center space-x-3 p-4 text-gray-700 hover:bg-gray-200 rounded-md transition"
            >
              <LogOut className="w-5 h-5 text-red-500" />
              <span className="text-sm font-medium">Đăng xuất</span>
            </p>
          </SidebarMenuButton>
        </div>
      </SidebarContent>
    </Sidebar>
  );
}

export default AppSidebar;
