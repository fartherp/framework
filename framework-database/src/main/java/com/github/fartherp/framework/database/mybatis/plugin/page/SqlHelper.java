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
 * Copyright (C) 2018 hyssop, Inc. All Rights Reserved.
 */

/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.mybatis.plugin.page;

import com.github.fartherp.framework.common.util.ReflectUtil;
import com.github.fartherp.framework.database.mybatis.plugin.page.dialect.Dialect;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/9/8
 */
public class SqlHelper {
    protected static Log log = LogFactory.getLog(SqlHelper.class);

    /**
     * 对SQL参数(?)设值,
     * 参考org.apache.ibatis.executor.parameter.DefaultParameterHandler。
     * @param ps              表示预编译的 SQL 语句的对象。
     * @param mappedStatement MappedStatement
     * @param boundSql        SQL
     * @param parameterObject 参数对象
     * @throws SQLException 数据库异常
     */
    @SuppressWarnings("unchecked")
    public static void setParameters(PreparedStatement ps, MappedStatement mappedStatement,
                                     BoundSql boundSql, Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = (parameterObject == null ? null : configuration.newMetaObject(parameterObject));
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
                        value = (metaObject == null ? null : metaObject.getValue(propertyName));
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter "
                                + propertyName + " of statement " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }

    /**
     * 查询总纪录数。
     *
     * @param sql             SQL语句
     * @param connection      数据库连接
     * @param mappedStatement mapped
     * @param parameterObject 参数
     * @param boundSql        boundSql
     * @return 总记录数
     */
    public static int getCount(String sql, Connection connection, MappedStatement mappedStatement,
                               Object parameterObject, BoundSql boundSql) {
        String countSql = "select count(1) from (" + excludeOrderBy(sql) + ") as tmp_count";
        log.debug("Pagination TotalCount SQL: " + countSql);

        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            connection.setReadOnly(true);
            connection.setAutoCommit(true);
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = createNewBoundSql(mappedStatement, parameterObject, boundSql, countSql);
            setParameters(countStmt, mappedStatement, countBS, parameterObject);
            rs = countStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return 0;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (countStmt != null) {
                    countStmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("", e);
            }
        }
    }

    /**
     * 根据BoundSql 创建新 BoundSql
     *
     * @param mappedStatement the mappedStatement
     * @param parameterObject 参数对象
     * @param boundSql        the boundSql
     * @param newSql          the newSql
     * @return boundSql
     */
    public static BoundSql createNewBoundSql(MappedStatement mappedStatement, Object parameterObject,
                                             BoundSql boundSql, String newSql) {
        BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), newSql,
                boundSql.getParameterMappings(), parameterObject);
        setValue(countBS, boundSql, "metaParameters", "additionalParameters");
        return countBS;
    }

    private static void setValue(BoundSql countBS, BoundSql boundSql, String... properties) {
        for (String p : properties) {
            Field metaParamsField = ReflectUtil.getFieldByFieldName(boundSql, p);
            Object mo = ReflectUtil.getValueByFieldName(boundSql, metaParamsField);
            ReflectUtil.setValueByFieldName(countBS, p, mo);
        }
    }

    /**
     * excludeOrderBy:去除sql中的order by，避免大数据分页查询时的性能问题. <br/>
     *
     * @param sql the sql
     * @return sql
     */
    private static String excludeOrderBy(String sql) {
        if (StringUtils.isBlank(sql)) {
            return sql;
        }
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        if (sb.length() == 0) {
            return sql;
        }
        return sb.toString();
    }

    /**
     * 根据数据库方言，生成特定的分页sql。
     *
     * @param sql     Mapper中的Sql语句
     * @param page    分页对象
     * @param dialect 方言类型
     * @return 分页SQL
     */
    public static String generatePageSql(String sql, Pagination<Serializable> page, Dialect dialect) {
        if (dialect.supportsLimit()) {
            int pageSize = page.getLimit();
            int index = (page.getCurrentPage() - 1) * pageSize;
            int start = index < 0 ? 0 : index;
            return dialect.getLimitString(sql, start, pageSize);
        } else {
            return sql;
        }
    }
}
