import React, { useState } from "react";
import { Avatar, AvatarImage, AvatarFallback } from "@radix-ui/react-avatar";
import { UserService } from "@/services/admin/user.service";
import { toast } from "sonner";
import {
  Camera,
  Edit2,
  Phone,
  Save,
  User,
  UserCheck,
  VenetianMask,
  XCircle,
} from "lucide-react";
import { FaGenderless, FaMars, FaMask, FaVenus } from "react-icons/fa";

interface ProfileEditModalProps {
  isOpen: boolean;
  onClose: () => void;
  user: {
    firstName?: string;
    lastName?: string;
    phoneNumber?: string;
    gender?: string;
    avatarImage?: string;
  };
}

export default function ProfileEditModal({
  isOpen,
  onClose,
  user,
}: ProfileEditModalProps) {
  const [isEditing, setIsEditing] = useState(false);

  const [firstName, setFirstName] = useState(user.firstName || "");
  const [lastName, setLastName] = useState(user.lastName || "");
  const [phone, setPhone] = useState(user.phoneNumber || "");
  const [gender, setGender] = useState(user.gender || "");
  const [avatarImage, setAvatarImage] = useState(user.avatarImage || "");
  const [avatarFile, setAvatarFile] = useState<File | null>(null);

  const [errors, setErrors] = useState({
    firstName: "",
    lastName: "",
    phone: "",
  });

  if (!isOpen) return null;

  const validateInputs = () => {
    const phoneRegex = /^0\d{9}$/;

    const newErrors = {
      firstName: firstName.trim() ? "" : "Vui lòng nhập họ",
      lastName: lastName.trim() ? "" : "Vui lòng nhập tên",
      phone: phoneRegex.test(phone.trim())
        ? ""
        : "Số điện thoại phải bắt đầu bằng 0 và gồm 10 chữ số",
    };

    setErrors(newErrors);
    return !Object.values(newErrors).some((e) => e);
  };

  const handleUpdateProfile = async () => {
    if (!validateInputs()) return;

    try {
      const userService = new UserService();

      // Nếu cần upload avatarFile thì xử lý riêng (ví dụ api khác hoặc kèm trong formData)
      // Hiện tại bạn chưa có logic upload avatarFile, chỉ update profile thôi.

      await userService.updateProfileUser(
        avatarFile,
        firstName,
        lastName,
        phone,
        gender === "male" ? true : false
      );

      setIsEditing(false);
      toast.success("Cập nhật thành công!");
    } catch (error) {
      toast.error("Lỗi khi cập nhật thông tin");
    }
  };

  const handleChangeAvatarFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    const allowedTypes = ["image/jpeg", "image/jpg", "image/png", "image/gif"];
    if (!allowedTypes.includes(file.type)) {
      toast.error("Chỉ chấp nhận ảnh định dạng jpg, jpeg, png, gif");
      return;
    }

    const maxSizeMB = 2;
    if (file.size > maxSizeMB * 1024 * 1024) {
      toast.error("Kích thước ảnh không được vượt quá 2MB");
      return;
    }

    const url = URL.createObjectURL(file);
    setAvatarImage(url);
    setAvatarFile(file);
  };

  const handleToggleEdit = () => {
    if (isEditing) {
      handleUpdateProfile();
    } else {
      setIsEditing(true);
    }
  };

  const handleClose = () => {
    setIsEditing(false);
    setErrors({
      firstName: "",
      lastName: "",
      phone: "",
    });
    setAvatarImage(user.avatarImage || "");
    onClose();
  };

