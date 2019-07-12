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
package com.github.fartherp.framework.core.bean.config;

import java.util.List;
import java.util.Properties;

/**
 * Property resolved interceptor.
 * Auth: CK
 * Date: 2016/8/29
 */
public interface PropertyResolvedInterceptor {
    /**
     * Call back if one property is fetch by API
     *
     * @param key
     * @param value
     * @return
     */
    List doListPropertyResolved(String key, List value);

    /**
     * Call back if one property is fetch by API
     *
     * @param key
     * @param value
     * @return
     */
    Object doPropertyResolved(String key, Object value);

    /**
     * Call back after all property is loaded finished
     *
     * @param properties
     */
    void onPropertiesLoad(Properties properties);
}
