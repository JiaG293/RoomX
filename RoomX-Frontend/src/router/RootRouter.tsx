
import LoginPage from "@/pages/auth/LoginPage";
import HomePage from "@/pages/HomePage";

import { createBrowserRouter, Navigate } from "react-router";
import ProtectedRoute from "./ProtectedRoute";


/* const RootRouter = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />
  },
  {
    path: "/home",
    element: (
      <ProtectedRoute>
        <HomePage />
      </ProtectedRoute>
    )
  },
  {
    path: "*",
    element: <Navigate to="/home" replace />
  }
]);
 */

const RootRouter = createBrowserRouter([
  {
    element: <ProtectedRoute />,
    children: [
      {
        path: "/home",
        element: <HomePage />
      }
    ]
  },
  {
    path: "/login",
    element: <LoginPage />
  },
  {
    path: "*",
    element: <Navigate to="/home" replace />
  }
]);

export default RootRouter;
