import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";
import Constants from "expo-constants";

const extra = Constants.manifest?.extra || Constants.expoConfig?.extra || {};

const {
  KEYCLOAK_URL,
  KEYCLOAK_REALM,
  KEYCLOAK_CLIENT_ID,
  KEYCLOAK_CLIENT_SECRET,
} = extra;

export class AuthService {
  login = async (username: string, password: string) => {
    try {
      const clientId = KEYCLOAK_CLIENT_ID;
      const clientSecret = KEYCLOAK_CLIENT_SECRET;
      const keycloakUrl = KEYCLOAK_URL;
      const realm = KEYCLOAK_REALM;

      if (!clientId || !clientSecret || !keycloakUrl || !realm) {
        throw new Error("Missing Keycloak config variables");
      }

      const params = new URLSearchParams({
        client_id: clientId,
        client_secret: clientSecret,
        username: username,
        password: password,
        grant_type: "password",
      });

      const response = await axios.post(
        `${keycloakUrl}/realms/${realm}/protocol/openid-connect/token`,
        params,
        { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
      );

      const data = response.data;

      // Lưu token vào AsyncStorage
      await AsyncStorage.setItem("token", data.access_token);
      await AsyncStorage.setItem("refreshToken", data.refresh_token || "");
      console.log("Đăng nhập thành công.");
      return { success: true, data };
    } catch (error) {
      console.error("Đăng nhập thất bại. Kiểm tra lại thông tin.");
      return { success: false, message: "Đăng nhập thất bại. Kiểm tra lại thông tin." };
    }
  };

  logout = async () => {
    try {
      await AsyncStorage.removeItem("token");
      await AsyncStorage.removeItem("refreshToken");
      console.log("Đăng xuất thành công.");
    } catch (error) {
      // console.error("Đăng xuất that bai.");
    }
  };
}
