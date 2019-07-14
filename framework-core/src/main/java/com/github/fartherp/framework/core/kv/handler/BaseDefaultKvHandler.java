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
package com.github.fartherp.framework.core.kv.handler;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/11/13
 */
public abstract class BaseDefaultKvHandler implements KvHandler {
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

    public BaseDefaultKvHandler(Integer name, String tableName, String keyField, String valueField,
		Map<String, Object> extraFields, int type, boolean daoExecuteFlag, int defaultValueFlag) {
        Preconditions.checkArgument(name != null,
			"class [" + this.getName() + "] the missing name");
        Preconditions.checkArgument(StringUtils.isNotBlank(keyField),
			"class [" + this.getName() + "] the missing keyField");
        Preconditions.checkArgument(StringUtils.isNotBlank(valueField),
			"class [" + this.getName() + "] the missing valueField");
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

    @Override
	public List<Map<String, Object>> extendExecute(Map<String, Object> params) {
        return fn.apply(params);
    }

    @Override
	public boolean isDaoExecute() {
        return daoExecuteFlag;
    }

    @Override
	public String createSql(Map<String, Object> params) {
        ST sql = setSql(type);
        setupTemplates(sql, params);
        return sql.render();
    }

    @Override
	public Integer getName() {
        return name;
    }

    @Override
	public String getKeyField() {
        return keyField;
    }

    @Override
	public String getValueField() {
        return valueField;
    }

    @Override
	public int getDefaultValueFlag() {
        return defaultValueFlag;
    }
}
