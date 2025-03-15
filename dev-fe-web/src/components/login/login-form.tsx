import { useState } from "react";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { useAuth } from "@/context/AuthProvider";

const LoginForm = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { t } = useTranslation();
  const { login } = useAuth();
  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const userInfo = await login(username, password);

      console.log("User info:", userInfo);

      if (userInfo?.roles?.includes("realm-management")) {
        window.location.href = "/admin/home";
      } else {
        window.location.href = "/portal/home";
      }
    } catch (error) {
      console.error("Login failed", error);
    }
  };

  return (
    <div className="relative z-10 shadow-lg w-full max-w-6xl min-h-[600px] flex flex-col md:flex-row">
      {/* Left side */}
      <div className="w-full h-60 md:h-auto md:w-1/2 bg-[url('/src/assets/img/login_bg.jpg')] bg-cover relative flex items-center justify-center">
        <div className="absolute top-0 left-0 w-full h-full bg-black opacity-60"></div>
        <div className="z-10 text-center text-white">
          <h1
            className="text-5xl font-mono"
            style={{ fontFamily: "Monoton, cursive" }}
          >
            Room X
          </h1>
          <h4 className="text-lg mt-4">{t("label_slogan")}</h4>
        </div>
      </div>

      {/* Right side */}
      <div
        style={{ backgroundColor: "hsl(var(--background))" }}
        className="w-full md:w-1/2 flex flex-col justify-center items-center px-6 py-8 md:px-12 "
      >
        <h2 className="text-2xl font-bold text-[-var(--foreground)] mb-6">
          {t("button_dang_nhap")}
        </h2>
        <form className="w-full" onSubmit={handleLogin}>
          <div className="mb-6">
            <label
              htmlFor="account"
              className="block text-[var(--foreground)] font-medium mb-2"
            >
              {t("label_tai_khoan")}
            </label>
            <input
              type="text"
              id="account"
              className="w-full border-b-2 border-gray-300 p-3 focus:outline-none focus:border-blue-500 transition duration-300 bg-transparent rounded-md"
              placeholder={t("placeholder_tai_khoan")}
              required
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              onInvalid={(e) =>
                (e.target as HTMLInputElement).setCustomValidity(
                  t("warning_nhap_tai_khoan")
                )
              }
              onInput={(e) =>
                (e.target as HTMLInputElement).setCustomValidity("")
              }
            />
          </div>
          <div className="mb-6 relative">
            <label
              htmlFor="password"
              className="block text-[var(--foreground)] font-medium mb-2"
            >
              {t("label_mat_khau")}
            </label>
            <div className="relative">
              <input
                type={showPassword ? "text" : "password"}
                id="password"
                className="w-full border-b-2 border-gray-300 p-3 pr-10 focus:outline-none focus:border-blue-500 transition duration-300 bg-transparent rounded-md"
                placeholder={t("placeholder_mat_khau")}
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                onInvalid={(e) =>
                  (e.target as HTMLInputElement).setCustomValidity(
                    t("warning_nhap_mat_khau")
                  )
                }
                onInput={(e) =>
                  (e.target as HTMLInputElement).setCustomValidity("")
                }
              />
              <div
                onClick={togglePasswordVisibility}
                className="absolute top-1/2 right-3 transform -translate-y-1/2 cursor-pointer"
                aria-label={showPassword ? "Hide password" : "Show password"}
              >
                {showPassword ? <FaEyeSlash size={20} /> : <FaEye size={20} />}
              </div>
            </div>
          </div>
          <button
            type="submit"
            className="w-full bg-primary text-primary-foreground font-bold py-3 rounded-lg"
          >
            {t("button_dang_nhap")}
          </button>
        </form>
        <p className="mt-6 text-gray-600">
          <Link
            to="/forgot-password"
            className="text-blue-500 font-medium hover:no-underline"
          >
            {t("link_quen_mat_khau")}
          </Link>
        </p>
      </div>
    </div>
  );
};

export default LoginForm;
