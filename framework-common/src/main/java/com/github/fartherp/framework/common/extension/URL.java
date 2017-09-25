/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.common.extension;

import java.io.Serializable;
import java.util.Map;

/**
 * URL - Uniform Resource Locator (Immutable, ThreadSafe)
 *
 * @author CK
 */
public final class URL implements Serializable {

    private static final long serialVersionUID = -1985165475234910535L;

    public static final String DEFAULT_KEY_PREFIX = "default.";

    private final Map<String, String> parameters;

    protected URL() {
        this.parameters = null;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameter(String key) {
        String value = parameters.get(key);
        if (value == null || value.length() == 0) {
            value = parameters.get(DEFAULT_KEY_PREFIX + key);
        }
        return value;
    }
}
