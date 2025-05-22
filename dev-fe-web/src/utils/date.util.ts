export const formatDate = (date: Date): string => {
  const day = String(date.getDate()).padStart(2, "0"); // Đảm bảo ngày có 2 chữ số
  const month = String(date.getMonth() + 1).padStart(2, "0"); // Đảm bảo tháng có 2 chữ số
  const year = date.getFullYear();
  return `${day}-${month}-${year}`;
};
export function calculateEndTime(startTime: string, duration: number): string {
  const [hours, minutes] = startTime.split(":").map(Number);
  const startDate = new Date();
  startDate.setHours(hours, minutes, 0, 0);
  const endDate = new Date(startDate.getTime() + duration * 60000);

  const endHours = endDate.getHours().toString().padStart(2, "0");
  const endMinutes = endDate.getMinutes().toString().padStart(2, "0");
  console.log(startTime, "-", `${endHours}:${endMinutes}`);

  return `${endHours}:${endMinutes}`;
}

// kiểm giờ hành chính
export function isBookingTimeValid(
  startTime: string,
  endTime: string,
  officeStartTime: string,
  officeEndTime: string
) {
  const toMinutes = (time: string) => {
    const [h, m] = time.split(":").map(Number);
    return h * 60 + m;
  };

  const start = toMinutes(startTime);
  const end = toMinutes(endTime);
  const officeStart = toMinutes(officeStartTime);
  const officeEnd = toMinutes(officeEndTime);

  return start >= officeStart && end <= officeEnd;
}
