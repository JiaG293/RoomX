import React from "react";
import { View, Text, StyleSheet, TouchableOpacity, Switch } from "react-native";

export default function SettingsScreen() {
  const [isDarkMode, setIsDarkMode] = React.useState(false);

  // Hàm xử lý chuyển đổi chế độ tối/sáng
  const toggleDarkMode = () => {
    setIsDarkMode((prevMode) => !prevMode);
  };

  return (
    <View style={[styles.container, isDarkMode ? styles.darkBackground : styles.lightBackground]}>
      <Text style={[styles.text, isDarkMode ? styles.darkText : styles.lightText]}>⚙️ Cài đặt</Text>

      {/* Cài đặt tài khoản */}
      <View style={styles.settingItem}>
        <Text style={[styles.settingText, isDarkMode ? styles.darkText : styles.lightText]}>
          Tài khoản
        </Text>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>Chỉnh sửa</Text>
        </TouchableOpacity>
      </View>

      {/* Cài đặt thông báo */}
      <View style={styles.settingItem}>
        <Text style={[styles.settingText, isDarkMode ? styles.darkText : styles.lightText]}>
          Thông báo
        </Text>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>Quản lý</Text>
        </TouchableOpacity>
      </View>

      {/* Chuyển đổi chế độ tối/sáng */}
      <View style={styles.settingItem}>
        <Text style={[styles.settingText, isDarkMode ? styles.darkText : styles.lightText]}>
          Chế độ tối
        </Text>
        <Switch value={isDarkMode} onValueChange={toggleDarkMode} />
      </View>

      {/* Cài đặt ngôn ngữ */}
      <View style={styles.settingItem}>
        <Text style={[styles.settingText, isDarkMode ? styles.darkText : styles.lightText]}>
          Ngôn ngữ
        </Text>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>Chọn ngôn ngữ</Text>
        </TouchableOpacity>
      </View>

      {/* Cài đặt về ứng dụng */}
      <View style={styles.settingItem}>
        <Text style={[styles.settingText, isDarkMode ? styles.darkText : styles.lightText]}>
          Về ứng dụng
        </Text>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>Xem thông tin</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
  },
  lightBackground: {
    backgroundColor: "#f7f7f7",
  },
  darkBackground: {
    backgroundColor: "#333",
  },
  text: {
    fontSize: 28,
    fontWeight: "bold",
    marginBottom: 20,
  },
  lightText: {
    color: "#333",
  },
  darkText: {
    color: "#fff",
  },
  settingItem: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    marginVertical: 15,
    paddingVertical: 10,
    paddingHorizontal: 15,
    backgroundColor: "#fff",
    borderRadius: 10,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.1,
    shadowRadius: 6,
    elevation: 5,
  },
  settingText: {
    fontSize: 18,
    fontWeight: "500",
  },
  button: {
    backgroundColor: "#1890ff",
    paddingVertical: 8,
    paddingHorizontal: 16,
    borderRadius: 6,
  },
  buttonText: {
    color: "#fff",
    fontWeight: "bold",
  },
});
