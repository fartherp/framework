/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.database.mybatis.plugin.search.vo;


import com.github.fartherp.framework.database.mybatis.plugin.page.PageRequest;
import com.github.fartherp.framework.database.mybatis.plugin.page.Pagination;
import com.github.fartherp.framework.database.mybatis.plugin.search.enums.SearchOperator;
import com.github.fartherp.framework.database.mybatis.plugin.search.exception.SearchException;
import com.github.fartherp.framework.database.mybatis.plugin.search.filter.CustomCondition;
import com.github.fartherp.framework.database.mybatis.plugin.search.filter.CustomConditionFactory;
import com.github.fartherp.framework.database.mybatis.plugin.search.filter.SearchFilter;
import com.github.fartherp.framework.database.mybatis.plugin.search.utils.SearchableConvertUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>查询条件（包括分页和排序）</p>
 * <p>User: hyssop
 * <p>Date: 16-1-15 上午7:29
 * <p>Version: 1.0
 */

public class SearchRequest extends Searchable {

    private Map<String, SearchFilter> searchFilterMap = Maps.newHashMap();
    /**
     * 使用这个的目的是保证拼sql的顺序是按照添加时的顺序
     */
    private List<SearchFilter> searchFilters = Lists.newArrayList();

    public SearchRequest(Map<String, Object> searchParams) {
        this(searchParams, null, null);
    }

    public SearchRequest() {
    }

    public SearchRequest(Map<String, Object> searchParams, PageRequest page) {
        this(searchParams, page, null);
    }

    public SearchRequest(Map<String, Object> searchParams, Sort sort) throws SearchException {
        this(searchParams, null, sort);
    }

    /**
     * <p>根据查询参数拼Search<br/>
     * 查询参数格式：property_op=value 或 customerProperty=value<br/>
     * customerProperty查找规则是：1、先查找domain的属性，2、如果找不到查找domain上的SearchPropertyMappings映射规则
     * 属性、操作符之间用_分割，op可省略/或custom，省略后值默认为custom，即程序中自定义<br/>
     * 如果op=custom，property也可以自定义（即可以与domain的不一样）,
     * </p>
     *
     * @param searchParams 查询参数组
     * @param page         分页
     * @param sort         排序
     */
    public SearchRequest(Map<String, Object> searchParams, PageRequest page, Sort sort)
            throws SearchException {
        toSearchFilters(searchParams);
        merge(sort, page);
    }

