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

  logout() {
    Cookies.remove("token");
    Cookies.remove("refreshToken");
    window.location.href = "/login";
    toast.warning("Phiên hết hạn, vui lòng đăng nhập lại!");
    
  }
}
