/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.*;

/**
 * <pre>
 *  @author: cuiyuqiang
 *  @email: cuiyuqiang@ddjf.com.cn
 *  @date: 2019/6/26 16:07
 *  @project: risk-control-parent
 * </pre>
 */
public class DateValueValidatorTest {

	@Test
	public void testValidate() {
		User user = new User();
		ValidateUtils.validate(user);
	}

	@Test
	public void testValidateSuccess() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		User user = new User();
		user.setCreateTime(calendar.getTime());
		calendar.add(Calendar.MONTH, 2);
		user.setUpdateTime(calendar.getTime());
		ValidateUtils.validate(user);
	}

	@Test
	public void testCreateTimeValidateSuFail() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		User user = new User();
		user.setCreateTime(calendar.getTime());
		calendar.add(Calendar.MONTH, 1);
		user.setUpdateTime(calendar.getTime());
		try {
			ValidateUtils.validate(user);
		} catch (Exception e) {
			String str = e.getMessage();
			assertEquals(str, "创建时间太晚");
		}
	}

	@Test
	public void testUpdateTimeValidateSuFail() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		User user = new User();
		user.setCreateTime(calendar.getTime());
		calendar.add(Calendar.MONTH, -1);
		user.setUpdateTime(calendar.getTime());
		try {
			ValidateUtils.validate(user);
		} catch (Exception e) {
			String str = e.getMessage();
			assertEquals(str, "更新时间太早");
		}
	}

	public class User {
		@DateValue(message = "创建时间太晚", values = "before")
		private Date createTime;

		@DateValue(message = "更新时间太早", values = "after")
		private Date updateTime;

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
	}
}
