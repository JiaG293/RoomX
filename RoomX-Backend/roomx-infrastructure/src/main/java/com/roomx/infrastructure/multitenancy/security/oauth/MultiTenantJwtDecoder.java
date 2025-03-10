package com.roomx.infrastructure.multitenancy.security.oauth;

import com.roomx.infrastructure.multitenancy.security.context.TenantContextHolder;
import com.roomx.infrastructure.multitenancy.tenantdetails.TenantDetailsService;
import com.roomx.shared.exception.exception.TenantNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class MultiTenantJwtDecoder implements JwtDecoder {

    private final TenantDetailsService tenantDetailsService;
    private final Map<String, JwtDecoder> jwtDecoders = new ConcurrentHashMap<>();

    @Override
    public Jwt decode(String token) throws JwtException {
        var tenantId = TenantContextHolder.getRequiredTenantIdentifier();  // Lấy tenant ID từ context
        JwtDecoder decoder = jwtDecoders.computeIfAbsent(tenantId, this::createDecoder);
        return decoder.decode(token);
    }


    // Decode jwt
    private JwtDecoder createDecoder(String tenantId) {
        //Tải danh sách tenant
        var tenantDetails = tenantDetailsService.loadTenantByIdentifier(tenantId);
        if (tenantDetails == null) {
            throw new TenantNotFoundException("Invalid tenant decoder ID: " + tenantId);
        }

        var issuerUri = tenantDetails.issuer();

        // Sử dụng NimbusJwtDecoder (có thể config KeycloakJwtDecoderFactory)
        return NimbusJwtDecoder.withIssuerLocation(issuerUri).build();
    }


}
