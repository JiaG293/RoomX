import ServiceList from '@/components/admin/services/service-list'
import CMSLayout from '@/layouts/cms-layout'
import React from 'react'

const Service: React.FC = () => {
  return (
    <CMSLayout title='Danh sách dịch vụ'>
      <ServiceList/>
    </CMSLayout>
  )
}

export default Service
