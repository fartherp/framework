/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The base config by read with operation properties file.
 * Auth: CK
 * Date: 2016/5/3
 */
public class BaseConfig {
    private final Properties properties = new Properties();

    public BaseConfig(String path) {
        init(path);
    }

    private void init(String path) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(Object key) {
       return properties.get(key).toString();
    }

    public boolean getBoolean(Object key) {
        return Boolean.parseBoolean(properties.get(key).toString());
    }

    public byte getByte(Object key) {
        return Byte.parseByte(properties.get(key).toString());
    }

    public short getShort(Object key) {
        return Short.parseShort(properties.get(key).toString());
    }

    public int getInt(Object key) {
        return Integer.parseInt(properties.get(key).toString());
    }

    public long getLong(Object key) {
        return Long.parseLong(properties.get(key).toString());
    }

    public float getFloat(Object key) {
        return Float.parseFloat(properties.get(key).toString());
    }

    public double getDouble(Object key) {
        return Double.parseDouble(properties.get(key).toString());
    }
}
