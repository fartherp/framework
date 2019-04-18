/*
 * Copyright (C) 2018 hyssop, Inc. All Rights Reserved.
 */

/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.dao;

import java.io.Serializable;
import java.util.List;

/**
 * insert、delete、update、select method SQL of XML Mapper file
 * Author: CK
 * Date: 2015/6/5.
 */
public interface DaoMapper<T, ID extends Serializable> {
    /**
     * delete action by primary key
     *
     * @param id primary key
     * @return delete result. 1 means success
     */
    int deleteByPrimaryKey(ID id);

    /**
     * do insert entity
     *
     * @param record entity bean to insert
     * @return insert result 1 means success
     */
    int insert(T record);

    /**
     * do insert entity ignore null property
     *
     * @param record entity bean to insert
     * @return insert result 1 means success
     */
    int insertSelective(T record);

    /**
     * find entity by primary key
     *
     * @param id primary key
     * @return entity bean
     */
    T selectByPrimaryKey(ID id);

    /**
     * update entity by primary key ignore null property
     *
     * @param record entity bean
     * @return effective count
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * update entity by primary key
     *
     * @param record entity bean
     * @return effective count
     */
    int updateByPrimaryKey(T record);

    /**
     * do select count(*) mapped id
     *
     * @return record count
     */
    long count();

    /**
     * find all records
     *
     * @return all records
     */
    List<T> selectAll();

    /**
     * save batch
     */
    void insertBatch(List<T> list);

    /**
     * delete batch
     * @param ids ids
     */
    void deleteBatch(List<ID> ids);
}
