import RoomList from '@/components/admin/rooms/room-list'
import CMSLayout from '@/layouts/cms-layout'
import React from 'react'
import { useTranslation } from 'react-i18next';

const Room: React.FC = () => {
  const { t } = useTranslation();

  return (
    <CMSLayout
      title={t("admin.menu.main.locations.title")}
      subtitle={t("admin.menu.main.locations.sub.rooms")}
    >
        <RoomList ></RoomList>
    </CMSLayout>
  )
}

export default Room