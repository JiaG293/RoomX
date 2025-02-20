package com.roomx.shared.exception;

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
    UNCATEGORIZED_EXCEPTION(9999, "error.uncategorized", HttpStatus.INTERNAL_SERVER_ERROR), // Sử dụng key
    INVALID_KEY(1001, "error.invalid_key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "error.user_existed", HttpStatus.CONFLICT),
    EMAIL_EXISTED(1003, "error.email_existed", HttpStatus.CONFLICT),
    USERNAME_INVALID(1004, "error.username_invalid", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1005, "error.invalid_password", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1006, "error.user_not_existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1007, "error.unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "error.unauthorized", HttpStatus.FORBIDDEN),
    INVALID_DOB(1009, "error.invalid_dob", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(1010, "error.token_invalid", HttpStatus.UNAUTHORIZED),
    ROLE_INVALID(1011, "error.role_invalid", HttpStatus.NOT_FOUND),
    TENANT_INVALID(1012, "error.tenant_invalid", HttpStatus.FORBIDDEN),
    TENANT_NOT_FOUND(1013, "error.tenant_not_found", HttpStatus.NOT_FOUND),
    EMPLOYEE_ID_EXISTED(1000, "error.employee_id_conflict", HttpStatus.CONFLICT),
    CREATE_USER_FAILED(1000, "error.employee_create_keycloak_failed", HttpStatus.BAD_REQUEST),
    ROLLBACK_FAILED(1000, "error.roll_back_data_failed", HttpStatus.INTERNAL_SERVER_ERROR);







    ErrorCode(int code, String messageKey, HttpStatusCode statusCode) {
        this.code = code;
        this.messageKey = messageKey;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String messageKey;
    private final HttpStatusCode statusCode;
}
