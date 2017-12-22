/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi;

/**
 * 默认读取类
 *
 * @author: CK
 * @date: 2017/12/22
 */
public abstract class DefaultReadDeal<E> implements ReadDeal<E> {
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
