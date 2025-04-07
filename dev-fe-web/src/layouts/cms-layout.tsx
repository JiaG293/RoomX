import { AppSidebar } from "@/components/admin/custom/app-sidebar";
import CMSHeader from "@/components/admin/custom/cms-header";
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar";

interface CMSLayoutProps {
  children: React.ReactNode;
  title: string;
}

const CMSLayout: React.FC<CMSLayoutProps> = ({ children, title }) => {
  return (
    <SidebarProvider>
      <AppSidebar />
      <SidebarInset>
        <CMSHeader title={title} />
        <div className="mt-16 flex flex-1 flex-col gap-4 p-4 overflow-y-auto h-[calc(100vh-4rem)]">
          {children}
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
};

export default CMSLayout;
