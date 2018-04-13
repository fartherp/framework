/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.database.mybatis.plugin.search.exception;


import com.github.fartherp.framework.database.mybatis.plugin.search.enums.SearchOperator;

/**
 * <p>author: hyssop
 * <p>Date: 16-1-17 上午11:59
 * <p>Version: 1.0
 */
public final class InvlidSearchOperatorException extends SearchException {

    public InvlidSearchOperatorException(String searchProperty, String operatorStr) {
        this(searchProperty, operatorStr, null);
    }

    public InvlidSearchOperatorException(String searchProperty, String operatorStr, Throwable cause) {
        super("Invalid Search Operator searchProperty [" + searchProperty + "], " +
                "operator [" + operatorStr + "], must be one of " + SearchOperator.toStringAllOperator(), cause);
    }
}
