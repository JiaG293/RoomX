package com.roomx.shared.exception;

public class KeycloakAdminException extends Exception {

    /**
     * Constructs a new KeycloakAdminException with the specified detail message.
     *
     * @param message the detail message.
     */
    public KeycloakAdminException(String message) {
        super(message);
    }

    /**
     * Constructs a new KeycloakAdminException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public KeycloakAdminException(String message, Throwable cause) {
        super(message, cause);
    }
}