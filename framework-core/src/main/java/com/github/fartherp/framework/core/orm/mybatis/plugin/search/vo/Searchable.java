/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.core.orm.mybatis.plugin.search.vo;

import com.github.fartherp.framework.core.orm.mybatis.plugin.page.PageRequest;
import com.github.fartherp.framework.core.orm.mybatis.plugin.page.Pagination;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.enums.SearchOperator;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.exception.InvalidSearchPropertyException;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.exception.InvalidSearchValueException;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.exception.SearchException;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.filter.SearchFilter;

import java.util.Collection;
import java.util.Map;

/**
 * <p>查询条件接口</p>
 * <p>User: hyssop
 * <p>Date: 16-1-16 上午8:47
 * <p>Version: 1.0
 */
public abstract class Searchable {

    public PageRequest page;
    public boolean converted;
    public Sort sort;

    /**
     * 创建一个新的查询
     */
    public static Searchable newSearchable() {
        return new SearchRequest();
    }

    public void setConverted(boolean converted) {
        this.converted = converted;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    /**
     * 创建一个新的查询
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams) throws SearchException {
        return new SearchRequest(searchParams);
    }

    /**
     * 创建一个新的查询
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams, final PageRequest page)
            throws SearchException {
        return new SearchRequest(searchParams, page);
    }

    /**
     * 创建一个新的查询
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams, final Sort sort)
            throws SearchException {
        return new SearchRequest(searchParams, sort);
    }

    /**
     * 创建一个新的查询
     */
    public static Searchable newSearchable(final Map<String, Object> searchParams, final PageRequest page, final Sort sort) {
        return new SearchRequest(searchParams, page, sort);
    }

    public abstract void removePageable();

    /**
     * 添加过滤条件 如key="parent.id_eq" value = 1
     * 如果添加时不加操作符 默认是custom 即如key=parent 实际key是parent_custom
     *
     * @param key   如 name_like
     * @param value 如果是in查询 多个值之间","分隔
     */
    public abstract Searchable addSearchParam(final String key, final Object value) throws SearchException;

    /**
     * 添加一组查询参数
     *
     * @param searchParams
     */
    public abstract Searchable addSearchParams(final Map<String, Object> searchParams) throws SearchException;

    /**
     * 添加过滤条件
     *
     * @param searchProperty 查询的属性名
     * @param operator       操作运算符
     * @param value          值
     */
    public abstract Searchable addSearchFilter(
            final String searchProperty, final SearchOperator operator, final Object value) throws SearchException;

    public abstract Searchable addSearchFilter(final SearchFilter searchFilter);

    /**
     * 添加多个and连接的过滤条件
     *
     * @param searchFilters
     * @return
     */
    public abstract Searchable addSearchFilters(final Collection<? extends SearchFilter> searchFilters);

    /**
     * 添加多个or连接的过滤条件
     *
     * @param first  第一个
     * @param others 其他
     * @return
     */
    public abstract Searchable or(final SearchFilter first, final SearchFilter... others);

    /**
     * 添加多个and连接的过滤条件
     *
     * @param first
     * @param others
     */
    public abstract Searchable and(final SearchFilter first, final SearchFilter... others);

    /**
     * 移除指定key的过滤条件
     */
    public abstract Searchable removeSearchFilter(final String key);

    /**
     * 移除指定属性 和 操作符的过滤条件
     */
    public abstract Searchable removeSearchFilter(String searchProperty, SearchOperator operator);

    /**
     * 把字符串类型的值转化为entity属性值
     */
    public abstract <T> Searchable convert(final Class<T> entityClass)
            throws InvalidSearchValueException, InvalidSearchPropertyException;

    /**
     * 标识为已经转换过了 避免多次转换
     */
    public abstract Searchable markConverted();

    public abstract Searchable setPage(final Pagination page);

    /**
     * @param pageNumber 分页页码 索引从 0 开始
     * @param pageSize   每页大小
     */
    public abstract Searchable setPage(final int pageNumber, final int pageSize);

    public abstract Searchable addSort(final Sort sort);

    public abstract Searchable addSort(final Sort.Direction direction, String property);

    /**
     * 获取查询过滤条件
     */
    public abstract Collection<SearchFilter> getSearchFilters();

    /**
     * 是否已经转换过了 避免多次转换
     */
    public abstract boolean isConverted();

    /**
     * 是否有查询参数
     */
    public abstract boolean hasSearchFilter();

    /**
     * 是否有排序
     */
    public abstract boolean hashSort();

    public abstract void removeSort();

    /**
     * 是否有分页
     */
    public abstract boolean hasPagination();

    public abstract void removePagination();

    /**
     * 获取分页和排序信息
     */
    public abstract Pagination getPage();

    /**
     * 是否包含查询键  如 name_like
     * 包括 or 和 and的
     */
    public abstract boolean containsSearchKey(final String key);

    /**
     * 获取查询属性对应的值
     * 不能获取or 或 and 的
     */
    public abstract <T> T getValue(final String key);

    /**
     * 是否有分页
     */
    public abstract boolean hasPageable();
}
