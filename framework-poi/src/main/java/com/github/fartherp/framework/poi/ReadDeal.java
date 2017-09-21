/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/17
 */
public interface ReadDeal<E> {
    /**
     * 批处理数量
     * @return 批处理数量
     */
    int getBatchCount();
    /**
     * 跳过开始行数
     * @return 跳过行数
     */
    int skipLine();
    /**
     * 批处理List
     * @param list List
     */
    void dealBatchBean(List<E> list);

    public static abstract class DefaultReadDeal<E> implements ReadDeal<E> {
        /**
         * 批处理数量(默认1000)
         * @return 批处理数量
         */
        public int getBatchCount() {
            return 1000;
        }

        /**
         * 跳过开始行数(默认1)
         * @return 跳过行数
         */
        public int skipLine() {
            return 1;
        }
    }
}
