package com.roomx.infrastructure.tenantdetails;

public record TenantDetails(
        String identifier,
        boolean enabled,
        String schema,
        String issuer
) {}
