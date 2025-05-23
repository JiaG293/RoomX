import CMSLayout from "@/layouts/cms-layout";
import React from "react";
import { useTranslation } from "react-i18next";

const CostStatistics: React.FC = () => {
  const { t } = useTranslation();

  return (
    <CMSLayout
      title={t("admin.menu.main.stats.title")}
      subtitle={t("admin.menu.main.stats.sub.cost")} 
    >
      CostStatistic
    </CMSLayout>
  );
};

export default CostStatistics;
