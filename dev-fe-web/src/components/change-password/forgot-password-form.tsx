import React, { useState } from "react";
import { useTranslation } from "react-i18next";

const ForgotPasswordForm: React.FC = () => {
  const [email, setEmail] = useState("");
  // const { resetPassword } = useAuth();
  const { t } = useTranslation();
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const keycloakUrl = import.meta.env.VITE_KEYCLOAK_URL;
      const realm = import.meta.env.VITE_KEYCLOAK_REALM;
      const clientId = import.meta.env.VITE_KEYCLOAK_CLIENT_ID;

      const response = await fetch(
        `${keycloakUrl}/realms/${realm}/login-actions/reset-credentials?client_id=${clientId}`,
        {
          method: "POST",
          mode: "no-cors",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
          body: new URLSearchParams({ username: email }),
        }
      );

      if (response.ok) {
        alert("Check your email for the reset password link!");
      } else {
        const errorData = await response.json();
        console.error("Error:", errorData);
        alert(errorData.error_description || "Failed to send reset email.");
      }
    } catch (error) {
      console.error("Network error:", error);
      alert("Network error. Please try again later.");
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-[url('/src/assets/img/login_bg.jpg')] bg-cover bg-center dark:bg-[url('/src/assets/img/login_bg.jpg')] dark:bg-cover dark:bg-center] text-foreground">
      <div
        style={{ backgroundColor: "hsl(var(--background))" }}
        className="absolute top-0 left-0 w-full h-full opacity-50 blur-lg"
      ></div>

      <div className="w-full max-w-md p-6 rounded-lg shadow-lg bg-card text-card-foreground shadow-gray-700 dark:shadow-gray-900 z-20">
        <h2 className="text-2xl font-bold text-center mb-4">{t("auth.forgot_password.title")}</h2>
        <p className="text-muted-foreground text-center text-sm mb-6">
          {t("auth.forgot_password.description")}
        </p>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium mb-1 text-foreground">
              {t("auth.forgot_password.label_email")}
            </label>
            <input
              type="email"
              className="w-full p-3 bg-input text-foreground rounded-lg border border-border focus:ring-2 focus:ring-primary focus:outline-none transition-all"
              placeholder={t("auth.forgot_password.placeholder_email")}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              onInvalid={(e) =>
                (e.target as HTMLInputElement).setCustomValidity(
                  t("auth.forgot_password.warning_required_email")
                )
              }
              onInput={(e) => {
                const input = e.target as HTMLInputElement;
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailRegex.test(input.value)) {
                  input.setCustomValidity(
                    t("auth.forgot_password.warning_invalid_email")
                  );
                } else {
                  input.setCustomValidity("");
                }
              }}
            />
          </div>

          <button
            type="submit"
            className="w-full bg-primary hover:opacity-90 text-primary-foreground font-semibold py-2 rounded-lg transition duration-300"
          >
            {t("auth.forgot_password.button_send_link")}
          </button>
        </form>
      </div>
    </div>
  );
};

export default ForgotPasswordForm;
