/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.csv;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/12/22
 */
public class CSVReadTest {
    @Test
    public void testRead() throws Exception {
        CSVRead<CsvReadDto> excelRead = new CSVRead<CsvReadDto>();
        excelRead.read(CSVReadTest.class.getResourceAsStream("/a.csv"), new CSVReadDeal<CsvReadDto>() {
            // 单条数据处理（每个excel一行对应一个javabean）
            public CsvReadDto dealBean(String[] arr) {
                CsvReadDto dto = new CsvReadDto();
                dto.setId(Long.valueOf(arr[0]));
                dto.setName(arr[1]);
                dto.setAge(Integer.valueOf(arr[2]));
                return dto;
            }

            // 批量数据处理（可以批量入库）
            public void dealBatchBean(List<CsvReadDto> list) {
                Assert.assertEquals("name1", list.get(0).getName());
                Assert.assertEquals("name2", list.get(1).getName());
                Assert.assertEquals("name3", list.get(2).getName());
            }
        });
    }

}