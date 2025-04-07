"use client";

import * as React from "react";
import {
  Home,
  BarChart,
  User,
  Users,
  MapPin,
  Calendar,
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
        logo: User,
        plan: "",
      },
      {
        name: "Trang người dùng",
        logo: Users,
        plan: "",
      },
    ],
    navMain: [
      {
        title: t("menu_tong_quan"),
        url: "/admin/home",
        icon: Home,
        color: "text-blue-500",
      },
      {
        title: t("menu_quan_ly_nguoi_dung"),
        url: "#",
        icon: Users,
        color: "text-red-500",
        items: [
          {
            title: t("menu_danh_sach_nguoi_dung"),
            url: "/admin/users",
          },
          {
            title: t("menu_quan_ly_nhom"),
            url: "/admin/users/groups",
          },
        ],
      },
      {
        title: "Cơ sở",
        url: "#",
        icon: MapPin,
        color: "text-purple-500",
        items: [
          {
            title: t("menu_danh_sach_chi_nhanh"),
            url: "/admin/branches",
          },
          {
            title: t("menu_danh_sach_phong_hop"),
            url: "/admin/rooms",
          },
        ],
      },
      {
        title: "Tài nguyên",
        url: "#",
        icon: Monitor,
        color: "text-pink-500",
        items: [
          {
            title: t("menu_danh_sach_thiet_bi"),
            url: "/admin/equipments",
          },
          {
            title: t("menu_cau_hinh_dich_vu"),
            url: "/admin/services",
          },
        ],
      },
      {
        title: t("menu_quan_ly_dat_phong"),
        url: "#",
        icon: Calendar,
        color: "text-orange-500",
        items: [
          {
            title: t("menu_quan_ly_dat_phong"),
            url: "/admin/meetings",
          },
          {
            title: t("menu_phe_duyet_cuoc_hop"),
            url: "/admin/meetings/room-approvals",
          },
        ],
      },
      
      {
        title: t("menu_thong_ke"),
        url: "/admin/statistics",
        icon: BarChart,
        color: "text-green-500",
      }

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
