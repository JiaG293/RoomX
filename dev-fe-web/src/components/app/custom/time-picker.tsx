"use client";

import { useEffect, useState } from "react";

// Tạo mốc giờ mỗi 15 phút (24 giờ format)
function generateTimes(): string[] {
  const times: string[] = [];
  for (let h = 0; h < 24; h++) {
    for (let m of [0, 15, 30, 45]) {
      const hour = h.toString().padStart(2, "0");
      const minute = m.toString().padStart(2, "0");
      times.push(`${hour}:${minute}`);
    }
  }
  return times;
}

// Lấy mốc thời gian gần nhất hiện tại (đã làm tròn theo 15 phút)
function getClosestTime(): string {
  const now = new Date();
  let h = now.getHours();
  let m = now.getMinutes();

  const remainder = m % 15;
  if (remainder !== 0) {
    m += 15 - remainder;
  }

  if (m === 60) {
    m = 0;
    h += 1;
  }

  h = h % 24;

  return `${h.toString().padStart(2, "0")}:${m.toString().padStart(2, "0")}`;
}

const timeOptions = generateTimes();

interface TimePickerProps {
  value?: string;
  onChange?: (value: string) => void;
}

export function TimePicker({ value, onChange }: TimePickerProps) {
  const [internalValue, setInternalValue] = useState("");

  useEffect(() => {
    if (!value) {
      const closest = getClosestTime();
      setInternalValue(closest);
      onChange?.(closest); // đồng bộ với cha
    }
  }, [value, onChange]);

  const selectedValue = value ?? internalValue;

  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const newValue = e.target.value;
    if (!value) setInternalValue(newValue); // nếu không controlled từ cha thì lưu local
    onChange?.(newValue);
  };

  return (
    <div className="flex flex-col gap-2">
      <select
        id="time-select"
        value={selectedValue}
        onChange={handleChange}
        className="border rounded-md px-3 py-2 w-[140px] bg-transparent"
      >
        {timeOptions.map((time) => (
          <option key={time} value={time}>
            {time}
          </option>
        ))}
      </select>
    </div>
  );
}
