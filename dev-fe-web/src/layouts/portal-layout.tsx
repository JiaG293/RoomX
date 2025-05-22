"use client";

import { AppSidebar } from "@/components/app/custom/app-sidebar";
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar";
import { useAuth } from "@/context/AuthProvider";
import { Bell, User } from "lucide-react";
import { useState } from "react";

interface PortalLayoutProps {
  children: React.ReactNode;
}

const CustomHeader = () => {
  const { getUserInfo } = useAuth();

  const [showNotifications, setShowNotifications] = useState(false);

  const fakeNotifications = [
    { id: 1, title: "New message from admin", time: "2 mins ago" },
    { id: 2, title: "System update completed", time: "10 mins ago" },
    { id: 3, title: "New comment on your post", time: "30 mins ago" },
  ];

  const toggleNotifications = () => {
    setShowNotifications(!showNotifications);
  };

  return (
    <header className="fixed top-0 left-0 right-0 h-16 bg-[#2b4dab] shadow-md flex items-center justify-between px-6 z-10 border-b border-[#1E40AF] font-poppins text-white">
      <img
        src="/public/img/logo.png"
        className="ml-12 w-20 h- hover:scale-105 cursor-pointer"
        alt=""
      />
      <div className="flex items-center space-x-4">
        <button onClick={toggleNotifications} className="bg-transparent relative p-2 rounded-full hover:bg-blue-700/30 transition">
          <Bell size={20} className="text-white" />
          <span className="absolute top-1 right-1 block h-2 w-2 bg-red-500 rounded-full"></span>
        </button>
        {/* Notification dropdown */}
        {showNotifications && (
          <div className="absolute right-56 top-14 w-80 bg-white text-black rounded-lg shadow-lg overflow-hidden border border-gray-200 z-50">
            <div className="p-4 border-b font-semibold text-base text-gray-700 bg-gray-100">
              Thông báo
            </div>
            <ul className="max-h-64 overflow-y-auto">
              {fakeNotifications.map((notif) => (
                <li
                  key={notif.id}
                  className="px-4 py-3 hover:bg-gray-100 text-sm border-b"
                >
                  <p className="font-medium">{notif.title}</p>
                  <span className="text-xs text-gray-500">{notif.time}</span>
                </li>
              ))}
              {fakeNotifications.length === 0 && (
                <li className="p-4 text-center text-sm text-gray-500">
                  No notifications
                </li>
              )}
            </ul>
            <button
              className="w-full py-3 text-center text-blue-600 text-sm hover:underline bg-gray-50"
              onClick={() => alert("Xem thêm...")}
            >
              Xem thêm
            </button>
          </div>
        )}
        <div className="flex items-center space-x-2 cursor-pointer">
          <User size={20} className="text-white" />
          <span className="text-sm">{getUserInfo()?.email}</span>
        </div>
      </div>
    </header>
  );
};

const PortalLayout: React.FC<PortalLayoutProps> = ({ children }) => {
  return (
    <SidebarProvider>
      <AppSidebar />
      <SidebarInset>
        <CustomHeader></CustomHeader>
        <div className=" mt-16 flex flex-1 flex-col gap-4 overflow-y-auto h-[calc(100vh-4rem)]">
          {children}
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
};

export default PortalLayout;
