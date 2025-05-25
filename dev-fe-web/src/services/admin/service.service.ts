import { UploadResponse } from "@/services/admin/user.service";
import { AuthService } from "@/services/auth.service";
import axios from "axios";
import Cookies from "js-cookie";
import { toast } from "sonner";

const API_BASE_URL = import.meta.env.VITE_BACKEND_HOST;

export class ServiceService {
  // Lấy danh sách dịch vụ
  async getListServices(page: number, size: number, keyword: string) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/services/filters`, {
        params: { page, size, keyword },
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

  // Lấy chi tiết dịch vụ
  async getDetailService(id: string) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/services/${id}`, {
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

  // Thêm dịch vụ
  async createService(serviceData: any) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.post(
        `${API_BASE_URL}/services`,
        serviceData,
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



  // Cập nhat dịch vụ
  async updateService(
    id: string,
    file: File | undefined,
    serviceData: any,
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
      ? { ...serviceData, imageUrls: [`${imageUrls}`] }
      : serviceData;

    try {
      // cập nhật thông tin dịch vụ
      await axios.patch(`${API_BASE_URL}/services/${id}`, data, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });

      // cập nhật giá nếu có
      if (price !== undefined) {
        await axios.post(
          `${API_BASE_URL}/services/${id}/prices`,
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

      toast.success("Cập nhật dịch vụ thành công!");
    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }

      console.error("Lỗi khi cập nhật:", error);
      toast.error("Lỗi khi cập nhật dịch vụ!");
      throw error;
    }
  }




}
