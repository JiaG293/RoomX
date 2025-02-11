import "./App.css";
import { RouterProvider } from "react-router";
import RootRouter from "./router/RootRouter";
import { AuthProvider } from "./context/AuthProvider";


const App: React.FC = () => {
  return (
    <AuthProvider>
      <RouterProvider router={RootRouter}></RouterProvider>
    </AuthProvider>
  );
};

export default App;
