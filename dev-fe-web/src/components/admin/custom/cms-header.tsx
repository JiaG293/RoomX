import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { SidebarTrigger } from "@/components/ui/sidebar";
import { Separator } from "@radix-ui/react-select";
import React from "react";
import { useTranslation } from "react-i18next";

type CMSHeaderProps = {
  title: string;
  subtitle?: string; // 👈 Thêm subtitle tùy chọn
};

const CMSHeader: React.FC<CMSHeaderProps> = ({ title, subtitle }) => {
  const { t } = useTranslation();
  return (
    <header className=" bg-[#1e3a8a] dark:bg-[#0f172a] border-b border-[#1e40af] dark:border-[#1e293b] shadow-sm fixed z-50 flex h-16 shrink-0 items-center gap-2 transition-[width,height] ease-linear group-has-[[data-collapsible=icon]]/sidebar-wrapper:h-12 w-full">
      <div className=" flex items-center gap-2 px-4">
        <SidebarTrigger className="-ml-1 text-white hover:bg-transparent hover:text-white hover:opacity-60" />
        <Separator aria-orientation="vertical" className="mr-2 h-4" />
        <Breadcrumb>
          <BreadcrumbList>
            <BreadcrumbItem className="hidden md:block">
              <BreadcrumbLink href="#" className="text-white">
                {t("admin.menu.top.admin")}
              </BreadcrumbLink>
            </BreadcrumbItem>
            <BreadcrumbSeparator className="hidden md:block" />
            <BreadcrumbItem className="hidden md:block">
              <BreadcrumbPage className="text-white">{title}</BreadcrumbPage>
            </BreadcrumbItem>
            {subtitle && (
              <>
                <BreadcrumbSeparator className="hidden md:block" />
                <BreadcrumbItem>
                  <BreadcrumbPage className="text-white">{subtitle}</BreadcrumbPage>
                </BreadcrumbItem>
              </>
            )}
            {!subtitle && ""}
          </BreadcrumbList>
        </Breadcrumb>
      </div>
    </header>
  );
};

export default CMSHeader;
