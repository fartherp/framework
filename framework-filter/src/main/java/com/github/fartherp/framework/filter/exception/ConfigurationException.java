/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.exception;

public class ConfigurationException extends BaseException {

    /**
     * Creates a new ConfigurationException.
     */
    public ConfigurationException() {
        super();
    }

    /**
     * Constructs a new ConfigurationException.
     *
     * @param message the reason for the exception
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new ConfigurationException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new ConfigurationException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
