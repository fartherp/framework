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

import com.github.fartherp.framework.exception.parse.PropertiesParse;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 系统基础异常抽象类
 * Author: CK
 * Date: 2016/2/5
 */
public abstract class BaseSystemException extends RuntimeException implements BaseException {
    /**
     * 异常消息信息
     */
    protected String message;

    /**
     * 异常消息信息参数
     */
    protected String[] args;

    /**
     * 实际的异常
     */
    protected Throwable throwable;

    /**
     * 数据库（默认mysql）
     */
    protected String database = MYSQL_DATABASE;

    public BaseSystemException(String msg, Throwable t, String... args) {
        // 判断异常消息的参数格式。
        if (args != null && args.length > 0) {
            // 根据异常消息的参数的长度，构造出一个对象型数组。
            Object[] obj = new Object[args.length];
            // 遍历该数组，并对其元素赋值。
            System.arraycopy(args, 0, obj, 0, args.length);
            // 把参数值同消息体合并。
            this.message = MessageFormat.format(msg, obj);
        } else {
            this.message = msg;
        }
        this.args = args;
        this.throwable = t;
    }

    public BaseSystemException(String msg) {
        this.message = msg;
    }

    public BaseSystemException(Throwable t) {
        // 通过实际异常获取message
        this.message = getMessage(t);
        this.throwable = t;
    }

    public BaseSystemException(String msg, Throwable t) {
        // 通过实际异常获取message
        this.message = getMessage(t);
        // 如果实际异常message为空，获取自己写的异常message
        if (null == this.message) {
            this.message = msg;
        }
        this.throwable = t;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * 获取JAVA公共异常信息
     * @param t 异常
     * @return message
     */
    protected String getCommonMessage(Throwable t) {
        String oldMessage = "";
        Properties properties = PropertiesParse.getProperties(COMMON_EXCEPTION_MESSAGE);
        Enumeration enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String s = enumeration.nextElement().toString();
            if (s.equals(t.getClass().getCanonicalName())) {
                oldMessage = properties.getProperty(s);
                break;
            }
        }
        return oldMessage;
    }

    /**
     * 获取SQL异常信息
     * @param t 异常
     * @return message
     */
    protected String getSQLException(Throwable t) {
        return getSQLException(t, PropertiesParse.getProperties(MYSQL_DATABASE));
    }

    /**
     * 从properties文件获取SQL异常信息
     * @param t 异常
     * @param parse SQL异常properties文件内容
     * @return message
     */
    protected String getSQLException(Throwable t, Properties parse) {
        int errorCode = ((SQLException) t).getErrorCode();
        Object temp = parse.getProperty(String.valueOf(errorCode));
        if (temp == null) {
            return parse.getProperty("unknow");
        }
		return temp.toString();
    }
}
