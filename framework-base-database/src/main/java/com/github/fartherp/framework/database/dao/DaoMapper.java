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
package com.github.fartherp.framework.database.dao;

import java.io.Serializable;
import java.util.List;

/**
 * insert、delete、update、select method SQL of XML Mapper file
 * @author CK
 * @date 2015/6/5.
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
	 * @param list list
	 * @return effective count
     */
    int insertBatch(List<T> list);

    /**
     * delete batch
     * @param ids ids
	 * @return effective count
     */
    int deleteBatch(List<ID> ids);
}
