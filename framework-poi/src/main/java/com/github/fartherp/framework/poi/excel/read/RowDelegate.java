/*
 * Copyright (c) 2021. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.read;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 *
 * @author cuiyuqiang
 * @date 2021/3/22
 */
public class RowDelegate {
	private Row row;

	public Optional<String> getString(int i) {
		Cell cell = row.getCell(i);
		return cell != null ? Optional.of(cell.getStringCellValue()) : Optional.empty();
	}

	public Optional<Double> getNumeric(int i) {
		Cell cell = row.getCell(i);
		return cell != null ? Optional.of(cell.getNumericCellValue()) : Optional.empty();
	}

	public Optional<Boolean> getBool(int i) {
		Cell cell = row.getCell(i);
		return cell != null ? Optional.of(cell.getBooleanCellValue()) : Optional.empty();
	}

	public Optional<Date> getDate(int i) {
		Cell cell = row.getCell(i);
		return cell != null ? Optional.of(cell.getDateCellValue()) : Optional.empty();
	}

	public void setRow(Row row) {
		this.row = row;
	}
}
