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
    <header className="fixed top-0 left-0 right-0 h-16 bg-white bg-opacity-80 backdrop-blur-lg shadow-md flex items-center justify-between px-6 z-10 border-b border-green-300 font-poppins">
<h1 className="text-4xl font-extrabold text-gray-600 font-poppins">
  RoomX
</h1>

      <div className="flex items-center space-x-4">
        <button className="bg-transparent relative p-2 rounded-full hover:bg-gray-100">
          <Bell size={24} />
          <span className="absolute top-0 right-0 block h-2 w-2 bg-red-500 rounded-full"></span>
        </button>
        <div className="flex items-center space-x-2 cursor-pointer">
          <User size={24} />
          <span className="text-sm font-medium text-green-800">
            {getUserInfo()?.email}
          </span>
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
        <div className=" mt-16 flex flex-1 flex-col gap-4 p-4 overflow-y-auto h-[calc(100vh-4rem)]">
          {children}
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
};

export default PortalLayout;
