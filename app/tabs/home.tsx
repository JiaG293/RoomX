import { useRouter } from "expo-router";
import React from "react";
import { View, Text, StyleSheet, FlatList, TouchableOpacity } from "react-native";

// Định nghĩa kiểu dữ liệu cho sự kiện
type EventItem = {
  id: string;
  title: string;
  date: string;
  time: string;
  room: string;
};

const Home = () => {
  const meetingStats = {
    total: 12,
    upcoming: 3,
    completed: 9,
  };

  const upcomingEvents: EventItem[] = [
    {
      id: "1",
      title: "Họp nhóm dự án A",
      date: "2025-04-11",
      time: "10:00",
      room: "Phòng 301",
    },
    {
      id: "2",
      title: "Thuyết trình sản phẩm",
      date: "2025-04-12",
      time: "14:00",
      room: "Phòng họp lớn",
    },
    {
      id: "3",
      title: "Họp ban điều hành",
      date: "2025-04-14",
      time: "09:00",
      room: "Phòng 204",
    },
  ];

  const router = useRouter();

  const renderEvent = ({ item }: { item: EventItem }) => (
    <TouchableOpacity
      onPress={() =>
        router.push({
          pathname: "/event-detail",
          params: {
            title: item.title,
            date: item.date,
            time: item.time,
            room: item.room,
          },
        })
      }
    >
      <View style={styles.eventItem}>
        <Text style={styles.eventTitle}>{item.title}</Text>
        <Text style={styles.eventInfo}>
          ⏰ {item.date} lúc {item.time} – 📍 {item.room}
        </Text>
      </View>
    </TouchableOpacity>
  );

  return (
    <View style={styles.container}>
      {/* phần nội dung giữ nguyên như trước */}
      <Text style={styles.header}>📊 Thống kê đặt lịch phòng họp tháng 4</Text>
      <View style={styles.statsContainer}>
        <View style={styles.statBox}>
          <Text style={styles.statLabel}>Tổng cộng</Text>
          <Text style={styles.statValue}>{meetingStats.total}</Text>
        </View>
        <View style={styles.statBox}>
          <Text style={styles.statLabel}>Sắp tới</Text>
          <Text style={styles.statValue}>{meetingStats.upcoming}</Text>
        </View>
        <View style={styles.statBox}>
          <Text style={styles.statLabel}>Đã hoàn tất</Text>
          <Text style={styles.statValue}>{meetingStats.completed}</Text>
        </View>
      </View>

      <Text style={styles.header}>📅 Sự kiện sắp diễn ra</Text>
      <FlatList
        data={upcomingEvents}
        keyExtractor={(item) => item.id}
        renderItem={renderEvent}
        contentContainerStyle={{ paddingBottom: 20 }}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 20,
    flex: 1,
    backgroundColor: "#f9f9f9",
  },
  header: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 15,
  },
  statsContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginBottom: 30,
  },
  statBox: {
    backgroundColor: "#e0e0e0",
    borderRadius: 10,
    padding: 15,
    alignItems: "center",
    flex: 1,
    marginHorizontal: 5,
  },
  statLabel: {
    fontWeight: "600",
    marginBottom: 5,
  },
  statValue: {
    fontSize: 20,
    fontWeight: "bold",
  },
  eventItem: {
    backgroundColor: "#ffffff",
    padding: 15,
    borderRadius: 10,
    marginBottom: 10,
    borderWidth: 1,
    borderColor: "#ddd",
  },
  eventTitle: {
    fontWeight: "600",
    marginBottom: 5,
  },
  eventInfo: {
    color: "#555",
  },
});

export default Home;
