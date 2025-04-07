import React, { useState } from "react";
import CMSLayout from "@/layouts/cms-layout";
import BranchEdit from "@/components/admin/branches/branch-edit";
import { Card } from "@/components/ui/card";
import { Button } from "@/components/ui/button";

const initialBuildings = [
  {
    id: 1,
    name: "Tòa nhà A",
    floors: ["Tầng 1", "Tầng 2", "Tầng 3", "Tầng 4", "Tầng 5"],
  },
  {
    id: 2,
    name: "Tòa nhà B",
    floors: ["Tầng 1", "Tầng 2", "Tầng 3"],
  },
  {
    id: 3,
    name: "Tòa nhà C",
    floors: ["Tầng 1", "Tầng 2", "Tầng 3", "Tầng 4"],
  },
  {
    id: 4,
    name: "Tòa nhà D",
    floors: ["Tầng 1", "Tầng 2"],
  },
  {
    id: 5,
    name: "Tòa nhà E",
    floors: ["Tầng 1", "Tầng 2", "Tầng 3", "Tầng 4", "Tầng 5", "Tầng 6"],
  },
];


const BuildingList: React.FC = () => {
  const [buildings, setBuildings] = useState(initialBuildings);
  const [selectedBuilding, setSelectedBuilding] = useState<number | null>(null);

  const handleSelectBuilding = (id: number) => {
    setSelectedBuilding(id);
  };

  const currentBuilding = buildings.find((b) => b.id === selectedBuilding);

  return (
    <Card className="p-4 h-full flex flex-col overflow-hidden">
      <div className="flex justify-between mb-4">
        <Button onClick={() => alert("Thêm toà nhà")}>Thêm toà nhà</Button>
        
      </div>
      <div className="flex-1 grid grid-rows-2 gap-4 overflow-hidden">
        {/* Danh sách tòa nhà */}
        <div className="overflow-y-auto border p-2 rounded h-full">
          <ul className="space-y-2">
            {buildings.map((building) => (
              <li
                key={building.id}
                onClick={() => handleSelectBuilding(building.id)}
                className={`p-2 border rounded cursor-pointer ${
                  selectedBuilding === building.id ? "bg-blue-100" : ""
                }`}
              >
                {building.name}
              </li>
            ))}
          </ul>
        </div>
        {/* Danh sách tầng của tòa nhà đã chọn */}
        <div className="overflow-y-auto border p-2 rounded h-full">
        <Button
          onClick={() =>
            selectedBuilding
              ? alert("Thêm tầng cho " + currentBuilding?.name)
              : alert("Chọn tòa nhà để thêm tầng")
          }
        >
          Thêm tầng
        </Button>
          {currentBuilding ? (
            <>
              <h4 className="font-bold mb-2">Các tầng của {currentBuilding.name}:</h4>
              <ul className="list-disc list-inside">
                {currentBuilding.floors.map((floor, index) => (
                  <li key={index}>{floor}</li>
                ))}
              </ul>
            </>
          ) : (
            <p className="text-gray-500">Chọn một tòa nhà để xem các tầng</p>
          )}
        </div>
      </div>
    </Card>
  );
};

const BranchDetail: React.FC = () => {
  return (
    <CMSLayout title="Thông tin chi nhánh">
      <div className="flex justify-center w-full px-4">
        <div className="grid grid-cols-2 gap-6 w-full">
          <BranchEdit />
          <BuildingList />
        </div>
      </div>
    </CMSLayout>
  );
};

export default BranchDetail;