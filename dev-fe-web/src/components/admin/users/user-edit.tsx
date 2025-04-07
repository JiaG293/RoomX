import { useState, useEffect } from "react";
import { Dialog, DialogContent, DialogTitle, DialogDescription, DialogClose } from "@/components/ui/dialog";
import { toast } from "sonner";

export interface User {
  userCode: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  password: string;
  gender: string;
  email: string;
  type: "EMPLOYEE" | "APPROVER";
  roles: string[];
}

interface UserEditModalProps {
  user: User | null;
  onEditSuccess: () => void;
}

const UserEditModal: React.FC<UserEditModalProps> = ({ user, onEditSuccess }) => {
  const [employeeId, setEmployeeId] = useState("");
  const [email, setEmail] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [userType, setUserType] = useState("employee");
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  // const userService = new UserService();

  // Cập nhật state khi user thay đổi
  useEffect(() => {
    if (user) {
      setEmployeeId(user.userCode);
      setEmail(user.email);
      setFirstName(user.firstName);
      setLastName(user.lastName);
      setPhoneNumber(user.phoneNumber);
      setUserType(user.type === "EMPLOYEE" ? "employee" : "approver");
      setIsDialogOpen(true);
    }
  }, [user]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!employeeId || !email || !firstName || !lastName || !phoneNumber || !userType) {
      toast.error("Vui lòng điền đầy đủ thông tin.");
      return;
    }

    const updatedUser: User = {
      userCode: employeeId,
      firstName: firstName,
      lastName: lastName,
      phoneNumber: phoneNumber,
      password: user?.password || "defaultpassword123",
      gender: user?.gender || "true",
      email: email,
      type: userType === "employee" ? "EMPLOYEE" : "APPROVER",
      roles: userType === "employee" ? ["USER"] : ["USER", "APPROVER"]
    };

    console.log("Updated User:", updatedUser);

    try {
    //   await userService.updateUser(updatedUser);
      toast.success("Cập nhật thành công!", {
        description: <strong>Thông tin của {firstName} {lastName} đã được cập nhật.</strong>,
      });
      setIsDialogOpen(false);
      onEditSuccess();
    } catch (error) {
      console.error("Lỗi cập nhật người dùng:", error);
      toast.error("Cập nhật thất bại.");
    }
  };

  return (
    <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
      <DialogContent className="p-6 bg-white rounded-lg shadow-lg max-w-lg mx-auto">
        <DialogTitle className="text-xl font-semibold mb-4">Chỉnh sửa người dùng</DialogTitle>
        <DialogDescription className="text-sm mb-6">Cập nhật thông tin người dùng</DialogDescription>

        <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">
          <div className="col-span-1">
            <label className="block text-sm font-medium text-gray-700 mb-1">Mã nhân viên</label>
            <input
              type="text"
              value={employeeId}
              onChange={(e) => setEmployeeId(e.target.value)}
              className="w-full p-2 bg-gray-100 border border-gray-300 rounded-md"
              readOnly
            />
          </div>

          <div className="col-span-1">
            <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
            />
          </div>

          <div className="col-span-1">
            <label className="block text-sm font-medium text-gray-700 mb-1">Họ</label>
            <input
              type="text"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
            />
          </div>

          <div className="col-span-1">
            <label className="block text-sm font-medium text-gray-700 mb-1">Tên</label>
            <input
              type="text"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
            />
          </div>

          <div className="col-span-1">
            <label className="block text-sm font-medium text-gray-700 mb-1">Số điện thoại</label>
            <input
              type="text"
              value={phoneNumber}
              onChange={(e) => setPhoneNumber(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
            />
          </div>

          <div className="col-span-1">
            <label className="block text-sm font-medium text-gray-700 mb-1">Loại người dùng</label>
            <select
              value={userType}
              onChange={(e) => setUserType(e.target.value)}
              className="w-full p-2 bg-transparent border border-gray-300 rounded-md"
            >
              <option value="employee">Nhân Viên</option>
              <option value="approver">Kiểm duyệt viên</option>
            </select>
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
              Lưu thay đổi
            </button>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
};

export default UserEditModal;
