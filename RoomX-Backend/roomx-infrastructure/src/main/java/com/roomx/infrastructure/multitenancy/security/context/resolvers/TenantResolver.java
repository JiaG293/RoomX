package com.roomx.infrastructure.multitenancy.security.context.resolvers;


import org.springframework.lang.Nullable;

@FunctionalInterface
public interface TenantResolver<T> {

    /**
     * Resolves a tenant identifier from the given source.
     */
    @Nullable
    String resolveTenantIdentifier(T source);

}
