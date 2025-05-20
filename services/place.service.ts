import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";

export const getAllBranches = async () => {
  const token = await AsyncStorage.getItem("token");

  if (!token) {
    throw new Error("No authentication token found");
  }
  try {
    const response = await axios.get(
      `https://apiroomx.jiag.id.vn/api/v1/places/hierarchy?placeType=BRANCH`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `sang`, // nếu dùng Expo
        },
      }
    );
    return response.data.result;
  } catch (error: any) {
    console.error("Lỗi khi lấy chi nhánh:", error);
    throw error;
  }
};

// Lấy danh sách phòng theo chi nháh
export const getListRoomsByBranchId = async (branchId: string) => {
  const token = await AsyncStorage.getItem("token");
  if (!token) {
    throw new Error("No authentication token found in cookies");
  }
  try {
    const response = await axios.get(
      `https://apiroomx.jiag.id.vn/api/v1/rooms/filters`,
      {
        params: { branchId },
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `sang`,
        },
      }
    );
    return response.data.result;
  } catch (error: any) {
    console.error("Lỗi khi lấy danh sách phòng.", error);
    throw error;
  }
};

export const bookSchedule = async (scheduleData: any) => {
  const token = await AsyncStorage.getItem("token");

  if (!token) {
    throw new Error("No authentication token found in cookies");
  }

  try {
    const response = await axios.post(
      `https://apiroomx.jiag.id.vn/api/v1/bookings`,
      scheduleData,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `sang`,
        },
      }
    );
    return response.data; // Trả về dữ liệu lịch đã được đặt
  } catch (error: any) {
    console.error("Lỗi khi lấy dữ liệu!");
    throw error;
  }
};
