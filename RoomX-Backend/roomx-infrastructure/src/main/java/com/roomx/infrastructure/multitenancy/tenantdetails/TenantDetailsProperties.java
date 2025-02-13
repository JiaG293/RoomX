package com.roomx.infrastructure.multitenancy.tenantdetails;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "multitenancy")
public record TenantDetailsProperties(List<TenantDetails> tenants) { }
