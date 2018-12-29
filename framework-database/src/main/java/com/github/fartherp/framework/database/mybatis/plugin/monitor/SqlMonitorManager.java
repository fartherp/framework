/*
 * Copyright (C) 2018 hyssop, Inc. All Rights Reserved.
 */

package com.github.fartherp.framework.database.mybatis.plugin.monitor;

import com.github.fartherp.framework.database.mybatis.orm.SqlEvent;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/8/27
 */
@Intercepts({
        @Signature(type = Executor.class, method = SqlMonitorManager.UPDATE, args = { MappedStatement.class,
                Object.class }),
        @Signature(type = Executor.class, method = SqlMonitorManager.QUERY, args = { MappedStatement.class,
                Object.class, RowBounds.class, ResultHandler.class }) })
public class SqlMonitorManager implements Interceptor {

    /**
     * query method name of {@link Executor}
     */
    public static final String QUERY = "query";
    /**
     * update method name of {@link Executor}
     */
    public static final String UPDATE = "update";

    /**
     * log this class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlMonitorManager.class);

    /**
     * show SQL property name
     */
    private static final String SHOW_SQL_PROP = "show_sql";

    /**
     * max parameters count support
     */
    private static final String DEFAULT_MAX_PARAMETERS_SIZE_PROP = "maxParameters";

    /**
     * call back class name
     */
    private static final String MONITOR_CALLBACK_PROP = "callbackClass";

    /**
     * default max parameters count
     */
    private static final int DEFAULT_MAX_PARAMETERS = 10000;
    /**
     * max parameters count support
     */
    private int maxParameters = DEFAULT_MAX_PARAMETERS;

    /**
     * {@link SqlEvent} instance
     */
    private SqlEvent callback;

    /**
     * show SQL status
     */
    private boolean showSql = false;

    /* (non-Javadoc)
     * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
     */

    public Object intercept(Invocation invocation) throws Throwable {
        Date begin = new Date();
        Date end;
        Object result = null;
        int queryRows = -1;
        Throwable t = null;
        try {
            result = invocation.proceed();
            queryRows = getQueryRows(invocation, result);
        } catch (Throwable e) {
            t = e;
            throw e;
        } finally {
            end = new Date();
            String sql = monitorExcuteSql(invocation);

            // call back
            if (callback != null) {
                callback.onExecuteSql(sql, begin, (System.currentTimeMillis() - end.getTime()), queryRows, t);
            }
        }
        return result;
    }

    /**
     * get query return rows size
     *
     * @param invocation method invocation
     * @param result query result
     * @return row size
     */
    protected int getQueryRows(Invocation invocation, Object result) {

        // only query
        if (QUERY.equals(invocation.getMethod().getName())) {
            if (result == null) {
                return 0;
            }
            if (result instanceof Collection) {
                return ((Collection) result).size();
            }
        }

        return -1;
    }

    /**
     * do monitor for SQL print
     *
     * @param invocation method invocation
     * @return SQL in string
     */
    private String monitorExcuteSql(Invocation invocation) {
        Object[] args = invocation.getArgs();
        if (args != null && args.length > 1) {
            MappedStatement ms = (MappedStatement) args[0];
            ms = ms.getConfiguration().getMappedStatement(ms.getId());
            // get BoundSql
            BoundSql boundSql = ms.getBoundSql(args[1]);
            String sql = boundSql.getSql();
            try {
                sql = setParameters(ms, boundSql, args[1]);
            } catch (Exception e) {
                LOGGER.debug(e.getMessage(), e);
            }
            sql = formatSql(sql);
            if (showSql) {
                LOGGER.info(sql);
                System.out.println(sql);
            }

            return sql;
        }
        return null;
    }

    /**
     * Formats SQL by remove "\n" and "\t"
     *
     * @param sql SQL in string
     * @return SQL after format
     */
    private static String formatSql(String sql) {
        sql = StringUtils.replace(sql, "\n", " ");
        sql = StringUtils.replace(sql, "\t", " ");
        return sql;
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
     */

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
     */

    public void setProperties(Properties properties) {
        if (properties == null) {
            return;
        }
        if (properties.containsKey(SHOW_SQL_PROP)) {
            String value = properties.getProperty(SHOW_SQL_PROP);
            if (Boolean.TRUE.toString().equals(value)) {
                showSql = true;
            }
        }
        if (properties.containsKey(DEFAULT_MAX_PARAMETERS_SIZE_PROP)) {
            String value = properties.getProperty(DEFAULT_MAX_PARAMETERS_SIZE_PROP);
            this.maxParameters = NumberUtils.toInt(value, DEFAULT_MAX_PARAMETERS);
        }

        // call back class
        if (properties.containsKey(MONITOR_CALLBACK_PROP)) {
            String value = properties.getProperty(MONITOR_CALLBACK_PROP);
            try {
                Class c = Class.forName(value);
                if (SqlEvent.class.isAssignableFrom(c)) {
                    callback = (SqlEvent) c.newInstance();
                } else {
                    throw new RuntimeException("error to initialize property '" + MONITOR_CALLBACK_PROP
                            + "', class must implements " + SqlEvent.class.getName());
                }
            } catch (Exception e) {
                throw new RuntimeException("error to initialize property '" + MONITOR_CALLBACK_PROP + "'", e);
            }

        }
    }

    /**
     * set parameters.
     *
     * @param mappedStatement {@link MappedStatement} instance.
     * @param boundSql {@link BoundSql} instance
     * @param parameterObject parameter object
     * @return SQL in string
     * @throws SQLException in case of any SQL operation exception
     */
    public String setParameters(MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject)
            throws SQLException {

        Configuration configuration = mappedStatement.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql();
        if (parameterMappings != null) {
            if (parameterMappings.size() > this.maxParameters) {
                LOGGER.warn("ingore set parameters due to too much parameters for sql id[" + mappedStatement.getId()
                        + "] max is " + maxParameters + " current is " + parameterMappings.size());
                return sql;
            }

            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            String[] parameters = new String[parameterMappings.size()];
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value).getValue(
                                    propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName
                                + " of statement " + mappedStatement.getId());
                    }
                    parameters[i] = formatValue(value);
                }
            }

            sql = getSql(sql, parameters);
        }
        return sql;
    }

    /**
     * To generate SQL in string
     *
     * @param sql perpared SQL in String
     * @param parameters parameters array
     * @return SQL in string
     */
    private String getSql(String sql, String[] parameters) {
        int len = sql.length();
        StringBuilder builder = new StringBuilder(len * 5);
        int pos = 0;
        for (int i = 0; i < len; i++) {
            char c = sql.charAt(i);
            if (c == '?') {
                builder.append(parameters[pos++]);
            } else {
                builder.append(c);
            }

        }

        return builder.toString();
    }

    /**
     * Converts value in string
     *
     * @param value target value
     * @return value in string
     */
    private String formatValue(Object value) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof Number) {
            return value.toString();
        }
        return "'" + value.toString() + "'";
    }
}
