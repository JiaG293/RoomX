"use client";

import { useEffect, useState } from "react";
import { ToggleGroup, ToggleGroupItem } from "@/components/ui/toggle-group";
import { Input } from "@/components/ui/input";
import { DataTable } from "@/components/admin/custom/data-table";
import { EquipmentService } from "@/services/admin/equipment.service";
// import EquipmentItem from "@/components/admin/equipments/equipment-item";
// import EquipmentAddModal from "@/components/admin/equipments/equipment-add";
import { columns } from "@/components/admin/equipments/column";
import EquipmentAddModal from "@/components/admin/equipments/equipment-add";

export interface EquipmentType {
  id: string;
  equipmentCode: string;
  name: string;
  brand: string;
  description: string;
  unitPrice: number;
  createdAt: string;
  updatedAt: string;
}

const EquipmentList: React.FC = () => {
  const [viewMode, setViewMode] = useState<"table" | "card">("table");
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [equipments, setEquipments] = useState<EquipmentType[]>([]);

  // State phân trang
  const [pageIndex, setPageIndex] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  // Fetch danh sách thiết bị
  const fetchEquipments = async () => {
    setLoading(true);
    try {
      const equipmentService = new EquipmentService();
      const data = await equipmentService.getListEquipments(pageIndex, 10);
      setEquipments(data.content || []);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error("Error fetching equipments:", error);
    }
    setLoading(false);
  };

  // Khi thêm thiết bị thành công
  const onAddSuccess = async () => {
    if (equipments.length >= 10) {
      setPageIndex(pageIndex + 1);
    } else {
      await fetchEquipments();
    }
  };

  console.log(loading)

  // // Khi chỉnh sửa thiết bị thành công
  // const onEditSuccess = async () => {
  //   await fetchEquipments();
  // };

  useEffect(() => {
    fetchEquipments();
  }, [pageIndex]);

  // Lọc thiết bị theo từ khóa tìm kiếm
  const filteredEquipments = equipments.filter((equipment) =>
    equipment.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="flex-1">
      {/* Thanh tìm kiếm, bộ lọc và nút Thêm */}
      <div className="mb-4 flex flex-col md:flex-row gap-4 items-center">
        <Input
          placeholder="Tìm kiếm thiết bị..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full md:w-1/3"
        />

        <ToggleGroup
          type="single"
          value={viewMode}
          onValueChange={(value) =>
            value && setViewMode(value as "table" | "card")
          }
        >
          <ToggleGroupItem value="table">Bảng</ToggleGroupItem>
          <ToggleGroupItem value="card">Thẻ</ToggleGroupItem>
        </ToggleGroup>

        {/* Nút Thêm Thiết Bị */}
        <EquipmentAddModal onAddSuccess={onAddSuccess} />
      </div>

      {viewMode === "table" ? (
        <div className="flex-1 min-h-[80vh]">
          <DataTable
            columns={columns}
            data={filteredEquipments}
            pageIndex={pageIndex}
            pageSize={10}
            pageCount={totalPages}
            onPageChange={(newPage) => {
              if (newPage >= 0 && newPage < totalPages) {
                setPageIndex(newPage);
              }
            }}
          />
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 p-4">
          {/* {filteredEquipments.map((equipment) => (
            <EquipmentItem key={equipment.id} equipment={equipment} />
          ))} */}
        </div>
      )}
    </div>
  );
};

export default EquipmentList;