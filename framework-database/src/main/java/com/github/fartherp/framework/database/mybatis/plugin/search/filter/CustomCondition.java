/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.database.mybatis.plugin.search.filter;

import com.github.fartherp.framework.database.mybatis.plugin.search.enums.RelationEnum;
import com.github.fartherp.framework.database.mybatis.plugin.search.enums.SearchOperator;
import com.github.fartherp.framework.database.mybatis.plugin.search.exception.InvlidSearchOperatorException;
import com.github.fartherp.framework.database.mybatis.plugin.search.exception.SearchException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>查询过滤条件</p>
 * <p>author: hyssop
 * <p>Date: 16-1-15 上午7:12
 * <p>Version: 1.0
 */
public class CustomCondition implements SearchFilter {

    //查询参数分隔符
    public static final String separator = "_";
    private String key;
    private String searchProperty;
    private SearchOperator operator;
    private Object value;

    private List<CustomCondition> andFilters = new ArrayList<CustomCondition>();
    private List<CustomCondition> orFilters = new ArrayList<CustomCondition>();

    public void addCustomCondition(RelationEnum value, List<CustomCondition> others) {
        if (value.getValue().equalsIgnoreCase("and")) {
            andFilters.addAll(others);
        }
        if (value.getValue().equalsIgnoreCase("or")) {
            orFilters.addAll(others);
        }
    }

    public List<CustomCondition> getAndFilters() {
        return this.andFilters;
    }

    public List<CustomCondition> getOrFilters() {
        return orFilters;
    }

    public boolean hasAndFilters() {

        return !andFilters.isEmpty();
    }

    public boolean hasOrFilters() {

        return !orFilters.isEmpty();
    }

    /**
     * 根据查询key和值生成Condition
     * @param key   如 name_like
     * @param value 如 张
     */
    static CustomCondition newCondition(String key, Object value) throws SearchException {
        Assert.notNull(key, "CustomCondition key must not null");
        String[] searchs = StringUtils.split(key, separator);
        if (searchs.length == 0) {
            throw new SearchException("CustomCondition key format must be : property or property_op");
        }

        String searchProperty = searchs[0];

        SearchOperator operator ;

        if (searchs.length == 1) {
            operator = SearchOperator.custom;
        } else if (searchs.length == 3) {
            searchProperty = searchs[0] + separator + searchs[1];
            operator = SearchOperator.valueOf(searchs[2]);
        } else {
            try {
                operator = SearchOperator.valueOf(searchs[1]);
            } catch (IllegalArgumentException e) {
                throw new InvlidSearchOperatorException(searchProperty, searchs[1]);
            }
        }

        boolean allowBlankValue = SearchOperator.isAllowBlankValue(operator);
        boolean isValueBlank = (value == null);
        isValueBlank = isValueBlank || (value instanceof String && StringUtils.isBlank((String) value));
        isValueBlank = isValueBlank || (value instanceof List && ((List) value).size() == 0);
        //过滤掉空值，即不参与查询
        if (!allowBlankValue && isValueBlank) {
            return null;
        }
        return newCondition(searchProperty, operator, value);
    }

    /**
     * 根据查询属性、操作符和值生成Condition
     *
     * @param searchProperty 查询的条件
     * @param operator 查询的符号 = like
     * @param value  查询值，张三、张等
     */
    static CustomCondition newCondition(String searchProperty, SearchOperator operator, Object value) {
        return new CustomCondition(searchProperty, operator, value);
    }

    /**
     * @param searchProperty 属性名
     * @param operator       操作
     * @param value          值
     */
    private CustomCondition(String searchProperty, SearchOperator operator, Object value) {
        this.searchProperty = searchProperty;
        this.operator = operator;
        this.value = value;
        this.key = this.searchProperty + separator + this.operator;
    }

    public String getKey() {
        return key;

    }

    public String getSearchProperty() {
        return searchProperty;
    }

    /**
     * 获取 操作符
     */
    public SearchOperator getOperator() throws InvlidSearchOperatorException {
        return operator;
    }

    /**
     * 获取自定义查询使用的操作符
     * 1、首先获取前台传的
     * 2、返回空
     */
    public String getOperatorStr() {
        if (operator != null) {
            return operator.getSymbol();
        }
        return "";
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setOperator(SearchOperator operator) {
        this.operator = operator;
    }

    public void setSearchProperty(String searchProperty) {
        this.searchProperty = searchProperty;
    }

    /**
     * 得到实体属性名
     */
    public String getEntityProperty() {
        return searchProperty;
    }

    /**
     * 是否是一元过滤 如is null is not null
     */
    public boolean isUnaryFilter() {
        String operatorStr = getOperator().getSymbol();
        return StringUtils.isNotEmpty(operatorStr) && operatorStr.startsWith("is");
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomCondition that = (CustomCondition) o;
        return !(key != null ? !key.equals(that.key) : that.key != null);
    }

    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    public String toString() {
        return "CustomCondition{" +
                "searchProperty='" + searchProperty + '\'' +
                ", operator=" + operator +
                ", value=" + value +
                '}';
    }
}
