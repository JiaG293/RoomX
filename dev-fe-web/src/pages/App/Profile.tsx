import { useEffect, useState } from "react";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import {
  Dialog,
  DialogContent,
  DialogTitle,
  DialogTrigger,
  DialogClose,
} from "@/components/ui/dialog";
import { Camera, Pencil, Save, X } from "lucide-react";
import { AuthService } from "@/services/auth.service";
import { getShortName } from "@/utils/string.util";

interface User {
  firstName: string;
  lastName: string;
  phoneNumber: string;
  email: string;
  gender: boolean;
  avatarImage: string;
  userCode: string;
}

export function Profile({ trigger }: { trigger?: React.ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [editedUser, setEditedUser] = useState<User | null>(null);
  const [isEditing, setIsEditing] = useState(false);

  const fullName = user ? `${user.firstName} ${user.lastName}` : "";

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const authService = new AuthService();
        const userData = await authService.getProfile();
        setUser(userData);
        setEditedUser(userData);
      } catch (error) {
        console.error("Không thể lấy thông tin người dùng:", error);
      }
    };
    fetchUser();
  }, []);

  const handleChange = (field: keyof User, value: string | boolean) => {
    if (editedUser) {
      setEditedUser({ ...editedUser, [field]: value });
    }
  };

  const handleSave = () => {
    setUser(editedUser);
    setIsEditing(false);
    // TODO: Gọi API lưu nếu có
  };

  const handleCancel = () => {
    setEditedUser(user);
    setIsEditing(false);
  };

  const defaultTrigger = (
    <button className="px-4 py-2 rounded bg-indigo-600 text-white hover:bg-indigo-700">
      Hồ sơ cá nhân
    </button>
  );

  if (!user || !editedUser) return null;

  return (
    <Dialog>
      <DialogTrigger asChild>{trigger ?? defaultTrigger}</DialogTrigger>

      <DialogContent className="max-w-md p-0 overflow-hidden rounded-lg shadow-lg">
        <div className="flex items-center justify-between p-4 border-b border-gray-200 dark:border-gray-700">
          <DialogTitle className="text-lg font-semibold text-gray-900 dark:text-gray-100">
            Thông tin tài khoản aaa
          </DialogTitle>
        </div>

        <div
          className="h-28 w-full bg-center bg-cover"
          style={{
            backgroundImage:
              "url(https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=60)",
          }}
        ></div>

        <div className="relative -mt-12 px-6 flex items-center gap-4">
          <div className="relative">
            <Avatar className="w-20 h-20 ring-4 ring-white dark:ring-gray-900 rounded-full bg-gray-300">
              {user.avatarImage ? (
                <AvatarImage src={user.avatarImage} alt={fullName} />
              ) : (
                <AvatarFallback>{getShortName(user.firstName)}</AvatarFallback>
              )}
            </Avatar>
            <button
              type="button"
              aria-label="Chỉnh sửa ảnh đại diện"
              className="absolute bottom-0 right-0 bg-white rounded-full p-1 shadow-md hover:bg-gray-100 transition"
            >
              <Camera className="w-5 h-5 text-gray-700" />
            </button>
          </div>

          <div>
            <h2 className="text-xl font-semibold text-gray-900 dark:text-gray-100">
              {fullName}
            </h2>
          </div>
        </div>

        <div className="px-6 pt-4 pb-6 text-gray-800 dark:text-gray-300">
          <dl className="space-y-3">
            {[
              { label: "Họ", field: "firstName", type: "text" },
              { label: "Tên", field: "lastName", type: "text" },
              { label: "Mã nhân viên", field: "userCode", type: "text" },
              { label: "Email", field: "email", type: "text" },
              { label: "Điện thoại", field: "phoneNumber", type: "text" },
            ].map(({ label, field, type }) => (
              <div
                key={field}
                className="flex justify-between items-center gap-4"
              >
                <dt className="font-medium">{label}</dt>
                <dd className="text-right flex-1">
                  {isEditing ? (
                    <input
                      type={type}
                      className="w-full bg-transparent border-b border-gray-400 focus:outline-none px-2 py-1 text-right"
                      value={(editedUser as any)[field]}
                      onChange={(e) =>
                        handleChange(field as keyof User, e.target.value)
                      }
                    />
                  ) : (
                    (user as any)[field]
                  )}
                </dd>
              </div>
            ))}

            <div className="flex justify-between items-center gap-4">
              <dt className="font-medium">Giới tính</dt>
              <dd className="text-right flex-1">
                {isEditing ? (
                  <select
                    className="bg-transparent w-full border px-2 py-1 rounded"
                    value={editedUser.gender ? "male" : "female"}
                    onChange={(e) =>
                      handleChange("gender", e.target.value === "male")
                    }
                  >
                    <option value="male">Nam</option>
                    <option value="female">Nữ</option>
                  </select>
                ) : editedUser.gender ? (
                  "Nam"
                ) : (
                  "Nữ"
                )}
              </dd>
            </div>
          </dl>
        </div>

        <div className="px-6 pb-6 text-center">
          {isEditing ? (
            <div className="flex justify-center gap-4">
              <button
                type="button"
                onClick={handleCancel}
                className="inline-flex items-center gap-2 px-4 py-2 bg-gray-300 text-black rounded hover:bg-gray-400"
              >
                <X className="w-4 h-4" />
                Hủy
              </button>
              <DialogClose asChild>
                <button
                  type="button"
                  onClick={handleSave}
                  className="inline-flex items-center gap-2 px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
                >
                  <Save className="w-4 h-4" />
                  Lưu
                </button>
              </DialogClose>
            </div>
          ) : (
            <button
              type="button"
              onClick={() => setIsEditing(true)}
              className="bg-transparent inline-flex items-center gap-2 text-indigo-600 font-semibold hover:text-indigo-800 focus:outline-none"
            >
              <Pencil className="w-5 h-5" />
              Cập nhật
            </button>
          )}
        </div>
      </DialogContent>
    </Dialog>
  );
}
