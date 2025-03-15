"use client";

import * as React from "react";
import {
  Home,
  BarChart,
  User,
  Users,
  MapPin,
  Calendar,
  Settings,
  File,
  CheckCircle,
  UserPlus,
  Monitor,
  Building2,
  Coffee,
} from "lucide-react";

import { NavMain } from "@/components/admin/custom/nav-main";
import { TeamSwitcher } from "@/components/admin/custom/team-switcher";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
} from "@/components/ui/sidebar";
import { NavUser } from "@/components/admin/custom/nav-user";
import { useAuth } from "@/context/AuthProvider";
import { useTranslation } from "react-i18next";

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  const { getUserInfo } = useAuth();
  const { t } = useTranslation();

  const data = {
    teams: [
      {
        name: "Trang quản trị",
        logo: User, // Đây có thể là icon đặc trưng cho quản trị viên
        plan: "",
      },
      {
        name: "Trang người dùng",
        logo: Users, // Người dùng
        plan: "",
      },
    ],
    navMain: [
      {
        title: t("menu_tong_quan"),
        url: "/admin/home",
        icon: Home,
        color: 'text-blue-500', // Màu cho icon "Trang chủ"
      },
      {
        title: t("menu_thong_ke"),
        url: "/admin/statistics",
        icon: BarChart,
        color: 'text-green-500', // Màu cho icon "Thống kê"
      },
      {
        title: t("menu_danh_sach_nguoi_dung"),
        url: "/admin/users",
        icon: Users,
        color: 'text-red-500', // Màu cho icon "Danh sách người dùng"
      },
      {
        title: t("menu_quan_ly_nhom"),
        url: "/admin/users/groups",
        icon: UserPlus,
        color: 'text-yellow-500', // Màu cho icon "Quản lý nhóm"
      },
      {
        title: t("menu_danh_sach_chi_nhanh"),
        url: "/admin/branches",
        icon: MapPin,
        color: 'text-purple-500', // Màu cho icon "Chi nhánh"
      },
      {
        title: t("menu_danh_sach_phong_hop"),
        url: "/admin/rooms",
        icon: Building2,
        color: 'text-indigo-500', // Màu cho icon "Phòng họp"
      },
      {
        title: t("menu_danh_sach_thiet_bi"),
        url: "/admin/equipments",
        icon: Monitor,
        color: 'text-pink-500', // Màu cho icon "Thiết bị"
      },
      {
        title: t("menu_cau_hinh_dich_vu"),
        url: "/admin/services",
        icon: Coffee,
        color: 'text-teal-500', // Màu cho icon "Dịch vụ"
      },
      {
        title: t("menu_quan_ly_dat_phong"),
        url: "/admin/meetings",
        icon: Calendar,
        color: 'text-orange-500', // Màu cho icon "Đặt phòng"
      },
      {
        title: t("menu_phe_duyet_cuoc_hop"),
        url: "/admin/meetings/room-approvals",
        icon: CheckCircle,
        color: 'text-lime-500', // Màu cho icon "Duyệt cuộc họp"
      },
    ],
  };

  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <TeamSwitcher teams={data.teams} />
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={data.navMain} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser
          user={{
            name: getUserInfo()?.username + "",
            email: getUserInfo()?.email + "",
            avatar: "",
          }}
        />
      </SidebarFooter>
    </Sidebar>
  );
}
