import GroupUserList from "@/components/admin/group-users/group-list";
import CMSLayout from "@/layouts/cms-layout";
import React from "react";
import { useTranslation } from "react-i18next";

const GroupUser: React.FC = () => {
  const { t } = useTranslation();

  return (
    <CMSLayout
      title={t("admin.menu.main.users.title")}
      subtitle={t("admin.menu.main.users.sub.groups")}
    >
      <GroupUserList></GroupUserList>
    </CMSLayout>
  );
};

export default GroupUser;
