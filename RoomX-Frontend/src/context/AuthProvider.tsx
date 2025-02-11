import React, { ReactNode, useEffect, useState } from "react";
import Keycloak from "keycloak-js";
import Cookies from "js-cookie";
// import KeycloakConfig from '@/config/KeycloakConfig';

const KeycloakConfig = new Keycloak({
  url: "http://192.168.1.251:5555", // Keycloak URL
  realm: "roomx", // Your Realm name
  // clientId: "roomx-tenantId-idp", // Your Client ID
  clientId: "roomx-frontend",
});

interface AuthContextType {
  isAuthenticated: boolean;
  keycloak: Keycloak | null;
  login: () => void;
  logout: () => void;
}

const AuthContext = React.createContext<AuthContextType>({
  isAuthenticated: false,
  keycloak: null,
  login: () => {},
  logout: () => {},
});

const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [keycloak, setKeycloak] = useState<Keycloak | null>(null);

  useEffect(() => {
    const initKeycloak = async () => {
      try {
        const authenticated = await KeycloakConfig.init({
          onLoad: "login-required",
          checkLoginIframe: false,
          redirectUri: `${window.location.origin}/home`,
        });

        if (authenticated) {
          // Truy xuất Access Token và Refresh Token
          const accessToken = KeycloakConfig.token;
          const refreshToken = KeycloakConfig.refreshToken;

          console.log("Access Token:", accessToken);
          console.log("Refresh Token:", refreshToken);

          // console.log("Keycloak context:", KeycloakConfig.createAccountUrl);

          // Bạn có thể lưu token vào state hoặc localStorage nếu cần
          sessionStorage.setItem("token", accessToken || "");
          Cookies.set("refreshToken", refreshToken || "", {
            secure: true,
            sameSite: "Strict",
            expires: 7,
          });

          setKeycloak(KeycloakConfig);
          setIsAuthenticated(true);
        }
      } catch (error) {
        console.error("Keycloak initialization error", error);
      }
    };

    initKeycloak();
  }, []);

  const login = () => {
    keycloak?.login({
      redirectUri: `${window.location.origin}/home`,

    });
  };

  const logout = () => {
    Cookies.remove("refreshToken");
    keycloak?.logout({
      redirectUri: `${window.location.origin}/login`,
    });
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, keycloak, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
export { AuthProvider, AuthContext };
