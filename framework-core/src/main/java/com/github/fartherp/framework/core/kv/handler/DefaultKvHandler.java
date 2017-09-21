/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.kv.handler;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.stringtemplate.v4.ST;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/11/13
 */
public abstract class DefaultKvHandler implements KvHandler {
    /**
     * 名称
     */
    protected Integer name;
    /**
     * 表名
     */
    protected String tableName;
    /**
     * Key 字段
     */
    protected String keyField;
    /**
     * Value 字段
     */
    protected String valueField;
    /**
     * 自定义处理器
     */
    protected Function<Map<String, Object>, List<Map<String, Object>>> fn;

    protected Map<String, Object> extraFields;

    protected boolean daoExecuteFlag = true;

    protected int type;

    /**
     * 默认值标志0:无,1:加全部,2:第一条为默认值
     */
    protected int defaultValueFlag;

    public DefaultKvHandler(Integer name, String tableName, String keyField, String valueField,
                            Map<String, Object> extraFields, int type, boolean daoExecuteFlag,
                            int defaultValueFlag) {
        Preconditions.checkArgument(name != null, "class [" + this.getName() + "] the missing name");
        Preconditions.checkArgument(StringUtils.isNotBlank(keyField), "class [" + this.getName() + "] the missing keyField");
        Preconditions.checkArgument(StringUtils.isNotBlank(valueField), "class [" + this.getName() + "] the missing valueField");
        this.type = type;
        this.name = name;
        this.tableName = tableName;
        this.keyField = keyField;
        this.valueField = valueField;
        this.extraFields = extraFields;
        this.daoExecuteFlag = daoExecuteFlag;
        this.defaultValueFlag = defaultValueFlag;
        this.setupTemplates();
    }

    public ST setSql(int type) {
        ST sql = null;
        switch (type) {
            case IN :
                sql = new ST("SELECT `<key>`, `<value>` FROM `<table>` WHERE `<key>` IN (:inset) "
                        + " <extras:{t| AND `<t.field>` = :<t.field> }> ");
                break;
            case ALL :
                sql = new ST("SELECT `<key>`, `<value>` FROM `<table>` WHERE 1 = 1 "
                        + " <extras:{t| AND `<t.field>` = :<t.field> }>");
                break;
            case PREFIX_SQL :
                sql = new ST("SELECT `<key>`, `<value>` FROM `<table>` WHERE `<key>` LIKE :prefix "
                        + "<extras:{t| AND `<t.field>` = :<t.field> }> LIMIT :maxLimit");
                break;
            default:
                break;
        }
        return sql;
    }

    protected void setupTemplates() {

    }

    protected void setupTemplates(ST sql, Map<String, Object> params) {
        sql.add("key", this.keyField);
        sql.add("value", this.valueField);
        sql.add("table", this.tableName);
        params.putAll(extraFields);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            } else if ("inset".equals(entry.getKey()) || "prefix".equals(entry.getKey())
                    || "maxLimit".equals(entry.getKey())) {
                continue;
            }
            sql.addAggr("extras.{field}", entry.getKey());
        }
    }

    public List<Map<String, Object>> extendExecute(Map<String, Object> params) {
        return fn.apply(params);
    }

    public boolean isDaoExecute() {
        return daoExecuteFlag;
    }

    public String createSql(Map<String, Object> params) {
        ST sql = setSql(type);
        setupTemplates(sql, params);
        return sql.render();
    }

    public Integer getName() {
        return name;
    }

    public String getKeyField() {
        return keyField;
    }

    public String getValueField() {
        return valueField;
    }

    public int getDefaultValueFlag() {
        return defaultValueFlag;
    }
}
