/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.orm.mybatis.plugin.search;

import com.github.fartherp.framework.core.orm.mybatis.plugin.page.dialect.Dialect;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.resolver.AbstractSqlResolverOuter;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.resolver.DelegeteSqlResolver;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.vo.Searchable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * setParameters 设置参数 getCount 查询总记录数 generatePageSql 生成分页查询SQL
 * Author: CK
 * Date: 2015/6/14
 */
public class SqlFacade {

    protected static Log log = LogFactory.getLog(SqlFacade.class);

    public static DelegeteSqlResolver DEFAULTDELEGETE = new DelegeteSqlResolver(null);

    public static AbstractSqlResolverOuter delegeteSqlResolver = DEFAULTDELEGETE;

    public void setDelegeteSqlResolver(AbstractSqlResolverOuter sqlResolver) {
        this.delegeteSqlResolver = sqlResolver;
    }

    public static String generateRealSql(String originalSql, Searchable parameter, Dialect dialect) {
        StringBuilder sb = new StringBuilder(originalSql);
        //条件拼接
        delegeteSqlResolver.compositeSql(sb, parameter, dialect);
        return sb.toString();
    }

    /**
     * 分页字符串拼接
     */
    public static String generateRealPageSql(String sql, Searchable searchable, Dialect dialect) {
        if (dialect.supportsLimit() && null != searchable.getPage()) {
            int pageSize = searchable.getPage().getLimit();
            int index = (searchable.getPage().getCurrentPage() - 1) * pageSize;
            int start = index < 0 ? 0 : index;
            return dialect.getLimitString(sql, start, pageSize);
        } else {
            return sql;
        }
    }
}
