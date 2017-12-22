/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.read;

import com.github.fartherp.framework.poi.ReadDeal;
import org.apache.poi.ss.usermodel.Row;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/12/22
 */
public interface ExcelReadDeal<E> extends ReadDeal<E> {
    /**
     * 一行excel数据返回业务BEAN
     * @param row 一行excel数据
     * @return 业务BEAN
     */
    E dealBean(Row row);
}
