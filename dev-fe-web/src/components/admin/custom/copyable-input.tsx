import {  Copy } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { toast } from "sonner";

interface CopyableInputProps {
  id: string;
  label: string;
  value: string;
  readOnly?: boolean;
  onChange?: (val: string) => void;

}

const CopyableInput: React.FC<CopyableInputProps> = ({
  id,
  label,
  value,
  readOnly = false,
  onChange
}) => {
  const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(value);
      toast.success("Đã sao chép vào clipboard!");
    } catch (err) {
      toast.error("Lỗi khi sao chép!");
    }
  };

  return (
    <div>
      <Label htmlFor={id}>{label}</Label>
      <div className="relative">
        <Input
          id={id}
          value={value}
          readOnly={readOnly}
          className="pr-12 cursor-default bg-gray-100"
          // onChange={(e) => onChange?.(e.target.value)}
        />
        <Copy 
          onClick={handleCopy}
          className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700 cursor-pointer"
          size={18} // Điều chỉnh kích thước nếu cần
        />
      </div>
    </div>
  );
};

export default CopyableInput;
