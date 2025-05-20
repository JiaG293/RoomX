import { useState } from "react";
import { View, Text, StyleSheet, TouchableOpacity } from "react-native";
import { Calendar } from "react-native-big-calendar";
import { Ionicons } from "@expo/vector-icons";

const events = [
  {
    title: "Họp dự án",
    room: "Phòng họp A",
    start: new Date(2025, 4, 10, 10, 0),
    end: new Date(2025, 4, 10, 11, 30),
    color: "#ff4d4f",
  },
  {
    title: "Họp với khách hàng",
    room: "Phòng họp B",
    start: new Date(2025, 4, 4, 10, 30),
    end: new Date(2025, 4, 4, 12, 0),
    color: "#40a9ff",
  },
  {
    title: "Nghỉ trưa",
    room: "Khu vực căng tin",
    start: new Date(2025, 2, 28, 12, 30),
    end: new Date(2025, 2, 28, 13, 30),
    color: "#ffa940",
  },
  {
    title: "Thảo luận kế hoạch",
    room: "Phòng họp B",
    start: new Date(2025, 3, 4, 14, 0),
    end: new Date(2025, 3, 4, 15, 30),
    color: "#40a9ff",
  },
  {
    title: "Đào tạo nội bộ",
    room: "Phòng họp C",
    start: new Date(2025, 3, 7, 9, 0),
    end: new Date(2025, 3, 7, 11, 0),
    color: "#73d13d",
  },
  {
    title: "Báo cáo tiến độ",
    room: "Phòng họp A",
    start: new Date(2025, 3, 10, 15, 0),
    end: new Date(2025, 3, 10, 16, 0),
    color: "#ff85c0",
  },
  {
    title: "Kiểm tra hệ thống",
    room: "Phòng server",
    start: new Date(2025, 3, 12, 10, 0),
    end: new Date(2025, 3, 12, 12, 0),
    color: "#ff4d4f",
  },
  {
    title: "Gặp mặt nhóm phát triển",
    room: "Phòng họp B",
    start: new Date(2025, 3, 15, 14, 0),
    end: new Date(2025, 3, 15, 16, 0),
    color: "#40a9ff",
  },
  {
    title: "Họp chiến lược quý 2",
    room: "Phòng họp A",
    start: new Date(2025, 3, 18, 9, 0),
    end: new Date(2025, 3, 18, 12, 0),
    color: "#faad14",
  },
  {
    title: "Thảo luận dự án mới",
    room: "Phòng họp C",
    start: new Date(2025, 3, 22, 10, 30),
    end: new Date(2025, 3, 22, 12, 0),
    color: "#9254de",
  },
  {
    title: "Demo sản phẩm",
    room: "Phòng họp A",
    start: new Date(2025, 3, 25, 14, 0),
    end: new Date(2025, 3, 25, 16, 0),
    color: "#ff7875",
  },
  {
    title: "Tổng kết tháng 4",
    room: "Phòng họp B",
    start: new Date(2025, 3, 30, 15, 0),
    end: new Date(2025, 3, 30, 17, 0),
    color: "#ffc53d",
  }
];


export default function HomeScreen() {
  const [mode, setMode] = useState<"day" | "week" | "month">("week");
  const [currentDate, setCurrentDate] = useState(new Date());

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
      newDate.setMonth(currentDate.getMonth() + (direction === "next" ? 1 : -1));
    }
    setCurrentDate(newDate);
  };

  return (
    <View style={styles.container}>

      {/* Điều hướng Next / Prev */}
      <View style={styles.navContainer}>
        <TouchableOpacity onPress={() => handleDateChange("prev")}>
          <Ionicons name="chevron-back" size={24} color="#1890ff" />
        </TouchableOpacity>
        <Text style={styles.currentViewText}>
          {mode === "day"
            ? currentDate.toLocaleDateString("vi-VN")
            : mode === "week"
            ? `Tuần ${currentDate.toLocaleDateString("vi-VN")}`
            : `Tháng ${currentDate.getMonth() + 1}, ${currentDate.getFullYear()}`}
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
          events={events.map((event) => ({
            ...event,
            title: `${event.title} - ${event.room}`,
          }))}
          height={500}
          mode={mode}
          swipeEnabled={false}
          date={currentDate} // Cập nhật theo ngày hiện tại
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
