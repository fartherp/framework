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

/**
 * 定义基础异常功能接口
 * @author CK
 * @date 2016/2/5
 */
public interface BaseException {
    /**
     * 默认数据库
     */
	String MYSQL_DATABASE = "MYSQL";
    String ORACLE_DATABASE = "ORACLE";
    String COMMON_EXCEPTION_MESSAGE = "COMMON";
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
