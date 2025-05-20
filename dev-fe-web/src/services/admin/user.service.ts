import { AuthService } from "@/services/auth.service";
import axios from "axios";
import Cookies from "js-cookie";
import { toast } from "sonner";

const API_BASE_URL = import.meta.env.VITE_BACKEND_HOST;

export class UserService {
  async getListUsers(page: number, size: number, keyword: string) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("Không tìm thấy token");
    }

    try {
      const response = await axios.get(`${API_BASE_URL}/users/filters`, {
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

  // Lấy thông tin chi tiết
  async getUserDetails(userId: string) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }

    try {
      const response = await axios.get(`${API_BASE_URL}/users/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      return response.data;
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

  // Phương thức POST để tạo người dùng mới
  async createUser(userData: {
    userCode: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    password: string;
    gender: string;
    email: string;
    type: string;
    roles: string[];
  }) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    }

    try {
      const response = await axios.post(`${API_BASE_URL}/users`, userData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      console.log("thanh cong");
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

  //vô hiệu hoá người dùng
  async deactivateUser(userId: string) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    }

    try {
      const response = await axios.patch(
        `${API_BASE_URL}/users/${userId}`,
        { enable: false }, 
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
            "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
          },
        }
      );

      return response.data;
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

  //kích hoạt người dùng
  async activateUser(userId: string) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    }

    try {
      const response = await axios.patch(
        `${API_BASE_URL}/users/${userId}`,
        { enable: true }, 
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
            "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
          },
        }
      );

      return response.data;
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
}
