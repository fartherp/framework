/*
 * Copyright (c) 2019. CK. All rights reserved.
 */
package com.github.fartherp.framework.common.util;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * A collection of utility methods to retrieve and parse the values of the Java system properties.
 */
public final class SystemPropertyUtil {

    /**
     * Returns {@code true} if and only if the system property with the specified {@code key}
     * exists.
     */
    public static boolean contains(String key) {
        return get(key) != null;
    }

    /**
     * Returns the value of the Java system property with the specified
     * {@code key}, while falling back to {@code null} if the property access fails.
     *
     * @return the property value or {@code null}
     */
    public static String get(String key) {
        return get(key, null);
    }

    /**
     * Returns the value of the Java system property with the specified
     * {@code key}, while falling back to the specified default value if
     * the property access fails.
     *
     * @return the property value.
     *         {@code def} if there's no such property or if an access to the
     *         specified property is not allowed.
     */
    public static String get(final String key, String def) {
        if (key == null) {
            throw new NullPointerException("key");
        }
        if (key.isEmpty()) {
            throw new IllegalArgumentException("key must not be empty.");
        }

        String value = null;
        try {
            if (System.getSecurityManager() == null) {
                value = System.getProperty(key);
            } else {
                value = AccessController.doPrivileged((PrivilegedAction<String>) () -> System.getProperty(key));
            }
        } catch (SecurityException e) {
        	// do nothing
        }

        if (value == null) {
            return def;
        }

        return value;
    }

    private SystemPropertyUtil() {
        // Unused
    }

	public static int getInt(String key, int def) {
		String value = get(key);
		if (value == null) {
			return def;
		}

		value = value.trim();
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			// Ignore
		}

		return def;
	}
}
