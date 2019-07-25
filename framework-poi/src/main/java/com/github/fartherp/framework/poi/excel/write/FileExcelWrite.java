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
package com.github.fartherp.framework.poi.excel.write;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * 文件流
 *
 * @author CK
 * @date 2017/11/25
 */
public final class FileExcelWrite implements OutputStreamDelegate {
	private FileExcelWrite() {
	}

	@Override
    public OutputStream createOutputStream(String fileName) {
        try {
            return new FileOutputStream(fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException("响应流异常", e);
        }
    }

    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     *  String fileName = "D:\\styleInputStream.xls";
     *  FileExcelWrite.build(this.getClass().getResourceAsStream("/c.xls"), fileName)
     *         .deal(new WriteDeal&lt;ExcelDto&gt;() {
     *             public String[] dealBean(ExcelDto obj) {
     *                 String[] result = new String[3];
     *                 result[0] = obj.getId() + "";
     *                 result[1] = obj.getName();
     *                 result[2] = obj.getAge() + "";
     *                 return result;
     *             }
     *
     *             public int skipLine() {
     *                 return 4;
     *             }
     *
     *             public Map&lt;String, Object&gt; additional() {
     *                 Map&lt;String, Object&gt; map = new HashMap&lt;&gt;();
     *                 map.put("quoteCurrency", "ETH");
     *                 map.put("symbol", "USDT_ETH");
     *                 map.put("startTime", "2019-01-09 00:00:00");
     *                 map.put("endTime", "2019-01-09 12:00:00");
     *                 return map;
     *             }
     *         }, getList())
     *         .write();
     * </pre>
     *
     * @param inputStream the file InputStream
     * @param fileName the output file name
     *
     * @see <a href="https://github.com/fartherp/framework/blob/master/framework-poi/src/test/resources/c.xls">
     *     file content</a>
     */
    public static ExcelWrite build(InputStream inputStream, String fileName) {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(fileName);
        return new CopyInputStreamExcelWrite(inputStream, fileName, new FileExcelWrite());
    }

    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     *  String[] title = new String [6];
     *  title[0] = "登录时间";
     *  title[1] = "用户名";
     *  title[2] = "访问端";
     *  title[3] = "版本系统";
     *  title[4] = "登录IP";
     *  title[5] = "状态";
     *  String fileName = "D:\\style1.xls";
     *  FileExcelWrite.build(fileName)
     *          .deal(title, obj -> {
     *              String[] result = new String[6];
     *              result[0] = obj.getTime();
     *              result[1] = obj.getName();
     *              result[2] = obj.getClient();
     *              result[3] = obj.getVersion();
     *              result[4] = obj.getIp();
     *              result[5] = obj.getStatus() + "";
     *              return result;
     *          }, ExcelDataList.getList())
     *          .write();
     * </pre>
     *
     * @param fileName the output file name
     */
    public static ExcelWrite build(String fileName) {
        Objects.requireNonNull(fileName);
        return new CreateNewExcelWrite(fileName, new FileExcelWrite());
    }
}
