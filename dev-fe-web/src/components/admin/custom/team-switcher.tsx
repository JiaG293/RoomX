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
              className="bg-[var(--navitem-bg)] hover:bg-[var(--navitem-bg-hover)] shadow-md text-[var(--navitem-text)] focus:outline-none border-none focus:border-none data-[state=open]:bg-sidebar-accent data-[state=open]:text-sidebar-accent-foreground"
            >
              <div className="bg-transparent flex aspect-square size-8 items-center justify-center rounded-lg text-sidebar-primary-foreground">
                <ShieldCheck className="size-5 text-gray-900 dark:text-gray-100" />
              </div>
              <div className="grid flex-1 text-left text-sm leading-tight">
                <span className="truncate font-semibold text-gray-900 dark:text-gray-100">
                  {t("admin.menu.top.admin")}
                </span>
                <span className="truncate text-xs"></span>
              </div>
              <ChevronsUpDown className="ml-auto" />
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

            <Link to="http://localhost:5173/portal/home" target="_blank">
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
