// hooks/useUserProfile.ts
import { useQuery } from "@tanstack/react-query";
import { AuthService } from "@/services/auth.service";

const fetchUserProfile = async () => {
  const authService = new AuthService();
  return await authService.getProfile(); // giả sử có hàm này
};

export const useUserProfile = () => {
  return useQuery({
    queryKey: ["user-profile"],
    queryFn: fetchUserProfile,
    staleTime: 1000 * 60 * 5, // cache 5 phút
  });
};
