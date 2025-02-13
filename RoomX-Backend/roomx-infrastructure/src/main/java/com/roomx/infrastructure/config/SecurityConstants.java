package com.roomx.infrastructure.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public class SecurityConstants {

    // Role administration
    static String HAS_ROLE_SUPER_ADMIN = "hasRole('SUPER_ADMIN')";
    static String HAS_ROLE_ADMIN = "hasRole('ADMIN')";
    static String HAS_ROLE_SUPPORT_STAFF = "hasRole('SUPPORT_STAFF')";
    static String HAS_ROLE_SALES_MANAGER = "hasRole('SALES_MANAGER')";
    static String HAS_ROLE_BILLING_MANAGER = "hasRole('BILLING_MANAGER')";
    static String HAS_ROLE_STAFF = "hasRole('ROLE_STAFF')";
    static String HAS_ROLE_TENANT_OWNER = "hasRole('TENANT_OWNER')";

    // Role tenant
    static String HAS_ROLE_TENANT_ADMIN = "hasRole('TENANT_ADMIN')";
    static String HAS_ROLE_MANAGER = "hasRole('MANAGER')";
    static String HAS_ROLE_EMPLOYEE = "hasRole('EMPLOYEE')";
    static String HAS_ROLE_SUPPORT = "hasRole('SUPPORT')";

}
