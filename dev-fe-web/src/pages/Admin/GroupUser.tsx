import GroupUserList from '@/components/admin/group-users/group-list'
import CMSLayout from '@/layouts/cms-layout'
import React from 'react'

const GroupUser: React.FC = () => {
  return (
    <CMSLayout title='Danh sách nhóm người dùng'>
      <GroupUserList></GroupUserList>
    </CMSLayout>
  )
}

export default GroupUser
