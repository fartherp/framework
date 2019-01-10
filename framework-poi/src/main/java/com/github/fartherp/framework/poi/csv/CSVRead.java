/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.csv;

import com.opencsv.CSVReader;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/17
 */
public class CSVRead<E> {
    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     * CSVRead<CsvReadDto> csvRead = new CSVRead<>();
     * csvRead.read(CSVReadTest.class.getResourceAsStream("/a.csv"), new CSVReadDeal<CsvReadDto>() {
     *     // 单条数据处理（每个excel一行对应一个javabean）
     *     public CsvReadDto dealBean(String[] arr) {
     *         CsvReadDto dto = new CsvReadDto();
     *         dto.setId(Long.valueOf(arr[0]));
     *         dto.setName(arr[1]);
     *         dto.setAge(Integer.valueOf(arr[2]));
     *         return dto;
     *     }
     * 
     *     // 批量数据处理（可以批量入库）
     *     public void dealBatchBean(List<CsvReadDto> list) {
     *         Assert.assertEquals("name1", list.get(0).getName());
     *         Assert.assertEquals("name2", list.get(1).getName());
     *         Assert.assertEquals("name3", list.get(2).getName());
     *     }
     * });
     * </pre>
     *
     * @param inputStream the file InputStream
     * @param deal the user-defined of CSVReadDeal
     *
     * @see <a href="https://github.com/fartherp/framework/blob/master/framework-poi/src/test/resources/a.csv">
     *     file content</a>
     */
    public void read(InputStream inputStream, CSVReadDeal<E> deal) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(new DataInputStream(inputStream)))) {
            int tmp = deal.getBatchCount();
            List<E> l = new ArrayList<>(tmp);
            int i = 0;
            String [] arr;
            while ((arr = reader.readNext()) != null) {
                ++i;
                if (i <= deal.skipLine()) {
                    continue;
                }
                E o = deal.dealBean(arr);
                if (o != null) {
                    l.add(o);
                    if (i % tmp == 0) {
                        deal.dealBatchBean(l);
                        l = new ArrayList<>(tmp);
                    }
                }
            }
            if (!l.isEmpty()) {
                deal.dealBatchBean(l);
            }
        } catch (IOException e) {
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
