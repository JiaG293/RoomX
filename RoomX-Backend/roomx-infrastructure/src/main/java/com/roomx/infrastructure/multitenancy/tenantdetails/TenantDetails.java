package com.roomx.infrastructure.multitenancy.tenantdetails;

public record TenantDetails(
        String identifier,
        boolean enabled,
        String schema,
        String issuer
) {}
