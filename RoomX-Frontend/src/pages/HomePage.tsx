import { Button } from "@/components/ui/button";
import { AuthContext } from "@/context/AuthProvider";

import { useContext } from "react";


const HomePage: React.FC = () => {
  const { logout, keycloak } = useContext(AuthContext);

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl mb-4">
        Welcome, {keycloak?.tokenParsed?.preferred_username}
      </h1>
      <Button onClick={logout}>Logout</Button>
    </div>
  );
};
export default HomePage;
