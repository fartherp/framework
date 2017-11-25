/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/11/25
 */
public interface WriteDeal<T> {
    /**
     * 一行excel数据返回业务BEAN
     *
     * @param obj 一行excel数据
     * @return 业务BEAN
     */
    String[] dealBean(T obj);

    /**
     * 设置每列宽度
     * @param title 标题
     * @return 宽度
     */
    int[] setColumnWidth(String[] title);

    /**
     * 设置高度
     * @return 高度
     */
    short setHeight();
}
