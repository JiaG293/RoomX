import BranchList from '@/components/admin/branches/branch-list'
import CMSLayout from '@/layouts/cms-layout'
import React from 'react'

const Branch: React.FC = () => {
  return (
    <CMSLayout title='Danh sách chi nhánh'>
      <BranchList/>
    </CMSLayout>
  )
}

export default Branch