return (
  <div
    className="fixed inset-0 bg-black bg-opacity-70 flex justify-center items-center z-50"
    onClick={handleClose}
  >
    <div
      className="relative bg-white bg-opacity-90 backdrop-blur-sm rounded-lg shadow-lg p-8 w-full max-w-md border-2 border-gray-300 dark:bg-gray-800 dark:bg-opacity-90 dark:border-gray-700"
      onClick={(e) => e.stopPropagation()}
    >
      {/* Close button with Lucide icon */}
      <button
        type="button"
        onClick={handleClose}
        className="focus:outline-none border-none hover:scale-110 absolute top-4 right-4 text-gray-600 hover:text-gray-900 dark:text-gray-300 dark:hover:text-white -mt-6 -mr-6 bg-transparent z-50"
        aria-label="Close"
      >
        <XCircle size={24} color="currentColor" />
      </button>

      <div className="relative h-28 w-full rounded-t-lg">
        <img
          src="https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80"
          alt="Cover"
          className="object-cover w-full h-full rounded-t-lg"
        />
        <div className="absolute left-1/2 bottom-[-48px] transform -translate-x-1/2 border-4 border-white rounded-full overflow-visible w-24 h-24 bg-gray-200 dark:bg-gray-700 flex items-center justify-center">
          <div className="w-24 h-24 rounded-full relative overflow-hidden">
            <img
              src={avatarImage || "https://via.placeholder.com/96"}
              alt="Avatar"
              className="w-full h-full object-cover rounded-full"
            />
          </div>
          {isEditing && (
            <label
              htmlFor="avatar-file-input"
              className="absolute -bottom-2 -right-2 bg-slate-600 hover:bg-slate-700 text-white rounded-full p-2 shadow-lg border-2 border-white cursor-pointer flex items-center justify-center"
              title="Chọn ảnh mới"
            >
              <Camera size={16} color="white" />
              <input
                type="file"
                accept="image/*"
                id="avatar-file-input"
                className="hidden"
                onChange={handleChangeAvatarFile}
              />
            </label>
          )}
        </div>
      </div>

      <div className="h-14" />
      <h2 className="text-xl font-semibold mb-6 text-gray-800 dark:text-gray-200 text-center">
        {isEditing ? (
          <>
            <Edit2 size={20} className="inline mr-2 text-blue-600 dark:text-blue-400" />
            Chỉnh sửa hồ sơ
          </>
        ) : (
          "Chi tiết hồ sơ"
        )}
      </h2>

      <form className="space-y-4" onSubmit={(e) => e.preventDefault()}>
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label
              htmlFor="firstName"
              className="flex items-center text-gray-700 dark:text-gray-300 text-sm font-medium mb-1 space-x-1"
            >
              <User size={16} className="text-blue-500 dark:text-blue-400" />
              <span>Họ</span>
            </label>
            <input
              id="firstName"
              type="text"
              value={firstName}
              disabled={!isEditing}
              onChange={(e) => setFirstName(e.target.value)}
              className={`w-full bg-transparent border rounded-md px-3 py-2 transition
                ${
                  !isEditing
                    ? "opacity-60 cursor-not-allowed border-gray-300 dark:border-gray-600"
                    : "border-gray-300 focus:ring-2 focus:ring-blue-400 focus:border-blue-400 dark:border-gray-600 dark:focus:ring-blue-500 dark:focus:border-blue-500"
                }
                text-gray-900 dark:text-gray-100
              `}
              placeholder="Nhập họ"
            />
            {errors.firstName && (
              <p className="text-red-500 text-sm mt-1">{errors.firstName}</p>
            )}
          </div>

          <div>
            <label
              htmlFor="lastName"
              className="flex items-center text-gray-700 dark:text-gray-300 text-sm font-medium mb-1 space-x-1"
            >
              <UserCheck size={16} className="text-green-500 dark:text-green-400" />
              <span>Tên</span>
            </label>
            <input
              id="lastName"
              type="text"
              value={lastName}
              disabled={!isEditing}
              onChange={(e) => setLastName(e.target.value)}
              className={`w-full bg-transparent border rounded-md px-3 py-2 transition
                ${
                  !isEditing
                    ? "opacity-60 cursor-not-allowed border-gray-300 dark:border-gray-600"
                    : "border-gray-300 focus:ring-2 focus:ring-blue-400 focus:border-blue-400 dark:border-gray-600 dark:focus:ring-blue-500 dark:focus:border-blue-500"
                }
                text-gray-900 dark:text-gray-100
              `}
              placeholder="Nhập tên"
            />
            {errors.lastName && (
              <p className="text-red-500 text-sm mt-1">{errors.lastName}</p>
            )}
          </div>
        </div>

        <div>
          <label
            htmlFor="phone"
            className="flex items-center text-gray-700 dark:text-gray-300 text-sm font-medium mb-1 space-x-1"
          >
            <Phone size={16} className="text-orange-500 dark:text-orange-400" />
            <span>Số điện thoại</span>
          </label>
          <input
            id="phone"
            type="tel"
            value={phone}
            disabled={!isEditing}
            onChange={(e) => setPhone(e.target.value)}
            className={`w-full bg-transparent border rounded-md px-3 py-2 transition
              ${
                !isEditing
                  ? "opacity-60 cursor-not-allowed border-gray-300 dark:border-gray-600"
                  : "border-gray-300 focus:ring-2 focus:ring-blue-400 focus:border-blue-400 dark:border-gray-600 dark:focus:ring-blue-500 dark:focus:border-blue-500"
              }
              text-gray-900 dark:text-gray-100
            `}
            placeholder="Nhập số điện thoại"
          />
          {errors.phone && (
            <p className="text-red-500 text-sm mt-1">{errors.phone}</p>
          )}
        </div>

        <div>
          <label
            htmlFor="gender"
            className="flex items-center text-gray-700 dark:text-gray-300 text-sm font-medium mb-1 space-x-1"
          >
            <VenetianMask size={16} className="text-purple-600 dark:text-purple-400" />
            <span>Giới tính</span>
          </label>
          <select
            id="gender"
            value={gender}
            disabled={!isEditing}
            onChange={(e) => setGender(e.target.value)}
            className={`w-full bg-transparent border rounded-md px-3 py-2 transition
              ${
                !isEditing
                  ? "opacity-60 cursor-not-allowed border-gray-300 dark:border-gray-600"
                  : "border-gray-300 focus:ring-2 focus:ring-blue-400 focus:border-blue-400 dark:border-gray-600 dark:focus:ring-blue-500 dark:focus:border-blue-500"
              }
              text-gray-900 dark:text-gray-100
            `}
          >
            <option value="male">Nam</option>
            <option value="female">Nữ</option>
          </select>
        </div>

        <div className="flex justify-center mt-6">
          <button
            type="button"
            onClick={handleToggleEdit}
            className="flex items-center px-6 py-2 bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded-md transition space-x-2 dark:bg-blue-500 dark:hover:bg-blue-600"
          >
            {isEditing ? (
              <>
                <Save size={18} />
                <span>Lưu</span>
              </>
            ) : (
              <>
                <Edit2 size={18} />
                <span>Chỉnh sửa</span>
              </>
            )}
          </button>
        </div>
      </form>
    </div>
  </div>
);

}
