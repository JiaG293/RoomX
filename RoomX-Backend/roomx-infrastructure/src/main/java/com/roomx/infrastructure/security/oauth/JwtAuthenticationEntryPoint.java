package com.roomx.infrastructure.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomx.shared.exception.api.ResultResponse;
import com.roomx.shared.exception.exception.code.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MessageSource messageSource;

    /*@Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResultResponse<?> resultResponse = ResultResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessageKey())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(resultResponse));
        response.flushBuffer();
    }*/


    // Cấu hình i18n
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {

        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(errorCode.getMessageKey(), null, locale);

        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResultResponse<?> resultResponse = ResultResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(resultResponse));
        response.flushBuffer();
    }
}

