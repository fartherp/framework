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
package com.github.fartherp.framework.database.mybatis.plugin.page.dialect;

/**
 * Mysql方言的实现
 * Author: CK
 * Date: 2015/6/14
 */
public class MySQLDialect implements Dialect {

    public String getLimitString(String sql, int offset, int pageSize) {
        StringBuilder sb = new StringBuilder(sql);
        sb.append(" limit ");
        if (offset > 0) {
            sb.append(offset).append(",").append(pageSize);
        } else {
            sb.append(pageSize);
        }
        return sb.toString();
    }

    public void getLimitString(StringBuilder sb, int offset, int pageSize) {
        sb.append(" limit ");
        if (offset > 0) {
            sb.append(offset).append(",").append(pageSize);
        } else {
            sb.append(pageSize);
        }


    }

    public boolean supportsLimit() {
        return true;
    }
}
