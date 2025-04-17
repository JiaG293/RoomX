const formatDate = (date: Date): string => {
    const day = String(date.getDate()).padStart(2, '0'); // Đảm bảo ngày có 2 chữ số
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Đảm bảo tháng có 2 chữ số
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
  };
  
  export default formatDate;  