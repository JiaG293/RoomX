import React, { useEffect, useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

interface SuggestedTimeSlots {
  timeMorning?: string[];
  timeAfternoon?: string[];
}

interface ScheduleResultItem {
  date: string;
  hasConflict: boolean;
  optimalRoomId: string | null;
  suggestedTimeSlots?: SuggestedTimeSlots | null;
}

interface CheckingTableProps {
  data: ScheduleResultItem[];
}

const CheckingTable: React.FC<CheckingTableProps> = ({ data }) => {
  const [exceptions, setExceptions] = useState<any[]>([]);

  useEffect(() => {
    const stored = localStorage.getItem("dateRequestExceptions");
    if (stored) {
      setExceptions(JSON.parse(stored));
    }
  }, []);

  const handleTimeChange = (
    date: string,
    roomId: string | null,
    type: "startTime" | "endTime",
    value: string
  ) => {
    if (!roomId) return;

    setExceptions((prev) => {
      const existingIndex = prev.findIndex(
        (item) => item.date === date && item.roomId === roomId
      );

      let newData;
      if (existingIndex !== -1) {
        // Update existing
        const updated = [...prev];
        updated[existingIndex] = {
          ...updated[existingIndex],
          [type]: value,
        };
        newData = updated;
      } else {
        // Add new
        const newEntry = {
          roomId,
          date,
          startTime: type === "startTime" ? value : "",
          endTime: type === "endTime" ? value : "",
        };
        newData = [...prev, newEntry];
      }

      localStorage.setItem("dateRequestExceptions", JSON.stringify(newData));
      return newData;
    });
  };

  if (!data || data.length === 0) return null;

  const durationOptions = [30, 45, 60, 75, 90, 105, 120, 135, 150, 165, 180];

  function generateTimeOptions(): string[] {
    const options = [];
    for (let h = 7; h <= 22; h++) {
      for (let m = 0; m < 60; m += 15) {
        if (h === 22 && m > 0) break;
        const hh = h.toString().padStart(2, "0");
        const mm = m.toString().padStart(2, "0");
        options.push(`${hh}:${mm}`);
      }
    }
    return options;
  }

  function addMinutesToTime(time: string, minutes: number): string {
    const [h, m] = time.split(":").map(Number);
    const date = new Date();
    date.setHours(h, m + minutes, 0, 0);
    return date.toTimeString().slice(0, 5);
  }

  return (
    <div className="mt-4 overflow-x-auto">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Ngày</TableHead>
            <TableHead>Giờ gợi ý</TableHead>
            <TableHead>Chọn giờ</TableHead>
            <TableHead>Trạng thái</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {data.map((item, index) => {
            const suggestions = [
              ...(item.suggestedTimeSlots?.timeMorning || []),
              ...(item.suggestedTimeSlots?.timeAfternoon || []),
            ];

            const firstRange = suggestions[0];
            const [minTime, maxTime] = firstRange
              ? firstRange.split(" - ")
              : ["08:00", "17:00"];

            const current = exceptions.find(
              (e) => e.date === item.date && e.roomId === item.optimalRoomId
            );

            return (
              <TableRow key={index}>
                <TableCell>{item.date}</TableCell>

                {/* Giờ gợi ý */}
                <TableCell>
                  {item.hasConflict && suggestions.length > 0
                    ? suggestions.map((range, i) => (
                        <div key={i} className="mb-1 text-sm text-gray-700">
                          {range}
                        </div>
                      ))
                    : "-"}
                </TableCell>

                {/* Input chọn giờ */}
                <TableCell>
                  {item.hasConflict && suggestions.length > 0 ? (
                    <div className="flex gap-6 items-center">
                      <div className="flex gap-3 items-center">
                        <label className="text-xs text-gray-500">Bắt đầu</label>
                        <select
                          className="border rounded px-2 py-1 bg-transparent"
                          value={current?.startTime || ""}
                          onChange={(e) =>
                            handleTimeChange(
                              item.date,
                              item.optimalRoomId,
                              "startTime",
                              e.target.value
                            )
                          }
                        >
                          <option value="">-- chọn --</option>
                          {generateTimeOptions().map((time) => (
                            <option key={time} value={time}>
                              {time}
                            </option>
                          ))}
                        </select>
                      </div>

                      <div className="flex gap-3 items-center">
                        <label className="text-xs text-gray-500">
                          Thời lượng
                        </label>
                        <select
                          className="border rounded px-2 py-1 bg-transparent"
                          value={current?.duration || ""}
                          onChange={(e) => {
                            const duration = parseInt(e.target.value, 10);
                            const start = current?.startTime || "";
                            if (start) {
                              const endTime = addMinutesToTime(start, duration);
                              handleTimeChange(
                                item.date,
                                item.optimalRoomId,
                                "endTime",
                                endTime
                              );
                              setExceptions((prev) =>
                                prev.map((ex) =>
                                  ex.date === item.date &&
                                  ex.roomId === item.optimalRoomId
                                    ? { ...ex, duration }
                                    : ex
                                )
                              );
                            }
                          }}
                        >
                          <option value="">-- chọn --</option>
                          {durationOptions.map((d) => (
                            <option key={d} value={d}>
                              {d} phút
                            </option>
                          ))}
                        </select>
                      </div>

                      {/* {current?.endTime && (
                        <div className="text-xs text-gray-600 whitespace-nowrap">
                          <span className="font-medium">Giờ kết thúc: </span>
                          {current.endTime}
                        </div>
                      )} */}
                    </div>
                  ) : (
                    "-"
                  )}
                </TableCell>

                <TableCell
                  className={
                    item.hasConflict ? "text-red-500" : "text-green-500"
                  }
                >
                  {item.hasConflict ? "❌ Có xung đột" : "✅ Hợp lệ"}
                </TableCell>
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
    </div>
  );
};

export default CheckingTable;
