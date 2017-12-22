/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.csv;

import com.github.fartherp.framework.poi.ReadDeal;

/**
 * CSV读取接口
 *
 * @author: CK
 * @date: 2017/12/22
 */
public interface CSVReaderDeal<E> extends ReadDeal<E> {
    /**
     * 一行CSV数据返回业务BEAN
     * @param arr 一行CSV数据
     * @return 业务BEAN
     */
    E dealBean(String[] arr);
}
