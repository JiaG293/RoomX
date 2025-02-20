package com.roomx.shared.exception;

public class KeycloakNotFoundException extends RuntimeException {
    public KeycloakNotFoundException() {
        super("No tenant found in the current context");
    }

    public KeycloakNotFoundException(String message) {
        super(message);
    }
}
