/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.database.service.impl;

import com.github.fartherp.framework.database.dao.DaoMapper;
import com.github.fartherp.framework.database.dao.BaseFieldAccessVo;
import com.github.fartherp.framework.database.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * XML配置Service实现
 * @author CK
 * @date 2015/6/5.
 */
public abstract class BaseGenericSqlMapServiceImpl<T extends BaseFieldAccessVo<ID>, ID extends Serializable>
        implements GenericService<T, ID> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * this get DaoMapper
	 * @return DaoMapper
	 */
	public abstract DaoMapper<T, ID> getDao();

    @Transactional(rollbackFor = Exception.class)
	@Override
    public void delete(ID id) {
        Assert.notNull(id, "delete failed due to PrimaryKey is null");
        getDao().deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
	@Override
    public void deleteBatch(List<ID> ids){
    	if (ids != null && !ids.isEmpty()) {
        	getDao().deleteBatch(ids);
		}
    }

	@Override
    public List<T> findAll() {
        return getDao().selectAll();
    }

	@Override
    public T findById(ID id) {
        return getDao().selectByPrimaryKey(id);
    }

	@Override
    public long count() {
        return getDao().count();
    }

    @Transactional(rollbackFor = Exception.class)
	@Override
    public ID saveEntity(T entity) {
        Assert.notNull(entity, "save entity failed due to entity is null");
        getDao().insert(entity);
        return entity.primaryKey();
    }

    @Transactional(rollbackFor = Exception.class)
	@Override
    public ID saveEntitySelective(T entity) {
        Assert.notNull(entity, "save entity failed due to entity is null");
        getDao().insertSelective(entity);
        return entity.primaryKey();
    }

    @Transactional(rollbackFor = Exception.class)
	@Override
    public void saveBatch(List<T> entitys) {
        if (entitys!= null && !entitys.isEmpty()) {
            getDao().insertBatch(entitys);
        }
    }

    @Transactional(rollbackFor = Exception.class)
	@Override
    public void updateEntity(T entity) {
        Assert.notNull(entity, "update entity failed due to entity is null");
        Assert.notNull(entity.primaryKey(), "update entity failed due to entity id is null");
        getDao().updateByPrimaryKey(entity);
    }

    @Transactional(rollbackFor = Exception.class)
	@Override
    public void updateEntitySelective(T entity) {
        Assert.notNull(entity, "update entity failed due to entity is null");
        Assert.notNull(entity.primaryKey(), "update entity failed due to entity id is null");
        getDao().updateByPrimaryKeySelective(entity);
    }

    @Transactional(rollbackFor = Exception.class)
	@Override
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

    @Transactional(rollbackFor = Exception.class)
	@Override
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
