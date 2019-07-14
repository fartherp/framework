/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.poi.excel.read;

import org.apache.poi.ss.usermodel.Row;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

public class ExcelReadTest {

    @Test
    public void testRead() {
        // a.xls/a.xlsx
        ExcelRead.read(ExcelReadTest.class.getResourceAsStream("/a.xls"), new ExcelReadDeal<ExcelReadDto>() {
            // 单条数据处理（每个excel一行对应一个对象）
            @Override
			public ExcelReadDto dealBean(Row row) {
                ExcelReadDto dto = new ExcelReadDto();
                dto.setId(new BigDecimal(row.getCell(0).toString()).longValue());
                dto.setName(row.getCell(1).toString());
                dto.setAge(Integer.valueOf(row.getCell(2).toString()));
                return dto;
            }

            // 批量数据处理（可以批量入库）
            @Override
			public void dealBatchBean(List<ExcelReadDto> list) {
                Assert.assertEquals("name1", list.get(0).getName());
                Assert.assertEquals("name2", list.get(1).getName());
                Assert.assertEquals("name3", list.get(2).getName());
            }
        });
    }
}
