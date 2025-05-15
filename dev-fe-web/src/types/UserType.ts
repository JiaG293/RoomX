export type User = {
  id: string;
  userCode: string;
  email: string;
  firstName: string | null;
  lastName: string | null;
  phoneNumber: string | null;
  userType: string;
  avatarImage: string | null;
};