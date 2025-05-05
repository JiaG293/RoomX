import { useState } from "react";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogTitle,
  DialogDescription,
  DialogClose,
} from "@/components/ui/dialog";
import { BranchService } from "@/services/admin/branch.service"; // Import BranchService
import { toast } from "sonner";
import { BranchValidator } from "@/validators/branch.validators";

export interface Branch {
  branchCode: string;
  name: string;
  phoneNumber: string;
  email: string;
  address: string;
}

interface BranchAddModalProps {
  onAddSuccess: () => void;
}

const BranchAddModal: React.FC<BranchAddModalProps> = ({ onAddSuccess }) => {
  const [branchCode, setBranchCode] = useState("");
  const [branchName, setBranchName] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [address, setAddress] = useState("");
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const branchService = new BranchService();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Kiểm tra validation
    if (!BranchValidator.isNotEmpty(branchCode)) {
      toast.error("Mã chi nhánh không được để trống!");
      return;
    }
    if (!BranchValidator.isValidBranchName(branchName)) {
      toast.error("Tên chi nhánh không hợp lệ!");
      return;
    }
    if (!BranchValidator.isValidEmail(email)) {
      toast.error("Email không hợp lệ!");
      return;
    }
    if (!BranchValidator.isValidPhoneNumber(phoneNumber)) {
      toast.error("Số điện thoại không hợp lệ!");
      return;
    }
    if (!BranchValidator.isNotEmpty(address)) {
      toast.error("Địa chỉ không được để trống!");
      return;
    }

    // Tạo đối tượng branch
    const newBranch: Branch = {
      branchCode: branchCode,
      name: branchName,
      phoneNumber: phoneNumber,
      email: email,
      address: address,
    };

    try {
      await branchService.createBranch(newBranch);
      toast.success("Tạo chi nhánh thành công!", {
        description: <strong>Chi nhánh {branchName} đã được thêm.</strong>,
      });
      setIsDialogOpen(false);
      onAddSuccess();
    } catch (error: any) {
      console.error("Lỗi khi tạo chi nhánh:", error);
      toast.error("Lỗi khi tạo chi nhánh!", {
        description:
          error.response?.data?.message || error.message || "Đã có lỗi xảy ra.",
      });
    }
  };

  return (
    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
      <DialogTrigger asChild>
        <button className="py-2 px-4 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors">
          Thêm chi nhánh
        </button>
      </DialogTrigger>

      <DialogContent className="p-6 bg-background rounded-lg shadow-lg max-w-lg mx-auto">
        <DialogTitle className="text-xl font-semibold mb-4">
          Tạo chi nhánh mới
        </DialogTitle>
        <DialogDescription className="text-sm mb-6">
          Điền đầy đủ các thông tin bên dưới
        </DialogDescription>

        <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">
          <div className="col-span-1">
            <label className="block text-sm font-medium text-card-foreground mb-1">
              Mã chi nhánh
            </label>
            <input
              type="text"
              value={branchCode}
              onChange={(e) => setBranchCode(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
              placeholder="Nhập mã chi nhánh"
            />
          </div>

          <div className="col-span-1">
            <label className="block text-sm font-medium text-card-foreground mb-1">
              Tên chi nhánh
            </label>
            <input
              type="text"
              value={branchName}
              onChange={(e) => setBranchName(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
              placeholder="Nhập tên chi nhánh"
            />
          </div>

          <div className="col-span-1">
            <label className="block text-sm font-medium text-card-foreground mb-1">
              Email
            </label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
              placeholder="Nhập email"
            />
          </div>

          <div className="col-span-1">
            <label className="block text-sm font-medium text-card-foreground mb-1">
              Số điện thoại
            </label>
            <input
              type="text"
              value={phoneNumber}
              onChange={(e) => setPhoneNumber(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
              placeholder="Nhập số điện thoại"
            />
          </div>

          <div className="col-span-2">
            <label className="block text-sm font-medium text-card-foreground mb-1">
              Địa chỉ
            </label>
            <input
              type="text"
              value={address}
              onChange={(e) => setAddress(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
              placeholder="Nhập địa chỉ"
            />
          </div>

          <div className="col-span-2 flex justify-between items-center mt-4">
            <DialogClose asChild>
              <button
                type="button"
                className="py-2 px-4 bg-gray-300 text-gray-800 rounded-md hover:bg-gray-400 transition-colors"
              >
                Huỷ
              </button>
            </DialogClose>

            <button
              type="submit"
              className="py-2 px-4 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
            >
              Thêm chi nhánh
            </button>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
};

export default BranchAddModal;
