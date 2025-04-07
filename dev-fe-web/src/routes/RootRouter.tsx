import ForgotPassword from "@/pages/Auth/ForgotPassword";
import Notfound from "@/pages/Error/Notfound";
import { createBrowserRouter } from "react-router-dom";
import ProtectedRoute from "@/routes/ProtectedRouter";
import Dashboard from "@/pages/Admin/Dashboard";
import Login from "@/pages/Auth/Login";
import User from "@/pages/Admin/User";
import Meeting from "@/pages/Admin/Meeting";
import Branch from "@/pages/Admin/Branch/Branch";
import GroupUser from "@/pages/Admin/GroupUser";
import MeetingApproval from "@/pages/Admin/MeetingApproval";
import Statistics from "@/pages/Admin/Statistics";
import Home from "@/pages/App/Home";
import UserDetail from "@/pages/Admin/UserDetail";
import BranchUpdate from "@/pages/Admin/Branch/BranchUpdate";
import Service from "@/pages/Admin/Service/Service";
import Equipment from "@/pages/Admin/Equipment/Equipment";
import Room from "@/pages/Admin/Room/Room";
import Booking from "@/pages/App/Booking";

export const RootRouter = createBrowserRouter([
  {
    element: <ProtectedRoute />,
    children: [
      {
        path: "/admin/home",
        element: <Dashboard />,
      },
      {
        path: "/admin/statistics",
        element: <Statistics />,
      },
      {
        path: "/admin/users",
        element: <User />,
      },
      {
        path: "/admin/users/:userId",
        element: <UserDetail />,
      },
      {
        path: "/admin/meetings",
        element: <Meeting />,
      },
      {
        path: "/admin/meetings/room-approvals",
        element: <MeetingApproval />,
      },
      {
        path: "/admin/branches",
        element: <Branch />,
      },
      {
        path: "/admin/branches/:branchId",
        element: <BranchUpdate />,
      },
      {
        path: "/admin/users/groups",
        element: <GroupUser />,
      },
      {
        path: "/admin/services",
        element: <Service />,
      },
      {
        path: "/admin/equipments",
        element: <Equipment />,
      },
      {
        path: "/admin/rooms",
        element: <Room />,
      },
      {
        path: "/portal/home",
        element: <Home />,
      },
      {
        path: "/portal/booking",
        element: <Booking />,
      },
    ],
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/forgot-password",
    element: <ForgotPassword />, //
  },
  {
    path: "*",
    element: <Notfound />,
  },
]);
