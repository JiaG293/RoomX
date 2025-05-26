import { UploadResponse } from "@/services/admin/user.service";
import { AuthService } from "@/services/auth.service";
import axios from "axios";
import Cookies from "js-cookie";
import { toast } from "sonner";

const API_BASE_URL = import.meta.env.VITE_BACKEND_HOST;

export class RoomService {
  // Lấy danh sách phòng
  async getListRooms(page: number, size: number) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/rooms/filters`, {
        params: { page, size },
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      return response.data.result;
    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        console.log("gọi hàm refreshtoken");
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }

      console.error("Error fetching users:", error);
      toast.error("Lỗi khi lấy dữ liệu!");
      throw error;
    }
  }

  // chi tiết
  async getDetailRoom(id: string) {
    const token = Cookies.get("token");
    const headers = {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
      "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
    };

    try {
      // Fetch room data
      const { data: roomData } = await axios.get(
        `${API_BASE_URL}/rooms/${id}`,
        { headers }
      );
      const { place, description, status, imageUrls, roomClass, totalPrice } =
        roomData.result;

      // Fetch place details
      const { data: placeData } = await axios.get(
        `${API_BASE_URL}/places/${place.id}`,
        { headers }
      );
      const { branch, building, floor } = placeData.result;

      return {
        branch,
        building,
        floor,
        place,
        description,
        status,
        imageUrls,
        roomClass,
        totalPrice,
      };
    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        console.log("Gọi hàm refreshToken");
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }

      console.error("Error fetching room detail:", error);
      toast.error("Lỗi khi lấy dữ liệu phòng!");
      throw error;
    }
  }

  // Lấy danh sách phòng theo chi nháh
  async getListRoomsByBranchId(branchId: string) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/rooms/filters`, {
        params: { branchId },
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      return response.data.result;
    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        console.log("gọi hàm refreshtoken");
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }

      // console.error("Error fetching users:", error);
      // toast.error("Lỗi khi lấy dữ liệu!");
      throw error;
    }
  }

  // Thêm phòng
  async createRoom(roomData: any) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    let imageUrls: string | undefined = undefined;

    // Nếu có file thì upload ảnh trước
    if (roomData.file) {
      const file = roomData.file;
      const formData = new FormData();
      formData.append("file", file);

      const uploadRes = await axios.post<UploadResponse>(
        `${API_BASE_URL}/s3/users/avatar`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
            "X-tenantId": import.meta.env.VITE_KEYCLOAK_REALM,
          },
        }
      );

      if (uploadRes.data.code !== 1000) {
        throw new Error(`Upload avatar failed, code: ${uploadRes.data.code}`);
      }

      imageUrls = uploadRes.data.result;
    }

    const generateRoomClassCode = () =>
      "" + Math.floor(1e7 + Math.random() * 9e7);

    try {
      const classRoomResponse = await axios.post(
        `${API_BASE_URL}/room-classes`,
        {
          roomClassCode: generateRoomClassCode(),
          basePrice: roomData.price,
          capacity: roomData.capacity,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
            "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
          },
        }
      );

      const roomClassId = classRoomResponse.data.result.id;  

      // tạo phòng
      const data = {
        roomCode: roomData.roomCode,
        placeId: roomData.floorId,
        roomClassId: roomClassId,
        description: roomData.description,
        imageUrls: [imageUrls],
        status: "AVAILABLE"
      }
      const response = await axios.post(`${API_BASE_URL}/rooms`, data, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      toast.success("Thêm phòng thanh cong!");
      window.location.reload();
      return response.data.result;

    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        console.log("gọi hàm refreshtoken");
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }

      console.error("Error fetching users:", error);
      toast.error("Lỗi khi thêm phòng!");
      throw error;
    }
  }

  async updateImageRoom(roomId: string, file: File) {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      let imageUrls: string | undefined = undefined;

      // Nếu có file thì upload ảnh mới
      if (file) {
        const formData = new FormData();
        formData.append("file", file);

        const uploadRes = await axios.post<UploadResponse>(
          `${API_BASE_URL}/s3/users/avatar`,
          formData,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "multipart/form-data",
              "X-tenantId": import.meta.env.VITE_KEYCLOAK_REALM,
            },
          }
        );

        if (uploadRes.data.code !== 1000) {
          throw new Error(`Upload avatar failed, code: ${uploadRes.data.code}`);
        }

        imageUrls = uploadRes.data.result;
      }

      const response = await axios.patch(
        `${API_BASE_URL}/rooms/${roomId}`,
        {
          imageUrls: [imageUrls],
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
            "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
          },
        }
      );
      toast.success("Cập nhật phòng thành cong!");
      window.location.reload();
      return response.data.result;
    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        console.log("gọi hàm refreshtoken");
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }

      console.error("Error fetching users:", error);
      toast.error("Lỗi khi cập nhật phòng!");
      throw error;
    }
  }

  async getLimitRoom() {
    const token = Cookies.get("token");
    if (!token) {
      throw new Error("No authentication token found in cookies");
    }
    try {
      const response = await axios.get(`${API_BASE_URL}/rooms/capacity-range`, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
        },
      });
      return response.data.result.max;
    } catch (error: any) {
      const responseData = error?.response?.data;

      if (error?.response?.status === 401 && responseData?.code === 1007) {
        console.log("gọi hàm refreshtoken");
        const authService = new AuthService();
        await authService.refreshToken();
        window.location.reload();
      }

      // console.error("Error fetching users:", error);
      // toast.error("Lỗi khi lấy dữ liệu!");
      throw error;
    }
  }
}
