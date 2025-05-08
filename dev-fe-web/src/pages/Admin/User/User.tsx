import UserList from '@/components/admin/users/user-list'
import CMSLayout from '@/layouts/cms-layout'
import React from 'react'

const User: React.FC = () => {
  return (
    <CMSLayout title='Danh sách người dùng'>
        <UserList ></UserList>
    </CMSLayout>
  )
}

export default User
