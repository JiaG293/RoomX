package com.roomx.shared.exception.exception;

public class RealmNotFoundException extends Exception {

  /**
   * Constructs a new RealmNotFoundException with the specified detail message.
   *
   * @param message the detail message.
   */
  public RealmNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs a new RealmNotFoundException with the specified detail message and cause.
   *
   * @param message the detail message.
   * @param cause   the cause (which is saved for later retrieval by the getCause() method).
   */
  public RealmNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
