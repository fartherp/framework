/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.core.orm.mybatis.plugin.search.exception;


/**
 * <p>author: hyssop
 * <p>Date: 16-1-17 上午11:43
 * <p>Version: 1.0
 */
public class SearchException extends RuntimeException {

    public SearchException(String msg) {
        super(msg);
    }

    public SearchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
