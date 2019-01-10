/*
 * Copyright (c) 2017. CK. All rights reserved.
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

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/17
 */
public class ExcelRead<E> {
    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     * ExcelRead<ExcelReadDto> excelRead = new ExcelRead<>();
     * excelRead.read(ExcelReadTest.class.getResourceAsStream("/a.xls"), new ExcelReadDeal<ExcelReadDto>() {
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
     *     public void dealBatchBean(List<ExcelReadDto> list) {
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
    public void read(InputStream inputStream, ExcelReadDeal<E> deal) {
        try (Workbook wb = WorkbookFactory.create(inputStream)) {
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
                    E o = deal.dealBean(row);
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
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // ignore
                    inputStream = null;
                }
            }
        }
    }
}
