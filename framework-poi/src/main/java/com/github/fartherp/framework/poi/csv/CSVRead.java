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
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/17
 */
public class CSVRead {
    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     * CSVRead.read(CSVReadTest.class.getResourceAsStream("/a.csv"), new CSVReadDeal&lt;CsvReadDto&gt;() {
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
     *     public void dealBatchBean(List&lt;CsvReadDto&gt; list) {
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
    public static <E> void read(InputStream inputStream, CSVReadDeal<E> deal) {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(deal);
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
			try {
				inputStream.close();
			} catch (IOException e) {
				// ignore
				inputStream = null;
			}
        }
    }

    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     * CSVParser parser = new CSVParserBuilder().withSeparator('|').withIgnoreQuotations(true).build();
     * CSVRead.read(CSVReadTest.class.getResourceAsStream("/a.csv"), new CSVReadDeal&lt;CsvReadDto&gt;() {
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
     *     public void dealBatchBean(List&lt;CsvReadDto&gt; list) {
     *         Assert.assertEquals("name1", list.get(0).getName());
     *         Assert.assertEquals("name2", list.get(1).getName());
     *         Assert.assertEquals("name3", list.get(2).getName());
     *     }
     * }, parser);
     * </pre>
     *
     * @param inputStream the file InputStream
     * @param deal the user-defined of CSVReadDeal
     *
     * @see <a href="https://github.com/fartherp/framework/blob/master/framework-poi/src/test/resources/a1.csv">
     *     file content</a>
     */
    public static <E> void read(InputStream inputStream, CSVReadDeal<E> deal, CSVParser parser) {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(deal);
        Objects.requireNonNull(parser);
        read(inputStream, deal, reader -> new CSVReaderBuilder(reader)
                .withSkipLines(deal.skipLine()).withCSVParser(parser).build());
    }

    /**
     * user-defined format
     * @param inputStream the file InputStream
     * @param deal the user-defined of CSVReadDeal
     * @param function the function for get Reader
     * @see {@link #read(java.io.InputStream, com.github.fartherp.framework.poi.csv.CSVReadDeal, com.opencsv.CSVParser)}
     */
    public static <E> void read(InputStream inputStream, CSVReadDeal<E> deal, Function<Reader, CSVReader> function) {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(deal);
        Objects.requireNonNull(function);
        try (CSVReader reader = function.apply(new InputStreamReader(new DataInputStream(inputStream)))) {
            int tmp = deal.getBatchCount();
            List<E> l = new ArrayList<>(tmp);
            int i = deal.skipLine();
            String[] arr;
            while ((arr = reader.readNext()) != null) {
                ++i;
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
			try {
				inputStream.close();
			} catch (IOException e) {
				// ignore
				inputStream = null;
			}
        }
    }
}
