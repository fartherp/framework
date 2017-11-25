/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.WriteDeal;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/5/29
 */
public interface ExcelWrite<T> {

    String getType();

    String[] getTitle();

    String getFileName();

    List<T> getList();

    OutputStream getOutputStream();

    void writeExcel(WriteDeal<T> deal);

    ExcelWrite<T> createOutputStream();
}
