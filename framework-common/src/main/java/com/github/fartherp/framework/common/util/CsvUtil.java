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
package com.github.fartherp.framework.common.util;

import com.opencsv.CSVWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * the CSV tools that the response write the csv file of the page and write the csv file of the user-defined
 * @author CK
 * @date 2015/9/22
 */
public class CsvUtil {
    /**
     * The csv file is output through the response of the page
     *
     * <p>
     * Example code:
     * </p>
     * <pre>
     * HttpServletResponse response = null;
     * HttpServletRequest request = null;
     * String filename = "TEST";
     * String[] title = SheetsTitlesEnum.USER_LOGIN_LOG.getTitle();
     * List&lt;String[]&gt; bodyList = new ArrayList&lt;&gt;();
     * CsvUtil.writeCsvFile(response, request, filename, title, bodyList);
     * </pre>
     *
     * @param response the response of the page
     * @param request the request of the page
     * @param filename the file name of user-defined
     * @param title the title content of the csv file
     * @param bodyList the body content of the csv file
     */
    public static void writeCsvFile(HttpServletResponse response, HttpServletRequest request,
                                    String filename, String[] title, List<String[]> bodyList) {
        response.setContentType("application/octet-stream;charset=GBK");
        String fileName = FileUtilies.getFileName(filename, request);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".csv");

        try (CSVWriter writer = new CSVWriter(response.getWriter())) {
			write(writer, title, bodyList);
        } catch (IOException e) {
            throw new RuntimeException("write csv file of response fail ", e);
        }
    }

	/**
	 * The csv file is output through the response of the page
	 *
	 * <p>
	 * Example code:
	 * </p>
	 * <pre>
	 * HttpServletResponse response = null;
	 * HttpServletRequest request = null;
	 * String filename = "TEST";
	 * String[] title = SheetsTitlesEnum.USER_LOGIN_LOG.getTitle();
	 * List&lt;User&gt; objs = new ArrayList&lt;&gt;();
	 * CsvUtil.writeCsvFile(response, request, filename, title, objs, t -> {
	 * 	   String[] bodyList = new String[2];
	 * 	   bodyList[0] = t.getId() + "";
	 * 	   bodyList[1] = t.getName();
	 * 	   return bodyList;
	 * });
	 * </pre>
	 *
	 * @param response the response of the page
	 * @param request the request of the page
	 * @param filename the file name of user-defined
	 * @param title the title content of the csv file
	 * @param objs the body content of the csv file
	 */
	public static <T> void writeCsvFile(HttpServletResponse response, HttpServletRequest request,
			String filename, String[] title, List<T> objs, Function<T, String[]> function) {
		List<String[]> bodyList = objs.stream().map(function).collect(Collectors.toList());
		writeCsvFile(response, request, filename, title, bodyList);
	}

    /**
     * The csv file wrote through user-defined
     *
     * <p>
     * Example code:
     * </p>
     * <pre>
     * String filename = "TEST";
     * String[] title = SheetsTitlesEnum.USER_LOGIN_LOG.getTitle();
     * List&lt;String[]&gt; bodyList = new ArrayList&lt;&gt;();
     * CsvUtil.writeCsvFile(filename, title, bodyList);
     * </pre>
     *
     * @param filename the file name
     * @param title the title content of the csv file
     * @param bodyList this body content of the csv file
     */
    public static void writeCsvFile(String filename, String[] title, List<String[]> bodyList) {
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(
        	new FileOutputStream(new File(filename + ".csv")), "GBK"))) {
            write(writer, title, bodyList);
        } catch (IOException e) {
            throw new RuntimeException("write csv file fail ", e);
        }
    }

	/**
	 * The csv file wrote through user-defined
	 *
	 * <p>
	 * Example code:
	 * </p>
	 * <pre>
	 * String filename = "TEST";
	 * String[] title = SheetsTitlesEnum.USER_LOGIN_LOG.getTitle();
	 * List&lt;User&gt; objs = new ArrayList&lt;&gt;();
	 * CsvUtil.writeCsvFile(filename, title, objs, t -> {
	 * 	   String[] bodyList = new String[2];
	 * 	   bodyList[0] = t.getId() + "";
	 * 	   bodyList[1] = t.getName();
	 * 	   return bodyList;
	 * });
	 * </pre>
	 *
	 * @param filename the file name
	 * @param title the title content of the csv file
	 * @param objs 对象列表
	 * @param function 对象转字符串数组回调
	 */
	public static <T> void writeCsvFile(String filename, String[] title, List<T> objs,
										Function<T, String[]> function) {
		List<String[]> bodyList = objs.stream().map(function).collect(Collectors.toList());
		writeCsvFile(filename, title, bodyList);
	}

    private static void write(CSVWriter writer, String[] title, List<String[]> bodyList) {
		if (title != null && title.length > 0) {
			writer.writeNext(title);
		}
		if (bodyList != null && !bodyList.isEmpty()) {
			writer.writeAll(bodyList);
		}
	}
}
