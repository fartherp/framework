/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel;

import com.github.fartherp.framework.poi.Constant;

/**
 * Excel写接口
 *
 * @author: CK
 * @date: 2017/11/25
 */
@FunctionalInterface
public interface WriteDeal<T> {

    /**
     * 跳过开始行数
     * @return 跳过行数
     */
    default int skipLine() {
        return 0;
    }

    /**
     * 一行excel数据返回业务BEAN
     *
     * @param obj 一行excel数据
     * @return 业务BEAN
     */
    String[] dealBean(T obj);

    /**
     * 设置sheet名称
     * @return 名称
     */
    default String name() {
        return null;
    }

    /**
     * 设置每列宽度
     * @param title 标题
     * @return 宽度
     */
    default int[] setColumnWidth(String[] title) {
        int[] columnWidth = new int[title.length];
        for (int i = 0; i < title.length; i++) {
            columnWidth[i] = Constant.WIDTH_DEFAULT;
        }
        return columnWidth;
    }

    /**
     * 设置高度
     * @return 高度
     */
    default short setHeight() {
        return Constant.HEIGHT_DEFAULT;
    }

    /**
     * 设置最大行数
     * @return 最大行数
     */
    default int setMaxRows(String type) {
        if (Constant.OFFICE_EXCEL_2010_POSTFIX.equals(type)) {
            return 1048576;
        }
        return 65536;
    }
}
