/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.exception;

public class UnknownClassException extends BaseException {

    /**
     * Creates a new UnknownClassException.
     */
    public UnknownClassException() {
        super();
    }

    /**
     * Constructs a new UnknownClassException.
     *
     * @param message the reason for the exception
     */
    public UnknownClassException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnknownClassException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public UnknownClassException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UnknownClassException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public UnknownClassException(String message, Throwable cause) {
        super(message, cause);
    }

}
