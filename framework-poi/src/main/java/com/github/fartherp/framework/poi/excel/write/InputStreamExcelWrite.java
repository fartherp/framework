/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.WriteDeal;

import java.util.List;
import java.util.Map;

/**
 * 已有Excel模板，读取流生成新Excel模板
 * Author: CK
 * Date: 2019/4/24
 */
public interface InputStreamExcelWrite extends ExcelWrite {

    /**
     * 具体操作
     * @param deal 操作回调
     * @return ExcelWrite
     */
    <T> InputStreamExcelWrite deal(WriteDeal<T> deal, List<T> list);

    /**
     * 额外数据
     * @param params map
     * @return ExcelWrite
     */
    InputStreamExcelWrite additional(Map<String, Object> params);
}
