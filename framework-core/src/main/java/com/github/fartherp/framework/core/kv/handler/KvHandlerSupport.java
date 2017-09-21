/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.kv.handler;

import java.util.List;
import java.util.Map;

/**
 * Support for Handler.
 * Author: CK
 * Date: 2015/11/13
 */
public class KvHandlerSupport {
    /** the default vale of the page */
    private Object defaultValue;
    /** the active deal Handler */
    private KvHandler kvHandler;

    /**
     * Whether exist the Handler
     * @return boolean
     */
    public boolean isExist() {
        return kvHandler != null;
    }

    /**
     * Perform the logic of user-defined.
     * @param params the params of condition
     * @return the new {@code List<Map>}
     */
    public List<Map<String, Object>> execute(Map<String, Object> params) {
        return kvHandler.extendExecute(params);
    }

    /**
     * Whether perform the logic of default(the execute database).
     * @return boolean
     */
    public boolean isDaoExecute() {
        return kvHandler.isDaoExecute();
    }

    /**
     * Get the SQL of the database.
     * @return the SQL of the database
     */
    public String getSql(Map<String, Object> params) {
        return kvHandler.createSql(params);
    }

    /**
     * Set kvHandler.
     * @param kvHandler the active deal Handler
     */
    public void setKvHandler(KvHandler kvHandler) {
        this.kvHandler = kvHandler;
    }

    /**
     * Get kvHandler.
     * @return the active deal Handler
     */
    public KvHandler getKvHandler() {
        return kvHandler;
    }

    /**
     * Get {@link #defaultValue} that the default value of page.
     * @return the default value
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * Get the default value flag of page.
     * @return the defaultValueFlag
     */
    public int getDefaultValueFlag() {
        return kvHandler.getDefaultValueFlag();
    }

    /**
     * Set the default value of user-defined.
     * @param defaultValue the default value
     */
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
