/* các service của firebase */
import Cookies from "js-cookie";
import { messaging } from "../firebase/firebase-config";
import { getToken, onMessage, deleteToken } from "firebase/messaging";
import axios from "axios";
import { AuthService } from "@/services/auth.service";

export const requestNotificationPermission = async (): Promise<string | null> => {
  const permission = await Notification.requestPermission();
  if (permission === "granted") {
    try {
      console.log("📱 Requesting FCM token...");
      const token = await getToken(messaging);
      console.log("fcm_token", token)
      if (token) {
        Cookies.set("fcm_token", token);
        console.log("📱 FCM Token saved:", token);
        return token;
      } else {
        console.warn("⚠️ No registration token available");
        return null;
      }
    } catch (error) {
      console.error("❌ Error getting FCM token:", error);
      return null;
    }
  } else {
    console.warn("🚫 Notification permission denied");
    return null;
  }
};

export const onMessageListener = (callback: (payload: any) => void) => {
  return onMessage(messaging, (payload) => {
    callback(payload);
  });
};

export const removeFcmToken = async (): Promise<void> => {
  try {
    const currentToken = await getToken(messaging);
    if (currentToken) {
      await deleteToken(messaging);
      Cookies.remove("fcm_token");
      console.log("✅ FCM token deleted successfully");
    } else {
      console.log("ℹ️ No FCM token found to delete");
    }
  } catch (error) {
    console.error("❌ Failed to delete FCM token:", error);
  }
};

// export const sendTokenToServer = async (fcm_token: string) => {
//   const token = Cookies.get("token");
//   const API_BASE_URL = import.meta.env.VITE_BACKEND_HOST;

//   if (!token ){
//     throw new Error("No authentication token found in cookies");
//   }
//   try {
//     const response = await axios.post(`${API_BASE_URL}/users/fcm-token`, { fcm_token }, {
//       headers: {
//         Authorization: `Bearer ${token}`,
//         "Content-Type": "application/json",
//         "X-tenantId": `${import.meta.env.VITE_KEYCLOAK_REALM}`,
//       },
//     });
//     return response.data.result;
//   }
//   catch (error: any) {
//     const responseData = error?.response?.data;

//     if (error?.response?.status === 401 && responseData?.code === 1007) {
//       console.log("gọi hàm refreshtoken");
//       const authService = new AuthService();
//       await authService.refreshToken();
//       window.location.reload();
//     }
//     throw error;
//   }
// };
