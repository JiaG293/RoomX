import { useTranslation } from "react-i18next";

const Notfound: React.FC = () => {

  const { t } = useTranslation();

  return (
    <div style={{ backgroundColor: "hsl(var(--background))" }} className="flex flex-col items-center justify-center h-screen text-[var(--foreground)]">
      <h1 className="text-9xl font-extrabold tracking-widest animate-pulse">404</h1>
      <p className="text-xl md:text-2xl mt-4 opacity-80 animate-fade-in">
        {t("common.not_found_message")}
      </p>
      <div className="absolute -z-10 w-72 h-72 bg-purple-500 rounded-full opacity-30 blur-3xl animate-floating"></div>
    </div>
  );
};

export default Notfound;
