import { ChevronsUpDown, LogOut, Moon, Sun, Globe, User } from "lucide-react";

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  useSidebar,
} from "@/components/ui/sidebar";
import { useAuth } from "@/context/AuthProvider";
import { useCallback, useEffect, useState } from "react";
import { ThemeToggle } from "@/components/admin/custom/theme-toggle";
import { LanguageSelect } from "@/components/admin/custom/select-language";
import { getShortName } from "@/utils/string.util";
import { useTranslation } from "react-i18next";
import ProfileEditModal from "@/components/admin/custom/profile-modal";
import { toast } from "sonner";
import { AuthService } from "@/services/auth.service";
import { set } from "date-fns";

export function NavUser({
  user,
}: {
  user: {
    name: string | undefined;
    email: string | undefined;
    avatar: string | undefined;
  };
}) {
  const { isMobile } = useSidebar();
  const { logout } = useAuth();
  const { t } = useTranslation();
   const [userProfile, setUserProfile] = useState<{
    id?: string;
    avatarImage?: string;
    email?: string;
    userCode?: string;
    firstName?: string;
    lastName?: string;
    phoneNumber?: string;
    gender?: string;
  }>({});

  const handleLogout = useCallback(() => {
    logout();
  }, [logout]);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const authService = new AuthService();
        const data = await authService.getProfile(); // Gọi API
        setUserProfile(data); // Lưu toàn bộ data
        console.log(data);
      } catch (error) {
        toast.error("Không thể tải dữ liệu người dùng");
      }
    };
    fetchProfile();
  }, []);

  const displayName = `${userProfile.firstName ?? ""} ${userProfile.lastName ?? ""}`.trim();
  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <>
      <SidebarMenu>
        <SidebarMenuItem>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <SidebarMenuButton
                size="lg"
                className="
    bg-white/10 backdrop-blur-sm
    flex items-center gap-2 text-slate-800
    hover:bg-white/20 hover:shadow-lg
    ring-0 border-none focus:outline-none transition-colors

    dark:bg-slate-900/40 dark:text-slate-100
    dark:hover:bg-slate-800/70 dark:hover:shadow-lg
  "
              >
                <Avatar className="h-8 w-8 rounded-lg bg-slate-300 text-slate-800 dark:bg-slate-700 dark:text-white">
                  <AvatarFallback className="rounded-lg">
                    {getShortName(user.name) || "AD"}
                  </AvatarFallback>
                </Avatar>
                <div className="grid flex-1 text-left text-sm leading-tight">
                  <span className="truncate font-semibold text-slate-500 dark:text-slate-100">
                    {user.name}
                  </span>
                  <span className="truncate text-xs text-slate-300 dark:text-slate-400">
                    {user.email}
                  </span>
                </div>
                <ChevronsUpDown className="ml-auto size-4 text-slate-500 dark:text-slate-400" />
              </SidebarMenuButton>
            </DropdownMenuTrigger>
            <DropdownMenuContent
              className="w-[--radix-dropdown-menu-trigger-width] min-w-56 rounded-lg"
              side={isMobile ? "bottom" : "right"}
              align="end"
              sideOffset={4}
            >
              <DropdownMenuLabel className="p-0 font-normal">
                <div className="flex items-center gap-2 px-1 py-1.5 text-left text-sm">
                  <Avatar className="h-8 w-8 rounded-lg bg-slate-300 text-slate-800 dark:bg-slate-700 dark:text-white">
                  {userProfile.avatarImage ? (
                    <AvatarImage src={userProfile.avatarImage} alt="avatar" />
                  ) : (
                    <AvatarFallback className="rounded-lg">
                      {getShortName(displayName) || "AD"}
                    </AvatarFallback>
                  )}
                </Avatar>

                  <div className="grid flex-1 text-left text-sm leading-tight">
                    <span className="truncate font-semibold">{user.name}</span>
                    <span className="truncate text-xs">{user.email}</span>
                  </div>
                </div>
              </DropdownMenuLabel>
              <DropdownMenuSeparator />
              {/* Hồ sơ */}
              <DropdownMenuGroup>
                <DropdownMenuItem
                  className="flex items-center h-10 cursor-pointer"
                  onClick={() => setIsModalOpen(true)}
                >
                  <User className="mr-2 h-4 w-4" />
                  <span>{t("admin.menu.bottom.profile")}</span>
                </DropdownMenuItem>
              </DropdownMenuGroup>
              <DropdownMenuSeparator />
              {/* Chọn ngôn ngữ */}
              <DropdownMenuGroup>
                <DropdownMenuItem
                  onSelect={(e) => e.preventDefault()}
                  className="flex items-center h-10"
                >
                  <Globe />
                  <LanguageSelect position="right"></LanguageSelect>
                </DropdownMenuItem>
                {/* Chế độ tối */}
                <DropdownMenuItem
                  onSelect={(e) => e.preventDefault()}
                  className="flex items-center h-10"
                >
                  {localStorage.getItem("theme") === "dark" ? (
                    <Moon className="w-5 h-5" />
                  ) : (
                    <Sun className="w-5 h-5" />
                  )}
                  <ThemeToggle variant="switch" />
                </DropdownMenuItem>
              </DropdownMenuGroup>
              <DropdownMenuSeparator />
              {/* Đăng xuất */}
              <DropdownMenuItem
                onClick={handleLogout}
                style={{ color: "hsl(0, 85%, 50%)", fontWeight: "500" }}
                className="cursor-pointer flex items-center h-10"
              >
                <LogOut />
                <span>{t("admin.menu.bottom.logout")}</span>
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </SidebarMenuItem>
      </SidebarMenu>
      {/* Modal chỉnh sửa */}
      <ProfileEditModal
        key={userProfile.id || "profile-modal"}
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        user={userProfile}
      />
    </>
  );
}
