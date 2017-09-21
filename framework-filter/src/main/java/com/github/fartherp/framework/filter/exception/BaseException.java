/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.exception;

public class BaseException extends RuntimeException {

    /**
     * Creates a new BaseException.
     */
    public BaseException() {
        super();
    }

    /**
     * Constructs a new BaseException.
     *
     * @param message the reason for the exception
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new BaseException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new BaseException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
