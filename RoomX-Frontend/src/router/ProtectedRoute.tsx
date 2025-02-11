import { AuthContext } from "@/context/AuthProvider";
import { useContext, useEffect } from "react";
import { Outlet } from "react-router-dom"; // Ensure you're using correct import from react-router


const ProtectedRoute: React.FC = () => {
  const { isAuthenticated, login } = useContext(AuthContext);

  useEffect(() => {
    if (!isAuthenticated) {
      login(); // Trigger login if not authenticated
    }
  }, [isAuthenticated, login]);

  // Show a loading message until authentication is checked
  if (!isAuthenticated) {
    return <div>Redirecting to login...</div>;
  }

  // Render protected content
  return <Outlet />;
};

export default ProtectedRoute;
