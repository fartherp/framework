/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.core.orm.mybatis.plugin.search.exception;

/**
 * <p>author: hyssop
 * <p>Date: 16-1-17 上午11:44
 * <p>Version: 1.0
 */
public final class InvalidSearchPropertyException extends SearchException {

    public InvalidSearchPropertyException(String searchProperty, String entityProperty) {
        this(searchProperty, entityProperty, null);
    }

    public InvalidSearchPropertyException(String searchProperty, String entityProperty, Throwable cause) {
        super("Invalid Search Property [" + searchProperty + "] Entity Property [" + entityProperty + "]", cause);
    }
}
