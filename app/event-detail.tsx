import React from "react";
import {
  View,
  Text,
  StyleSheet,
  StatusBar,
  ScrollView,
  TouchableOpacity,
} from "react-native";
import { useLocalSearchParams, useRouter } from "expo-router";

const EventDetail = () => {
  const router = useRouter(); // hook điều hướng
  const { title, date, time, room } = useLocalSearchParams();

  // Dữ liệu giả lập
  const duration = "2 giờ";
  const host = "Nguyễn Văn A";
  const participants = 15;
  const services = ["Trà", "Bánh ngọt", "Nước lọc"];
  const devices = ["Máy chiếu", "TV", "Mic không dây"];

  return (
    <View style={styles.container}>
      <StatusBar barStyle="dark-content" backgroundColor="#f2f2f2" />

      <ScrollView contentContainerStyle={styles.scrollContent}>
        <Text style={styles.title}>📌 Chi tiết sự kiện</Text>

        <View style={styles.card}>
          <Text style={styles.cardTitle}>{title}</Text>
          <Text style={styles.cardSub}>📅 {date} — 🕒 {time}</Text>
          <Text style={styles.cardSub}>📍 {room} — ⏳ {duration}</Text>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>👨‍💼 Chủ trì</Text>
          <View style={styles.card}>
            <Text style={styles.cardItem}>{host}</Text>
          </View>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>👥 Số người tham gia</Text>
          <View style={styles.card}>
            <Text style={styles.cardItem}>{participants} người</Text>
          </View>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>🍽️ Dịch vụ đi kèm</Text>
          <View style={styles.card}>
            {services.map((item, idx) => (
              <Text key={idx} style={styles.cardItem}>• {item}</Text>
            ))}
          </View>
        </View>

        <View style={styles.section}>
          <Text style={styles.sectionTitle}>💻 Thiết bị hỗ trợ</Text>
          <View style={styles.card}>
            {devices.map((item, idx) => (
              <Text key={idx} style={styles.cardItem}>• {item}</Text>
            ))}
          </View>
        </View>

        {/* Nút quay về */}
        <TouchableOpacity onPress={() => router.back()} style={styles.backButton}>
          <Text style={styles.backButtonText}>⬅️ Quay về</Text>
        </TouchableOpacity>
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#f2f2f2",
  },
  scrollContent: {
    padding: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
  },
  section: {
    marginBottom: 20,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: "600",
    marginBottom: 8,
    color: "#333",
  },
  card: {
    backgroundColor: "#fff",
    borderRadius: 10,
    padding: 15,
    elevation: 2,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  cardTitle: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 6,
  },
  cardSub: {
    fontSize: 14,
    color: "#555",
    marginBottom: 4,
  },
  cardItem: {
    fontSize: 14,
    color: "#444",
    marginBottom: 4,
  },
  backButton: {
    backgroundColor: "#007bff",
    padding: 12,
    borderRadius: 10,
    alignItems: "center",
    marginTop: 30,
  },
  backButtonText: {
    color: "#fff",
    fontSize: 16,
    fontWeight: "600",
  },
});

export default EventDetail;
