import { Moon, Sun } from "lucide-react";
import { useTheme } from "@/components/admin/custom/theme-provider";

interface ThemeToggleProps {
  variant?: "icon" | "switch"; // Kiểu hiển thị
}

export const ThemeToggle: React.FC<ThemeToggleProps> = ({ variant = "icon" }) => {
  const { theme, setTheme } = useTheme();

  const toggleTheme = () => {
    setTheme(theme === "dark" ? "light" : "dark");
  };

  return variant === "switch" ? (
    <div
      onClick={toggleTheme}
      className="flex items-center justify-between w-full py-1 cursor-pointer rounded-md hover:bg-gray-100 dark:hover:bg-gray-800"
    >
      <span>Chế độ tối</span>
      <div className="relative w-10 h-5 flex items-center bg-gray-300 dark:bg-gray-600 rounded-full p-1">
        <div
          className={`absolute bg-white w-4 h-4 rounded-full shadow-md ${
            theme === "dark" ? "translate-x-5" : "translate-x-0"
          }`}
        />
      </div>
    </div>
  ) : (
    <div onClick={toggleTheme} className="cursor-pointer flex justify-center items-center">
      {theme === "dark" ? <Sun className="w-5 h-5" /> : <Moon className="w-5 h-5" />}
    </div>
  );
};
