export class BranchValidator {
    static isValidBranchCode(branchCode: string): boolean {
      const branchCodeRegex = /^[a-zA-Z0-9]{6,}$/;
      return branchCodeRegex.test(branchCode);
    }
  
    static isValidBranchName(branchName: string): boolean {
      const branchNameRegex = /^[a-zA-ZÀ-Ỹà-ỹ\s']{2,}$/u;
      return branchNameRegex.test(branchName);
    }
  
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
  }
  