import * as Keychain from 'react-native-keychain';

// Lưu token vào Keychain
export const storeToken = async (token: string) => {
  try {
    await Keychain.setGenericPassword('token', token);
    console.log('Token saved successfully');
  } catch (error) {
    console.error('Error storing token', error);
  }
};

// Lấy token từ Keychain
export const getToken = async () => {
  try {
    const credentials = await Keychain.getGenericPassword();
    if (credentials) {
      console.log('Token loaded:', credentials.password);
      return credentials.password;
    } else {
      console.log('No token stored');
      return null;
    }
  } catch (error) {
    console.error('Error retrieving token', error);
    return null;
  }
};

// Xóa token từ Keychain
export const clearToken = async () => {
  try {
    await Keychain.resetGenericPassword();
    console.log('Token cleared');
  } catch (error) {
    console.error('Error clearing token', error);
  }
};
