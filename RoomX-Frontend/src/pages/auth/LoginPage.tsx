import { AuthContext } from "@/context/AuthProvider";
import { useContext, useEffect } from "react";
import { useNavigate } from "react-router";


const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const { isAuthenticated, keycloak } = useContext(AuthContext);

  useEffect(() => {
    if (isAuthenticated && keycloak) {
      navigate("/home");
    }
  }, [isAuthenticated, keycloak, navigate]);

  if (!isAuthenticated) {
    return <div>Redirecting to login...</div>;
  }

  return null;
};

export default LoginPage;
