import axios from "axios";
import Cookies from "js-cookie";
import { toast } from "sonner";

const API_BASE_URL = import.meta.env.VITE_BACKEND_HOST;

export class ScheduleService {

  async getAllSchedules(month: number, year: number) {
    try { 
      const response = await axios.get(`${API_BASE_URL}/bookings/list`, {
        headers: {
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
          Authorization: `Bearer ${Cookies.get("token")}`,
        },
        params: { month, year },
      });
      return response.data;  
    } catch (error) {
      console.error("Error fetching branches:", error);
      throw error;
    }
  }


  // Kiểm tra lịch trước khi đặt

  async checkSchedule(scheduleData: any) {
    try { 
      const response = await axios.post(`${API_BASE_URL}/bookings/checking`, scheduleData, {
        headers: {
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
          Authorization: `Bearer ${Cookies.get("token")}`,
        },
      });
      return response.data;  
    } catch (error) {
      console.error("Error fetching branches:", error);
      throw error;
    }
  }

  // Đặt lịch
  async createSchedule(scheduleData: any) {
    try { 
      const response = await axios.post(`${API_BASE_URL}/bookings`, scheduleData, {
        headers: {
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
          Authorization: `Bearer ${Cookies.get("token")}`,
        },
      });
      toast.success("Đặt lịch thành công")
      return response.data;  
    } catch (error) {
      toast.error("Đặt lịch thất bại")
      console.error("Error fetching branches:", error);
      throw error;
    }
  }

  //

}
