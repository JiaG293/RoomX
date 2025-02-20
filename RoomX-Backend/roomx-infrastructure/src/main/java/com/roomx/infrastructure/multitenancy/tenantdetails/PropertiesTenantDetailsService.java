package com.roomx.infrastructure.multitenancy.tenantdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertiesTenantDetailsService implements TenantDetailsService {

    private final TenantDetailsProperties tenantDetailsProperties;

    @Override
    public List<TenantDetails> loadAllTenants() {
        return tenantDetailsProperties.tenants();
    }

    @Override
    public TenantDetails loadTenantByIdentifier(String identifier) {
        return tenantDetailsProperties.tenants().stream()
                .filter(TenantDetails::enabled)
                .filter(tenantDetails -> identifier.equals(tenantDetails.identifier()))
                .findFirst().orElse(null);
    }

}