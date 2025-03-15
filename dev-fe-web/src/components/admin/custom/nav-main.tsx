"use client";

import { ChevronRight, type LucideIcon } from "lucide-react";
import {
  SidebarGroup,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { Link, useLocation } from "react-router-dom";
import { useState, useEffect } from "react";

export function NavMain({
  items,
}: {
  items: {
    title: string;
    url: string;
    icon?: LucideIcon;
    color?: string;
    isActive?: boolean;
  }[];
}) {
  const location = useLocation(); // Lấy location hiện tại từ react-router-dom
  const [activeItem, setActiveItem] = useState<string>(location.pathname); // Khởi tạo activeItem từ URL hiện tại

  // Cập nhật activeItem khi location thay đổi
  useEffect(() => {
    setActiveItem(location.pathname);
  }, [location]);

  return (
    <SidebarGroup>
      <SidebarMenu>
        {items.map((item) => {
          const isItemActive = activeItem === item.url; // Kiểm tra nếu item đang được chọn

          return (
            <SidebarMenuItem
              key={item.title}
              className={`${
                isItemActive
                  ? 'bg-blue-300 text-white border-l-4 border-blue-400' // Đổi màu nền và thêm viền cho mục được chọn
                  : 'bg-transparent'
              } rounded-md`}
            >
              <Link to={item.url}>
                <SidebarMenuButton
                  tooltip={item.title}
                  className="bg-transparent border-none flex items-center p-2 rounded-md" // Loại bỏ focus và hover effects
                >
                  {item.icon && (
                    <item.icon
                      className={`mr-3 text-md ${item.color || 'text-gray-600'}`} // Sử dụng màu sắc từ thuộc tính color
                    />
                  )}
                  <span className={`text-sm font-medium ${
                    isItemActive ? 'text-white' : 'text-gray-900'
                  }`}>{item.title}</span>
                  {isItemActive && (
                    <ChevronRight className="ml-auto text-lg text-white" />
                  )}
                </SidebarMenuButton>
              </Link>
            </SidebarMenuItem>
          );
        })}
      </SidebarMenu>
    </SidebarGroup>
  );
}
