// src/components/common/NotificationToast.tsx
import React, { useEffect, useState } from "react";

interface NotificationData {
  title: string;
  body: string;
  image?: string;
}

export const NotificationToast: React.FC<NotificationData> = ({
  title,
  body,
  image,
}) => {
  const [apiData, setApiData] = useState<any>(null);

  let parsedBody: {
    message?: string;
    image?: string;
    path?: string;
    domain?: string;
  } = {};

  try {
    parsedBody = JSON.parse(body);
  } catch (error) {
    console.warn("❌ Invalid notification body JSON:", error);
  }


  // fetch api ngầm với notification
  useEffect(() => {
    if (parsedBody.domain && parsedBody.path) {
      const url = `${parsedBody.domain}${parsedBody.path}`;
      fetch(url)
        .then((res) => res.json())
        .then((data) => {
          setApiData(data);
          console.log("✅ API data from notification fetched:", data);
        })
        .catch((err) => {
          console.error("❌ Error fetching API from notification body:", err);
        });
    }
  }, [parsedBody.domain, parsedBody.path]);

  return (
  <div className="flex gap-3 items-start max-w-sm p-4 rounded-xl shadow-md border border-zinc-200 dark:border-zinc-700 bg-white dark:bg-zinc-900 text-zinc-900 dark:text-white">
    {parsedBody.image && (
      <img
        src={parsedBody.image}
        alt="Notification"
        className="w-12 h-12 rounded object-cover"
      />
    )}
    <div className="flex flex-col">
      <p className="font-semibold text-base">{title}</p>
      <p className="text-sm text-zinc-700 dark:text-zinc-300">{parsedBody.message}</p>

      {apiData && (
        <pre className="text-xs mt-2 p-2 rounded-lg bg-zinc-100 dark:bg-zinc-800 border border-zinc-300 dark:border-zinc-700 overflow-auto max-h-32 text-zinc-800 dark:text-zinc-100">
          {JSON.stringify(apiData, null, 2)}
        </pre>
      )}
    </div>
  </div>
);

};
