"use client";

import { ChevronRight, type LucideIcon } from "lucide-react";
import {
  Collapsible,
  CollapsibleContent,
  CollapsibleTrigger,
} from "@/components/ui/collapsible";
import {
  SidebarGroup,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarMenuSub,
  SidebarMenuSubButton,
  SidebarMenuSubItem,
} from "@/components/ui/sidebar";
import { Link, useLocation } from "react-router-dom";

export function NavMain({
  items,
}: {
  items: {
    title: string;
    url: string;
    icon?: LucideIcon;
    isActive?: boolean;
    items?: {
      title: string;
      url: string;
    }[];
    color?: string;
  }[];
}) {
  const location = useLocation(); // 👈 Lấy pathname hiện tại
  const pathname = location.pathname;

  return (
    <SidebarGroup>
      <SidebarMenu className="space-y-3">
        {items.map((item) => {
          const isParentActive = item.items?.some((sub) =>
            pathname.startsWith(sub.url)
          );
          const isDirectActive = pathname === item.url;

          return item.items && item.items.length > 0 ? (
            <Collapsible
              key={item.title}
              asChild
              defaultOpen={isParentActive}
              className="group/collapsible"
            >
              <SidebarMenuItem>
                <CollapsibleTrigger asChild>
                  <SidebarMenuButton
                    tooltip={item.title}
                    className={`bg-transparent border-none focus:outline-none hover:bg-slate-700 ${
                      isParentActive ? "bg-slate-700" : ""
                    }`}
                  >
                    {item.icon && <item.icon className={item.color ?? ""} />}
                    <span className="text-slate-300 dark:text-slate-400">
                      {item.title}
                    </span>
                    <ChevronRight className="ml-auto transition-transform duration-200 group-data-[state=open]/collapsible:rotate-90" />
                  </SidebarMenuButton>
                </CollapsibleTrigger>
                <CollapsibleContent>
                  <SidebarMenuSub>
                    {item.items.map((subItem) => {
                      const isActive = pathname === subItem.url;

                      return (
                        <SidebarMenuSubItem key={subItem.title}>
                          <SidebarMenuSubButton asChild>
                            <Link
                              to={subItem.url}
                              className={`hover:bg-slate-700 px-2 py-1 block rounded ${
                                isActive
                                  ? "bg-slate-700 text-white"
                                  : "text-slate-300 dark:text-slate-400"
                              }`}
                            >
                              <span>{subItem.title}</span>
                            </Link>
                          </SidebarMenuSubButton>
                        </SidebarMenuSubItem>
                      );
                    })}
                  </SidebarMenuSub>
                </CollapsibleContent>
              </SidebarMenuItem>
            </Collapsible>
          ) : (
            <SidebarMenuItem key={item.title}>
              <SidebarMenuButton
                tooltip={item.title}
                className={`bg-transparent border-none focus:outline-none hover:bg-slate-700 ${
                  isDirectActive ? "bg-slate-700" : ""
                }`}
                asChild
              >
                <Link
                  to={item.url}
                  className={`flex items-center gap-2 ${
                    isDirectActive
                      ? "text-white"
                      : "text-slate-300 dark:text-slate-400"
                  }`}
                >
                  {item.icon && <item.icon className={item.color ?? ""} />}
                  <span>{item.title}</span>
                </Link>
              </SidebarMenuButton>
            </SidebarMenuItem>
          );
        })}
      </SidebarMenu>
    </SidebarGroup>
  );
}
