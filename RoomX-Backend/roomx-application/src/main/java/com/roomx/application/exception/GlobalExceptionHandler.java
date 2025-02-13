package com.roomx.application.exception;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

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

   /* @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())đâsdđâsđấđádádad
                        .message(errorCode.getMessage())
                        .build());
    }*/

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
            /*var constraintViolation = exception.getBindingResult().getAllErrors().stream()
                    .filter(error -> error instanceof ConstraintViolation)
                    .map(error -> (ConstraintViolation<?>) error)
                    .findFirst()
                    .orElse(null);*/

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
    }
}
