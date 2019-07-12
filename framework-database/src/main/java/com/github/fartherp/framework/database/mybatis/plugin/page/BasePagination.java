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
package com.github.fartherp.framework.database.mybatis.plugin.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页基础类
 * Author: CK
 * Date: 2015/6/14
 */
public class BasePagination<T extends Serializable> implements Pagination<T> {

    /**
     * 默认的每页数据量（pageSize）
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 默认页码（第一页）
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    public int firstPage = DEFAULT_PAGE_NUM;

    /**
     * 默认显示页码标签的个数 如： {首页 1 2 3 4 5 ... 16 17 18 末页}
     */
    public static final int DEFAULT_MAX_PAGE_INDEX_NUMBER = 9;

    /**
     * 显示页码标签的个数
     */
    private transient int maxPageIndexNumber = DEFAULT_MAX_PAGE_INDEX_NUMBER;

    /**
     * 页码编号数组
     */
    protected transient int[] pageNumberList = new int[0];

    /**
     * 总数据量
     */
    protected int total;

    /**
     * 每页数据量
     */
    protected int limit = DEFAULT_PAGE_SIZE;

    /**
     * 总页数
     */
    protected transient int totalPage;

    /**
     * 当前页码
     */
    protected transient int currentPage;

    /**
     * 下一页页码
     */
    protected transient int nextPage;

    /**
     * 上一页页码
     */
    protected transient int previousPage;

    /**
     * 是否有下一页
     */
    protected transient boolean hasNext = false;

    /**
     * 是否有前一页
     */
    protected transient boolean hasPrevious = false;

    /**
     * 获取该页的数据列表
     */
    protected List<T> rows;

    public BasePagination() {
        this(0);


    }

    public BasePagination(int total) {
        this(total, DEFAULT_PAGE_NUM);
    }

    public BasePagination(int total, int currentPage) {
        this(total, DEFAULT_PAGE_SIZE, currentPage);
    }

    public BasePagination(int total, int limit, int currentPage) {
        this(total, limit, currentPage, DEFAULT_MAX_PAGE_INDEX_NUMBER);
    }

    public BasePagination(int total, int limit, int currentPage, int maxPageIndexNumber) {
        this.maxPageIndexNumber = maxPageIndexNumber;
        init(total, limit, currentPage);
    }

    public BasePagination(List<T> content, int total) {
        this(content, total, DEFAULT_PAGE_NUM);
    }

    public BasePagination(List<T> content, int total, int currentPage) {
        this(total, DEFAULT_PAGE_SIZE, currentPage);
        setRows(content);
    }

    public void init(int total, int limit, int currentPage) {
        this.total = total;
        this.limit = limit;
        this.currentPage = currentPage;
        calculatePage();
    }

    /**
     * 计算当前页的取值范围：pageStartRow和pageEndRow
     */
    private void calculatePage() {
        // 计算总页数
        if ((total % limit) == 0) {
            totalPage = total / limit;
        } else {
            totalPage = total / limit + 1;
        }
        // 判断是否还有上一页
        hasPrevious = (currentPage - 1) > 0;

        // 判断是否还有下一页
        hasNext = currentPage < totalPage;
        // 计算上一页
        if (hasPrevious) {
            previousPage = currentPage - 1;
        }
        // 计算下一页
        if (hasNext) {
            nextPage = currentPage + 1;
        }
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getNextPage() {
        return nextPage;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public int getMaxPageIndexNumber() {
        return maxPageIndexNumber;
    }

    public void setMaxPageIndexNumber(int maxPageIndexNumber) {
        this.maxPageIndexNumber = maxPageIndexNumber;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    /**
     * 获取页码列表 <br/>
     *
     * @return 页码列表
     */
    public int[] getPageNumberList() {
        if (totalPage > this.maxPageIndexNumber) {
            this.pageNumberList = new int[this.maxPageIndexNumber];
            int offset = (this.maxPageIndexNumber - 4) / 2;
            if (this.currentPage - offset <= (1 + 2)) {
                for (int index = 0; index < maxPageIndexNumber - 2; index++) {
                    pageNumberList[index] = (index + 1);
                }
            } else if (this.currentPage + offset >= (totalPage - 2)) {
                int start = totalPage;
                for (int index = maxPageIndexNumber - 1; index > 1; index--) {
                    pageNumberList[index] = (start--);
                }
            } else {
                int start = currentPage - offset;
                for (int index = 2; index < maxPageIndexNumber - 2; index++) {
                    pageNumberList[index] = (start++);
                }
            }
            pageNumberList[0] = 1;
            pageNumberList[maxPageIndexNumber - 1] = totalPage;
        } else {
            // 总页数小于 设置的页码标签数
            this.pageNumberList = new int[this.totalPage];
            for (int index = 0; index <= totalPage - 1; index++) {
                pageNumberList[index] = (index + 1);
            }
        }
        return pageNumberList;
    }

    public String toString() {
        String contentType = "UNKNOWN";

        if (rows.size() > 0) {
            contentType = rows.get(0).getClass().getName();
        }
        return String.format("Page %s of %d containing %s instances", getLimit(), getTotal(), contentType);
    }
}
