/*
 * Copyright (c) 2017. CK. All rights reserved.
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

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/11/25
 */
public class FileExcelWriteTest {

    public static final String[] title = new String[6];
    public static final String[] title1 = new String [6];
    static {
        title[0] = "登录时间";
        title[1] = "用户名";
        title[2] = "访问端";
        title[3] = "版本系统";
        title[4] = "登录IP";
        title[5] = "状态";
        title1[0] = "id";
        title1[1] = "type";
        title1[2] = "desc";
    }

	File tmpdir = PlatformDependent.tmpdir();

	String fileName = new File(tmpdir, "test_style1.xls").getPath();

	@AfterMethod
	public void tearDown() throws IOException {
		File file = new File(fileName);
		Assert.assertTrue(file.exists());
		FileUtils.forceDelete(file);
	}

	@Test
    public void writeSingleOverMaxSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .write();
    }

    @Test
    public void writeSingleNullSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), null)
                .write();
    }

    @Test
    public void writeSingleEmptySheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), new ArrayList<>())
                .write();
    }

    @Test
    public void writeTwoOverMaxSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .deal(title, get(), ExcelDataList.getList())
                .write();
    }

    @Test
    public void writeThreeOverMaxSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .deal(title, get(), ExcelDataList.getList())
                .deal(title, get(), ExcelDataList.getList())
                .write();
    }

    @Test
    public void writeSingleEqualMaxSheetExcel() {
        List<ExcelDto> excelDtos = ExcelDataList.getList();
        excelDtos.remove(65535);
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), excelDtos)
                .write();
    }

    @Test
    public void writeTwoEqualMaxSheetExcel() {
        List<ExcelDto> excelDtos = ExcelDataList.getList();
        excelDtos.remove(65535);
        List<ExcelDto> excelDtos1 = ExcelDataList.getList();
        excelDtos1.remove(65535);
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), excelDtos)
                .deal(title, get(), excelDtos1)
                .write();
    }

    @Test
    public void writeThreeEqualMaxSheetExcel() {
        List<ExcelDto> excelDtos = ExcelDataList.getList();
        excelDtos.remove(65535);
        List<ExcelDto> excelDtos1 = ExcelDataList.getList();
        excelDtos1.remove(65535);
        List<ExcelDto> excelDtos2 = ExcelDataList.getList();
        excelDtos2.remove(65535);

        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), excelDtos)
                .deal(title, get(), excelDtos1)
                .deal(title, get(), excelDtos2)
                .write();
    }

    @Test
    public void writeSingleLtMaxSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getTenList())
                .write();
    }

    @Test
    public void writeSingleGtMaxSheetExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getList())
                .write();
    }

    private WriteDeal<ExcelDto> get() {
        return obj -> {
            String[] result = new String[6];
            result[0] = obj.getTime();
            result[1] = obj.getName();
            result[2] = obj.getClient();
            result[3] = obj.getVersion();
            result[4] = obj.getIp();
            result[5] = obj.getStatus() + "";
            return result;
        };
    }

    @Test
    public void writeMultipleSheetExcel() {
        String[] title1 = new String [6];
        title1[0] = "id";
        title1[1] = "type";
        title1[2] = "desc";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title1, get1(), ExcelDataList.getTenList1())
                .write();
    }

    private WriteDeal<ExcelDto1> get1() {
        return obj -> {
            String[] result = new String[3];
            result[0] = obj.getId() + "";
            result[1] = obj.getType();
            result[2] = obj.getDesc();
            return result;
        };
    }

    @Test
    public void writeMultipleSheetOverMaxCountExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .deal(title1, get1(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void writeMultipleSheetNullExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), null)
                .deal(title1, get1(), null)
                .write();
    }

    @Test
    public void writeMultipleSheetEmptyExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), new ArrayList<>())
                .deal(title1, get1(), new ArrayList<>())
                .write();
    }

    @Test
    public void writeMultipleSheetOverMaxTwoCountExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .deal(title1, get1(), ExcelDataList.getTenList1())
                .deal(title, get(), ExcelDataList.getList())
                .write();
    }

    @Test
    public void writeMultipleSheetOverMaxTwoDoubleCountExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .deal(title1, get1(), ExcelDataList.getTenList1())
                .deal(title, get(), ExcelDataList.getList())
                .deal(title1, get1(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void writeMultipleSheetAndNameExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void writeMultipleSheetAndNameLtMaxExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void writeMultipleSheetAndNameLtMaxDisorderExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    public WriteDeal<ExcelDto> getExcelDtoName() {
        return new WriteDeal<ExcelDto>() {
            @Override
            public String[] dealBean(ExcelDto obj) {
                String[] result = new String[6];
                result[0] = obj.getTime();
                result[1] = obj.getName();
                result[2] = obj.getClient();
                result[3] = obj.getVersion();
                result[4] = obj.getIp();
                result[5] = obj.getStatus() + "";
                return result;
            }

            @Override
            public String name() {
                return "ExcelDto";
            }
        };
    }

    public WriteDeal<ExcelDto1> getTestName() {
        return new WriteDeal<ExcelDto1>() {
            @Override
            public String[] dealBean(ExcelDto1 obj) {
                String[] result = new String[3];
                result[0] = obj.getId() + "";
                result[1] = obj.getType();
                result[2] = obj.getDesc();
                return result;
            }

            @Override
            public String name() {
                return "test";
            }
        };
    }

    @Test
    public void writeMultipleSheetAndNameOverMaxCountExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void writeMultipleSheetAndNameNullExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), null)
                .deal(title1, getTestName(), null)
                .write();
    }

    @Test
    public void writeMultipleSheetAndNameEmptyExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), new ArrayList<>())
                .deal(title1, getTestName(), new ArrayList<>())
                .write();
    }

    @Test
    public void writeMultipleSheetAndNameOverMaxCountTwoExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void writeMultipleSheetAndNameOverMaxCountTwoDisorderExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void writeMultipleSheetAndNameOverMaxCountTwoTwoDisorderExcel() {
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .deal(title1, getTestName(), ExcelDataList.getList1())
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();
    }

    @Test
    public void writeExcelByInputStream() {
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
    public void writeMultipleSheetExcelByInputStream() {
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

    @Test(expectedExceptions = RuntimeException.class)
    public void writeExcelOverMaxCountExceptionByInputStream() {
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
    public void writeExcelMaxCountByInputStream() {
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
