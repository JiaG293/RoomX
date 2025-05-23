import CMSLayout from '@/layouts/cms-layout'
import React from 'react'
import { useTranslation } from 'react-i18next';

const RoomStatistics: React.FC = () => {
  const { t } = useTranslation();

  return (
    <CMSLayout
      title={t("admin.menu.main.stats.title")}
      subtitle={t("admin.menu.main.stats.sub.room")}
    >RoomStatistics</CMSLayout>
  )
}

export default RoomStatistics