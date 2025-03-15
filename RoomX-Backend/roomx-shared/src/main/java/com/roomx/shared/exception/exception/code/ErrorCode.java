package com.roomx.shared.exception.exception.code;

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
    INVALID_KEY(1001, "error.invalid.key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "error.user.existed", HttpStatus.CONFLICT),
    EMAIL_EXISTED(1003, "error.email.existed", HttpStatus.CONFLICT),
    USERNAME_INVALID(1004, "error.username.invalid", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1005, "error.invalid.password", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1006, "error.user.not.existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1007, "error.unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "error.unauthorized", HttpStatus.FORBIDDEN),
    INVALID_DOB(1009, "error.invalid.dob", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(1010, "error.token.invalid", HttpStatus.UNAUTHORIZED),
    ROLE_INVALID(1011, "error.role.invalid", HttpStatus.NOT_FOUND),
    TENANT_INVALID(1012, "error.tenant.invalid", HttpStatus.FORBIDDEN),
    TENANT_NOT_FOUND(1013, "error.tenant.not_found", HttpStatus.NOT_FOUND),
    EMPLOYEE_ID_EXISTED(1000, "error.employee.id.conflict", HttpStatus.CONFLICT),
    CREATE_USER_FAILED(1000, "error.employee.create.keycloak.failed", HttpStatus.BAD_REQUEST),
    ROLLBACK_FAILED(1000, "error.roll.back.data.failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_CONFLICT(1000, "error.role.conflict", HttpStatus.CONFLICT),
    ROLE_CREATE_FAILED(1000, "error.role.create_failed", HttpStatus.BAD_REQUEST),
    PERMISSION_CREATE_FAILED(1000, "error.permission.create.failed", HttpStatus.BAD_REQUEST),
    PERMISSION_CONFLICT(1000, "error.permission.conflict", HttpStatus.CONFLICT),
    ROLE_REMOVED_FAILED(1000, "error.removed.failed", HttpStatus.NOT_FOUND),
    BRANCH_CONFLICT(1000, "error.branch.conflict", HttpStatus.CONFLICT),
    BRANCH_NOT_FOUND(1000, "error.branch.not_found", HttpStatus.NOT_FOUND ),
    SERVICE_NOT_FOUND(1000, "error.service.not_found", HttpStatus.NOT_FOUND),
    SERVICE_SERVICE_CODE_CONFLICT(1000, "error.service.conflict", HttpStatus.CONFLICT ),
    PLACE_SLUG_NOT_FOUND(1000, "error.place.slug.not_found", HttpStatus.CONFLICT),
    PLACE_SLUG_CONFLICT(1000, "error.place.slug.conflict", HttpStatus.CONFLICT),
    PLACE_NOT_FOUND(1000, "error.place.not_found", HttpStatus.NOT_FOUND),
    PLACE_CONFLICT(1000, "error.place.conflict" , HttpStatus.CONFLICT),
    ROOM_CLASS_CONFLICT(1000, "error.room_class.room_class_code.conflict", HttpStatus.CONFLICT),
    ROOM_CLASS_NOT_FOUND(1000, "error.room_class.not_found", HttpStatus.NOT_FOUND),
    ROOM_CLASS_NOT_ACTIVE(1000, "error.room_class.not_active", HttpStatus.FORBIDDEN),
    ;



    private final int code;
    private final String messageKey;
    private final HttpStatusCode statusCode;


    ErrorCode(int code, String messageKey, HttpStatusCode statusCode) {
        this.code = code;
        this.messageKey = messageKey;
        this.statusCode = statusCode;
    }

}
