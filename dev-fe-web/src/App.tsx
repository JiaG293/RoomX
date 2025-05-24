import i18next from "@/locales/i18n.config";
import { I18nextProvider } from "react-i18next";
import { RouterProvider } from "react-router-dom";

import { ThemeProvider } from "@/components/admin/custom/theme-provider";
import { AuthProvider } from "@/context/AuthProvider";
import { RootRouter } from "@/routes/RootRouter";
import { Toaster, toast } from "sonner";

import {  onMessageListener } from "@/services/notification.service";
import { useEffect } from "react";
import { NotificationToast } from "@/components/notification/NotificationToast";

function App() {
  
  useEffect(() => {
  const unsubscribe = onMessageListener((payload: any) => {
    const { title, body, image } = payload.notification || {};
    if (!title || !body) return;

    toast.custom(() => (
      <NotificationToast title={title} body={body} image={image} />
    ));
  });

  return () => unsubscribe();
}, []);

  return (
    <AuthProvider>
      <I18nextProvider i18n={i18next}>
        <ThemeProvider storageKey="vite-ui-theme">
          <RouterProvider router={RootRouter} />
          <Toaster richColors position="top-right" duration={1500} />
        </ThemeProvider>
      </I18nextProvider>
    </AuthProvider>
  );
}

export default App;
