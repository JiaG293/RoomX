package com.roomx.shared.exception.exception;

public class TenantNotFoundException extends IllegalStateException {

    public TenantNotFoundException() {
        super("Not found user in keycloak");
    }

    public TenantNotFoundException(String message) {
        super(message);
    }

}