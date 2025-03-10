package com.roomx.shared.exception.exception.config;

import com.roomx.shared.exception.api.ResultResponse;
import com.roomx.shared.exception.exception.AppException;
import com.roomx.shared.exception.exception.code.ErrorCode;
import com.roomx.shared.exception.exception.TenantNotFoundException;
import com.roomx.shared.exception.exception.TenantResolutionException;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ResultResponse> handlingRuntimeException(Exception exception) {
        log.error("Exception: ", exception);
        ResultResponse resultResponse = new ResultResponse();

        resultResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        resultResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(resultResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResultResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ResultResponse resultResponse = new ResultResponse();

        resultResponse.setCode(errorCode.getCode());
        resultResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(resultResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResultResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ResultResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ResultResponse> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);

            //Java 21
            var constraintViolation =
                    exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            //Java 17
//            var constraintViolation = exception.getBindingResult().getAllErrors().stream()
//                    .filter(error -> error instanceof ConstraintViolation)
//                    .map(error -> (ConstraintViolation<?>) error)
//                    .findFirst()
//                    .orElse(null);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        } catch (IllegalArgumentException e) {
            log.error("Invalid error code enum: {}", enumKey, e);
        }

        ResultResponse resultResponse = new ResultResponse();

        resultResponse.setCode(errorCode.getCode());
        resultResponse.setMessage(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(), attributes)
                        : errorCode.getMessage());

        return ResponseEntity.badRequest().body(resultResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }*/

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ResultResponse> handlingRuntimeException(Exception exception) {
        return buildErrorResponse(exception, ErrorCode.UNCATEGORIZED_EXCEPTION);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResultResponse> handlingAppException(AppException exception) {
        return buildErrorResponse(exception, exception.getErrorCode(), exception.getArgs());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResultResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        return buildErrorResponse(exception, ErrorCode.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ResultResponse> handlingValidation(MethodArgumentNotValidException exception) {
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Object[] messageArgs = null;

        try {
            ConstraintViolation<?> constraintViolation = exception.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);
            String messageTemplate = constraintViolation.getMessageTemplate();

            if (messageTemplate.contains("username")) {
                errorCode = ErrorCode.USERNAME_INVALID;
            } else if (messageTemplate.contains("password")) {
                errorCode = ErrorCode.INVALID_PASSWORD;
            } else if (messageTemplate.contains("email")) {
                errorCode = ErrorCode.EMAIL_EXISTED;
            }

            Map<String, Object> attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            if (attributes != null && !attributes.isEmpty()) {
                messageArgs = attributes.values().toArray();
            }

        } catch (Exception e) {
            return buildErrorResponse(e, ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        return buildErrorResponse(exception, errorCode, messageArgs);
    }

    @ExceptionHandler(TenantResolutionException.class)
    public ResponseEntity<ResultResponse> handleTenantResolutionException(TenantResolutionException ex) {
        return buildErrorResponse(ex, ErrorCode.TENANT_INVALID);
    }

    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<ResultResponse> handleTenantNotFoundException(TenantNotFoundException ex) {
        return buildErrorResponse(ex, ErrorCode.TENANT_NOT_FOUND);
    }

    private ResponseEntity<ResultResponse> buildErrorResponse(Exception ex, ErrorCode errorCode) {
        String message = getMessage(errorCode.getMessageKey(), null);

        ResultResponse<Object> resultResponse = ResultResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();

        return new ResponseEntity<>(resultResponse, errorCode.getStatusCode());
    }


    private ResponseEntity<ResultResponse> buildErrorResponse(Exception ex, ErrorCode errorCode, Object[] messageArgs) {
        String errorId = UUID.randomUUID().toString();
        if (messageArgs == null) {
            messageArgs = new Object[]{errorId};
        }
        String message = getMessage(errorCode.getMessageKey(), messageArgs);

        ResultResponse<Object> resultResponse = ResultResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();

        return new ResponseEntity<>(resultResponse, errorCode.getStatusCode());
    }

    private String getMessage(String code, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, code, locale);
    }

}



