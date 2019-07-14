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

import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2016/3/24
 */
public enum DialectType {
	/**
	 * mysql
	 */
    MYSQL("mysql", MySQLDialect.class.getName()),
	/**
	 * oracle
	 */
    ORACLE("oracle", ""),
	/**
	 * sql server
	 */
    SQL_SERVER("microsoft sql server", ""),;

    String name;

    String className;

    DialectType(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public static Dialect getDialect(String name) {
        DialectType[] types = DialectType.values();
        for (DialectType type : types) {
            if (StringUtils.equalsIgnoreCase(type.getName(), name)) {
                try {
                    Class<Dialect> forName = (Class<Dialect>) Class.forName(type.getClassName());
                    return forName.newInstance();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
			}
        }
        return null;
    }
}
