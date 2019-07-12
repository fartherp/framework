/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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
        String oldMessage;
        if (t instanceof InvocationTargetException) {
            oldMessage = getMessage(((InvocationTargetException) t).getTargetException());
        } else if (t instanceof SystemException) {
            oldMessage = t.getMessage();
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
