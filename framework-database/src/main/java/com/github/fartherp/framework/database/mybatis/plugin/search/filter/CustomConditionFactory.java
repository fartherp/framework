/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.database.mybatis.plugin.search.filter;


import com.github.fartherp.framework.database.mybatis.plugin.search.enums.RelationEnum;
import com.github.fartherp.framework.database.mybatis.plugin.search.enums.SearchOperator;
import com.github.fartherp.framework.database.mybatis.plugin.search.exception.SearchException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;

import java.util.Arrays;

/**
 * <p>Author: hyssop
 * <p>Date: 16-5-24 下午4:10
 * <p>Version: 1.0
 */
public class CustomConditionFactory {
    /**
     * 根据查询key和值生成Condition
     * @param key   如 name_like
     */
    public static SearchFilter newCustomCondition(String key, Object value) throws SearchException {
        return CustomCondition.newCondition(key, value);
    }
    /**
     * 根据查询属性、操作符和值生成Condition
     */
    public static SearchFilter newCustomCondition(String searchProperty, SearchOperator operator, Object value) {
        return CustomCondition.newCondition(searchProperty, operator, value);
    }

    /**
     * 拼or条件
     */
    public static SearchFilter or(SearchFilter first, SearchFilter... others) {
        if (ObjectUtils.notEqual(first,ObjectUtils.NULL)&&ArrayUtils.isNotEmpty(others)) {
            if(others instanceof CustomCondition[]){
                ((CustomCondition)first).addCustomCondition(RelationEnum.valueOf("or"),Arrays.asList((CustomCondition[])others));
            }

        }
        return first;
    }

    /**
     * 拼and条件
     */
    public static SearchFilter and(SearchFilter first, SearchFilter... others) {
        if (ObjectUtils.notEqual(first,ObjectUtils.NULL)&&ArrayUtils.isNotEmpty(others)) {
            if(others instanceof CustomCondition[]){
                ((CustomCondition)first).addCustomCondition(RelationEnum.valueOf("and"),Arrays.asList((CustomCondition[])others));
            }
        }
        return first;
    }
}
