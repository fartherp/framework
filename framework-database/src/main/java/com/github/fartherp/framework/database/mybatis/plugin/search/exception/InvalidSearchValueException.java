/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.database.mybatis.plugin.search.exception;

/**
 * <p>author: hyssop
 * <p>Date: 16-1-17 上午11:44
 * <p>Version: 1.0
 */
public final class InvalidSearchValueException extends SearchException {

    public InvalidSearchValueException(String searchProperty, String entityProperty, Object value) {
        this(searchProperty, entityProperty, value, null);
    }

    public InvalidSearchValueException(String searchProperty, String entityProperty, Object value, Throwable cause) {
        super("Invalid Search Value, searchProperty [" + searchProperty + "], " +
                "entityProperty [" + entityProperty + "], value [" + value + "]", cause);
    }

}
