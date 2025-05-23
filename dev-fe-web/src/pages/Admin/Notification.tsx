import CMSLayout from "@/layouts/cms-layout";
import React from "react";
import { useTranslation } from "react-i18next";

const Notification: React.FC = () => {
  const { t } = useTranslation();
  return <CMSLayout title={t("admin.menu.main.notify")} >
    ntf
  </CMSLayout>;
};

export default Notification;
