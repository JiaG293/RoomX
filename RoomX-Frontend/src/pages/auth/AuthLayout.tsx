import { Outlet, Routes } from "react-router";

const AuthLayout = () => {
  return (
    <div>
      <h1>HEADER</h1>

      <main>
        <Outlet></Outlet>
      </main>

      <h1>FOOTER</h1>
    </div>
  );
};

export default AuthLayout;
