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

import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 已有Excel模板，读取流生成新Excel模板
 * @author CK
 * @date 2019/4/24
 */
public class CopyInputStreamExcelWrite extends AbstractExcelWrite {

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

	@Override
	public void close(Consumer<Object> consumer) {
    	try {
			super.close(consumer);
		} catch (Throwable ex) {
			if (this.inputStream != null) {
				try {
					this.inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    		throw ex;
		}
	}

	/**
     * 通过现有的excel读取，生成对应的excel数据
     */
    @Override
    public <T> Sheet createSheet(SheetWrapper currentSheetWrapper, String[] title, WriteDeal<T> deal) {
        int count = currentSheetWrapper.getTotal() + deal.skipLine();
        if (count > deal.setMaxRows(type)) {
            throw new RuntimeException("数据太大，超过当前sheet的最大数量");
        }
        if (currentSheetNumber >= wb.getNumberOfSheets()) {
            throw new RuntimeException("数据太大，超过设置的sheet数");
        }
        Sheet currentSheet = wb.getSheetAt(currentSheetNumber++);
        // 额外参数
        Map<String, Object> params = deal.additional();

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
                if (this.headStyle != null) {
                    cell.setCellStyle(this.headStyle);
                }
            }
        }
        return currentSheet;
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
