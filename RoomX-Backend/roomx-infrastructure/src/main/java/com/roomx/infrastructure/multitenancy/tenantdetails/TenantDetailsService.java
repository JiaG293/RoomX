package com.roomx.infrastructure.multitenancy.tenantdetails;

import org.springframework.lang.Nullable;

import java.util.List;

public interface TenantDetailsService {

    List<TenantDetails> loadAllTenants();

    @Nullable
    TenantDetails loadTenantByIdentifier(String identifier);

}
