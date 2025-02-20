package com.roomx.infrastructure.multitenancy.tenantdetails;

import java.util.List;

public record TenantDetails(
        String identifier,
        boolean enabled,
        String schema,
        String issuer
) {}
