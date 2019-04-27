/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.ExcelDto;
import com.github.fartherp.framework.poi.excel.ExcelDto1;

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
}
