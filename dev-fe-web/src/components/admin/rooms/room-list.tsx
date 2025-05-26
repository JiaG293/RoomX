"use client";

import { useEffect, useState } from "react";
import { Input } from "@/components/ui/input";
import { RoomService } from "@/services/admin/room.service";
import RoomAddModal from "@/components/admin/rooms/room-add";
import { BadgeDollarSign, Info, Users } from "lucide-react";
import RoomDetailModal from "@/components/admin/rooms/room-detail-modal";

export interface RoomType {
  id: string;
  roomCode: string;
  imageUrls: string[] | null;
  description: string | null;
  status: string;
  floorPlaceId: string;
  buildingPlaceId: string | null;
  branchPlaceId: string;
  roomClassId: string;
  roomClassCode: string;
  capacity: number;
  equipments: any[];
  services: any[];
  totalPrice: number;
}

const RoomList: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [rooms, setRooms] = useState<RoomType[]>([]);
const [selectedRoomId, setSelectedRoomId] = useState<string | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const getRoomStatusLabel = (status: string): string => {
    switch (status) {
      case "AVAILABLE":
        return "Khả dụng";
      case "RESERVED":
        return "Đã đặt";
      case "BLOCKED":
        return "Bảo trì";
      case "CLEANED":
        return "Vệ sinh";
      default:
        return "";
    }
  };

  const fetchRooms = async () => {
    setLoading(true);
    try {
      const roomService = new RoomService();
      const data = await roomService.getListRooms(0, 1000);
      setRooms(data.content || []);
    } catch (error) {
      console.error("Error fetching rooms:", error);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchRooms();
  }, []);

  const filteredRooms = rooms.filter((room) => {
    const search = searchTerm.toLowerCase();
    return (
      room.description?.toLowerCase().includes(search) ||
      false ||
      room.roomCode.toLowerCase().includes(search)
    );
  });

  if (loading)
    return (
      <div className="text-center mt-10 text-gray-700 dark:text-gray-300">
        Đang tải dữ liệu phòng...
      </div>
    );

  return (
    <>
      <div
        className="flex flex-col max-w-7xl mx-auto border rounded shadow
                 border-gray-200 bg-white dark:border-gray-700 dark:bg-gray-900"
        style={{ height: "85vh" }}
      >
        {/* Phần tìm kiếm + nút thêm: cố định chiều cao */}
        <div
          className="p-4 border-b flex flex-col sm:flex-row sm:items-center gap-4
                   bg-gray-50 border-gray-200 dark:bg-gray-800 dark:border-gray-700"
          style={{ flexShrink: 0 }}
        >
          <Input
            placeholder="Tìm kiếm phòng..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="flex-grow max-w-md"
          />
          <RoomAddModal />
        </div>

        {/* Phần danh sách phòng cuộn độc lập, chiếm phần còn lại */}
        <div
          className="flex-1 overflow-y-auto p-4
                   bg-gray-50 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6
                   dark:bg-gray-900"
        >
          {filteredRooms.length === 0 && (
            <p className="col-span-full text-center text-gray-500 dark:text-gray-400">
              Không có phòng phù hợp
            </p>
          )}
          {filteredRooms.map((room) => {
            // Tách vị trí
            const [building, floor, roomNumber] = room.roomCode.split("-");

            return (
              <div
                key={room.id}
                className="cursor-pointer w-[290px] h-[300px] border rounded-lg shadow-md hover:shadow-xl transition-shadow duration-300
                 bg-white dark:bg-gray-800
                 border-gray-200 dark:border-gray-700
                 flex flex-col"
                onClick={() => {
                  setSelectedRoomId(room.id); // Gán roomId cụ thể
                  setIsModalOpen(true); // Mở modal
                }}
              >
                {/* Ảnh phòng */}
                <div className="h-40 bg-gray-200 dark:bg-gray-700 flex items-center justify-center rounded-t-lg overflow-hidden">
                  {room.imageUrls && room.imageUrls.length > 0 ? (
                    <img
                      src={room.imageUrls[0]}
                      alt={`Ảnh phòng ${room.roomCode}`}
                      className="object-cover w-full h-full"
                    />
                  ) : (
                    <span className="text-gray-400 dark:text-gray-400">
                      Chưa có ảnh
                    </span>
                  )}
                </div>

                {/* Nội dung */}
                <div className="p-4 flex flex-col flex-1 gap-2">
                  {/* Vị trí phòng */}
                  <div className="text-sm text-gray-600 dark:text-gray-400">
                    <div className="flex gap-4 flex-wrap">
                      <div>
                        <span className="font-semibold text-gray-900 dark:text-white">
                          Tòa:
                        </span>{" "}
                        {building}
                      </div>
                      <div>
                        <span className="font-semibold text-gray-900 dark:text-white">
                          Lầu:
                        </span>{" "}
                        {floor}
                      </div>
                      <div>
                        <span className="font-semibold text-gray-900 dark:text-white">
                          Phòng:
                        </span>{" "}
                        {roomNumber}
                      </div>
                    </div>
                  </div>

                  {/* Trạng thái */}
                  <div className="flex items-center text-sm text-gray-700 dark:text-gray-300">
                    <Info className="w-4 h-4 mr-2 text-blue-500 dark:text-blue-400" />
                    <span className="font-medium text-gray-900 dark:text-white">
                      {getRoomStatusLabel(room.status)}
                    </span>
                  </div>

                  {/* Sức chứa */}
                  <div className="flex items-center text-sm text-gray-700 dark:text-gray-300">
                    <Users className="w-4 h-4 mr-2 text-purple-500 dark:text-purple-400" />
                    <span className="font-medium text-gray-900 dark:text-white">
                      {room.capacity} người
                    </span>
                  </div>

                  {/* Giá */}
                  <div className="flex justify-end text-green-600 dark:text-green-400 font-semibold">
                    <BadgeDollarSign className="w-5 h-5 mr-1" />
                    {room.totalPrice.toLocaleString("vi-VN", {
                      style: "currency",
                      currency: "VND",
                    })}
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      </div>
      {isModalOpen && (
        <RoomDetailModal
          open
          roomId={selectedRoomId}
          onClose={() => setIsModalOpen(false)}
        />
      )}
    </>
  );
};

export default RoomList;
