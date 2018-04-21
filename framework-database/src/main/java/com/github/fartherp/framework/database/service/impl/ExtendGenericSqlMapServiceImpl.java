/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.service.impl;

import com.github.fartherp.framework.database.dao.ExtendDaoMapper;
import com.github.fartherp.framework.database.dao.FieldAccessVo;
import com.github.fartherp.framework.database.mybatis.plugin.page.Pagination;
import com.github.fartherp.framework.database.mybatis.plugin.search.vo.Searchable;
import com.github.fartherp.framework.database.mybatis.plugin.search.vo.Sort;
import com.github.fartherp.framework.database.service.ExtendGenericService;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/21
 */
public abstract class ExtendGenericSqlMapServiceImpl<T extends FieldAccessVo, ID extends Serializable>
        extends GenericSqlMapServiceImpl<T, ID> implements ExtendGenericService<T, ID> {

    public abstract ExtendDaoMapper<T, ID> getDao();

    /**
     * 条件查询 searchable
     *
     * @param searchable
     * @return
     */
    public Pagination<T> findBySearchable(Searchable searchable) {
        return getDao().findBySearchable(searchable);
    }

    /**
     * 条件查询 sort
     *
     * @param sort
     * @return
     */
    public List<T> findBySort(Sort sort) {
        return getDao().findBySort(sort);
    }

    /**
     * 根据条件统计所有记录数
     *
     * @param searchable
     * @return
     */
    public long countBySearchable(Searchable searchable) {
        return getDao().countBySearchable(searchable);
    }
}

