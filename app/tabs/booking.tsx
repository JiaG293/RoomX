import React, { useEffect, useState } from "react";
import {
  View,
  StyleSheet,
  TouchableOpacity,
  ScrollView,
  FlatList,
  Alert,
} from "react-native";
import { Picker } from "@react-native-picker/picker";
import {
  Chip,
  Card,
  TextInput as PaperInput,
  Button,
  Text,
  useTheme,
} from "react-native-paper";
import { Ionicons } from "@expo/vector-icons";
import DateTimePicker from "@react-native-community/datetimepicker";
import {
  bookSchedule,
  getAllBranches,
  getListRoomsByBranchId,
} from "@/services/place.service";

type TagSelectorProps = {
  label: string;
  options: string[];
  selectedValues: string[];
  onAdd: (value: string) => void;
  onRemove: (value: string) => void;
};

const iconColors = [
  "#EF5350",
  "#AB47BC",
  "#42A5F5",
  "#26A69A",
  "#FFCA28",
  "#8D6E63",
];

const TagSelector: React.FC<TagSelectorProps> = ({
  label,
  options,
  selectedValues,
  onAdd,
  onRemove,
}) => {
  const [searchTerm, setSearchTerm] = useState("");
  const filteredOptions = options.filter(
    (option) =>
      option.toLowerCase().includes(searchTerm.toLowerCase()) &&
      !selectedValues.includes(option)
  );
  const theme = useTheme();

  return (
    <Card style={styles.card}>
      <Card.Title
        title={label}
        left={(props) => (
          <Ionicons
            name="list"
            size={props.size}
            color={theme.colors.primary}
            style={styles.cardIcon}
          />
        )}
      />
      <Card.Content>
        <PaperInput
          mode="outlined"
          placeholder={`Tìm kiếm ${label.toLowerCase()}`}
          value={searchTerm}
          onChangeText={setSearchTerm}
          left={<PaperInput.Icon icon="magnify" />}
          style={styles.searchInput}
        />
        {searchTerm.trim() !== "" && (
          <FlatList
            data={filteredOptions.slice(0, 5)}
            horizontal
            keyExtractor={(item) => item}
            renderItem={({ item }) => (
              <TouchableOpacity
                onPress={() => {
                  onAdd(item);
                  setSearchTerm("");
                }}
              >
                <Chip style={styles.addTagChip} icon="plus">
                  {item}
                </Chip>
              </TouchableOpacity>
            )}
            showsHorizontalScrollIndicator={false}
          />
        )}
        <View style={styles.selectedTags}>
          {selectedValues.map((item, index) => (
            <Chip
              key={item}
              icon={() => (
                <Ionicons
                  name="checkmark-circle"
                  size={18}
                  color={iconColors[index % iconColors.length]}
                />
              )}
              onClose={() => onRemove(item)}
              style={[
                styles.chip,
                {
                  backgroundColor: `${iconColors[index % iconColors.length]}33`,
                },
              ]}
              textStyle={{ color: iconColors[index % iconColors.length] }}
            >
              {item}
            </Chip>
          ))}
        </View>
      </Card.Content>
    </Card>
  );
};

