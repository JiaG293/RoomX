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
import {
  Eye,
  EyeOff,
  Hash,
  Mail,
  Phone,
  User,
  UserPlus,
  Users,
  Lock,
} from "lucide-react";
// import { useTranslation } from "react-i18next";

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
  const [password, setPassword] = useState("password123");
  const [showPassword, setShowPassword] = useState(false);

  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const userService = new UserService();
  // const { t } = useTranslation();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!UserValidator.isNotEmpty(employeeId)) {
      toast.error("Mã nhân viên không được để trống!");
      return;
    }
    if (!UserValidator.isValidUserCode(employeeId)) {
      toast.error("Mã nhân viên chỉ được chứa chữ, số và lớn hơn 6 ký tự!");
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
    if (!UserValidator.isValidPassword(password)) {
      toast.error("Mật khẩu phải có ít nhất 6 ký tự!");
      return;
    }

    const newUser: User = {
      userCode: employeeId,
      firstName,
      lastName,
      phoneNumber,
      password,
      gender: "true",
      email,
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
    setPassword("");
    setShowPassword(false);
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
        <button className="py-2 px-4 bg-green-500 text-white rounded-xl hover:bg-green-600 transition duration-200 shadow flex items-center gap-2">
          <UserPlus size={18} /> Thêm người dùng
        </button>
      </DialogTrigger>

      <DialogContent className="p-6 bg-background rounded-lg shadow-lg max-w-lg mx-auto">
        <DialogTitle className="text-xl font-semibold mb-4">
          Tạo người dùng mới
        </DialogTitle>
        <DialogDescription className="text-sm mb-6">
          Điền đầy đủ các thông tin bên dưới
        </DialogDescription>

        <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">
          {/* Mã nhân viên */}
          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-card-foreground mb-1">
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

          {/* Email */}
          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-card-foreground mb-1">
              Email
            </label>
            <div className="relative">
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full p-2 bg-transparent border border-gray-300 rounded-md pl-10"
                placeholder="Nhập email"
                autoComplete="off"
              />
              <Mail
                className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"
                size={18}
              />
            </div>
          </div>

          {/* Họ */}
          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-card-foreground mb-1">
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

          {/* Tên */}
          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-card-foreground mb-1">
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

          {/* Số điện thoại */}
          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-card-foreground mb-1">
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

          {/* Loại người dùng */}
          <div className="col-span-1 relative">
            <label className="block text-sm font-medium text-card-foreground mb-1">
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

          {/* Mật khẩu */}
          <div className="col-span-2 relative">
            <label className="block text-sm font-medium text-card-foreground mb-1">
              Mật khẩu
            </label>
            <div className="relative">
              <input
                type={showPassword ? "text" : "password"}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full p-2 bg-transparent border border-gray-300 rounded-md pl-10 pr-10"
                placeholder="Nhập mật khẩu"
                autoComplete="new-password"
              />
              <Lock
                className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"
                size={18}
              />
              <button
                type="button"
                onClick={() => setShowPassword((prev) => !prev)}
                className="bg-transparent border-none absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500 outline-none focus:outline-none ring-0 focus:ring-0"
              >
                {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
              </button>
            </div>
          </div>

          {/* Buttons */}
          <div className="col-span-2 flex justify-end gap-4 mt-6">
            <DialogClose asChild>
              <button
                type="button"
                className="px-4 py-2 bg-gray-200 text-gray-700 rounded-xl hover:bg-gray-300 transition"
              >
                Hủy
              </button>
            </DialogClose>
            <button
              type="submit"
              className="px-4 py-2 bg-green-500 text-white rounded-xl hover:bg-green-600 transition"
            >
              Tạo mới
            </button>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
};

export default UserAddModal;
