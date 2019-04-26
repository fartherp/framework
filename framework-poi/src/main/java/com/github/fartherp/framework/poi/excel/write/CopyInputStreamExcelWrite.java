/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

/**
 * 已有Excel模板，读取流生成新Excel模板
 * Author: CK
 * Date: 2019/4/24
 */
public class CopyInputStreamExcelWrite extends AbstractExcelWrite implements InputStreamExcelWrite {

    /**
     * 文件输入流
     */
    protected InputStream inputStream;

    public CopyInputStreamExcelWrite(InputStream inputStream, String fileName, OutputStreamDelegate delegate) {
        super(fileName, delegate);
        this.inputStream = inputStream;
    }

    @Override
    public void createWb() {
        Objects.requireNonNull(inputStream);
        try {
            this.wb = WorkbookFactory.create(this.inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过现有的excel读取，生成对应的excel数据
     */
    @Override
    public <T> void createSheet(DealWrapper<T> dealWrapper) {
        int count = dealWrapper.getList().size() + dealWrapper.getDeal().skipLine();
        if (count > dealWrapper.getDeal().setMaxRows(type)) {
            throw new RuntimeException("数据太大，超过当前sheet的最大数量");
        }
        if (currentSheetNumber >= wb.getNumberOfSheets()) {
            throw new RuntimeException("数据太大，超过设置的sheet数");
        }
        currentSheet = wb.getSheetAt(currentSheetNumber++);
        // 额外参数
        Map<String, Object> params = dealWrapper.getDeal().additional();

        // 处理跳过的行数据
        int lastRowNum = currentSheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            Row row = currentSheet.getRow(i);
            int lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                Cell cell = row.getCell(j);
                String stringCellValue = cell.getStringCellValue();
                if (StringUtils.isBlank(stringCellValue) || MapUtils.isEmpty(params)) {
                    continue;
                }
                stringCellValue =  new StringSubstitutor(params).replace(stringCellValue);
                cell.setCellValue(stringCellValue);
                if(this.headStyle != null){
                    cell.setCellStyle(this.headStyle);
                }
            }
        }
    }

    @Override
    public void write() {
        try {
            super.write();
        } finally {
            if (this.inputStream != null) {
                try {
                    this.inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
