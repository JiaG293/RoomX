package com.roomx.application.dto.auth.request;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}