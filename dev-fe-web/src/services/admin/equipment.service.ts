import { UploadResponse } from "@/services/admin/user.service";
import { AuthService } from "@/services/auth.service";
import axios from "axios";
import Cookies from "js-cookie";
import { toast } from "sonner";

const API_BASE_URL = import.meta.env.VITE_BACKEND_HOST;

export class EquipmentService {
  // Lấy danh sách thiết bị
  async getListEquipments(page: number, size: number, keyword: string) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/equipments/filters`, {
        params: { page, size, keyword },
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      console.log(response.data.result);

      return response.data.result;
    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        console.log("gọi hàm refreshtoken");
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }

      console.error("Error fetching users:", error);
      toast.error("Lỗi khi lấy dữ liệu!");
      throw error;
    }
  }

  // Lấy chi tiết thiết bị
  async getDetailEquipment(id: string) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("Không có token");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/equipments/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      return response.data.result;
    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        console.log("gọi hàm refreshtoken");
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }
      console.error("Error fetching users:", error);
      toast.error("Lỗi khi lấy dữ liệu!");
      throw error;
    }
  }

  // Thêm thiết bị
  async createEquipment(equipmentData: any) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("Không có token");
    }
    try {
      const response = await axios.post(
        `${API_BASE_URL}/equipments`,
        equipmentData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
            "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
          },
        }
      );
      return response.data.result;
    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        console.log("gọi hàm refreshtoken");
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }

      console.error("Error fetching users:", error);
      toast.error("Lỗi khi lấy dữ liệu!");
      throw error;
    }
  }

  // Cập nhat thiết bị
  async updateEquipment(
    id: string,
    file: File | undefined,
    equipmentData: any,
    price: number | undefined
  ) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("Không có token");
    }

    let imageUrls: string | undefined = undefined;

    // Nếu có file thì upload ảnh trước
    if (file) {
      const formData = new FormData();
      formData.append("file", file);

      const uploadRes = await axios.post<UploadResponse>(
        `${API_BASE_URL}/s3/users/avatar`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
            "X-tenantId": import.meta.env.VITE_KEYCLOAK_REALM,
          },
        }
      );

      if (uploadRes.data.code !== 1000) {
        throw new Error(`Upload avatar failed, code: ${uploadRes.data.code}`);
      }

      imageUrls = uploadRes.data.result;
    }

    // Merge thêm ảnh vào data nếu có
    const data = imageUrls
      ? { ...equipmentData, imageUrls: [`${imageUrls}`] }
      : equipmentData;

    try {
      // cập nhật thông tin thiết bị
      await axios.patch(`${API_BASE_URL}/equipments/${id}`, data, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });

      // cập nhật giá nếu có
      if (price !== undefined) {
        await axios.post(
          `${API_BASE_URL}/equipments/${id}/prices`,
          { unitPrice: price },
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
              "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
            },
          }
        );
      }

      toast.success("Cập nhật thiết bị thành công!");
    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }

      console.error("Lỗi khi cập nhật:", error);
      toast.error("Lỗi khi cập nhật thiết bị!");
      throw error;
    }
  }
}
