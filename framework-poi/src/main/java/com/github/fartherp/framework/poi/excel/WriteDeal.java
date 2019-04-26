/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel;

import com.github.fartherp.framework.poi.Constant;

import java.util.Map;

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
     * 额外添加的条件数据
     * @return 额外添加的条件数据
     */
    default Map<String, Object> additional() {
        return null;
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
