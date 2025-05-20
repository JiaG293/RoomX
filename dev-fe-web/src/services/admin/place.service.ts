import { AuthService } from "@/services/auth.service";
import axios from "axios";
import Cookies from "js-cookie";
import { toast } from "sonner";

const API_BASE_URL = import.meta.env.VITE_BACKEND_HOST;

export class PlaceService {
  async getDetailPlaces(id: string) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/places/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      return response.data.result.content;
    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        console.log("goi ham refreshtoken");
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }
      console.error("Error fetching users:", error);
      toast.error("Loi khi lay du lieu!");
      throw error;
    }
  }
}
