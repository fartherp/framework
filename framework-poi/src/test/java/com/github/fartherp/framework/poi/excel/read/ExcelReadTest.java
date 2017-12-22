/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.read;

import org.apache.poi.ss.usermodel.Row;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ExcelReadTest {

    @Test
    public void testRead() throws Exception {
        ExcelRead<ExcelReadDto> excelRead = new ExcelRead<ExcelReadDto>();
        excelRead.read(ExcelReadTest.class.getResourceAsStream("/a.xls"), "a.xls", new DefaultExcelReadDeal<ExcelReadDto>() {
            public ExcelReadDto dealBean(Row row) {
                ExcelReadDto dto = new ExcelReadDto();
                dto.setId(Long.valueOf(row.getCell(0).toString()));
                dto.setName(row.getCell(1).toString());
                dto.setAge(Integer.valueOf(row.getCell(2).toString()));
                return dto;
            }

            public void dealBatchBean(List<ExcelReadDto> list) {
                Assert.assertEquals("name1", list.get(0).getName());
                Assert.assertEquals("name2", list.get(1).getName());
                Assert.assertEquals("name3", list.get(2).getName());
            }
        });
    }
}