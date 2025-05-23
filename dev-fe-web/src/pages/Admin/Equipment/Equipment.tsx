import EquipmentList from '@/components/admin/equipments/equipment-list'
import CMSLayout from '@/layouts/cms-layout'
import React from 'react'
import { useTranslation } from 'react-i18next';

const Equipment: React.FC = () => {
  const { t } = useTranslation();

  return (
  <CMSLayout
      title={t("admin.menu.main.resources.title")}
      subtitle={t("admin.menu.main.resources.sub.equipments")}
    >
      <EquipmentList/>
    </CMSLayout>
  )
}

export default Equipment
