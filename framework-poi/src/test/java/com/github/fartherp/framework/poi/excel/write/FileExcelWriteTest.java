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
import com.github.fartherp.framework.poi.excel.ExcelDto1;
import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.fartherp.framework.poi.excel.write.ExcelDataList.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2017/11/25
 */
public class FileExcelWriteTest {

	File tmpdir = PlatformDependent.tmpdir();

	String fileName = new File(tmpdir, "test_style1.xls").getPath();

	@AfterMethod
	public void tearDown() throws IOException {
		File file = new File(fileName);
		Assert.assertTrue(file.exists());
		FileUtils.forceDelete(file);
	}

	@Test
    public void testWriteSingleOverMaxSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), ExcelDataList.getList())
                .write();
    }

    @Test
    public void testWriteSingleNullSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), null)
                .write();
    }

    @Test
    public void testWriteSingleEmptySheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), new ArrayList<>())
                .write();
    }

    @Test
    public void testWriteTwoOverMaxSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), ExcelDataList.getList())
                .deal(getTitle(), get(), ExcelDataList.getList())
                .write();
    }

    @Test
    public void testWriteThreeOverMaxSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), ExcelDataList.getList())
                .deal(getTitle(), get(), ExcelDataList.getList())
                .deal(getTitle(), get(), ExcelDataList.getList())
                .write();
    }

    @Test
    public void testWriteSingleEqualMaxSheetExcel() {
        List<ExcelDto> excelDtos = ExcelDataList.getList();
        excelDtos.remove(65535);
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), excelDtos)
                .write();
    }

    @Test
    public void testWriteTwoEqualMaxSheetExcel() {
        List<ExcelDto> excelDtos = ExcelDataList.getList();
        excelDtos.remove(65535);
        List<ExcelDto> excelDtos1 = ExcelDataList.getList();
        excelDtos1.remove(65535);
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), excelDtos)
                .deal(getTitle(), get(), excelDtos1)
                .write();
    }

    @Test
    public void testWriteThreeEqualMaxSheetExcel() {
        List<ExcelDto> excelDtos = ExcelDataList.getList();
        excelDtos.remove(65535);
        List<ExcelDto> excelDtos1 = ExcelDataList.getList();
        excelDtos1.remove(65535);
        List<ExcelDto> excelDtos2 = ExcelDataList.getList();
        excelDtos2.remove(65535);

        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), excelDtos)
                .deal(getTitle(), get(), excelDtos1)
                .deal(getTitle(), get(), excelDtos2)
                .write();
    }

    @Test
    public void testWriteSingleLtMaxSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), ExcelDataList.getTenList())
                .deal(getTitle(), get(), ExcelDataList.getTenList())
                .deal(getTitle(), get(), ExcelDataList.getTenList())
                .deal(getTitle(), get(), ExcelDataList.getTenList())
                .deal(getTitle(), get(), ExcelDataList.getTenList())
                .write();
    }

    @Test
    public void testWriteSingleGtMaxSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), ExcelDataList.getTenList())
                .deal(getTitle(), get(), ExcelDataList.getTenList())
                .deal(getTitle(), get(), ExcelDataList.getList())
                .write();
    }

    @Test
    public void testWriteMultipleSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), ExcelDataList.getTenList())
                .deal(getTitle1(), get1(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void testWriteMultipleSheetOverMaxCountExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), ExcelDataList.getList())
                .deal(getTitle1(), get1(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void testWriteMultipleSheetNullExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), null)
                .deal(getTitle1(), get1(), null)
                .write();
    }

    @Test
    public void testWriteMultipleSheetEmptyExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), new ArrayList<>())
                .deal(getTitle1(), get1(), new ArrayList<>())
                .write();
    }

    @Test
    public void testWriteMultipleSheetOverMaxTwoCountExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), ExcelDataList.getList())
                .deal(getTitle1(), get1(), ExcelDataList.getTenList1())
                .deal(getTitle(), get(), ExcelDataList.getList())
                .write();
    }

    @Test
    public void testWriteMultipleSheetOverMaxTwoDoubleCountExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), get(), ExcelDataList.getList())
                .deal(getTitle1(), get1(), ExcelDataList.getTenList1())
                .deal(getTitle(), get(), ExcelDataList.getList())
                .deal(getTitle1(), get1(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void testWriteMultipleSheetAndNameExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getTenList())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void testWriteMultipleSheetAndNameLtMaxExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getTenList())
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getTenList())
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getTenList())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void testWriteMultipleSheetAndNameLtMaxDisorderExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getTenList())
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getTenList())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getTenList())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void testWriteMultipleSheetAndNameOverMaxCountExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getList())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void testWriteMultipleSheetAndNameNullExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), getExcelDtoName(), null)
                .deal(getTitle1(), getTestName(), null)
                .write();
    }

    @Test
    public void testWriteMultipleSheetAndNameEmptyExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), getExcelDtoName(), new ArrayList<>())
                .deal(getTitle1(), getTestName(), new ArrayList<>())
                .write();
    }

    @Test
    public void testWriteMultipleSheetAndNameOverMaxCountTwoExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getList())
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getList())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void testWriteMultipleSheetAndNameOverMaxCountTwoDisorderExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getList())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getList())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void testWriteMultipleSheetAndNameOverMaxCountTwoTwoDisorderExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getList())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .deal(getTitle1(), getTestName(), ExcelDataList.getList1())
                .deal(getTitle(), getExcelDtoName(), ExcelDataList.getList())
                .deal(getTitle1(), getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void testWriteExcelByInputStream() {
        FileExcelWrite.build(this.getClass().getResourceAsStream("/c.xls"), fileName)
                .setLargeDataMode(false)
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

    @Test
    public void testWriteMultipleSheetExcelByInputStream() {
        FileExcelWrite.build(this.getClass().getResourceAsStream("/d.xls"), fileName)
                .setLargeDataMode(true)
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
                .deal(new WriteDeal<ExcelDto1>() {
                    public String[] dealBean(ExcelDto1 obj) {
                        String[] result = new String[3];
                        result[0] = obj.getId() + "";
                        result[1] = obj.getType();
                        result[2] = obj.getDesc();
                        return result;
                    }

                    public int skipLine() {
                        return 4;
                    }

                    @Override
                    public Map<String, Object> additional() {
                        Map<String, Object> map = new HashMap<>();
                        map.put("quoteCurrency", "BTC");
                        map.put("symbol", "BTC_USDT");
                        map.put("startTime", "2019-04-01 00:00:00");
                        map.put("endTime", "2019-04-01 12:00:00");
                        return map;
                    }
                }, ExcelDataList.getTenList1())
                .write();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testWriteExcelOverMaxCountExceptionByInputStream() {
		FileExcelWrite.build(this.getClass().getResourceAsStream("/c.xls"), fileName)
				.setLargeDataMode(true)
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
				}, ExcelDataList.getList())
				.write();
    }

    @Test
    public void testWriteExcelMaxCountByInputStream() {
        List<ExcelDto> list = ExcelDataList.getList();
        list.remove(65535);
        list.remove(65534);
        list.remove(65533);
        list.remove(65532);
        FileExcelWrite.build(this.getClass().getResourceAsStream("/c.xls"), fileName)
                .setLargeDataMode(true)
                .deal(new WriteDeal<ExcelDto>() {
                    public String[] dealBean(ExcelDto obj) {
                        String[] result = new String[3];
                        result[0] = obj.getIp();
                        result[1] = obj.getName();
                        result[2] = obj.getVersion();
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
                }, list)
                .write();
    }
}
