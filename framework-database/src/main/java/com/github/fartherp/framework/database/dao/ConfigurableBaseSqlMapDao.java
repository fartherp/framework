/*
 * Copyright (C) 2018 hyssop, Inc. All Rights Reserved.
 */


package com.github.fartherp.framework.database.dao;


import com.github.fartherp.framework.database.mybatis.orm.SqlMapDaoSupport;
import com.github.fartherp.framework.database.mybatis.plugin.page.BasePagination;
import com.github.fartherp.framework.database.mybatis.plugin.page.Pagination;
import com.github.fartherp.framework.database.mybatis.plugin.search.vo.Searchable;
import com.github.fartherp.framework.database.mybatis.plugin.search.vo.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * dao基础类,实现基础增删改查功能
 * Author: CK
 * Date: 2015/6/5.
 */
public abstract class ConfigurableBaseSqlMapDao<T extends FieldAccessVo, ID extends Serializable>
        extends SqlMapDaoSupport {

    public abstract SqlMapDao<T,ID> getSqlMapDao();
    public void flush() {
        getSqlSession().clearCache();
    }


    public void delete(ID id) {
        getSqlMapDao().delete(id);
    }

    public List<T> findAll() {
        return getSqlMapDao().findAll();
    }

    public T findById(ID id) {
        return getSqlMapDao().findById(id);
    }

    public void save(T entity) {
        getSqlMapDao().save(entity);
    }

    public void saveSelective(T entity) {
        getSqlMapDao().saveSelective(entity);
    }

    public void update(T entity) {
        getSqlMapDao().updateSelective(entity);
    }

    public void updateSelective(T entity) {
        getSqlMapDao().saveOrUpdateSelective(entity);
    }

    public void saveOrUpdate(T entity) {
        ID id = entity.getPrimaryKey();
        if (id == null) {
            save(entity);
        } else {
            if (findById(id) != null) {
                update(entity);
            } else {
                save(entity);
            }
        }
    }

    public void saveOrUpdateSelective(T entity) {
        ID id = entity.getPrimaryKey();
        if (id == null) {
            saveSelective(entity);
        } else {
            if (findById(id) != null) {
                updateSelective(entity);
            } else {
                saveSelective(entity);
            }
        }
    }

    public void saveBatch(List<T> entitys) {
        getSqlMapDao().insertBatch(entitys);
    }

    @SuppressWarnings("unchecked")
    public Pagination page(Pagination pagination, SqlMapDao.SqlCallback selectCount, SqlMapDao.SqlCallback select) {
        int totalCount = getSqlSession().selectOne(selectCount.getSqlId(), selectCount.getParameters());

        List dataList = getSqlSession().selectList(select.getSqlId(), select.getParameters());
        pagination.init(totalCount, pagination.getLimit(), pagination.getCurrentPage());
        pagination.setRows(dataList);
        return pagination;
    }

    public void deleteBatch(ID[] ids) {
        getSqlMapDao().deleteBatch(ids);
    }

    @SuppressWarnings("unchecked")
    public Pagination<T> findBySearchable(Searchable searchable) {
        Pagination<T> content = getSqlMapDao().findBySearchable(searchable);
        int currentPage = searchable.hasPageable() ? searchable.getPage().getCurrentPage() : 1;
        Pagination pagination = searchable.getPage();
        long total = searchable.hasPageable() ? countBySearchable(searchable.setPage(null)) : content.getTotal();
        searchable.setPage(pagination);
        return new BasePagination<T>(content.getRows(), Integer.parseInt(String.valueOf(total)), currentPage);
    }

    public List<T> findBySort(Sort sort) {
        return getSqlMapDao().findBySort(sort);
    }

    public long countBySearchable(Searchable searchable) {
        return getSqlMapDao().countBySearchable(searchable);
    }


}
