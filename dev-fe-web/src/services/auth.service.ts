import Cookies from "js-cookie";
import axios from "axios";
import { toast } from "sonner";

export class AuthService {
  async refreshToken() {
    const refreshToken = Cookies.get("refreshToken");

    if (!refreshToken) {
      this.logout();
      throw new Error("No refresh token found");
    }

    try {
      const response = await axios.post(
        `${import.meta.env.VITE_KEYCLOAK_URL}/realms/${
          import.meta.env.VITE_KEYCLOAK_REALM
        }/protocol/openid-connect/token`,
        new URLSearchParams({
          client_id: import.meta.env.VITE_KEYCLOAK_CLIENT_ID || "",
          refresh_token: refreshToken,
          client_secret: import.meta.env.VITE_KEYCLOAK_CLIENT_SECRET || "",
          grant_type: "refresh_token",
        }),
        { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
      );

      const data = response.data;
      Cookies.set("token", data.access_token);
      Cookies.set("refreshToken", data.refresh_token || "");
      console.log("✅ Token refreshed");
    } catch (error: any) {
      const errorData = error.response?.data;

      // Nếu refresh token hết hạn hoặc không hợp lệ
      if (errorData?.error === "invalid_grant") {
        console.warn("⚠️ Refresh token không hợp lệ hoặc hết hạn. Đăng xuất.");
        this.logout();
      } else {
        console.error("❌ Lỗi không xác định khi refresh token:", error);
        this.logout();
      }

      throw error;
    }
  }

  async getProfile() {
    const token = Cookies.get("token");

    if (!token) {
      this.logout();
      throw new Error("Không tìm thấy token");
    }

    try {
      const response = await axios.get(
        `https://apiroomx.jiag.id.vn/api/v1/users/info`,
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

  logout() {
    Cookies.remove("token");
    Cookies.remove("refreshToken");
    window.location.href = "/login";
    toast.warning("Phiên hết hạn, vui lòng đăng nhập lại!");
  }
}
