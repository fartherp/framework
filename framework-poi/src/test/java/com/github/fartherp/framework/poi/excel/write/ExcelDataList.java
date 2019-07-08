/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.ExcelDto;
import com.github.fartherp.framework.poi.excel.ExcelDto1;
import com.github.fartherp.framework.poi.excel.WriteDeal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2019/4/24
 */
public class ExcelDataList {

    public static List<ExcelDto> getTenList() {
        List<ExcelDto> excelDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelDto dto = new ExcelDto();
            dto.setId((long) i);
            dto.setName("name" + i);
            dto.setAge(i);
            dto.setTime("2017-10-20 10:10:1" + i);
            dto.setClient("client"  + i);
            dto.setVersion("1.0." + i);
            dto.setIp("192.168.1." + i);
            dto.setStatus(i);
            excelDtos.add(dto);
        }
        return excelDtos;
    }

    public static List<ExcelDto1> getTenList1() {
        List<ExcelDto1> excelDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelDto1 dto = new ExcelDto1();
            dto.setId((long) i);
            dto.setType("type" + i);
            dto.setDesc("desc" + i);
            excelDtos.add(dto);
        }
        return excelDtos;
    }

    public static List<ExcelDto> getList() {
        List<ExcelDto> excelDtos = new ArrayList<>();
        for (int i = 0; i < 65536; i++) {
            ExcelDto dto = new ExcelDto();
            dto.setTime("2017-10-20 10:10:1" + i);
            dto.setName("name" + i);
            dto.setClient("client"  + i);
            dto.setVersion("1.0." + i);
            dto.setIp("192.168.1." + i);
            dto.setStatus(i);
            excelDtos.add(dto);
        }
        return excelDtos;
    }

    public static List<ExcelDto1> getList1() {
        List<ExcelDto1> excelDtos = new ArrayList<>();
        for (int i = 0; i < 65536; i++) {
            ExcelDto1 dto = new ExcelDto1();
            dto.setId((long) i);
            dto.setType("type" + i);
            dto.setDesc("desc" + i);
            excelDtos.add(dto);
        }
        return excelDtos;
    }

	public static WriteDeal<ExcelDto> get() {
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

	public static WriteDeal<ExcelDto1> get1() {
		return obj -> {
			String[] result = new String[3];
			result[0] = obj.getId() + "";
			result[1] = obj.getType();
			result[2] = obj.getDesc();
			return result;
		};
	}

	public static String[] getTitle() {
    	String[] title = new String[6];
		title[0] = "登录时间";
		title[1] = "用户名";
		title[2] = "访问端";
		title[3] = "版本系统";
		title[4] = "登录IP";
		title[5] = "状态";
		return title;
	}

	public static String[] getTitle1() {
		String[] title = new String[3];
		title[0] = "id";
		title[1] = "type";
		title[2] = "desc";
		return title;
	}

	public static WriteDeal<ExcelDto> getExcelDtoName() {
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

	public static WriteDeal<ExcelDto1> getTestName() {
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
}
