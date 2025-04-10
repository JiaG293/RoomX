package com.roomx.shared.exception.exception;

public class RealmAlreadyExistsException extends Exception {

    /**
     * Constructs a new RealmAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message.
     */
    public RealmAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new RealmAlreadyExistsException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public RealmAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
