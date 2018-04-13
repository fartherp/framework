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
public interface SqlResolver {

    void prepareSQL(StringBuilder ql, Searchable search);

    void prepareOrder(StringBuilder ql, Searchable search);

    /**
     * 根据search给query赋值及设置分页信息
     *
     * @param query
     * @param search
     */
    void setValues(StringBuilder query, Searchable search);

    void setPageable(StringBuilder query, Searchable search, Dialect dialect);

}
