import PullToRefresh from "@/components/pull-to-refresh";
import { View, Text, StyleSheet } from "react-native";

const groups = [
  { id: "1", name: "Phòng nhân sự" },
  { id: "2", name: "Nhóm sự kiện" },
  { id: "3", name: "Nhân viên xuất sắc" },
  { id: "4", name: "Đối tác kinh doanh" },
];

// Hàm tạo chữ viết tắt từ tên nhóm
const getInitials = (name: string) => {
  const words = name.split(" ");
  return words.length > 1
    ? (words[0][0] + words[1][0]).toUpperCase() // Lấy chữ cái đầu của 2 từ đầu tiên
    : words[0].substring(0, 2).toUpperCase(); // Nếu chỉ có 1 từ, lấy 2 chữ cái đầu
};

export default function GroupScreen() {
  return (
    <PullToRefresh
      data={groups}
      renderItem={({ item }) => (
        <View style={styles.groupItem}>
          <View style={styles.avatar}>
            <Text style={styles.avatarText}>{getInitials(item.name)}</Text>
          </View>
          <Text style={styles.groupName}>{item.name}</Text>
        </View>
      )}
      keyExtractor={(item) => item.id}
    >
      <Text style={styles.title}>Danh sách nhóm</Text>
    </PullToRefresh>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    padding: 16,
  },
  title: {
    fontSize: 22,
    fontWeight: "bold",
    marginBottom: 12,
    textAlign: "center",
  },
  groupItem: {
    flexDirection: "row",
    alignItems: "center",
    padding: 12,
    backgroundColor: "#f0f0f0",
    borderRadius: 10,
    marginBottom: 10,
  },
  avatar: {
    width: 50,
    height: 50,
    borderRadius: 25,
    backgroundColor: "#007bff",
    justifyContent: "center",
    alignItems: "center",
    marginRight: 12,
  },
  avatarText: {
    fontSize: 18,
    fontWeight: "bold",
    color: "#fff",
  },
  groupName: {
    fontSize: 16,
    fontWeight: "500",
  },
});
