/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.exception;

/**
 * 定义基础异常功能接口
 * Author: CK
 * Date: 2016/2/5
 */
public interface BaseException {
    /**
     * 默认数据库
     */
    public static final String MYSQL_DATABASE = "MYSQL";
    public static final String ORACLE_DATABASE = "ORACLE";
    public static final String COMMON_EXCEPTION_MESSAGE = "COMMON";
    /**
     * <p>获得异常消息信息。</p>
     *
     * @return String 异常消息信息
     */
    String getMessage();
    /**
     * <p>获得实际产生的异常。</p>
     *
     * @return Throwable 实际产生的异常
     */
    Throwable getThrowable();
    /**
     * 设置数据库信息
     * @param database 数据库
     */
    void setDatabase(String database);
    /**
     * 通过实际异常获取message
     * @param t 实际异常
     * @return message
     */
    String getMessage(Throwable t);
}
