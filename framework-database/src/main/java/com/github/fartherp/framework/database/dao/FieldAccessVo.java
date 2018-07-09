/*
 * Copyright (C) 2018 hyssop, Inc. All Rights Reserved.
 */

/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.dao;

import com.github.fartherp.framework.database.dao.annotation.ColumnDescription;
import com.github.fartherp.framework.database.dao.annotation.Id;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字段操作类
 * Author: CK
 * Date: 2015/6/5.
 */
public class FieldAccessVo implements Cloneable, Serializable {
    /**
     * get column description by field name
     *
     * @param name
     *            field name
     * @return column description
     * @see ColumnDescription
     */
    public String columnDesc(String name) {
        Field field = ReflectionUtils.findField(getClass(), name);
        if (field == null) {
            return null;
        }
        ColumnDescription cd = field.getAnnotation(ColumnDescription.class);
        return cd == null ? null : cd.desc();
    }

    /**
     * get primary key
     *
     * @param <T>
     *            primary key class type
     * @return primary value
     */
    @SuppressWarnings("unchecked")
    public <T> T primaryKey() {
        final List<Field> FIELDS = new ArrayList<Field>();
        ReflectionUtils.doWithFields(getClass(), new ReflectionUtils.FieldCallback() {
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                if (field.getAnnotation(Id.class) != null) {
                    field.setAccessible(true);
                    FIELDS.add(field);
                }
            }
        });
        if (FIELDS.size() == 1) {
            Field field = FIELDS.get(0);
            return (T) ReflectionUtils.getField(field, this);
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            for (Field field : FIELDS) {
                map.put(field.getName(), ReflectionUtils.getField(field, this));
            }
            return (T) map;
        }
    }
}
