import BranchList from '@/components/admin/branches/branch-list'
import CMSLayout from '@/layouts/cms-layout'
import React from 'react'
import { useTranslation } from 'react-i18next';

const Branch: React.FC = () => {
   const { t } = useTranslation();

  return (
    <CMSLayout
      title={t("admin.menu.main.locations.title")}
      subtitle={t("admin.menu.main.locations.sub.branches")}
    >
      <BranchList/>
    </CMSLayout>
  )
}

export default Branch
