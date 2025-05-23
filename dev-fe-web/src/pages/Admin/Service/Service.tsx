import ServiceList from "@/components/admin/services/service-list";
import CMSLayout from "@/layouts/cms-layout";
import React from "react";
import { useTranslation } from "react-i18next";

const Service: React.FC = () => {
  const { t } = useTranslation();
  return (
    <CMSLayout title={t("admin.menu.main.resources.title")} subtitle={t("admin.menu.main.resources.sub.services")}>
      <ServiceList />
    </CMSLayout>
  );
};

export default Service;
