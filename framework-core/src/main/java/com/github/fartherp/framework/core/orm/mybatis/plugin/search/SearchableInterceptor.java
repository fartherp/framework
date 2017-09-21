/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.orm.mybatis.plugin.search;

import com.github.fartherp.framework.core.orm.mybatis.plugin.BaseInterceptor;
import com.github.fartherp.framework.core.orm.mybatis.plugin.SqlHelper;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.vo.Searchable;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 查询插件 根据searchble接口的查询条件动态调整查询sql完成查询(包括了条件、排序和分页条件)
 * Author: hassop
 * Date: 2016/9/06
 */
@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class SearchableInterceptor extends BaseInterceptor {

    public static final String SEARCHABLE = ".*BySearchable*.*";

    public SearchableInterceptor() {
        sqlPattern = SEARCHABLE;
    }

    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

        if (mappedStatement.getId().matches(sqlPattern)) {

            Object parameter = invocation.getArgs()[1];

            if (!Searchable.class.isAssignableFrom(parameter.getClass())) {
                return invocation.proceed();
            }

            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String originalSql = boundSql.getSql().trim();
            //parameter is searhabel
            String realSql = SqlFacade.generateRealSql(originalSql, (Searchable) parameter, dialect);

            log.info("最终 的sql为:" + realSql);

            invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);

            BoundSql newBoundSql = SqlHelper.createNewBoundSql(mappedStatement, boundSql.getParameterObject(),
                    boundSql, realSql);

            MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));

            invocation.getArgs()[0] = newMs;

        }
        return invocation.proceed();
    }
}
