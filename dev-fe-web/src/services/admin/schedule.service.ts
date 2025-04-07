import axios from "axios";
import Cookies from "js-cookie";

const API_BASE_URL = import.meta.env.VITE_BACKEND_HOST;

export class ScheduleService {

  // Lấy danh sách lịch 
  async getAllSchedules(month: number, year: number) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    } 

    try { 
      const response = await axios.get(`${API_BASE_URL}/bookings/filters`, {
        params: { month, year, size: -1 },
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      return response.data.result.content;  
    } catch (error) {
      console.error("Error fetching branches:", error);
      throw error;
    }
  }
  
  //Lấy các lịch cần duyệt
  async getPendingSchedules(month: number, year: number) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    } 

    try { 
      const response = await axios.get(`${API_BASE_URL}/bookings/list`, {
        params: { month, year },
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      return response.data.result.content;  
    } catch (error) {
      console.error("Error fetching branches:", error);
      throw error;
    }
  }

  // Thêm phương thức book lịch
  async bookSchedule(scheduleData: any) {
    const token = Cookies.get("token");

    if (!token) {
      throw new Error("No authentication token found in cookies");
    }

    try {
      const response = await axios.post(`${API_BASE_URL}/booking`, scheduleData);
      return response.data; // Trả về dữ liệu lịch đã được đặt
    } catch (error) {
      console.error("Error booking schedule:", error);
      throw error; // Lỗi khi gọi API
    }
  }

  // duyệt lịch
  async approveSchedules(id: string) {
    const token = Cookies.get("token");
  
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
  
    try {
      const response = await axios.post(
        `${API_BASE_URL}/bookings/${id}`,
        {}, // Không cần gửi dữ liệu body nếu API không yêu cầu
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
            "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
          },
        }
      );
      return response.data;
    } catch (error) {
      console.error("Error approving schedule:", error);
      throw error;
    }
  }
  

}
