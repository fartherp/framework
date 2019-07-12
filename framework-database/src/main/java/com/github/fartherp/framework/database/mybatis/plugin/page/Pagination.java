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
 * 分页接口
 * Author: CK
 * Date: 2015/6/14
 */
public interface Pagination<T> extends Serializable {

    /**
     * 当pagination做为参数放入map传到mybatis时，该值为取pagination的key
     */
    public final static String MAP_PAGE_FIELD = Pagination.class.getName() + ".MAP_PAGE_FIELD";
    /**
     * 默认pagesize
     */
    public final static int DEFAULT_PAGE_SIZE = 50;

    /**
     * 获取需分页的数据总量 <br/>
     *
     * @return 分页的数据总量
     */
    public int getTotal();

    /**
     * 获取每页数据容量<br/>
     *
     * @return 每页数据容量
     */
    int getLimit();

    /**
     * 获取总页数 <br/>
     *
     * @return 总页数
     */
    public int getTotalPage();

    /**
     * 获取当前页码数 <br/>
     *
     * @return 当前页码数
     */
    int getCurrentPage();

    /**
     * 是否还有下一页 <br/>
     *
     * @return 是否还有下一页
     */
    public boolean isHasNext();

    /**
     * 获取下一页页码 <br/>
     *
     * @return 获取下一页页码
     */
    public int getNextPage();

    /**
     * 是否还有上一页 <br/>
     *
     * @return 是否还有上一页
     */
    public boolean isHasPrevious();

    /**
     * 获取上一页页码 <br/>
     *
     * @return 获取上一页页码
     */
    public int getPreviousPage();

    /**
     * 获取该页的数据列表 <br/>
     *
     * @return 获取该页的数据列表
     */
    public List<T> getRows();

    /**
     * 设置该页数据 <br/>
     *
     * @param rows 数据列表
     */
    public void setRows(List<T> rows);

    /**
     * 初始化page各项参数 <br/>
     *
     * @param totalCount 总数
     * @param pageSize 页大小
     * @param currentPage 当前页
     */
    public void init(int totalCount, int pageSize, int currentPage);

    /**
     * 获取页码标签列表大小 <br/>
     *
     * @return 页码标签列表大小
     */
    public int getMaxPageIndexNumber();

    /**
     * 设置页码标签列表大小 <br/>
     *
     * @param maxPageIndexNumber 页码标签列表大小
     */
    public void setMaxPageIndexNumber(int maxPageIndexNumber);

    /**
     * 获取页码列表 <br/>
     *
     * @return 页码列表
     */
    public int[] getPageNumberList();

    /**
     * 设置总页数 <br/>
     *
     * @param totalCount 总页数
     */
    public void setTotal(int totalCount);

    /**
     * 设置每页大小 <br/>
     *
     * @param limit 每页大小
     */
    public void setLimit(int limit);

    /**
     * 设置当前页 <br/>
     *
     * @param currentPage 当前页
     */
    public void setCurrentPage(int currentPage);

}
