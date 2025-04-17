import { useState } from "react";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogTitle,
  DialogDescription,
  DialogClose,
} from "@/components/ui/dialog";
import { UserService } from "@/services/admin/user.service";
import { UserValidator } from "@/validators/user.validators";
import { toast } from "sonner";
import { Hash, Mail, Phone, User, UserPlus, Users } from "lucide-react";

export interface User {
  userCode: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  password: string;
  gender: string;
  email: string;
  type: "EMPLOYEE";
  roles: ("USER" | "APPROVER" | "ADMIN")[];
}


interface UserAddModalProps {
  onAddSuccess: () => void;
}

const UserAddModal: React.FC<UserAddModalProps> = ({ onAddSuccess }) => {
  const [employeeId, setEmployeeId] = useState("");
  const [email, setEmail] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [userRole, setUserRole] = useState("USER");

  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const userService = new UserService();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Kiểm tra validation
    if (!UserValidator.isNotEmpty(employeeId)) {
      toast.error("Mã nhân viên không được để trống!");
      return;
    }
    if (!UserValidator.isValidUserCode(employeeId)) {
      toast.error("Mã nhân viên chỉ được chứa chữ, số và lớn hơn 6 ký tự!");
      return;
    }
    if (!UserValidator.isValidEmail(email)) {
      toast.error("Email không hợp lệ!");
      return;
    }
    if (!UserValidator.isValidName(firstName)) {
      toast.error("Họ không hợp lệ!");
      return;
    }
    if (!UserValidator.isValidName(lastName)) {
      toast.error("Tên không hợp lệ!");
      return;
    }
    if (!UserValidator.isValidPhoneNumber(phoneNumber)) {
      toast.error("Số điện thoại không hợp lệ!");
      return;
    }

    // Tạo đối tượng user
    const newUser: User = {
      userCode: employeeId,
      firstName: firstName,
      lastName: lastName,
      phoneNumber: phoneNumber,
      password: "password123",
      gender: "true",
      email: email,
      type: "EMPLOYEE",
      roles: userRole === "USER" ? ["USER"] : ["USER", "APPROVER"],
    };

    try {
      await userService.createUser(newUser);
      toast.success("Tạo người dùng thành công!", {
        description: (
          <strong>
            Người dùng {firstName} {lastName} đã được thêm.
          </strong>
        ),
      });
      setIsDialogOpen(false);
      onAddSuccess();
    } catch (error: any) {
      console.error("Error creating user:", error);
      toast.error("Lỗi tạo người dùng!", {
        description:
          error.response?.data?.message || error.message || "Đã có lỗi xảy ra.",
      });
    }
  };

  const resetForm = () => {
    setEmployeeId("");
    setEmail("");
    setFirstName("");
    setLastName("");
    setPhoneNumber("");
    setUserRole("USER");
  };

  return (
    <Dialog
      open={isDialogOpen}
      onOpenChange={(open) => {
        setIsDialogOpen(open);
        if (!open) resetForm();
      }}
    >
      <DialogTrigger asChild>
        <button className="py-2 px-4 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors flex items-center gap-2">
          <UserPlus size={18} /> Thêm người dùng
        </button>
      </DialogTrigger>

      <DialogContent className="p-6 bg-white rounded-lg shadow-lg max-w-lg mx-auto">
        <DialogTitle className="text-xl font-semibold mb-4">
          Tạo người dùng mới
        </DialogTitle>
        <DialogDescription className="text-sm mb-6">
          Điền đầy đủ các thông tin bên dưới
        </DialogDescription>

        <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">
          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Mã nhân viên
            </label>
            <div className="relative">
              <input
                type="text"
                value={employeeId}
                onChange={(e) => setEmployeeId(e.target.value)}
                className="w-full p-2 bg-transparent border border-gray-300 rounded-md pl-10"
                placeholder="Nhập mã nhân viên"
              />
              <Hash
                className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"
                size={18}
              />
            </div>
          </div>

          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Email
            </label>
            <div className="relative">
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full p-2 bg-transparent border border-gray-300 rounded-md pl-10"
                placeholder="Nhập email"
              />
              <Mail
                className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"
                size={18}
              />
            </div>
          </div>

          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Họ
            </label>
            <div className="relative">
              <input
                type="text"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                className="w-full p-2 bg-transparent border border-gray-300 rounded-md pl-10"
                placeholder="Nhập họ"
              />
              <User
                className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"
                size={18}
              />
            </div>
          </div>

          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Tên
            </label>
            <div className="relative">
              <input
                type="text"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                className="w-full p-2 bg-transparent border border-gray-300 rounded-md pl-10"
                placeholder="Nhập tên"
              />
              <User
                className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"
                size={18}
              />
            </div>
          </div>

          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Số điện thoại
            </label>
            <div className="relative">
              <input
                type="text"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
                className="w-full p-2 bg-transparent border border-gray-300 rounded-md pl-10"
                placeholder="Nhập số điện thoại"
              />
              <Phone
                className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"
                size={18}
              />
            </div>
          </div>

          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Loại người dùng
            </label>
            <div className="relative">
              <select
                value={userRole}
                onChange={(e) => setUserRole(e.target.value)}
                className="w-full p-2 bg-transparent border border-gray-300 rounded-md pl-10"
              >
                <option value="USER">Nhân Viên</option>
                <option value="APPROVER">Kiểm duyệt viên</option>
              </select>
              <Users
                className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"
                size={18}
              />
            </div>
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
              className="py-2 px-4 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors flex items-center gap-2"
            >
              <UserPlus size={18} /> Thêm người dùng
            </button>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
};

export default UserAddModal;
