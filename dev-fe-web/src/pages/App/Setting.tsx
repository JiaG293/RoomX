// components/Settings.tsx
import React, { useEffect, useState } from "react";
import {
  Camera,
  Edit2,
  Phone,
  Save,
  User,
  UserCheck,
  XCircle,
} from "lucide-react";
import { toast } from "sonner";
import { UserService } from "@/services/admin/user.service";
import { AuthService } from "@/services/auth.service";
import { Portal } from "@radix-ui/react-dialog";
import PortalLayout from "@/layouts/portal-layout";
import { ThemeToggle } from "@/components/admin/custom/theme-toggle";
import { LanguageSelect } from "@/components/admin/custom/select-language";

export default function Settings() {
  const [isEditing, setIsEditing] = useState(false);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phone, setPhone] = useState("");
  const [gender, setGender] = useState("");
  const [avatarImage, setAvatarImage] = useState("");
  const [avatarFile, setAvatarFile] = useState<File | null>(null);

  const [errors, setErrors] = useState({
    firstName: "",
    lastName: "",
    phone: "",
  });

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const authService = new AuthService();
        const data = await authService.getProfile();

        // Cập nhật dữ liệu form
        setFirstName(data.firstName || "");
        setLastName(data.lastName || "");
        setPhone(data.phoneNumber || "");
        setGender(data.gender || "");
        setAvatarImage(data.avatarImage || "");
      } catch (error) {
        toast.error("Không thể tải dữ liệu người dùng");
      }
    };
    fetchProfile();
  }, []);

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

  return (
    <PortalLayout>
  <div className="bg-white dark:bg-gray-900 p-6 shadow-md rounded-xl border dark:border-gray-700 max-w-2xl mx-auto mt-10 relative">
        {/* Đặt cái này ở góc phải trên cùng container */}
        <div className="flex justify-end gap-4 absolute top-4 right-4">
          <ThemeToggle variant="icon" />
          <LanguageSelect />
        </div>

        <div>
          <h2 className="text-2xl font-semibold text-gray-800 dark:text-white mb-4">
            Thông tin cơ bản
          </h2>
        </div>

        <div className="relative">
          <div className="relative h-28 w-full rounded-t-lg overflow-hidden">
            <img
              src="https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80"
              alt="Cover"
              className="w-full h-full object-cover"
            />
          </div>

          {/* Avatar (moved out of cover image container) */}
          <div className="absolute left-1/2 top-20 transform -translate-x-1/2 border-4 border-white dark:border-gray-900 rounded-full w-24 h-24 bg-gray-200 dark:bg-gray-700 flex items-center justify-center shadow-lg">
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
                className="absolute -bottom-2 -right-2 bg-slate-600 hover:bg-slate-700 text-white rounded-full p-2 shadow-lg border-2 border-white cursor-pointer"
                title="Chọn ảnh mới"
              >
                <Camera size={16} />
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

        <div className="h-16" />
        <div className="text-center mb-6">
          <h2 className="text-xl font-semibold text-gray-800 dark:text-gray-200">
            {isEditing ? (
              <span className="flex justify-center items-center gap-2">
                <Edit2 size={20} className="text-blue-500" />
                Chỉnh sửa hồ sơ
              </span>
            ) : (
              ""
            )}
          </h2>
        </div>

        <form className="space-y-4" onSubmit={(e) => e.preventDefault()}>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label
                htmlFor="firstName"
                className="text-sm font-medium text-gray-700 dark:text-gray-300"
              >
                Họ
              </label>
              <input
                id="firstName"
                type="text"
                value={firstName}
                disabled={!isEditing}
                onChange={(e) => setFirstName(e.target.value)}
                className={`w-full px-3 py-2 border rounded-md ${
                  !isEditing
                    ? "opacity-60 cursor-not-allowed"
                    : "focus:ring-2 focus:ring-blue-400"
                }`}
                placeholder="Nhập họ"
              />
              {errors.firstName && (
                <p className="text-red-500 text-sm">{errors.firstName}</p>
              )}
            </div>

            <div>
              <label
                htmlFor="lastName"
                className="text-sm font-medium text-gray-700 dark:text-gray-300"
              >
                Tên
              </label>
              <input
                id="lastName"
                type="text"
                value={lastName}
                disabled={!isEditing}
                onChange={(e) => setLastName(e.target.value)}
                className={`w-full px-3 py-2 border rounded-md ${
                  !isEditing
                    ? "opacity-60 cursor-not-allowed"
                    : "focus:ring-2 focus:ring-blue-400"
                }`}
                placeholder="Nhập tên"
              />
              {errors.lastName && (
                <p className="text-red-500 text-sm">{errors.lastName}</p>
              )}
            </div>
          </div>

          <div>
            <label
              htmlFor="phone"
              className="text-sm font-medium text-gray-700 dark:text-gray-300"
            >
              Số điện thoại
            </label>
            <input
              id="phone"
              type="tel"
              value={phone}
              disabled={!isEditing}
              onChange={(e) => setPhone(e.target.value)}
              className={`w-full px-3 py-2 border rounded-md ${
                !isEditing
                  ? "opacity-60 cursor-not-allowed"
                  : "focus:ring-2 focus:ring-blue-400"
              }`}
              placeholder="Nhập số điện thoại"
            />
            {errors.phone && (
              <p className="text-red-500 text-sm">{errors.phone}</p>
            )}
          </div>
          {/* <div>
            <label
              htmlFor="gender"
              className="text-sm font-medium text-gray-700 dark:text-gray-300"
            >
              Giới tính
            </label>
            <select
              id="gender"
              value={gender}
              disabled={!isEditing}
              onChange={(e) => setGender(e.target.value)}
              className={`w-full px-3 py-2 border rounded-md bg-transparent text-gray-800 dark:text-gray-200 ${
                !isEditing
                  ? "opacity-60 cursor-not-allowed"
                  : "focus:ring-2 focus:ring-blue-400"
              }`}
            >
              <option value="male">Nam</option>
              <option value="female">Nữ</option>
            </select>
          </div> */}

          <div className="flex justify-end mt-6">
            <button
              type="button"
              onClick={handleToggleEdit}
              className="flex items-center gap-2 px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md transition"
            >
              {isEditing ? <Save size={16} /> : <Edit2 size={16} />}
              {isEditing ? "Lưu thay đổi" : "Chỉnh sửa"}
            </button>
          </div>
        </form>
      </div>
    </PortalLayout>
  );
}