export default function BookingScreen() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [room, setRoom] = useState<string>("");
  const [branch, setBranch] = useState("");
  const [scheduleType, setScheduleType] = useState("");
  const [capacity, setCapacity] = useState("");
  const [startDate, setStartDate] = useState(new Date());
  const [startTime, setStartTime] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [pickerMode, setPickerMode] = useState<
    "startDate" | "endDate" | "startTime" | "endTime" | null
  >(null);

  const theme = useTheme();

  const [branches, setBranches] = useState<{ id: string; name: string }[]>([]);
  const [rooms, setRooms] = useState<{ id: string; name: string }[]>([]);

  useEffect(() => {
    const fetchBranches = async () => {
      try {
        const data = await getAllBranches();
        setBranches(data);
      } catch (error) {}
    };

    fetchBranches();
  }, []);

  const handleBranchChange = async (branchId: string) => {
    setBranch(branchId);
    setRoom(""); // Reset phòng khi đổi chi nhánh

    if (branchId) {
      try {
        const roomsData = await getListRoomsByBranchId(branchId);
        console.log(roomsData.content); // Mảng phòng thật sự
        setRooms(
          roomsData.content.map((r: any) => ({ id: r.id, name: r.roomCode }))
        );
      } catch (error) {
        console.error("Lỗi lấy phòng:", error);
      }
    } else {
      setRooms([]); // Nếu chưa chọn chi nhánh
    }
  };

  const participantsList = [
    "user006.roomx@gmail.com",
    "user005.roomx@gmail.com",
    "user004.roomx@gmail.com",
    "user002.roomx@gmail.com",
    "user001.roomx@gmail.com",
    "sang@gmail.com",
    "sang2002@gmail.com",
    "puss@gmail.com",
    "nvga2k2111@gmail.com",
    "nvg2k2@gmail.com",
    "nvg2k21@gmail.com",
    "nvg2k21111111@gmail.com",
    "hoangvanthu@gmail.com",
    "giau1@gmail.com",
    "asgy2002@gmail.com",
    "admin1@example.com",
    "abcxyz@gmail.com",
    "821377326.jiag@gmail.com",
  ];

  // Tag selectors (giữ nguyên)
  const [service, setService] = useState<string[]>([]);
  const [device, setDevice] = useState<string[]>([]);
  const [participants, setParticipants] = useState<string[]>([]);

  const handleAddTag = (
    value: string,
    type: "service" | "device" | "participants"
  ) => {
    if (type === "service" && !service.includes(value)) {
      setService([...service, value]);
    } else if (type === "device" && !device.includes(value)) {
      setDevice([...device, value]);
    } else if (type === "participants" && !participants.includes(value)) {
      setParticipants([...participants, value]);
    }
  };

  const handleRemoveTag = (
    value: string,
    type: "service" | "device" | "participants"
  ) => {
    if (type === "service") {
      setService(service.filter((item) => item !== value));
    } else if (type === "device") {
      setDevice(device.filter((item) => item !== value));
    } else if (type === "participants") {
      setParticipants(participants.filter((item) => item !== value));
    }
  };

  // Hàm kiểm tra hợp lệ dữ liệu đặt lịch, trả về lỗi nếu có
  const validateBooking = () => {
    if (!title.trim()) return "Tiêu đề không được để trống.";
    if (!branch) return "Bạn chưa chọn chi nhánh.";
    if (!room) return "Bạn chưa chọn phòng.";
    if (!scheduleType) return "Bạn chưa chọn loại lịch.";
    // Kết hợp ngày giờ

    const combinedStart = new Date(
      startDate.getFullYear(),
      startDate.getMonth(),
      startDate.getDate(),
      startTime.getHours(),
      startTime.getMinutes()
    );
    const combinedEnd = new Date(
      endDate.getFullYear(),
      endDate.getMonth(),
      endDate.getDate(),
      endTime.getHours(),
      endTime.getMinutes()
    );

    if (combinedStart >= combinedEnd)
      return "Thời gian bắt đầu phải trước thời gian kết thúc.";
    const diffMs = combinedEnd.getTime() - combinedStart.getTime();
    const diffMinutes = diffMs / (1000 * 60);
    if (diffMinutes < 30) return "Cuộc họp phải kéo dài ít nhất 30 phút.";
    return null;
  };

  const formatDate = (date: Date) => {
    // YYYY-MM-DD
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, "0");
    const day = date.getDate().toString().padStart(2, "0");
    return `${year}-${month}-${day}`;
  };

  const formatTime = (date: Date) => {
    // HH:mm
    const hours = date.getHours().toString().padStart(2, "0");
    const minutes = date.getMinutes().toString().padStart(2, "0");
    return `${hours}:${minutes}`;
  };

  const resetBookingForm = () => {
    setTitle("");
    setDescription("");
    setBranch("");
    setRoom("");
    setScheduleType("");
    setStartDate(new Date());
    setEndDate(new Date());
    setStartTime(new Date());
    setEndTime(new Date());
    setParticipants([]);
  };

  const handleBooking = async () => {
    const errorMsg = validateBooking();
    if (errorMsg) {
      Alert.alert("Lỗi đặt lịch", errorMsg);
      return;
    }
    const daysOfWeek = "MO,TU,WE,TH,FR,SA,SU";

    const bookingDetails = {
      title,
      priority: 0,
      description,
      roomId: room,
      branchId: branch,
      recurrenceType: scheduleType,
      startDate: formatDate(startDate),
      endDate: formatDate(endDate),
      startTime: formatTime(startTime),
      endTime: formatTime(endTime),
      daysOfWeek,
      participants,
    };

    try {
      const result = await bookSchedule(bookingDetails);
      console.log("Đặt lịch thành công với thông tin:", result);
      Alert.alert("Đặt lịch", "Đặt lịch thành công!");
      resetBookingForm();
    } catch (error) {
      console.error("Lỗi khi đặt lịch:", error);
      Alert.alert("Lỗi", "Có lỗi xảy ra khi đặt lịch, vui lòng thử lại.");
    }
  };

  return (
    <ScrollView style={styles.container}>
      <PaperInput
        label="Tiêu đề"
        mode="outlined"
        value={title}
        onChangeText={setTitle}
        style={styles.input}
        left={<PaperInput.Icon icon="text" color="#FF5722" />} // cam nóng
      />
      <PaperInput
        label="Mô tả"
        mode="outlined"
        value={description}
        onChangeText={setDescription}
        style={styles.input}
        multiline
        numberOfLines={3}
        left={<PaperInput.Icon icon="file-document-outline" color="#6200ee" />} // tím chủ đạo
      />

      {/* Chi nhánh */}
      <View
        style={{
          flexDirection: "row",
          alignItems: "center",
          borderWidth: 1,
          borderColor: "#ccc",
          borderRadius: 8,
          paddingHorizontal: 12,
          height: 50,
          marginBottom: 12,
          backgroundColor: "white",
        }}
      >
        <Ionicons
          name="business"
          size={24}
          color="#1E88E5" // xanh dương đậm hơn
          style={{ marginRight: 8 }}
        />
        <Picker
          selectedValue={branch}
          onValueChange={handleBranchChange}
          style={{ flex: 1, height: 50 }}
          itemStyle={{ fontSize: 16, height: 50, color: "#000" }}
        >
          <Picker.Item label="Chọn chi nhánh" value="" />
          {branches.map((b) => (
            <Picker.Item key={b.id} label={b.name} value={b.id} />
          ))}
        </Picker>
      </View>

      {/* Phòng */}
      <View
        style={{
          flexDirection: "row",
          alignItems: "center",
          borderWidth: 1,
          borderColor: "#ccc",
          borderRadius: 8,
          paddingHorizontal: 12,
          height: 50,
          marginBottom: 12,
          backgroundColor: "white",
        }}
      >
        <Ionicons
          name="home"
          size={24}
          color="#43A047" // xanh lá tươi
          style={{ marginRight: 8 }}
        />
        <Picker
          selectedValue={room}
          onValueChange={(value) => {
            console.log("Phòng được chọn:", value);
            setRoom(value);
          }}
          style={{ flex: 1, height: 50 }}
          itemStyle={{ fontSize: 16, height: 50, color: "#000" }}
        >
          <Picker.Item label="Chọn phòng" value="" />
          {rooms.map((r) => (
            <Picker.Item key={r.id} label={r.name} value={r.id} />
          ))}
        </Picker>
      </View>

      {/* Loại lịch */}
      <View
        style={{
          flexDirection: "row",
          alignItems: "center",
          borderWidth: 1,
          borderColor: "#ccc",
          borderRadius: 8,
          paddingHorizontal: 12,
          height: 50,
          marginBottom: 12,
          backgroundColor: "white",
        }}
      >
        <Ionicons
          name="refresh" // icon giống nút refresh
          size={24}
          color="#F4511E" // màu cam đỏ nổi bật
          style={{ marginRight: 8 }}
        />
        <Picker
          selectedValue={scheduleType}
          onValueChange={setScheduleType}
          style={{ flex: 1, height: 60 }}
          itemStyle={{ fontSize: 16, height: 50, color: "#000" }}
        >
          <Picker.Item label="Chọn loại lịch" value="" />
          <Picker.Item label="Ngày" value="DAILY" />
          <Picker.Item label="Tuần" value="WEEKLY" />
          <Picker.Item label="Tháng" value="MONTHLY" />
        </Picker>
      </View>

      {/* Nhóm NGÀY bắt đầu và kết thúc */}
      <Text style={styles.sectionTitle}>Ngày bắt đầu - kết thúc</Text>
      <View style={styles.datetimeRow}>
        <Button
          mode="outlined"
          icon="calendar" // icon calendar như nút refresh kia
          onPress={() => setPickerMode("startDate")}
          style={styles.datetimeButton}
          textColor="#F4511E" // đồng màu icon
        >
          {startDate.toLocaleDateString()}
        </Button>
        <Button
          mode="outlined"
          icon="calendar"
          onPress={() => setPickerMode("endDate")}
          style={styles.datetimeButton}
          textColor="#F4511E"
        >
          {endDate.toLocaleDateString()}
        </Button>
      </View>

      {/* Nhóm GIỜ bắt đầu và kết thúc */}
      <Text style={styles.sectionTitle}>Giờ bắt đầu - kết thúc</Text>
      <View style={styles.datetimeRow}>
        <Button
          mode="outlined"
          icon="clock"
          onPress={() => setPickerMode("startTime")}
          style={styles.datetimeButton}
          textColor="#43A047" // đồng màu với icon home (phòng)
        >
          {startTime.toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
          })}
        </Button>
        <Button
          mode="outlined"
          icon="clock"
          onPress={() => setPickerMode("endTime")}
          style={styles.datetimeButton}
          textColor="#43A047"
        >
          {endTime.toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
          })}
        </Button>
      </View>

      {pickerMode && (
        <DateTimePicker
          value={
            pickerMode === "startDate"
              ? startDate
              : pickerMode === "startTime"
              ? startTime
              : pickerMode === "endDate"
              ? endDate
              : endTime
          }
          mode={pickerMode.includes("Time") ? "time" : "date"}
          is24Hour={true}
          display="default"
          onChange={(event, selectedDate) => {
            if (selectedDate) {
              if (pickerMode === "startDate") setStartDate(selectedDate);
              if (pickerMode === "startTime") setStartTime(selectedDate);
              if (pickerMode === "endDate") setEndDate(selectedDate);
              if (pickerMode === "endTime") setEndTime(selectedDate);
            }
            setPickerMode(null);
          }}
        />
      )}
      <TagSelector
        label="Người tham gia"
        options={participantsList}
        selectedValues={participants}
        onAdd={(val) => handleAddTag(val, "participants")}
        onRemove={(val) => handleRemoveTag(val, "participants")}
      />

      <Button
        mode="contained"
        onPress={handleBooking}
        icon="calendar" // icon lịch
        style={{
          marginVertical: 5,
          borderRadius: 10,
          paddingVertical: 5,
          backgroundColor: "#007bff", // xanh dương chuẩn
          elevation: 4,
          shadowColor: "#000",
          shadowOffset: { width: 0, height: 2 },
          shadowOpacity: 0.25,
          shadowRadius: 3.84,
        }}
        labelStyle={{
          color: "white",
          fontWeight: "700",
          fontSize: 16,
        }}
      >
        Đặt lịch
      </Button>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingHorizontal: 16, // tăng padding cho rộng hơn
    backgroundColor: "#fff",
  },
  header: {
    fontSize: 30, // tăng cỡ chữ header
    marginVertical: 20, // tăng margin trên dưới
    fontWeight: "700", // đậm hơn một chút
    textAlign: "center",
    color: "#333", // màu chữ đậm hơn
  },
  input: {
    marginBottom: 16, // tăng khoảng cách giữa các input
    backgroundColor: "#f9f9f9", // thêm màu nền nhẹ cho input
  },
  card: {
    marginBottom: 16, // tăng margin bottom
    elevation: 3, // tạo shadow nhẹ (Android)
    shadowColor: "#000", // shadow cho iOS
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  pickerWrapper: {
    borderWidth: 1,
    borderColor: "#bbb", // màu viền đậm hơn chút cho rõ
    borderRadius: 8, // bo góc lớn hơn
    overflow: "hidden",
    backgroundColor: "#fafafa", // màu nền nhẹ cho picker
  },
  picker: {
    height: 50, // cao hơn picker một chút
    width: "100%",
  },
  cardIcon: {
    marginRight: 8,
  },
  sectionTitle: {
    fontWeight: "700", // đậm hơn
    fontSize: 16, // lớn hơn
    marginBottom: 10, // cách dưới rộng hơn
    color: "#333",
  },
  datetimeRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginBottom: 12,
  },
  datetimeButton: {
    flex: 1,
    marginHorizontal: 4,
  },
  datetimeValue: {
    marginTop: 6,
    fontWeight: "700",
    fontSize: 16,
    color: "#222",
  },
  searchInput: {
    marginBottom: 12,
  },
  addTagChip: {
    marginRight: 8,
  },
  selectedTags: {
    flexDirection: "row",
    flexWrap: "wrap",
    marginTop: 12,
  },
  chip: {
    marginRight: 8,
    marginBottom: 8,
  },
});
