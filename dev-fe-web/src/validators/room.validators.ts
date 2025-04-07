export class RoomValidator {
    static isValidRoomCode(roomCode: string): boolean {
      const roomCodeRegex = /^[a-zA-Z0-9]{6,}$/;
      return roomCodeRegex.test(roomCode);
    }
  
    static isNotEmpty(value: string): boolean {
      return value.trim().length > 0;
    }
  }
  