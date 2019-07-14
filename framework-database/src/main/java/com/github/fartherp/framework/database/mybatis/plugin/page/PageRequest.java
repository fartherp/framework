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

/**
 * Created by framework .
 * @author hyssop
 * @date 12:29
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

    @Override
	public String toString() {
        return String.format("Page request [number: %d, limit %d, sort: %s]", currentPage, limit);
    }
}
