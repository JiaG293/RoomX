import EquipmentList from '@/components/admin/equipments/equipment-list'
import CMSLayout from '@/layouts/cms-layout'
import React from 'react'

const Equipment: React.FC = () => {
  return (
    <CMSLayout title='Danh sách thiết bị'>
      <EquipmentList/>
    </CMSLayout>
  )
}

export default Equipment
