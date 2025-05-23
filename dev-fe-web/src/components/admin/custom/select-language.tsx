import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
} from "@/components/ui/select";
import { Languages } from "lucide-react";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";

interface LanguageSelectProps {
  position?: "bottom" | "right";
}

export const LanguageSelect: React.FC<LanguageSelectProps> = ({ position = "bottom" }) => {
  const {t, i18n } = useTranslation();
  const [language, setLanguage] = useState<string>(i18n.language || "en");
  useEffect(() => {
    const savedLanguage = localStorage.getItem("language");
    if (savedLanguage && savedLanguage !== i18n.language) {
      i18n.changeLanguage(savedLanguage);
      setLanguage(savedLanguage);
    }
  }, [i18n]);

  const handleChangeLanguage = (lang: string) => {
    i18n.changeLanguage(lang);
    localStorage.setItem("language", lang);
    setLanguage(lang);
  };

  return (
    <Select key={language} onValueChange={handleChangeLanguage} value={language}>
      <SelectTrigger
        className="p-2 bg-transparent border-none outline-none focus:ring-0 focus-visible:ring-0 flex items-center"
        aria-label="Select language"
      >
        {position === "bottom" ? (
          <Languages className="w-5 h-5 cursor-pointer" />
        ) : (
          <span className="text-sm font-normal -ml-2">
            {t("admin.menu.bottom.language")}
          </span>
        )}
      </SelectTrigger>

      <SelectContent side={position} className={position === "right" ? "ml-2" : ""}>
        <SelectItem value="vi">Tiếng Việt</SelectItem>
        <SelectItem value="en">English</SelectItem>
      </SelectContent>
    </Select>
  );
};
