import UserList from '@/components/admin/users/user-list'
import CMSLayout from '@/layouts/cms-layout'
import React from 'react'
import { useTranslation } from 'react-i18next'

const User: React.FC = () => {
  const { t } = useTranslation()
  return (
    <CMSLayout title={t("admin.menu.main.users.title")} subtitle={t("admin.menu.main.users.sub.accounts")}>
        <UserList ></UserList>
    </CMSLayout>
  )
}

export default User
