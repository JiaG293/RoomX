package com.roomx.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


/**
 * Trả về mã trạng thái lỗi hệ thống
 * code: mã lỗi hệ thống quy định
 * message: thông báo lỗi từ hệ thống
 * statusCode: mã lỗi http status code
 *
 * 1:
 * 2:
 * 3:
 * 4:
 * 5:
 * 6:
 * 7:
 * 8:
 * 9:
 */
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.CONFLICT),
    EMAIL_EXISTED(1003, "Email existed", HttpStatus.CONFLICT),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(1009, "Token invalid verify", HttpStatus.UNAUTHORIZED),
    ROLE_INVALID(1010, "Role must be candidate or company", HttpStatus.NOT_FOUND),;







    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
