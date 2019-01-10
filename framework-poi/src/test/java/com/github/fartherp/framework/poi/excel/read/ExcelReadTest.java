/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.read;

import org.apache.poi.ss.usermodel.Row;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

public class ExcelReadTest {

    @Test
    public void testRead() throws Exception {
        ExcelRead<ExcelReadDto> excelRead = new ExcelRead<>();
        // a.xls/a.xlsx
        excelRead.read(ExcelReadTest.class.getResourceAsStream("/a.xls"), new ExcelReadDeal<ExcelReadDto>() {
            // 单条数据处理（每个excel一行对应一个对象）
            public ExcelReadDto dealBean(Row row) {
                ExcelReadDto dto = new ExcelReadDto();
                dto.setId(new BigDecimal(row.getCell(0).toString()).longValue());
                dto.setName(row.getCell(1).toString());
                dto.setAge(Integer.valueOf(row.getCell(2).toString()));
                return dto;
            }

            // 批量数据处理（可以批量入库）
            public void dealBatchBean(List<ExcelReadDto> list) {
                Assert.assertEquals("name1", list.get(0).getName());
                Assert.assertEquals("name2", list.get(1).getName());
                Assert.assertEquals("name3", list.get(2).getName());
            }
        });
    }
}