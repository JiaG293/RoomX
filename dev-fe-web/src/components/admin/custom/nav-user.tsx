"use client";

import { ChevronsUpDown, LogOut, Moon, Sun, Globe, User } from "lucide-react";

import { Avatar, AvatarFallback } from "@/components/ui/avatar";
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
import { useCallback } from "react";
import { ThemeToggle } from "@/components/admin/custom/theme-toggle";
import { LanguageSelect } from "@/components/admin/custom/select-language";
import { getShortName } from "@/utils/string.util";
import { useTranslation } from "react-i18next";
import { Profile } from "@/components/admin/custom/profile-modal";

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
  const { logout, getUserInfo } = useAuth();
  const { t } = useTranslation();

  const handleLogout = useCallback(() => {
    console.log("Tự chạy logout");
    logout();
  }, [logout]);

  console.log(getUserInfo());

  return (
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

    dark:hover:bg-slate-800/70 dark:hover:shadow-lg
  "
            >
              <Avatar className="h-8 w-8 rounded-lg bg-slate-300 text-slate-800 dark:bg-slate-700 dark:text-white">
                <AvatarFallback className="rounded-lg">
                  {getShortName(user.name) || "AD"}
                </AvatarFallback>
              </Avatar>
              <div className="grid flex-1 text-left text-sm leading-tight">
                <span className="truncate font-semibold text-slate-200 dark:text-slate-100">
                  {user.name}
                </span>
                <span className="truncate text-xs text-slate-400 dark:text-slate-400">
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
                <Avatar className="h-8 w-8 rounded-lg">
                  {/* <AvatarImage src={user.avatar} alt={user.name} /> */}
                  <AvatarFallback className="rounded-lg">
                    {user.name
                      ? user.name
                          .split(" ")
                          .map((word) => word[0])
                          .join("")
                          .slice(0, 2)
                          .toUpperCase()
                      : "NA"}
                  </AvatarFallback>
                </Avatar>

                <div className="grid flex-1 text-left text-sm leading-tight">
                  <span className="truncate font-semibold">{user.name}</span>
                  <span className="truncate text-xs">{user.email}</span>
                </div>
              </div>
            </DropdownMenuLabel>
            <DropdownMenuSeparator />
            <DropdownMenuGroup>
              <Profile
                trigger={
                  <div
                    className="cursor-pointer relative flex select-none items-center gap-2 rounded-sm px-2 py-1.5 text-sm outline-none transition-colors focus:bg-accent focus:text-accent-foreground data-[disabled]:pointer-events-none data-[disabled]:opacity-50 [&_svg]:pointer-events-none [&_svg]:size-4 [&_svg]:shrink-0"
                    onClick={(e) => e.stopPropagation()}
                  >
                    <User />
                    {t("admin.menu.bottom.profile")}
                  </div>
                }
              />
            </DropdownMenuGroup>
            <DropdownMenuSeparator />
            <DropdownMenuGroup>
              <DropdownMenuItem
                onSelect={(e) => e.preventDefault()}
                className="flex items-center h-10"
              >
                <Globe />
                <LanguageSelect position="right"></LanguageSelect>
              </DropdownMenuItem>
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
            <DropdownMenuItem
              onClick={handleLogout}
              style={{ color: "hsl(0, 85%, 50%)", fontWeight: "500" }}
            >
              <LogOut />
              <span>{t("admin.menu.bottom.logout")}</span>
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </SidebarMenuItem>
    </SidebarMenu>
  );
}
