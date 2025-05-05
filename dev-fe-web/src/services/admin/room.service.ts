import { AuthService } from "@/services/auth.service";
import axios from "axios";
import Cookies from "js-cookie";
import { toast } from "sonner";

const API_BASE_URL = import.meta.env.VITE_BACKEND_HOST;

export class RoomService {
  // Lấy danh sách phòng
  async getListRooms(page: number, size: number) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/rooms/filters`, {
        params: { page, size },
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

  // load toà nhà của phòng theo chi nhánh
  async getBuildingByBranch(branchId: string) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/places/hierarchy`, {
        params: { branchId },
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

  // load toà nhà của phòng theo chi nhánh
  async getFloorByBranchAndBuilding(branchId: string, building: string) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/places/hierarchy`, {
        params: { branchId, building },
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
  
  // Thêm phòng
  async createRoom(roomData: any) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.post(`${API_BASE_URL}/rooms`, roomData, {
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
      toast.error("Lỗi khi thêm phòng!");
      throw error;
    }
  }
}
