"use client";

import { useEffect, useState } from "react";
import { Input } from "@/components/ui/input";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { BranchService } from "@/services/admin/branch.service";
import { Button } from "@/components/ui/button";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";
import { Plus, Pencil, Building2, X, Check } from "lucide-react";
import { useBranchStore } from "@/store/useBranchStore";
import AddBuildingModal from "@/components/admin/branches/building-modal-add";
import AddFloorModal from "@/components/admin/branches/floor-modal-add";

export type Floor = {
  id: string;
  code: string;
  name: string;
};

export type Building = {
  id: string;
  code: string;
  name: string;
  children: Floor[];
};

export type Branch = {
  id: string;
  code: string;
  name: string;
  layout: string;
  children: Building[];
};

const BranchList: React.FC = () => {
  const { branch, setBranch } = useBranchStore();
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(false);
  const [addingBranch, setAddingBranch] = useState(false);
  const [newBranch, setNewBranch] = useState({ code: "", name: "" });
  const [editingBranchId, setEditingBranchId] = useState<string | null>(null);
  const [editedBranch, setEditedBranch] = useState({ code: "", name: "" });
  const [isBuildingModalOpen, setIsBuildingModalOpen] = useState(false);
  const [selectedBranchId, setSelectedBranchId] = useState<string | null>(null);
  const [isFloorModalOpen, setIsFloorModalOpen] = useState(false);
  const [selectedBuildingId, setSelectedBuildingId] = useState<string | null>(
    null
  );

  const fetchBranches = async () => {
    setLoading(true);
    try {
      const service = new BranchService();
      const data = await service.getAllBranchesWithHierarchy();
      setBranch(data);
      console.log(data);
    } catch (error) {
      console.error("Fetch failed:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBranches();
  }, []);

  const filteredBranches = branch.filter((branch) =>
    branch.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleAddBranch = async () => {
    if (newBranch.code.trim() && newBranch.name.trim()) {
      console.log("New branch created:", newBranch);
      setAddingBranch(false);
      setNewBranch({ code: "", name: "" });
      const service = new BranchService();
      await service.createBranch(newBranch);
      await fetchBranches();
    }
  };

  const handleCancelAdd = () => {
    setAddingBranch(false);
    setNewBranch({ code: "", name: "" });
  };

  const handleEditBranch = (branch: Branch) => {
    setEditingBranchId(branch.id);
    setEditedBranch({ code: branch.code, name: branch.name });
  };

  const cancelEdit = () => {
    setEditingBranchId(null);
    setEditedBranch({ code: "", name: "" });
  };

  const saveEdit = () => {
    const updatedBranches = branch.map((b) =>
      b.id === editingBranchId
        ? { ...b, code: editedBranch.code, name: editedBranch.name }
        : b
    );
    setBranch(updatedBranches);
    cancelEdit();
  };

  const handleOpenAddBuildingModal = (branchId: string) => {
    console.log("Open add building modal for branch:", branchId);
    setSelectedBranchId(branchId);
    setIsBuildingModalOpen(true);
  };

  const handleOpenAddFloorModal = (buildingId: string) => {
    setSelectedBuildingId(buildingId);
    setIsFloorModalOpen(true);
  };

  const handleSubmitBuilding = async (buildingData: {
    code: string;
    branchId: string;
  }) => {
    if (!selectedBranchId) return;
    const service = new BranchService();
    await service.createBuilding(buildingData);
    setIsBuildingModalOpen(false);
    fetchBranches(); // reload lại danh sách
  };

  const handleSubmitFloor = async (floorData: {
    code: string;
    buildingId: string;
  }) => {
    if (!selectedBuildingId) return;
    const service = new BranchService();
    await service.createFloor(floorData);
    setIsFloorModalOpen(false);
    fetchBranches(); // reload lại dữ liệu
  };

  return (
    <>
      <TooltipProvider>
        <div className="h-[85vh] flex flex-col bg-white dark:bg-[#1a1a1a] rounded-lg shadow-md overflow-hidden">
          {/* Thanh tìm kiếm và nút thêm */}
          <div className="sticky top-0 z-10 bg-white dark:bg-[#1a1a1a] p-4 border-b border-gray-200 dark:border-gray-700">
            <div className="flex flex-col md:flex-row gap-4 items-center">
              <Input
                placeholder="Tìm kiếm chi nhánh..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="w-full md:w-1/3 bg-gray-100 dark:bg-gray-800 text-black dark:text-white"
              />
              <Tooltip>
                <TooltipTrigger asChild>
                  <Button
                    size="icon"
                    className="bg-blue-600 hover:bg-blue-700 text-white dark:bg-blue-500 dark:hover:bg-blue-600"
                    onClick={() => setAddingBranch(true)}
                  >
                    <Plus className="w-5 h-5" />
                  </Button>
                </TooltipTrigger>
                <TooltipContent>Thêm chi nhánh</TooltipContent>
              </Tooltip>
            </div>

            {/* Form thêm chi nhánh mới */}
            {addingBranch && (
              <div className="flex flex-col md:flex-row gap-2 mt-4 items-center">
                <Input
                  placeholder="Mã chi nhánh"
                  value={newBranch.code}
                  onChange={(e) =>
                    setNewBranch({ ...newBranch, code: e.target.value })
                  }
                  className="w-full md:w-1/4 bg-gray-100 dark:bg-gray-800 text-black dark:text-white"
                />
                <Input
                  placeholder="Tên chi nhánh"
                  value={newBranch.name}
                  onChange={(e) =>
                    setNewBranch({ ...newBranch, name: e.target.value })
                  }
                  className="w-full md:w-1/4 bg-gray-100 dark:bg-gray-800 text-black dark:text-white"
                />
                <Button
                  size="icon"
                  className="bg-green-600 hover:bg-green-700 text-white"
                  onClick={handleAddBranch}
                >
                  <Check className="w-5 h-5" />
                </Button>
                <Button
                  size="icon"
                  className="bg-red-600 hover:bg-red-700 text-white"
                  onClick={handleCancelAdd}
                >
                  <X className="w-5 h-5" />
                </Button>
              </div>
            )}
          </div>

          {/* Danh sách chi nhánh */}
          <div className="flex-1 overflow-y-auto p-4">
            {loading ? (
              <p className="text-gray-600 dark:text-gray-300">
                Đang tải dữ liệu...
              </p>
            ) : (
              <Accordion type="multiple" className="w-full">
                {filteredBranches.map((branch) => (
                  <AccordionItem
                    key={branch.id}
                    value={`branch-${branch.id}`}
                    className="border border-gray-200 dark:border-gray-700 rounded-md mb-2"
                  >
                    <div className="flex justify-between items-center bg-gray-100 dark:bg-gray-800 px-4 py-2 rounded-t-md hover:bg-gray-200 dark:hover:bg-gray-700 text-black dark:text-white">
                      <AccordionTrigger className="bg-transparent border-none focus:outline-none text-xl font-semibold flex-1 text-left">
                        🏢 {branch.name}
                        <span className="ml-2 text-sm text-gray-500 dark:text-gray-400">
                          ({branch.code})
                        </span>
                      </AccordionTrigger>
                      <div className="flex gap-2">
                        <Tooltip>
                          <TooltipTrigger asChild>
                            <Button
                              size="icon"
                              variant="ghost"
                              className="bg-transparent focus:outline-none border-none text-green-600 hover:text-green-700"
                              onClick={() =>
                                handleOpenAddBuildingModal(branch.id)
                              }
                            >
                              <Plus className="w-4 h-4" />
                            </Button>
                          </TooltipTrigger>
                          <TooltipContent>Thêm toà nhà</TooltipContent>
                        </Tooltip>
                        {/* <Tooltip>
                          <TooltipTrigger asChild>
                            <Button
                              size="icon"
                              variant="ghost"
                              className="bg-transparent focus:outline-none border-none text-yellow-500 hover:text-yellow-600"
                            >
                              <Pencil className="w-4 h-4" />
                            </Button>
                          </TooltipTrigger>
                          <TooltipContent>Sửa chi nhánh</TooltipContent>
                        </Tooltip> */}
                      </div>
                    </div>

                    <AccordionContent className="pl-4 space-y-3 bg-gray-50 dark:bg-gray-900 text-black dark:text-white py-3">
                      <Accordion type="multiple" className="ml-4 space-y-2">
                        {branch.children?.map((building) => (
                          <AccordionItem
                            key={building.id}
                            value={`building-${building.id}`}
                            className="border border-gray-200 dark:border-gray-700 rounded-md"
                          >
                            <div className="flex justify-between items-center bg-gray-100 dark:bg-gray-800 px-3 py-1 rounded-t-md hover:bg-gray-200 dark:hover:bg-gray-700 text-black dark:text-white">
                              <AccordionTrigger className="bg-transparent border-none focus:outline-none font-medium flex-1 text-left">
                                🏢 {building.name}
                                <span className="ml-2 text-sm text-gray-500 dark:text-gray-400">
                                  ({building.code})
                                </span>
                              </AccordionTrigger>
                              <div className="flex gap-2">
                                <Tooltip>
                                  <TooltipTrigger asChild>
                                    <Button
                                      size="icon"
                                      variant="ghost"
                                      className="bg-transparent focus:outline-none border-none text-green-600 hover:text-green-700"
                                      onClick={() =>
                                        handleOpenAddFloorModal(building.id)
                                      }
                                    >
                                      <Plus className="w-4 h-4" />
                                    </Button>
                                  </TooltipTrigger>
                                  <TooltipContent>Thêm lầu</TooltipContent>
                                </Tooltip>
                                {/* <Tooltip>
                                  <TooltipTrigger asChild>
                                    <Button
                                      size="icon"
                                      variant="ghost"
                                      className="bg-transparent focus:outline-none border-none text-yellow-500 hover:text-yellow-600"
                                    >
                                      <Pencil className="w-4 h-4" />
                                    </Button>
                                  </TooltipTrigger>
                                  <TooltipContent>Sửa toà nhà</TooltipContent>
                                </Tooltip> */}
                              </div>
                            </div>

                            <AccordionContent className="pl-4 space-y-1 bg-gray-50 dark:bg-gray-900 py-2">
                              <ul className="list-disc list-inside text-black dark:text-white">
                                {building.children?.map((floor) => (
                                  <li
                                    key={floor.id}
                                    className="flex items-center justify-between pr-4"
                                  >
                                    <span>🪜 {floor.name}</span>
                                    {/* <Tooltip>
                                      <TooltipTrigger asChild>
                                        <Button
                                          size="icon"
                                          variant="ghost"
                                          className="bg-transparent focus:outline-none border-none text-yellow-500 hover:text-yellow-600"
                                        >
                                          <Pencil className="w-4 h-4" />
                                        </Button>
                                      </TooltipTrigger>
                                      <TooltipContent>Sửa lầu</TooltipContent>
                                    </Tooltip> */}
                                  </li>
                                ))}
                              </ul>
                            </AccordionContent>
                          </AccordionItem>
                        ))}
                      </Accordion>
                    </AccordionContent>
                  </AccordionItem>
                ))}
              </Accordion>
            )}
          </div>
        </div>
      </TooltipProvider>
      {isBuildingModalOpen && (
        <AddBuildingModal
          open={isBuildingModalOpen}
          onClose={() => setIsBuildingModalOpen(false)}
          branchId={selectedBranchId}
          onSubmit={handleSubmitBuilding}
        />
      )}
      {isFloorModalOpen && selectedBuildingId && (
        <AddFloorModal
          open={isFloorModalOpen}
          buildingId={selectedBuildingId}
          onClose={() => setIsFloorModalOpen(false)}
          onSubmit={handleSubmitFloor}
        />
      )}
    </>
  );
};

export default BranchList;
