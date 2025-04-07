export class UserValidator {
    static isValidEmail(email: string): boolean {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return emailRegex.test(email);
    }
  
    static isValidPhoneNumber(phone: string): boolean {
      const phoneRegex = /^[0-9]{10,11}$/;
      return phoneRegex.test(phone);
    }
  
    static isNotEmpty(value: string): boolean {
      return value.trim().length > 0;
    }
  
    static isValidUserCode(userCode: string): boolean {
      const userCodeRegex = /^[a-zA-Z0-9]{6,}$/;
      return userCodeRegex.test(userCode);
    }
  
    static isValidName(name: string): boolean {
      const nameRegex = /^[a-zA-ZÀ-Ỹà-ỹ\s']{2,}$/u; 
      return nameRegex.test(name);
    }
  }
  