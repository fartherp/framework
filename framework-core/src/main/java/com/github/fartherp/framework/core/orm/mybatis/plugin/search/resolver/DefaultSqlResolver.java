/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.orm.mybatis.plugin.search.resolver;

import com.github.fartherp.framework.core.orm.mybatis.plugin.search.enums.SearchOperator;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.filter.CustomCondition;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.filter.SearchFilter;
import com.github.fartherp.framework.core.orm.mybatis.plugin.search.vo.Searchable;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by framework
 * Auth: hyssop
 * Date: 2016/9/6 0006
 */
public class DefaultSqlResolver extends AbstractSqlResolverInter {

    private ResolveSelf resolveSelf = new ResolveSelf();

    private ResolveWithAndFilters resolveWithAndFilters = new ResolveWithAndFilters();

    private ResolveWithOrFilters resolveWithOrFilters = new ResolveWithOrFilters();

    public DefaultSqlResolver(String alias) {
        super(alias);
    }

    public void prepareSQL(StringBuilder ql, Searchable search) {
        if (!search.hasSearchFilter()) {
            return;
        }
        for (SearchFilter searchFilter : search.getSearchFilters()) {

            CustomCondition customCondition = (CustomCondition) searchFilter;
            if (customCondition.getOperator() == SearchOperator.custom) {
                continue;
            }

            ql.append(" and ");
            boolean needAppendBracket = customCondition.hasAndFilters() || customCondition.hasOrFilters();
            if (needAppendBracket) {
                ql.append("(");
            }
            resolveSelf.genCondition(ql, searchFilter);
            if (customCondition.hasAndFilters()) {
                resolveWithAndFilters.genCondition(ql, searchFilter);
            }
            if (customCondition.hasOrFilters()) {
                resolveWithOrFilters.genCondition(ql, searchFilter);
            }
            if (needAppendBracket) {
                ql.append(")");
            }
        }
    }

    public void setValues(StringBuilder query, Searchable search) {
        int paramIndex = 1;
        for (SearchFilter searchFilter : search.getSearchFilters()) {
            paramIndex = resolveSelf.setValues(query, searchFilter, paramIndex);
            if (((CustomCondition) searchFilter).hasAndFilters()) {
                paramIndex = resolveWithAndFilters.setValues(query, searchFilter, paramIndex);
            }
            if (((CustomCondition) searchFilter).hasOrFilters()) {
                paramIndex = resolveWithOrFilters.setValues(query, searchFilter, paramIndex);
            }
        }
    }

    public class ResolveSelf {

        public int setValues(StringBuilder query, SearchFilter searchFilter, int paramIndex) {
            CustomCondition customCondition = (CustomCondition) searchFilter;
            if (customCondition.getOperator() == SearchOperator.custom) {
                return paramIndex;
            }
            if (customCondition.isUnaryFilter()) {
                return paramIndex;
            }
            query.toString().replaceAll(param.paramPrefix + paramIndex++, param.formtValue(customCondition, customCondition.getValue()).toString());
            return paramIndex;
        }

        public void genCondition(StringBuilder ql, SearchFilter searchFilter) {
            CustomCondition customCondition = (CustomCondition) searchFilter;
            String entityProperty = customCondition.getEntityProperty(); //自定义条件
            String operatorStr = customCondition.getOperatorStr();
            Object entityValue = customCondition.getValue();
            ql.append(param.aliasWithDot);
            ql.append(entityProperty);
            ql.append(" ");
            if (!customCondition.isUnaryFilter()) {
                ql.append(operatorStr);
                if ("in".equalsIgnoreCase(operatorStr)) {
                    String tep = StringUtils.arrayToDelimitedString((String[]) entityValue, ",");
                    ql.append("(");
                    ql.append(tep);
                    ql.append(")");
                } else {
                    ql.append("\"");
                    ql.append(entityValue);
                    ql.append("\"");
                }

            }
        }
    }

    public class ResolveWithAndFilters {
        ResolveSelf resolveSelf = new ResolveSelf();

        public int setValues(StringBuilder query, List<SearchFilter> searchFilters, int paramIndex) {
            for (SearchFilter andSearchFilter : searchFilters) {
                paramIndex = setValues(query, andSearchFilter, paramIndex);
            }
            return paramIndex;
        }

        public int setValues(StringBuilder query, SearchFilter searchFilter, int paramIndex) {
            for (SearchFilter andSearchFilter : ((CustomCondition) searchFilter).getAndFilters()) {
                paramIndex = resolveSelf.setValues(query, andSearchFilter, paramIndex);
            }
            return paramIndex;
        }

        public void genCondition(StringBuilder ql, SearchFilter searchFilter) {
            boolean isFirst = true;
            for (SearchFilter andSearchFilter : ((CustomCondition) searchFilter).getAndFilters()) {
                if (!isFirst) {
                    ql.append(" and ");
                }

                resolveSelf.genCondition(ql, andSearchFilter);
                isFirst = false;
            }
        }
    }

    public class ResolveWithOrFilters {
        ResolveSelf resolveSelf = new ResolveSelf();

        public int setValues(StringBuilder query, List<SearchFilter> searchFilters, int paramIndex) {
            for (SearchFilter orSearchFilter : searchFilters) {
                paramIndex = resolveSelf.setValues(query, orSearchFilter, paramIndex);
            }
            return paramIndex;
        }

        public int setValues(StringBuilder query, SearchFilter searchFilter, int paramIndex) {
            for (SearchFilter orSearchFilter : ((CustomCondition) searchFilter).getOrFilters()) {
                paramIndex = setValues(query, orSearchFilter, paramIndex);
            }
            return paramIndex;
        }

        public void genCondition(StringBuilder ql, SearchFilter searchFilter) {
            boolean isFirst = true;
            for (SearchFilter orSearchFilter : ((CustomCondition) searchFilter).getOrFilters()) {
                if (!isFirst) {
                    ql.append(" or ");
                }
                resolveSelf.genCondition(ql, orSearchFilter);
                isFirst = false;
            }
        }
    }
}
