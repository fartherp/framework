/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.service;


import com.github.fartherp.framework.database.mybatis.plugin.page.Pagination;
import com.github.fartherp.framework.database.mybatis.plugin.search.vo.Searchable;
import com.github.fartherp.framework.database.mybatis.plugin.search.vo.Sort;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/21
 */
public interface ExtendGenericService<T, ID> extends GenericService<T, ID> {
    /**
     * 条件查询 searchable
     * @param searchable
     * @return
     */
    Pagination<T> findBySearchable(Searchable searchable);

    /**
     * 条件查询 sort
     * @param sort
     * @return
     */
    List<T> findBySort(Sort sort);

    /**
     * 根据条件统计所有记录数
     *
     * @param searchable
     * @return
     */
    long countBySearchable(Searchable searchable);
}
