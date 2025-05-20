import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";
import Constants from "expo-constants";


const extra = Constants.manifest?.extra || Constants.expoConfig?.extra || {};

const {
  KEYCLOAK_URL,
  KEYCLOAK_REALM,
  KEYCLOAK_CLIENT_ID,
  KEYCLOAK_CLIENT_SECRET,
  API_BASE_URL
} = extra;

export class ScheduleService {
  // Lấy danh sách lịch đã duyệt của người dùng (user)
  async getAllSchedulesUser(month: number, year: number) {
    const token = await AsyncStorage.getItem("token");
    if (!token) {
      throw new Error("No authentication token found in AsyncStorage");
    }
    try {
      const response = await axios.get(`https://api.jiag.id.vn/api/v1/bookings/list`, {
        params: { month, year, size: -1, isAdmin: false },
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `sang`,
        },
      });
      return response.data.result.content;
    } catch (error: any) {

      console.error(error);
      throw error;
    }
  }
  // Lấy các lịch cần duyệt của người dùng (user)
  async getPendingSchedulesUser(month: number, year: number) {
    const token = await AsyncStorage.getItem("token");
    if (!token) {
      throw new Error("No authentication token found in AsyncStorage");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/bookings/request/list`, {
        params: { month, year, size: -1, isAdmin: false, status: "PENDING, CONFLICT" },
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${process.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      return response.data.result.content;
    } catch (error: any) {
      console.error("Lỗi khi lấy dữ liệu!");
      throw error;
    }
  }
}
