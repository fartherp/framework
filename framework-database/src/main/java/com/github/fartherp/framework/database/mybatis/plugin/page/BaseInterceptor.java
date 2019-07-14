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
/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.mybatis.plugin.page;

import com.github.fartherp.framework.database.mybatis.plugin.page.dialect.Dialect;
import com.github.fartherp.framework.database.mybatis.plugin.page.dialect.DialectType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;

import java.io.Serializable;
import java.util.Properties;

/**
 * 1. 参数对象转换为Page对象。<br>
 * 2. 配置读取：dialectClass, sqlPattern, pageFieldName
 * @author CK
 * @date 2015/6/14
 */
public abstract class BaseInterceptor implements Interceptor, Serializable {

    protected final Log log = LogFactory.getLog(this.getClass());

    protected Dialect dialect = DialectType.getDialect("mysql");

    protected String dialectClass;

    protected String sqlPattern;

    public MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(),
                ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        String[] keyProperties = ms.getKeyProperties();
        builder.keyProperty(StringUtils.join(keyProperties, ','));
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.cache(ms.getCache());
        return builder.build();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        if (properties.keySet().isEmpty()) {
            return;
        }
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(this);
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        for (Object o : properties.keySet()) {
            PropertyValue propertyValue = new PropertyValue(o.toString(), properties.get(o));
            mutablePropertyValues.addPropertyValue(propertyValue);
        }
        beanWrapper.setPropertyValues(mutablePropertyValues);
        if (StringUtils.isNotBlank(dialectClass)) {
            dialect = DialectType.getDialect(dialectClass);
            if (dialect == null) {
                throw new RuntimeException("方言实例错误");
            }
        }
    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    public void setDialectClass(String dialectClass) {
        this.dialectClass = dialectClass;
    }

    public void setSqlPattern(String sqlPattern) {
        this.sqlPattern = sqlPattern;
    }
}
