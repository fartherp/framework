/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.dao;

import com.github.fartherp.framework.core.orm.mybatis.plugin.page.Pagination;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.vo.Searchable;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.vo.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * dao基本方法接口
 * Author: CK
 * Date: 2015/6/5.
 */
public interface SqlMapDao<T, ID extends Serializable> {
    /**
     * 通过主键查询
     * @param id 主键
     * @return 存在：返回对象，否则，返回null
     */
    T findById(ID id);

    /**
     * 删除
     * @param id 主键
     */
    void delete(ID id);

    /**
     * 批量新增
     * @param entitys 集合
     */
    void saveBatch(List<T> entitys);

    /**
     * 新增
     * @param entity entity bean
     */
    void save(T entity);

    /**
     * 主键存在更新，否则新增
     * @param entity entity bean
     */
    void saveOrUpdate(T entity);

    /**
     * 根据主键修改
     * @param entity entity bean
     */
    void update(T entity);

    /**
     * 查询所有对象
     * @return all entities
     */
    List<T> findAll();

    /**
     * 清除缓存
     */
    void flush();

    /**
     * 保存对象
     * @param entity entity bean
     */
    void saveSelective(T entity);

    /**
     * 更新对象
     * @param entity entity bean
     */
    void updateSelective(T entity);

    /**
     * 保存或更新(如果对象已存在)
     * @param entity entity bean
     */
    void saveOrUpdateSelective(T entity);

    /**
     * 分页查询
     *
     * @param pagination 分页对象
     * @param selectCount 查询数量对象（SQL及条件）
     * @param select 结果列表对象（SQL及条件）
     */
    Pagination page(Pagination pagination, SqlCallback selectCount, SqlCallback select);

    void deleteBatch(ID... ids);

    Pagination<T> findBySearchable(Searchable searchable);

    List<T> findBySort(Sort sort);

    long countBySearchable(Searchable searchable);

    /**
     * sql call back interface.
     */
    public interface SqlCallback {
        /**
         * get the maped sql id
         *
         * @return the mapped sql id
         */
        String getSqlId();

        /**
         * get the paramters with sql id
         *
         * @return the paramters with sql id
         */
        Object getParameters();
    }
}
