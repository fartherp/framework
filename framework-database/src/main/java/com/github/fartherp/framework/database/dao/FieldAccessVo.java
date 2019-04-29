/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.dao;

import java.io.Serializable;

/**
 * 字段操作类
 * Author: CK
 * Date: 2015/6/5.
 */
public abstract class FieldAccessVo<ID> implements Cloneable, Serializable {

    /**
     * get primary key
     *
     * @return primary value
     */
    public abstract ID primaryKey();
}
