import ForgotPassword from "@/pages/Auth/ForgotPassword";
import Notfound from "@/pages/Error/Notfound";
import { createBrowserRouter } from "react-router-dom";
import ProtectedRoute from "@/routes/ProtectedRouter";
import Dashboard from "@/pages/Admin/Dashboard";
import Login from "@/pages/Auth/Login";
import User from "@/pages/Admin/User/User";
import Meeting from "@/pages/Admin/Schedule/Meeting";
import Branch from "@/pages/Admin/Branch/Branch";
import GroupUser from "@/pages/Admin/User/GroupUser";
import MeetingApproval from "@/pages/Admin/Schedule/MeetingApproval";
import Statistics from "@/pages/Admin/Statistics";
import Home from "@/pages/App/Home";
import UserDetail from "@/pages/Admin/User/UserDetail";
import BranchUpdate from "@/pages/Admin/Branch/BranchUpdate";
import Service from "@/pages/Admin/Service/Service";
import Equipment from "@/pages/Admin/Equipment/Equipment";
import Room from "@/pages/Admin/Room/Room";
import Booking from "@/pages/App/Booking";
import About from "@/pages/Admin/Guide/About";
import Pending from "@/pages/App/Pending";

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
        path: "/admin/about",
        element: <About />,
      },
      // {
      //   path: "/admin/profile",
      //   element: <Profile />,
      // }
      {
        path: "/portal/home",
        element: <Home />,
      },
      {
        path: "/portal/booking",
        element: <Booking />,
      },
      {
        path: "/portal/pending",
        element: <Pending />,
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
