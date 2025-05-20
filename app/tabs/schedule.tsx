import { useEffect, useState } from "react";
import { View, Text, StyleSheet, TouchableOpacity, Alert } from "react-native";
import { Calendar } from "react-native-big-calendar";
import { Ionicons } from "@expo/vector-icons";
import { ScheduleService } from "@/services/schedule.service";
import { useFocusEffect } from "@react-navigation/native";
import { useCallback } from "react";
import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";

export default function HomeScreen() {
  const [mode, setMode] = useState<"day" | "week" | "month">("month");
  const [currentDate, setCurrentDate] = useState(new Date());

  const [events, setEvents] = useState([]);
  const scheduleService = new ScheduleService();

  const translateStatus = (status: string) => {
    switch (status) {
      case "COMPLETED":
        return "✅ Đã hoàn thành";
      case "SCHEDULED":
        return "📅 Đã lên lịch";
      case "PENDING":
        return "⏳ Đang chờ"; // icon đồng hồ cát
      case "CONFLICT":
        return "⚠️ Xung đột lịch";
      default:
        return status;
    }
  };

  const handlePressEvent = (event: any) => {
    const { data } = event;

    const message = `
📌 Mã đặt phòng: ${data.bookingCode}
🏢 Chi nhánh: ${data.branch?.name}
🚪 Phòng: ${data.room?.name}
📅 Ngày: ${data.meetingDate}
⏰ Thời gian: ${data.meetingStart} - ${data.meetingEnd}
✅ Trạng thái: ${translateStatus(data.status)}
`;

    Alert.alert("Thông tin sự kiện", message.trim());
  };

  useFocusEffect(
    useCallback(() => {
      const fetchEvents = async () => {
        try {
          const token = await AsyncStorage.getItem("token");
          if (!token) throw new Error("No authentication token found");

          const month = currentDate.getMonth() + 1;
          const year = currentDate.getFullYear();

          const response = await axios.get(
            "https://apiroomx.jiag.id.vn/api/v1/bookings/list",
            {
              params: { month, year, size: -1, isAdmin: false },
              headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
                "X-tenantId": "sang",
              },
              timeout: 10000,
              validateStatus: (status) => true, // không throw error status code
            }
          );

          console.log("Response status:", response.status);
          console.log("Response data:", response.data);

          if (response.status >= 200 && response.status < 300) {
            const schedules = response.data.result.content;

            console.log(schedules);
            const mappedEvents = schedules.map((item: any) => {
              const startDateTime = new Date(
                `${item.meetingDate}T${item.meetingStart}`
              );
              const endDateTime = new Date(
                `${item.meetingDate}T${item.meetingEnd}`
              );

              return {
                title: item.title || "Sự kiện",
                start: startDateTime,
                end: endDateTime,
                data: item,
              };
            });

            console.log("Mapped events:::::::::::::::::;");
            console.log(mappedEvents);

            setEvents(mappedEvents);
          } else {
            console.error("API error:", response.status, response.data);
          }
        } catch (err: any) {
          console.error("Axios error:", err.message);
          if (err.request) {
            console.error("Request made but no response:", err.request);
          }
          if (err.response) {
            console.error("Response error:", err.response);
          }
        }
      };

      fetchEvents();
    }, [currentDate])
  );

  // Chuyển đổi chế độ xem
  const handleChangeMode = (newMode: "day" | "week" | "month") => {
    setMode(newMode);
  };

  // Next / Prev
  const handleDateChange = (direction: "next" | "prev") => {
    const newDate = new Date(currentDate);
    if (mode === "day") {
      newDate.setDate(currentDate.getDate() + (direction === "next" ? 1 : -1));
    } else if (mode === "week") {
      newDate.setDate(currentDate.getDate() + (direction === "next" ? 7 : -7));
    } else {
      newDate.setMonth(
        currentDate.getMonth() + (direction === "next" ? 1 : -1)
      );
    }
    setCurrentDate(newDate);
  };

  return (
    <View style={styles.container}>
      <View style={styles.navContainer}>
        <TouchableOpacity onPress={() => handleDateChange("prev")}>
          <Ionicons name="chevron-back" size={24} color="#1890ff" />
        </TouchableOpacity>
        <Text style={styles.currentViewText}>
          {mode === "day"
            ? currentDate.toLocaleDateString("vi-VN")
            : mode === "week"
            ? `Tuần ${currentDate.toLocaleDateString("vi-VN")}`
            : `Tháng ${
                currentDate.getMonth() + 1
              }, ${currentDate.getFullYear()}`}
        </Text>
        <TouchableOpacity onPress={() => handleDateChange("next")}>
          <Ionicons name="chevron-forward" size={24} color="#1890ff" />
        </TouchableOpacity>
      </View>

      {/* Chế độ xem */}
      <View style={styles.buttonContainer}>
        {["day", "week", "month"].map((item) => (
          <TouchableOpacity
            key={item}
            style={[styles.button, mode === item && styles.activeButton]}
            onPress={() => handleChangeMode(item as "day" | "week" | "month")}
          >
            <Text
              style={[
                styles.buttonText,
                mode === item && styles.activeButtonText,
              ]}
            >
              {item === "day" ? "Ngày" : item === "week" ? "Tuần" : "Tháng"}
            </Text>
          </TouchableOpacity>
        ))}
      </View>

      {/* Lịch */}
      <View style={styles.calendarWrapper}>
        <Calendar
          events={events}
          height={500}
          mode={mode}
          swipeEnabled={false}
          date={currentDate} // Cập nhật theo ngày hiện tại
          onPressEvent={handlePressEvent} // thêm callback ở đây
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
    backgroundColor: "#f0f2f5",
  },
  header: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    backgroundColor: "#fff",
    padding: 15,
    borderRadius: 10,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  headerTitle: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#333",
  },
  headerDate: {
    fontSize: 14,
    color: "#555",
  },
  navContainer: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    backgroundColor: "#fff",
    padding: 10,
    borderRadius: 10,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
    marginVertical: 10,
  },
  currentViewText: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#333",
  },
  buttonContainer: {
    flexDirection: "row",
    justifyContent: "center",
    marginVertical: 15,
  },
  button: {
    paddingVertical: 8,
    paddingHorizontal: 20,
    borderRadius: 20,
    backgroundColor: "#ddd",
    marginHorizontal: 5,
  },
  activeButton: {
    backgroundColor: "#1890ff",
  },
  buttonText: {
    fontSize: 16,
    fontWeight: "500",
    color: "#333",
  },
  activeButtonText: {
    color: "#fff",
  },
  calendarWrapper: {
    flex: 1,
    backgroundColor: "#fff",
    borderRadius: 12,
    padding: 10,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.1,
    shadowRadius: 6,
    elevation: 5,
  },
});
