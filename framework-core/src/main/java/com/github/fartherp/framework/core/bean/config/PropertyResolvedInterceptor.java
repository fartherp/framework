/*
 * Copyright (c) 2017. CK. All rights reserved.
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
