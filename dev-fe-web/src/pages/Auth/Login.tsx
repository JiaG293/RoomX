import { LanguageSelect } from "@/components/admin/custom/select-language";
import { ThemeToggle } from "@/components/admin/custom/theme-toggle";
import LoginForm from "@/components/login/login-form";
import { useTranslation } from "react-i18next";

const Login: React.FC = () => {
  const { t } = useTranslation();

  return (
    /* Container */
    <div className="flex min-h-screen justify-center items-center relative bg-[url('/src/assets/img/login_bg.jpg')] bg-cover bg-center">
      {/* Container-overlay */}
      <div
        style={{ backgroundColor: "hsl(var(--background))" }}
        className="absolute top-0 left-0 w-full h-full opacity-50 blur-lg"
      ></div>

      {/* Group icons in top right corner */}
      <div className="absolute top-4 right-4 z-20 flex gap-4">
        <ThemeToggle />
        <LanguageSelect />
      </div>

      {/* Container-content */}
      <div className="z-10 w-full flex flex-col items-center">
        <br />
        <LoginForm />
        {/* Footer */}
        <div className="mt-6 text-center text-md text-[var(--foreground)]">
          <p>{t("auth.login.footer_note")}</p>
        </div>
      </div>
    </div>
  );
};

export default Login;
