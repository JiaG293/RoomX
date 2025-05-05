import { AuthService } from "@/services/auth.service";
import axios from "axios";
import Cookies from "js-cookie";
import { toast } from "sonner";

const API_BASE_URL = import.meta.env.VITE_BACKEND_HOST;

export class BranchService {
  async getAllBranches() {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    }

    try {
      const response = await axios.get(`${API_BASE_URL}/places/hierarchy?placeType=BRANCH`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      return response.data.result;
    } catch (error: any) {
      const responseData = error?.response?.data;
  
      if (
        error?.response?.status === 401 &&
        responseData?.code === 1007
      ) {
        console.log("gọi hàm refreshtoken")
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }
  
      console.error("Error fetching users:", error);
      toast.error("Lỗi khi lấy dữ liệu!");
      throw error;
    }
  }

  // Lấy danh sách chi nhánh với phân trang
  async getListBranches(size: number) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    }

    try {
      const response = await axios.get(
        `${API_BASE_URL}/branchs/filters?${size}`,
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
  
      if (
        error?.response?.status === 401 &&
        responseData?.code === 1007
      ) {
        console.log("gọi hàm refreshtoken")
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }
  
      console.error("Error fetching users:", error);
      toast.error("Lỗi khi lấy dữ liệu!");
      throw error;
    }
  }

  // Lấy danh chi tiết chi nhánh
  async getDetailBranch(id: string) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    }

    try {
      const response = await axios.get(
        `${API_BASE_URL}/places/${id}`,
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
  
      if (
        error?.response?.status === 401 &&
        responseData?.code === 1007
      ) {
        console.log("gọi hàm refreshtoken")
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }
  
      console.error("Error fetching users:", error);
      toast.error("Lỗi khi lấy dữ liệu!");
      throw error;
    }
  }

  // Tạo chi nhánh mới
  async createBranch(branchData: {
    branchCode: string;
    name: string;
    email: string;
    phoneNumber: string;
    address: string;
  }) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    }

    try {
      //Tạo tài nguyên branch
      const response = await axios.post(`${API_BASE_URL}/branchs`, branchData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      // console.log(response.data.result.id);
      // await axios.post(
      //   `${API_BASE_URL}/places`,
      //   {
      //     branchId: response.data.result.id,
      //     layout: "url",
      //     placeType: "BRANCH",
      //   },
      //   {
      //     headers: {
      //       Authorization: `Bearer ${token}`,
      //       "Content-Type": "application/json",
      //       "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
      //     },
      //   }
      // );
      console.log("oke")
      return response.data.result;
    } catch (error: any) {
      const responseData = error?.response?.data;
  
      if (
        error?.response?.status === 401 &&
        responseData?.code === 1007
      ) {
        console.log("gọi hàm refreshtoken")
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }
  
      console.error("Error fetching users:", error);
      toast.error("Lỗi khi lấy dữ liệu!");
      throw error;
    }
  }

  // Cập nhật chi nhánh
  async updateBranch(id: string, branchData: any) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    }

    console.log(`${API_BASE_URL}/branchs/${id}`);

    try {
      const response = await axios.patch(
        `${API_BASE_URL}/branchs/${id}`,
        branchData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
            "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
          },
        }
      );
      console.log("Branch updated successfully");
      return response.data.result;
    } catch (error: any) {
      const responseData = error?.response?.data;
  
      if (
        error?.response?.status === 401 &&
        responseData?.code === 1007
      ) {
        console.log("gọi hàm refreshtoken")
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
