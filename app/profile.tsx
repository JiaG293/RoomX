import React from 'react';
import { View, Text, StyleSheet, Image, TouchableOpacity, Alert } from 'react-native';
import Icon from 'react-native-vector-icons/Feather';
import { useRouter } from 'expo-router';

const ProfileScreen = () => {
  const router = useRouter();

  const handleEdit = () => {
    Alert.alert('Chỉnh sửa', 'Tính năng này đang được phát triển.');
  };

  const handleLogout = () => {
    Alert.alert('Đăng xuất', 'Bạn đã đăng xuất!');
    router.replace('/');
  };

  return (
    <View style={styles.container}>
      <Image
        source={{ uri: 'https://i.pravatar.cc/300?img=13' }}
        style={styles.avatar}
      />

      <Text style={styles.name}>Sang Huynh</Text>

      <View style={styles.infoContainer}>
        <InfoRow icon="mail" label="Email" value="sanghuynh@example.com" />
        <InfoRow icon="hash" label="Mã nhân viên" value="NV2025-001" />
        <InfoRow icon="phone" label="Số điện thoại" value="+84 123 456 789" />
        <InfoRow icon="map-pin" label="Địa chỉ" value="TP. Hồ Chí Minh, Việt Nam" />
        <InfoRow icon="users" label="Phòng ban" value="Phòng Công Nghệ Thông Tin" />
      </View>

      <TouchableOpacity style={styles.button} onPress={handleEdit}>
        <Text style={styles.buttonText}>Chỉnh sửa hồ sơ</Text>
      </TouchableOpacity>

      <TouchableOpacity style={[styles.button, styles.logoutButton]} onPress={handleLogout}>
        <Text style={styles.buttonText}>Đăng xuất</Text>
      </TouchableOpacity>
    </View>
  );
};

const InfoRow = ({ icon, label, value }: { icon: string; label: string; value: string }) => (
  <View style={styles.infoRow}>
    <Icon name={icon} size={20} color="#555" style={{ marginRight: 10, marginTop: 2 }} />
    <View>
      <Text style={styles.label}>{label}</Text>
      <Text style={styles.value}>{value}</Text>
    </View>
  </View>
);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 24,
    backgroundColor: '#f9f9f9',
    alignItems: 'center',
  },
  avatar: {
    width: 100,
    height: 100,
    borderRadius: 60,
    marginBottom: 12,
    borderWidth: 2,
    borderColor: '#ccc',
  },
  name: {
    fontSize: 22,
    fontWeight: '600',
    color: '#333',
  },
  infoContainer: {
    width: '100%',
    marginTop: 20,
    marginBottom: 30,
  },
  infoRow: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    marginBottom: 15,
  },
  label: {
    fontSize: 13,
    color: '#666',
  },
  value: {
    fontSize: 15,
    color: '#222',
    fontWeight: '500',
  },
  button: {
    width: '100%',
    paddingVertical: 12,
    borderRadius: 8,
    backgroundColor: '#007AFF',
    marginBottom: 12,
    alignItems: 'center',
  },
  logoutButton: {
    backgroundColor: '#FF3B30',
  },
  buttonText: {
    color: '#fff',
    fontWeight: '600',
    fontSize: 16,
  },
});

export default ProfileScreen;
