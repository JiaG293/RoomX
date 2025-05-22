"use client";

import { AppSidebar } from "@/components/app/custom/app-sidebar";
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar";
import { useAuth } from "@/context/AuthProvider";
import { Bell, User } from "lucide-react";

interface PortalLayoutProps {
  children: React.ReactNode;
}

const CustomHeader = () => {
  const { getUserInfo } = useAuth();
  return (
    <header className="fixed top-0 left-0 right-0 h-16 bg-[#4d6dc5] shadow-md flex items-center justify-between px-6 z-10 border-b border-[#1E40AF] font-poppins text-white">
      <img src="/public/img/logo.png" className="ml-12 w-20 h- hover:scale-105 cursor-pointer" alt="" />
      <div className="flex items-center space-x-4">
        <button className="bg-transparent relative p-2 rounded-full hover:bg-blue-700/30 transition">
          <Bell size={20} className="text-white" />
          <span className="absolute top-1 right-1 block h-2 w-2 bg-red-500 rounded-full"></span>
        </button>
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
