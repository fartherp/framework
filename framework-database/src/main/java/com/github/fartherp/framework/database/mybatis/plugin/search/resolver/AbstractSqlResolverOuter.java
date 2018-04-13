/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.mybatis.plugin.search.resolver;


import com.github.fartherp.framework.database.mybatis.plugin.page.dialect.Dialect;
import com.github.fartherp.framework.database.mybatis.plugin.search.vo.Searchable;

/**
 * Created by IntelliJ IDEA .
 * Auth: hyssop
 * Date: 2016/9/6 0006
 */
public abstract class AbstractSqlResolverOuter {

    public abstract void compositeSql(StringBuilder query, Searchable search, Dialect dialect) ;
}
