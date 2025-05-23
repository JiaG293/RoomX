"use client";

import * as React from "react";
import {
  Home,
  User,
  Users,
  MapPin,
  Calendar,
  Monitor,
  BookOpenText,
  BarChart,
  Bell,
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
      name: t("admin.menu.top.admin"),
      logo: User,
      plan: "",
    },
    {
      name: t("admin.menu.top.portal"),
      logo: Users,
      plan: "",
    },
  ],
  navMain: [
    {
      title: t("admin.menu.main.home"),
      url: "/admin/home",
      icon: Home,
      color: "text-blue-600",
    },
    {
      title: t("admin.menu.main.users.title"),
      url: "#",
      icon: Users,
      color: "text-indigo-500",
      items: [
        {
          title: t("admin.menu.main.users.sub.accounts"),
          url: "/admin/users",
        },
        {
          title: t("admin.menu.main.users.sub.groups"),
          url: "/admin/users/groups",
        },
      ],
    },
    {
      title: t("admin.menu.main.locations.title"),
      url: "#",
      icon: MapPin,
      color: "text-green-500",
      items: [
        {
          title: t("admin.menu.main.locations.sub.branches"),
          url: "/admin/branches",
        },
        {
          title: t("admin.menu.main.locations.sub.rooms"),
          url: "/admin/rooms",
        },
      ],
    },
    {
      title: t("admin.menu.main.resources.title"),
      url: "#",
      icon: Monitor,
      color: "text-cyan-500",
      items: [
        {
          title: t("admin.menu.main.resources.sub.equipments"),
          url: "/admin/equipments",
        },
        {
          title: t("admin.menu.main.resources.sub.services"),
          url: "/admin/services",
        },
      ],
    },
    {
      title: t("admin.menu.main.schedule.title"),
      url: "#",
      icon: Calendar,
      color: "text-orange-500",
      items: [
        {
          title: t("admin.menu.main.schedule.sub.overview"),
          url: "/admin/meetings",
        },
        {
          title: t("admin.menu.main.schedule.sub.pending"),
          url: "/admin/meetings/room-approvals",
        },
      ],
    },
    {
      title: t("admin.menu.main.stats.title"),
      url: "/admin/statistics",
      icon: BarChart,
      color: "text-amber-500",
      items: [
        {
          title: t("admin.menu.main.stats.sub.time"),
          url: "/admin/statistics/times",
        },
        {
          title: t("admin.menu.main.stats.sub.room"),
          url: "/admin/statistics/rooms",
        },
        {
          title: t("admin.menu.main.stats.sub.user"),
          url: "/admin/statistics/users",
        },
        {
          title: t("admin.menu.main.stats.sub.cost"),
          url: "/admin/statistics/costs",
        },
      ],
    },
    {
      title: t("admin.menu.main.notify"),
      url: "/admin/notifications",
      icon: Bell,
      color: "text-rose-500",
    },
    {
      title: t("admin.menu.main.info.title"),
      url: "#",
      icon: BookOpenText,
      color: "text-slate-500",
      items: [
        {
          title: t("admin.menu.main.info.sub.guide"),
          url: "/admin/guide",
        },
        {
          title: t("admin.menu.main.info.sub.about"),
          url: "/admin/about",
        },
      ],
    },
  ],
};

  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <TeamSwitcher />
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
