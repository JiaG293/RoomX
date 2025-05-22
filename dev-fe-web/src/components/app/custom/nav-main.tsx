"use client";

import { type LucideIcon } from "lucide-react";
import {
  SidebarGroup,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { Link, useLocation } from "react-router-dom";

export function NavMain({
  items,
}: {
  items: {
    title: string;
    url: string;
    icon?: LucideIcon;
  }[];
}) {
  const location = useLocation();

  const iconColors = [
    "text-blue-500",
    "text-purple-500",
    "text-green-500",
    "text-yellow-500",
    "text-pink-500",
    "text-orange-500",
  ];

  return (
    <SidebarGroup>
      <SidebarMenu className="space-y-3">
        {items.map((item, index) => {
          const isActive = location.pathname === item.url;
          const iconColor = iconColors[index % iconColors.length];

          return (
            <SidebarMenuItem
              key={item.title}
              className={`relative rounded-md ${
                isActive ? "bg-blue-200" : "text-gray-700"
              } hover:bg-gray-100 transition`}
            >
              {isActive && (
                <div className="absolute right-0 top-1/2 -translate-y-1/2 h-8 w-1.5 bg-blue-500 rounded-l-sm" />
              )}

              <SidebarMenuButton asChild>
                <Link
                  to={item.url}
                  className="flex items-center space-x-5 px-6 py-4 text-gray-800 font-sans"
                  style={{ fontFamily: '"Inter", "Segoe UI", Tahoma, Geneva, Verdana, sans-serif' }}
                >
                  {item.icon && (
                    <item.icon
                      className={`w-7 h-7 ${
                        isActive ? "text-blue-600" : iconColor
                      }`}
                    />
                  )}
                  <span className="text-base font-medium tracking-wide">{item.title}</span>
                </Link>
              </SidebarMenuButton>
            </SidebarMenuItem>
          );
        })}
      </SidebarMenu>
    </SidebarGroup>
  );
}
