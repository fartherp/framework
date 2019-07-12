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

import com.github.fartherp.framework.common.util.PlatformDependent;
import com.github.fartherp.framework.poi.excel.ExcelDto;
import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.github.fartherp.framework.poi.excel.write.ExcelDataList.get;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2019/7/8
 */
public class HttpServletResponseExcelWriteTest {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	File tmpdir = PlatformDependent.tmpdir();

	String fileName = new File(tmpdir, "http_test_style.xls").getPath();

	@BeforeMethod
	public void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Test
	public void testWriteExcel() {
		HttpServletResponseExcelWrite.build(fileName, request, response)
			.deal(ExcelDataList.getTitle(), get(), ExcelDataList.getTenList())
			.write();
	}

	@Test
	public void testWriteExcelByInputStream() {
		HttpServletResponseExcelWrite.build(this.getClass().getResourceAsStream("/c.xls"), fileName, request, response)
			.deal(new WriteDeal<ExcelDto>() {
				public String[] dealBean(ExcelDto obj) {
					String[] result = new String[3];
					result[0] = obj.getId() + "";
					result[1] = obj.getName();
					result[2] = obj.getAge() + "";
					return result;
				}

				public int skipLine() {
					return 4;
				}

				@Override
				public Map<String, Object> additional() {
					Map<String, Object> map = new HashMap<>();
					map.put("quoteCurrency", "ETH");
					map.put("symbol", "USDT_ETH");
					map.put("startTime", "2019-01-09 00:00:00");
					map.put("endTime", "2019-01-09 12:00:00");
					return map;
				}
			}, ExcelDataList.getTenList())
			.write();
	}
}
