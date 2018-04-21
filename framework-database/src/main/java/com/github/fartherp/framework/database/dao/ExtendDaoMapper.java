/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.dao;

import com.github.fartherp.framework.database.mybatis.plugin.page.Pagination;
import com.github.fartherp.framework.database.mybatis.plugin.search.vo.Searchable;
import com.github.fartherp.framework.database.mybatis.plugin.search.vo.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/21
 */
public interface ExtendDaoMapper<T, ID extends Serializable> extends DaoMapper<T, ID> {
    Pagination<T> findBySearchable(Searchable searchable);

    List<T> findBySort(Sort sort);

    long countBySearchable(Searchable searchable);
}
