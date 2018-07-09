/*
 * Copyright (C) 2018 hyssop, Inc. All Rights Reserved.
 */

/*
 * Copyright (C) 2018 hyssop, Inc. All Rights Reserved.
 */

/*
 * Copyright (C) 2018 hyssop, Inc. All Rights Reserved.
 */

/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.service.impl;


import com.github.fartherp.framework.database.dao.DaoMapper;
import com.github.fartherp.framework.database.dao.FieldAccessVo;
import com.github.fartherp.framework.database.service.GenericService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * XML配置Service实现
 * Author: CK
 * Date: 2015/6/5.
 */
public abstract class GenericSqlMapServiceImpl<T extends FieldAccessVo, ID extends Serializable>
        implements GenericService<T, ID> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public abstract DaoMapper<T, ID> getDao();

    @Transactional(rollbackFor = {Exception.class})
    @SuppressWarnings("unchecked")
    public void delete(ID id) {
        Assert.notNull(id, "delete failed due to PrimaryKey is null");
        getDao().deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteBatch(ID[] ids){
        getDao().deleteBatch(ids);
    }

    public List<T> findAll() {
        return getDao().selectAll();
    }

    public T findById(ID id) {
        return getDao().selectByPrimaryKey(id);
    }

    public long count() {
        return getDao().count();
    }

    @Transactional(rollbackFor = {Exception.class})
    @SuppressWarnings("unchecked")
    public ID saveEntity(T entity) {
        Assert.notNull(entity, "save entity failed due to entity is null");
        getDao().insert(entity);
        return (ID) entity.primaryKey();
    }

    @Transactional(rollbackFor = {Exception.class})
    @SuppressWarnings("unchecked")
    public ID saveEntitySelective(T entity) {
        Assert.notNull(entity, "save entity failed due to entity is null");
        getDao().insertSelective(entity);
        return (ID) entity.primaryKey();
    }

    @Transactional(rollbackFor = {Exception.class})
    public void saveBatch(List<T> entitys) {
        Assert.notNull(entitys, "saveBatch entitys failed due to entitys is null");
        if (CollectionUtils.isNotEmpty(entitys)) {
            getDao().insertBatch(entitys);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateEntity(T entity) {
        Assert.notNull(entity, "update entity failed due to entity is null");
        getDao().updateByPrimaryKey(entity);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateEntitySelective(T entity) {
        Assert.notNull(entity, "update entity failed due to entity is null");
        getDao().updateByPrimaryKeySelective(entity);
    }

    @Transactional(rollbackFor = {Exception.class})
    public T saveOrUpdate(T entity) {
        Assert.notNull(entity, "save or update entity failed due to entity is null");
        ID id = entity.primaryKey();
        if (id == null) {
            saveEntity(entity);
        } else {
            if (findById(id) != null) {
                updateEntity(entity);
            } else {
                saveEntity(entity);
            }
        }
        return entity;
    }

    @Transactional(rollbackFor = {Exception.class})
    public T saveOrUpdateSelective(T entity) {
        Assert.notNull(entity, "save or update entity failed due to entity is null");
        ID id = entity.primaryKey();
        if (id == null) {
            saveEntitySelective(entity);
        } else {
            if (findById(id) != null) {
                updateEntitySelective(entity);
            } else {
                saveEntitySelective(entity);
            }
        }
        return entity;
    }
}
