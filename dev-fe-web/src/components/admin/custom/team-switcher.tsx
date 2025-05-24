import * as React from "react";
import { ChevronsUpDown, Shield, ShieldCheck, User, Users } from "lucide-react";
import { Link } from "react-router-dom";

import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuShortcut,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  useSidebar,
} from "@/components/ui/sidebar";
import { useTranslation } from "react-i18next";

export function TeamSwitcher() {
  const { isMobile } = useSidebar();
  const { t } = useTranslation();

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
              <div className="bg-transparent flex aspect-square size-8 items-center justify-center rounded-lg text-slate-700 dark:text-slate-300">
                <ShieldCheck className="size-5 text-slate-300 dark:text-slate-300" />
              </div>
              <div className="grid flex-1 text-left text-sm leading-tight">
                <span className="truncate font-semibold text-slate-300 dark:text-slate-100 drop-shadow-[0_0_2px_rgba(0,0,0,0.4)]">
                  {t("admin.menu.top.admin")}
                </span>
                <span className="truncate text-xs text-slate-500 dark:text-slate-400"></span>
              </div>
              <ChevronsUpDown className="ml-auto size-4 text-slate-500 dark:text-slate-400" />
            </SidebarMenuButton>
          </DropdownMenuTrigger>
          <DropdownMenuContent
            className="w-[--radix-dropdown-menu-trigger-width] min-w-56 rounded-lg"
            align="start"
            side={isMobile ? "bottom" : "right"}
            sideOffset={4}
          >
            <DropdownMenuLabel className="text-xs text-muted-foreground">
              {t("admin.menu.top.switch")}
            </DropdownMenuLabel>

            <Link to="#">
              <DropdownMenuItem className="gap-2 p-2 cursor-pointer">
                <div className="flex size-6 items-center justify-center rounded-sm border">
                  <User className="size-4 shrink-0" />
                </div>
                {t("admin.menu.top.admin")}
                <DropdownMenuShortcut>⌘1</DropdownMenuShortcut>
              </DropdownMenuItem>
            </Link>

            <Link to="/portal/home" target="_blank">
              <DropdownMenuItem className="gap-2 p-2 cursor-pointer">
                <div className="flex size-6 items-center justify-center rounded-sm border">
                  <Users className="size-4 shrink-0" />
                </div>
                {t("admin.menu.top.portal")}
                <DropdownMenuShortcut>⌘2</DropdownMenuShortcut>
              </DropdownMenuItem>
            </Link>
          </DropdownMenuContent>
        </DropdownMenu>
      </SidebarMenuItem>
    </SidebarMenu>
  );
}
