/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.mybatis.plugin.page;

import java.io.Serializable;

/**
 * Created by framework .
 * Auth: hyssop
 * Date: 12:29
 */
public class PageRequest extends BasePagination implements Pagination, Serializable {

    private static long serialVersionUID = 8280485938848398236L;

    /**
     * 分页从1开始，limit为每页的数量
     *
     * @param page
     * @param limit
     */
    public PageRequest(int page, int limit) {
        this.setCurrentPage(page);
        this.setLimit(limit);
    }

    public int getPageSize() {
        return totalPage;
    }

    public int getPageNumber() {
        return currentPage;
    }

    public int getOffset() {
        return currentPage * limit;
    }

    public Pagination previousOrFirst() {
        return hasPrevious ? new PageRequest(currentPage - 1, limit) : this;
    }

    public Pagination first() {
        return new PageRequest(0, limit);
    }

    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PageRequest)) {
            return false;
        }
        PageRequest that = (PageRequest) obj;
        boolean pageEqual = this.currentPage == that.currentPage;
        boolean sizeEqual = this.limit == that.limit;

        return pageEqual && sizeEqual;
    }

    public int hashCode() {

        int result = 17;
        result = 31 * result + currentPage;
        result = 31 * result + limit;
        result = 31 * result;

        return result;
    }

    public String toString() {
        return String.format("Page request [number: %d, limit %d, sort: %s]", currentPage, limit);
    }
}
