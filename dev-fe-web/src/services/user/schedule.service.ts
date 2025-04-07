import axios from "axios";
import Cookies from "js-cookie";

const API_BASE_URL = import.meta.env.VITE_BACKEND_HOST2;

export class ScheduleService {

  async getAllSchedules(month: number, year: number, email: string) {
    try { 
      const response = await axios.get(`${API_BASE_URL}/schedules/user/${email}`, {
        params: { month, year },
      });
      return response.data;  
    } catch (error) {
      console.error("Error fetching branches:", error);
      throw error;
    }
  }

}
