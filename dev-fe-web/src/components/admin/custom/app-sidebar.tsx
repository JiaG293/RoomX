"use client";

import * as React from "react";
import {
  Activity,
  AudioWaveform,
  BookOpen,
  Building,
  CalendarCheck,
  Command,
  GalleryVerticalEnd,
  LayoutDashboard,
  Package,
  Users,
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
        name: "Acme Inc",
        logo: GalleryVerticalEnd,
        plan: "Enterprise",
      },
      {
        name: "Acme Corp.",
        logo: AudioWaveform,
        plan: "Startup",
      },
      {
        name: "Evil Corp.",
        logo: Command,
        plan: "Free",
      },
    ],
    navMain: [
      {
        title: t("menu_trang_chu"),
        url: "#",
        icon: LayoutDashboard,
        isActive: false,
        items: [
          {
            title: t("menu_tong_quan"),
            url: "/admin/home",
          },
          {
            title: t("menu_thong_ke"),
            url: "/admin/statistics",
          },
          // {
          //   title: t("menu_doanh_thu"),
          //   url: "#",
          // },
          // {
          //   title: t("menu_bao_cao"),
          //   url: "/report",
          // },
        ],
      },
      {
        title: t("menu_quan_ly_nguoi_dung"),
        url: "#",
        icon: Users,
        items: [
          {
            title: t("menu_danh_sach_nguoi_dung"),
            url: "/admin/users",
          },
          // {
          //   title: t("menu_cau_hinh_quyen"),
          //   url: "#",
          // },
          {
            title: t("menu_quan_ly_nhom"),
            url: "/admin/users/groups",
          },
        ],
      },
      {
        title: t("menu_quan_ly_co_so"),
        url: "#",
        icon: Building,
        items: [
          {
            title: t("menu_danh_sach_chi_nhanh"),
            url: "/admin/branches",
          },
          // {
          //   title: t("menu_danh_sach_phong_ban"),
          //   url: "#",
          // },
          /* {
            title: t("menu_phan_bo_ngan_sach"),
            url: "#",
          }, */
        ],
      },
      {
        title: t("menu_quan_ly_tai_nguyen"),
        url: "#",
        icon: Package,
        items: [
          {
            title: t("menu_danh_sach_phong_hop"),
            url: "/admin/rooms",
          },
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
        title: t("menu_quan_ly_cuoc_hop"),
        url: "#",
        icon: CalendarCheck,
        items: [
          {
            title: t("menu_quan_ly_dat_phong"),
            url: "/admin/meetings",
          },
          {
            title: t("menu_phe_duyet_cuoc_hop"),
            url: "/admin/meetings/room-approvals",
          },
          // {
          //   title: t("menu_cau_hinh_cuoc_hop"),
          //   url: "#",
          // },
          // {
          //   title: t("menu_yeu_cau_dich_vu"),
          //   url: "#",
          // },
          // {
          //   title: t("menu_phan_hoi_nhanh"),
          //   url: "#",
          // },
        ],
      },
      /* {
        title: t("menu_hoat_dong"),
        url: "#",
        icon: Activity,
        items: [
          {
            title: t("menu_quan_ly_phien"),
            url: "#",
          },
          {
            title: t("menu_nhat_ky_hoat_dong"),
            url: "#",
          },
        ],
      }, */
      /* {
        title: t("menu_thong_tin"),
        url: "#",
        icon: BookOpen,
        items: [
          {
            title: t("menu_huong_dan_su_dung"),
            url: "#",
          },
          {
            title: t("menu_ve_chung_toi"),
            url: "#",
          },
          {
            title: t("menu_thong_tin_san_pham"),
            url: "#",
          },
          {
            title: t("menu_ma_nguon"),
            url: "#",
          },
        ],
      }, */
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