    private void toSearchFilters(Map<String, Object> searchParams) throws SearchException {
        if (searchParams == null || searchParams.size() == 0) {
            return;
        }
        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            addSearchFilter(CustomConditionFactory.newCustomCondition(key, value));
        }
    }

    public void removePageable() {
        page = null;
    }

    public Searchable addSearchParam(String key, Object value) throws SearchException {
        addSearchFilter(CustomConditionFactory.newCustomCondition(key, value));
        return this;
    }

    public Searchable addSearchParams(Map<String, Object> searchParams) throws SearchException {
        toSearchFilters(searchParams);
        return this;
    }

    public Searchable addSearchFilter(String searchProperty, SearchOperator operator, Object value) {
        SearchFilter searchFilter = CustomConditionFactory.newCustomCondition(searchProperty, operator, value);
        return addSearchFilter(searchFilter);
    }

    public Searchable addSearchFilter(SearchFilter searchFilter) {
        if (searchFilter == null) {
            return this;
        }
        if (searchFilter instanceof CustomCondition) {
            CustomCondition customCondition = (CustomCondition) searchFilter;
            String key = customCondition.getKey();
            searchFilterMap.put(key, customCondition);
        }
        int index = searchFilters.indexOf(searchFilter);
        if (index != -1) {
            searchFilters.set(index, searchFilter);
        } else {
            searchFilters.add(searchFilter);
        }
        return this;

    }

    public Searchable addSearchFilters(Collection<? extends SearchFilter> searchFilters) {
        if (CollectionUtils.isEmpty(searchFilters)) {
            return this;
        }
        for (SearchFilter searchFilter : searchFilters) {
            addSearchFilter(searchFilter);
        }
        return this;
    }

    public Searchable or(SearchFilter first, SearchFilter... others) {
        addSearchFilter(CustomConditionFactory.or(first, others));
        return this;
    }

    public Searchable and(SearchFilter first, SearchFilter... others) {
        addSearchFilter(CustomConditionFactory.and(first, others));
        return this;
    }

    public Searchable removeSearchFilter(String searchProperty, SearchOperator operator) {
        this.removeSearchFilter(searchProperty + CustomCondition.separator + operator);
        return this;
    }

    public Searchable removeSearchFilter(String key) {
        if (key == null) {
            return this;
        }

        SearchFilter searchFilter = searchFilterMap.remove(key);

        if (searchFilter == null) {
            searchFilter = searchFilterMap.remove(getCustomKey(key));
        }

        if (searchFilter == null) {
            return this;
        }

        searchFilters.remove(searchFilter);

        return this;
    }

    private String getCustomKey(String key) {
        return key + CustomCondition.separator + SearchOperator.custom;
    }

    public Searchable setPage(PageRequest page) {
        merge(sort, page);
        return this;
    }

    public Searchable setPage(int pageNumber, int pageSize) {
        merge(sort, new PageRequest(pageNumber, pageSize));
        return this;
    }

    public Searchable addSort(Sort sort) {
        merge(sort, page);
        return this;
    }

    public Searchable addSort(Sort.Direction direction, String property) {
        merge(new Sort(direction, property), page);
        return this;
    }


    public <T> Searchable convert(Class<T> entityClass) {
        SearchableConvertUtils.convertSearchValueToEntityValue(this, entityClass);
        markConverted();
        return this;
    }


    public Searchable markConverted() {
        this.converted = true;
        return this;
    }

    public Searchable setPage(Pagination page) {
        this.page = (PageRequest) page;
        return this;
    }

    public Collection<SearchFilter> getSearchFilters() {
        return Collections.unmodifiableCollection(searchFilters);
    }

    public boolean isConverted() {
        return converted;
    }

    public boolean hasSearchFilter() {
        return searchFilters.size() > 0;
    }

    public boolean hashSort() {
        return this.sort != null && this.sort.iterator().hasNext();
    }

    public void removeSort() {
        this.sort = null;
        if (this.page != null) {
            this.page = new PageRequest(page.getCurrentPage(), page.getLimit());
        }
    }

    public boolean hasPagination() {
        return false;
    }

    public void removePagination() {
        this.page = null;
    }

    public Pagination getPage() {
        return page;
    }


    public boolean containsSearchKey(String key) {
        boolean contains =
                searchFilterMap.containsKey(key) ||
                        searchFilterMap.containsKey(getCustomKey(key));

        if (contains) {
            return true;
        }

        //否则检查其中的or 和 and
        return containsSearchKey(searchFilters, key);
    }

    public boolean hasPageable() {
        return this.page != null && this.page.getLimit() > 0;
    }

    private boolean containsSearchKey(List<SearchFilter> searchFilters, String key) throws ClassCastException {
        boolean contains = false;
        for (SearchFilter searchFilter : searchFilters) {
            if (searchFilter instanceof CustomCondition) {
                CustomCondition customCondition = (CustomCondition) searchFilter;
                contains = customCondition.getKey().equals(key) || customCondition.getSearchProperty().equals(key);
            }

            if (((CustomCondition) searchFilter).hasOrFilters()) {
                List<CustomCondition> orConditions = ((CustomCondition) searchFilter).getOrFilters();
                contains = containsCustomKey(orConditions, key);
            }

            if (((CustomCondition) searchFilter).hasAndFilters()) {
                List<CustomCondition> andConditions = ((CustomCondition) searchFilter).getAndFilters();
                contains = containsCustomKey(andConditions, key);
            }
            if (contains) {
                break;
            }
        }

        return contains;
    }

    private boolean containsCustomKey(List<CustomCondition> conditions, String key) {
        for (CustomCondition customCondition : conditions) {
            if (customCondition.getKey().equals(key) || customCondition.getSearchProperty().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public Object getValue(String key) {
        SearchFilter searchFilter = searchFilterMap.get(key);
        if (searchFilter == null) {
            searchFilter = searchFilterMap.get(getCustomKey(key));
        }
        if (searchFilter == null) {
            return null;
        }
        if (searchFilter instanceof CustomCondition) {
            CustomCondition customCondition = (CustomCondition) searchFilter;
            return customCondition.getValue();
        }
        return null;
    }

    private void merge(Sort sort, PageRequest page) {
        if (sort == null) {
            sort = this.sort;
        }
        if (page == null) {
            page = this.page;
        }
        //合并排序
        if (sort == null) {
            this.sort = page != null ? sort : null;
        } else {
            this.sort = (page != null ? sort.and(this.sort) : sort);
        }
        //把排序合并到page中
        if (page != null) {
            this.page = new PageRequest(page.getCurrentPage(), page.getLimit());
        } else {
            this.page = null;
        }
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public String toString() {
        return "SearchRequest{" +
                "searchFilterMap=" + searchFilterMap +
                ",page=" + page +
                ",sort=" + sort +
                '}';
    }

}
