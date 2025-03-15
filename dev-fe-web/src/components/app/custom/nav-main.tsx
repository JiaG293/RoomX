"use client";

import { type LucideIcon } from "lucide-react";
import { SidebarGroup, SidebarMenu, SidebarMenuButton, SidebarMenuItem } from "@/components/ui/sidebar";
import { Link } from "react-router-dom";

export function NavMain({
  items,
}: {
  items: {
    title: string;
    url: string;
    icon?: LucideIcon;
    isActive?: boolean;
  }[];
}) {
  return (
    <SidebarGroup>
      <SidebarMenu>
        {items.map((item, index) => {
          // Danh sách màu sắc đa dạng
          const colors = ["text-blue-500", "text-purple-500", "text-green-500", "text-orange-500", "text-red-500"];
          const bgColors = ["bg-blue-100", "bg-purple-100", "bg-green-100", "bg-orange-100", "bg-red-100"];
          const borderColors = ["border-blue-400", "border-purple-400", "border-green-400", "border-orange-400", "border-red-400"];
          const colorClass = colors[index % colors.length];
          const bgColorClass = bgColors[index % bgColors.length];
          const borderColorClass = borderColors[index % borderColors.length];

          return (
            <SidebarMenuItem
              key={item.title}
              className={`rounded-lg ${bgColorClass} ${borderColorClass} border-2 hover:bg-opacity-80 hover:scale-105 hover:shadow-lg hover:border-2 transition-all duration-300 p-2`} // Added border class and hover effects
            >
              <SidebarMenuButton asChild>
                <Link to={item.url} className="flex items-center space-x-3">
                  {item.icon && <item.icon className={`w-6 h-6 ${colorClass} transition-all duration-300`} />}
                  <span className="text-sm font-semibold text-gray-900 hover:text-gray-800 transition-colors duration-300">
                    {item.title}
                  </span>
                </Link>
              </SidebarMenuButton>
            </SidebarMenuItem>
          );
        })}
      </SidebarMenu>
    </SidebarGroup>
  );
}
