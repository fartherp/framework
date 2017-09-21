/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.exception;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * 系统统一异常
 * Author: CK
 * Date: 2016/2/5
 */
public class SystemException extends BaseSystemException {
    public SystemException(String msg, Throwable t, String... args) {
        super(msg, t, args);
    }

    public SystemException(String msg) {
        super(msg);
    }

    public SystemException(Throwable t) {
        super(t);
    }

    public SystemException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * 通过实际异常获取message
     * @param t 异常
     * @return message
     */
    public String getMessage(Throwable t) {
        String oldMessage = "";
        if (t instanceof InvocationTargetException) {
            oldMessage = getMessage(((InvocationTargetException) t).getTargetException());
        } else if (t instanceof SystemException) {
            oldMessage = ((SystemException) t).getMessage();
        } else if (t instanceof SQLException) {
            oldMessage = getSQLException(t);
        } else if (t instanceof RuntimeException) {
            if (t.getMessage() != null) {
                oldMessage = t.getMessage();
            } else if (null != t.getCause() && null != t.getCause().getMessage()) {
                oldMessage = t.getCause().getMessage();
            } else {
                oldMessage = getCommonMessage(t);
            }
        } else {
            oldMessage = getCommonMessage(t);
        }
        return oldMessage;
    }
}
