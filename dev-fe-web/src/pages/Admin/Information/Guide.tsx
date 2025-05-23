import CMSLayout from "@/layouts/cms-layout";
import React from "react";
import { useTranslation } from "react-i18next";

const Guide: React.FC = () => {
  const { t } = useTranslation();
  return (
    <CMSLayout
      title={t("admin.menu.main.info.title")}
      subtitle={t("admin.menu.main.info.sub.guide")}
    >
      Guide
    </CMSLayout>
  );
};

export default Guide;
