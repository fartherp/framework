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
package com.github.fartherp.framework.database.mybatis.plugin.page;


import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;

/**
 * 数据库分页插件，只拦截查询语句.
 * Author: CK
 * Date: 2015/6/14
 */
@Intercepts({ @Signature(type = Executor.class, method = "query",
        args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PaginationInterceptor extends BaseInterceptor {

    public static final String MAP_PAGE_FIELD = Pagination.MAP_PAGE_FIELD;

    public static final String PAGE = ".*findPage*.*";

    public PaginationInterceptor() {
        sqlPattern = PAGE;
    }

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (mappedStatement.getId().matches(sqlPattern)) {
            // 拦截需要分页的SQL
            Object parameter = invocation.getArgs()[1];
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String originalSql = boundSql.getSql().trim();
            Object parameterObject = boundSql.getParameterObject();

            // 分页参数--上下文传参
            Pagination<Serializable> page = null;

            // map传参每次都将currentPage重置,先判读map再判断context
            if (parameterObject != null) {
                page = convertParameter(parameterObject, page);
            }

            // 后面用到了context的东东
            if (page != null) {
                int totPage = page.getTotal();
                // 得到总记录数
                if (totPage <= 0) {
                    Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource()
                            .getConnection();
                    totPage = SqlHelper.getCount(originalSql, connection, mappedStatement, parameterObject, boundSql);
                }

                // 分页计算
                page.init(totPage, page.getLimit(), page.getCurrentPage());

                String pageSql = SqlHelper.generatePageSql(originalSql, page, dialect);
                log.info("分页SQL:" + pageSql);
                invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
                BoundSql newBoundSql = SqlHelper.createNewBoundSql(mappedStatement, boundSql.getParameterObject(),
                        boundSql, pageSql);
                MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));

                invocation.getArgs()[0] = newMs;
            }
        }
        return invocation.proceed();
    }

    /**
     * 对参数进行转换和检查
     *
     * @param parameterObject 参数对象
     * @param pagination      参数VO
     * @return 参数VO
     */
    private Pagination convertParameter(Object parameterObject, Pagination pagination) {
        if (parameterObject instanceof Pagination) {
            pagination = (Pagination) parameterObject;
        } else if (parameterObject instanceof Map) {
            Map parameterMap = (Map) parameterObject;
            pagination = (Pagination) parameterMap.get(MAP_PAGE_FIELD);
            if (pagination == null) {
                throw new PersistenceException("分页参数不能为空");
            }
            parameterMap.put(MAP_PAGE_FIELD, pagination);
        }
        return pagination;
    }
}
