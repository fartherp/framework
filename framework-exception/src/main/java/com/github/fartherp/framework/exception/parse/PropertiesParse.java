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
package com.github.fartherp.framework.exception.parse;

import com.github.fartherp.framework.exception.BaseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 异常properties解析类
 * @author CK
 * @date 2016/2/5
 */
public enum PropertiesParse {
	/**
	 * 公共异常信息
	 */
    COMMON_INSTANCE(BaseException.COMMON_EXCEPTION_MESSAGE),
	/**
	 * mysql异常信息
	 */
    MYSQL_INSTANCE(BaseException.MYSQL_DATABASE),
	/**
	 * oracle异常信息
	 */
    ORACLE_INSTANCE(BaseException.ORACLE_DATABASE);

    String path;

    private static final Map<String, Properties> PROPERTIES_HASH_MAP;

    static {
		PROPERTIES_HASH_MAP = new HashMap<>();
        for (PropertiesParse parse : PropertiesParse.values()) {
            String tmp = "conf/" + parse.path.toLowerCase() + "-exception.properties";
            InputStream in = PropertiesParse.class.getClassLoader().getResourceAsStream(tmp);
            try {
                Properties properties = new Properties();
                properties.load(in);
                PROPERTIES_HASH_MAP.put(parse.path, properties);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    PropertiesParse(String path) {
        this.path = path;
    }

    public static Properties getProperties(String name) {
        return PROPERTIES_HASH_MAP.get(name);
    }
}
