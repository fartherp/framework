/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.service.annotation;

import com.github.fartherp.framework.core.dao.FieldAccessVo;
import com.github.fartherp.framework.core.dao.SqlMapDao;
import com.github.fartherp.framework.core.service.GenericService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * 注解配置Service实现
 * 只适合单数据源（默认名称）
 * Author: CK
 * Date: 2015/6/5.
 */
@Service
@Transactional
public abstract class AnnotationGenericSqlMapServiceImpl<T extends FieldAccessVo, ID extends Serializable>
        implements GenericService<T, ID> {

    public abstract SqlMapDao<T, ID> getDao();

    @SuppressWarnings("unchecked")
    public void delete(T entity) {
        Assert.notNull(entity, "delete failed due to entity is null");
        getDao().delete((ID) entity.getPrimaryKey());
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Transactional(readOnly = true)
    public T findById(ID id) {
        return getDao().findById(id);
    }

    @SuppressWarnings("unchecked")
    public ID saveEntity(T entity) {
        Assert.notNull(entity, "save entity failed due to entity is null");
        getDao().save(entity);
        return (ID) entity.getPrimaryKey();
    }

    @SuppressWarnings("unchecked")
    public ID saveEntitySelective(T entity) {
        Assert.notNull(entity, "save entity failed due to entity is null");
        getDao().saveSelective(entity);
        return (ID) entity.getPrimaryKey();
    }

    public void updateEntity(T entity) {
        Assert.notNull(entity, "update entity failed due to entity is null");
        getDao().update(entity);
    }

    public void updateEntitySelective(T entity) {
        Assert.notNull(entity, "update entity failed due to entity is null");
        getDao().updateSelective(entity);
    }

    public T saveOrUpdate(T entity) {
        Assert.notNull(entity, "save or update entity failed due to entity is null");
        getDao().saveOrUpdate(entity);
        return entity;
    }

    public T saveOrUpdateSelective(T entity) {
        Assert.notNull(entity, "save or update entity failed due to entity is null");
        getDao().saveOrUpdateSelective(entity);
        return entity;
    }

    public void saveBatch(List<T> entitys) {
        Assert.notNull(entitys, "saveBatch entitys failed due to entitys is null");
        getDao().saveBatch(entitys);
    }
}
