import { useState, useEffect } from "react";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogTitle,
  DialogDescription,
  DialogClose,
} from "@/components/ui/dialog";
import { RoomService } from "@/services/admin/room.service";
import { BranchService } from "@/services/admin/branch.service";
import { toast } from "sonner";
import { RoomValidator } from "@/validators/room.validators";

export interface Room {
  roomCode: string;
  placeId: string;
  roomClassId: string;
  description: string;
  status: string;
  buildingId?: string; // Thêm buildingId vào Room interface
  floorId?: string;
}


const RoomAddModal: React.FC = () => {
  const [roomCode, setRoomCode] = useState("");
  const [placeId, setPlaceId] = useState("");
  const [roomClassId, setRoomClassId] = useState("");
  const [description, setDescription] = useState("");
  const [status, setStatus] = useState("AVAILABLE");
  const [branches, setBranches] = useState<any[]>([]);
  const [buildings, setBuildings] = useState<any[]>([]);
  const [buildingId, setBuildingId] = useState("");
  const [floors, setFloors] = useState<{ id: string; name: string }[]>([]);
  const [floorId, setFloorId] = useState("");
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const roomService = new RoomService();
  const branchService = new BranchService();

  return <></>;
};
export default RoomAddModal;
