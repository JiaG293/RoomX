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
    <header className="bg-[var(--header-background)] border-b border-[var(--header-border)] shadow-sm fixed z-50 flex h-16 shrink-0 items-center gap-2 transition-[width,height] ease-linear group-has-[[data-collapsible=icon]]/sidebar-wrapper:h-12 w-full">
      <div className="flex items-center gap-2 px-4">
        <SidebarTrigger className="-ml-1" />
        <Separator aria-orientation="vertical" className="mr-2 h-4" />
        <Breadcrumb>
          <BreadcrumbList>
            <BreadcrumbItem className="hidden md:block">
              <BreadcrumbLink href="#">{t("admin.menu.top.admin")}</BreadcrumbLink>
            </BreadcrumbItem>
            <BreadcrumbSeparator className="hidden md:block" />
            <BreadcrumbItem className="hidden md:block">
              <BreadcrumbPage>{title}</BreadcrumbPage>
            </BreadcrumbItem>
            {subtitle && (
              <>
                <BreadcrumbSeparator className="hidden md:block" />
                <BreadcrumbItem>
                  <BreadcrumbPage>{subtitle}</BreadcrumbPage>
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
