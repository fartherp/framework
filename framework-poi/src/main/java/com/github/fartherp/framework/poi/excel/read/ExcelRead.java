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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2016/1/17
 */
public class ExcelRead {
    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     * ExcelRead.read(ExcelReadTest.class.getResourceAsStream("/a.xls"), new ExcelReadDeal&lt;ExcelReadDto&gt;() {
     *     // 单条数据处理（每个excel一行对应一个javabean）
     *     public ExcelReadDto dealBean(Row row) {
     *         ExcelReadDto dto = new ExcelReadDto();
     *         dto.setId(new BigDecimal(row.getCell(0).toString()).longValue());
     *         dto.setName(row.getCell(1).toString());
     *         dto.setAge(Integer.valueOf(row.getCell(2).toString()));
     *         return dto;
     *     }
     *
     *     // 批量数据处理（可以批量入库）
     *     public void dealBatchBean(List&lt;ExcelReadDto&gt; list) {
     *         Assert.assertEquals("name1", list.get(0).getName());
     *         Assert.assertEquals("name2", list.get(1).getName());
     *         Assert.assertEquals("name3", list.get(2).getName());
     *     }
     *
     *     // 批量加载多少数据，统一处理（默认1000）
     *     public int getBatchCount() {
     *         return super.getBatchCount();
     *     }
     *
     *     // 从第几行开始加载（默认跳过第一行）
     *     public int skipLine() {
     *         return super.skipLine();
     *     }
     * });
     * </pre>
     *
     * @param inputStream the file InputStream
     * @param deal the user-defined of ExcelReadDeal
     *
     * @see <a href="https://github.com/fartherp/framework/blob/master/framework-poi/src/test/resources/a.xls">
     *     file content</a>
     */
    public static <E> void read(InputStream inputStream, ExcelReadDeal<E> deal) {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(deal);
        try (Workbook wb = WorkbookFactory.create(inputStream)) {
			RowDelegate delegate = new RowDelegate();
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheetAt(i);
                if (null == sheet) {
                    continue;
                }
                int tmp = deal.getBatchCount();
                int index = 0;
                List<E> l = new ArrayList<>(tmp);
                for (Row row : sheet) {
                    ++index;
                    if (index <= deal.skipLine()) {
                        continue;
                    }
					delegate.setRow(row);
                    E o = deal.dealBean(delegate);
                    if (null != o) {
                        l.add(o);
                        if (index % tmp == 0) {
                            deal.dealBatchBean(l);
                            l = new ArrayList<>(tmp);
                        }
                    }
                }
                if (!l.isEmpty()) {
                    deal.dealBatchBean(l);
                }
            }
        } catch (Exception e) {
            // ignore
            e.printStackTrace();
        } finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// ignore
				inputStream = null;
			}
        }
    }
}
