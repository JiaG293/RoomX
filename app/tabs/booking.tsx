import React, { useState } from "react";
import {
  View,
  StyleSheet,
  TouchableOpacity,
  ScrollView,
  FlatList,
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
import DatePicker from "react-native-date-picker";
import { Ionicons } from "@expo/vector-icons";

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
  const [room, setRoom] = useState("");
  const [branch, setBranch] = useState("");
  const [scheduleType, setScheduleType] = useState("day");
  const [capacity, setCapacity] = useState("");
  const [startDate, setStartDate] = useState(new Date());
  const [startTime, setStartTime] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [pickerMode, setPickerMode] = useState<
    "startDate" | "endDate" | "startTime" | "endTime" | null
  >(null);

  const theme = useTheme();

  const branches = ["Chi nhánh A", "Chi nhánh B", "Chi nhánh C"];
  const rooms = ["Phòng 101", "Phòng 102", "Phòng 201", "Phòng 202"];
  const services = [
    "Dịch vụ 1",
    "Dịch vụ 2",
    "Dịch vụ 3",
    "Dịch vụ 4",
    "Dịch vụ 5",
    "Dịch vụ 6",
  ];
  const devices = [
    "Thiết bị 1",
    "Thiết bị 2",
    "Thiết bị 3",
    "Thiết bị 4",
    "Thiết bị 5",
    "Thiết bị 6",
  ];
  const participantsList = [
    "Người tham gia 1",
    "Người tham gia 2",
    "Người tham gia 3",
    "Người tham gia 4",
    "Người tham gia 5",
    "Người tham gia 6",
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

  const handleSubmit = () => {
    // Gộp ngày + giờ lại cho start/end datetime chuẩn
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

    const bookingDetails = {
      title,
      description,
      branch,
      room,
      scheduleType,
      capacity,
      startDate: combinedStart,
      endDate: combinedEnd,
      // Các trường khác nếu có
    };
    console.log(bookingDetails);
  };

  return (
    <ScrollView style={styles.container}>
      <Text style={styles.header}>Đặt Lịch</Text>

      <PaperInput
        label="Tiêu đề"
        mode="outlined"
        value={title}
        onChangeText={setTitle}
        style={styles.input}
        left={<PaperInput.Icon icon="text" />}
      />
      <PaperInput
        label="Mô tả"
        mode="outlined"
        value={description}
        onChangeText={setDescription}
        style={styles.input}
        multiline
        numberOfLines={3}
        left={<PaperInput.Icon icon="file-document-outline" />}
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
          color={theme.colors.primary}
          style={{ marginRight: 8 }}
        />
        <Picker
          selectedValue={branch}
          onValueChange={setBranch}
          style={{ flex: 1, height: 50 }}
          itemStyle={{ fontSize: 16, height: 50, color: "#000" }}
        >
          <Picker.Item label="Chọn chi nhánh" value="" />
          {branches.map((b) => (
            <Picker.Item key={b} label={b} value={b} />
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
          color={theme.colors.primary}
          style={{ marginRight: 8 }}
        />
        <Picker
          selectedValue={room}
          onValueChange={setRoom}
          style={{ flex: 1, height: 60 }}
          itemStyle={{ fontSize: 16, height: 50, color: "#000" }}
        >
          <Picker.Item label="Chọn phòng" value="" />
          {rooms.map((r) => (
            <Picker.Item key={r} label={r} value={r} />
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
          name="calendar"
          size={24}
          color={theme.colors.primary}
          style={{ marginRight: 8 }}
        />
        <Picker
          selectedValue={scheduleType}
          onValueChange={setScheduleType}
          style={{ flex: 1, height: 60 }}
          itemStyle={{ fontSize: 16, height: 50, color: "#000" }}
        >
          <Picker.Item label="Ngày" value="day" />
          <Picker.Item label="Tuần" value="week" />
          <Picker.Item label="Tháng" value="month" />
          <Picker.Item label="Năm" value="year" />
          <Picker.Item label="Tùy chỉnh" value="custom" />
        </Picker>
      </View>

      {/* Ngày và giờ bắt đầu */}
      <Text style={styles.sectionTitle}>Thời gian bắt đầu</Text>
      <View style={styles.datetimeRow}>
        <TouchableOpacity
          onPress={() => setPickerMode("startDate")}
          style={styles.datetimeButton}
        >
          <Text>Bắt đầu ngày</Text>
          <Text style={styles.datetimeValue}>
            {startDate.toLocaleDateString()}
          </Text>
        </TouchableOpacity>
        <TouchableOpacity
          onPress={() => setPickerMode("startTime")}
          style={styles.datetimeButton}
        >
          <Text>Bắt đầu giờ</Text>
          <Text style={styles.datetimeValue}>
            {startTime.toLocaleTimeString([], {
              hour: "2-digit",
              minute: "2-digit",
            })}
          </Text>
        </TouchableOpacity>
      </View>

      {/* Ngày và giờ kết thúc */}
      <Text style={styles.sectionTitle}>Thời gian kết thúc</Text>
      <View style={styles.datetimeRow}>
        <TouchableOpacity
          onPress={() => setPickerMode("endDate")}
          style={styles.datetimeButton}
        >
          <Text>Kết thúc ngày</Text>
          <Text style={styles.datetimeValue}>
            {endDate.toLocaleDateString()}
          </Text>
        </TouchableOpacity>
        <TouchableOpacity
          onPress={() => setPickerMode("endTime")}
          style={styles.datetimeButton}
        >
          <Text>Kết thúc giờ</Text>
          <Text style={styles.datetimeValue}>
            {endTime.toLocaleTimeString([], {
              hour: "2-digit",
              minute: "2-digit",
            })}
          </Text>
        </TouchableOpacity>
      </View>

      {/* DatePicker modal */}
      <DatePicker
        modal
        open={pickerMode !== null}
        date={
          pickerMode === "startDate"
            ? startDate
            : pickerMode === "endDate"
            ? endDate
            : pickerMode === "startTime"
            ? startTime
            : endTime
        }
        mode={pickerMode?.includes("Date") ? "date" : "time"}
        onConfirm={(date) => {
          if (pickerMode === "startDate") setStartDate(date);
          else if (pickerMode === "endDate") setEndDate(date);
          else if (pickerMode === "startTime") setStartTime(date);
          else if (pickerMode === "endTime") setEndTime(date);
          setPickerMode(null);
        }}
        onCancel={() => setPickerMode(null)}
      />

      {/* Các tag selectors */}
      <TagSelector
        label="Dịch vụ"
        options={services}
        selectedValues={service}
        onAdd={(val) => handleAddTag(val, "service")}
        onRemove={(val) => handleRemoveTag(val, "service")}
      />
      <TagSelector
        label="Thiết bị"
        options={devices}
        selectedValues={device}
        onAdd={(val) => handleAddTag(val, "device")}
        onRemove={(val) => handleRemoveTag(val, "device")}
      />
      <TagSelector
        label="Người tham gia"
        options={participantsList}
        selectedValues={participants}
        onAdd={(val) => handleAddTag(val, "participants")}
        onRemove={(val) => handleRemoveTag(val, "participants")}
      />

      <Button
        mode="contained"
        onPress={handleSubmit}
        style={{ marginVertical: 24 }}
      >
        Xác nhận
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
    fontSize: 18, // lớn hơn
    marginTop: 24, // cách trên rộng hơn
    marginBottom: 12, // cách dưới rộng hơn
    color: "#444",
  },
  datetimeRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    marginBottom: 16,
  },
  datetimeButton: {
    flex: 1,
    borderWidth: 1,
    borderColor: "#aaa", // màu viền đậm hơn để rõ ràng
    borderRadius: 8,
    paddingVertical: 14, // tăng padding dọc
    marginHorizontal: 6, // tăng margin ngang cho button rộng rãi hơn
    alignItems: "center",
    backgroundColor: "#f0f0f0", // nền nhẹ cho nút chọn ngày giờ
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
