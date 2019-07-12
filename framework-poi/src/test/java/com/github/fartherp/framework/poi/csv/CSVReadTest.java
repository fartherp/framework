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
package com.github.fartherp.framework.poi.csv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
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
    public void testRead() {
        CSVRead.read(CSVReadTest.class.getResourceAsStream("/a.csv"), new CSVReadDeal<CsvReadDto>() {
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

    @Test
    public void testRead1() {
        CSVParser parser = new CSVParserBuilder().withSeparator('|').withIgnoreQuotations(true).build();
        CSVRead.read(CSVReadTest.class.getResourceAsStream("/a1.csv"), new CSVReadDeal<CsvReadDto>() {
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
        }, parser);
    }
}