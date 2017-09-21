/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.exception.web;

/**
 * JSON异常
 * Auth: CK
 * Date: 2016/9/9
 */
public class ResponseException extends RuntimeException {

    public ResponseException() {
        super();
    }

    public ResponseException(String message) {
        super(message);
    }

    public ResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseException(Throwable cause) {
        super(cause);
    }
}
