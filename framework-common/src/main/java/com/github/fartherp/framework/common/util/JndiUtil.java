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
package com.github.fartherp.framework.common.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * A utility class for Jndi operation.
 * Auth: CK
 * Date: 2016/8/29
 */
public class JndiUtil {
    /**
     * jave env prefix
     */
    public static final String JNDI_PREFIX = "java:comp/env";

    /**
     * get value by key from namng service
     *
     * @param key name
     * @return value
     * @throws NamingException in case of connect naming service failed.
     */
    public static String getValue(String key) throws NamingException {
        Context ctx = new InitialContext();
        return (String) ctx.lookup(key);
    }
}
