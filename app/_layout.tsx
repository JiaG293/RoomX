import Header from "@/components/header";
import { Stack } from "expo-router";

export default function RootLayout() {
  return (
    <Stack>
      <Stack.Screen name="index" options={{ headerShown: false }} />
      <Stack.Screen 
        name="tabs" 
        options={{
          header: () => <Header />,  
        }} 
      />
      <Stack.Screen name="profile" options={{ headerShown: false }}/>
      <Stack.Screen name="event-detail" options={{ headerShown: false }}/>
    </Stack>
  );
}
