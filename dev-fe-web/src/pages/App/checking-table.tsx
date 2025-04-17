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
                    <div className="flex gap-4 items-center">
                      <div className="flex flex-col">
                        <label className="text-xs text-gray-500">Bắt đầu</label>
                        <input
                          type="time"
                          className="border rounded px-2 py-1 bg-transparent"
                          min={minTime}
                          max={maxTime}
                          step="60"
                          defaultValue=""
                          onChange={(e) =>
                            handleTimeChange(
                              item.date,
                              item.optimalRoomId,
                              "startTime",
                              e.target.value
                            )
                          }
                        />
                      </div>
                      <div className="flex flex-col">
                        <label className="text-xs text-gray-500">
                          Kết thúc
                        </label>
                        <input
                          type="time"
                          className="border rounded px-2 py-1 bg-transparent"
                          min={minTime}
                          max={maxTime}
                          step="60"
                          defaultValue=""
                          onChange={(e) =>
                            handleTimeChange(
                              item.date,
                              item.optimalRoomId,
                              "endTime",
                              e.target.value
                            )
                          }
                        />
                      </div>
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
