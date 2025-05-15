import UserList from '@/components/admin/users/user-list'
import CMSLayout from '@/layouts/cms-layout'
import React from 'react'
import { useTranslation } from 'react-i18next'

const User: React.FC = () => {
  const { t } = useTranslation()
  return (
    <CMSLayout title={t('menu_danh_sach_nguoi_dung')}>
        <UserList ></UserList>
    </CMSLayout>
  )
}

export default User
