"use client";

import { useEffect, useState } from "react";
import { ToggleGroup, ToggleGroupItem } from "@/components/ui/toggle-group";
import { Input } from "@/components/ui/input";
import { DataTable } from "@/components/admin/custom/data-table";
// import RoomItem from "@/components/admin/rooms/room-item";
// import RoomAddModal from "@/components/admin/rooms/room-add";
// import { RoomService } from "@/services/admin/room.service";
import { columns } from "@/components/admin/rooms/column";
import { RoomService } from "@/services/admin/room.service";
import RoomAddModal from "@/components/admin/rooms/room-add";

export interface RoomType {
  id: string;
  roomCode: string;
  roomClassId: string;
  placeId: string;
  description: string;
  status: string;
  totalPrice: number;
}

const RoomList: React.FC = () => {
  const [viewMode, setViewMode] = useState<"table" | "card">("table");
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [rooms, setRooms] = useState<RoomType[]>([]);
  console.log(loading)
  // State phân trang
  const [pageIndex, setPageIndex] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  // Fetch danh sách phòng
  const fetchRooms = async () => {
    setLoading(true);
    try {
      const roomService = new RoomService();
      const data = await roomService.getListRooms(pageIndex, 10);
      setRooms(data.content || []);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error("Error fetching rooms:", error);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchRooms();
  }, [pageIndex]);

  // Lọc phòng theo từ khóa tìm kiếm
  const filteredRooms = rooms.filter((room) =>
    room.description.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="flex-1">
      {/* Thanh tìm kiếm, bộ lọc và nút Thêm */}
      <div className="mb-4 flex flex-col md:flex-row gap-4 items-center">
        <Input
          placeholder="Tìm kiếm phòng..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full md:w-1/3"
        />

        <ToggleGroup
          type="single"
          value={viewMode}
          onValueChange={(value) => value && setViewMode(value as "table" | "card")}
        >
          <ToggleGroupItem value="table">Bảng</ToggleGroupItem>
          {/* <ToggleGroupItem value="card">Thẻ</ToggleGroupItem> */}
        </ToggleGroup>

        {/* Nút Thêm Phòng */}
        <RoomAddModal onAddSuccess={fetchRooms} />
      </div>

      {viewMode === "table" ? (
        <div className="flex-1 min-h-[80vh]">
          <DataTable
            columns={columns}
            data={filteredRooms}
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
          {/* {filteredRooms.map((room) => (
            <RoomItem key={room.id} room={room} />
          ))} */}
        </div>
      )}
    </div>
  );
};

export default RoomList;