import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";

const KEYCLOAK_URL = "https://keycloak.jiag.id.vn"
const KEYCLOAK_REALM = "sang"
const KEYCLOAK_CLIENT_ID = "roomx-fe"
const KEYCLOAK_CLIENT_SECRET = "1wocApS42f0b51sLXMNDwulzRAzTnEIe"

export class AuthService {
  login = async (username: string, password: string) => {
    try {
        const clientId = KEYCLOAK_CLIENT_ID;
        const clientSecret = KEYCLOAK_CLIENT_SECRET;

        console.log(clientId)
        console.log(clientSecret)
        
        if (!clientId || !clientSecret) {
          throw new Error("Client ID or Client Secret is not defined");
        }
        
        const params = new URLSearchParams({
          client_id: clientId,
          client_secret: clientSecret,
          username: username,
          password: password,
          grant_type: "password",
        });
        

      const response = await axios.post(
        `https://keycloak.jiag.id.vn/realms/sang/protocol/openid-connect/token`,
        params,
        { headers: { "Content-Type": "application/x-www-form-urlencoded" } }
      );

      const data = response.data;

      // Lưu token vào AsyncStorage
      await AsyncStorage.setItem("token", data.access_token);
      await AsyncStorage.setItem("refreshToken", data.refresh_token || "");

      return { success: true, data };
    } catch (error) {
      console.error("Login error:", error);
      return { success: false, message: "Đăng nhập thất bại. Kiểm tra lại thông tin." };
    }
  };
}
