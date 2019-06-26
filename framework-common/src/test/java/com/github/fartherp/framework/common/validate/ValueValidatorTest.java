/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * <pre>
 *  @author: cuiyuqiang
 *  @email: cuiyuqiang@ddjf.com.cn
 *  @date: 2019/6/26 16:03
 *  @project: risk-control-parent
 * </pre>
 */
public class ValueValidatorTest {

	@Test
	public void testValidateSuccess() {
		User user = new User();
		user.setRealName("admin");
		ValidateUtils.validate(user);
	}

	@Test
	public void testValidateFail() {
		User user = new User();
		user.setRealName("a");
		try {
			ValidateUtils.validate(user);
		} catch (Exception e) {
			String str = e.getMessage();
			assertEquals(str, "姓名值不对");
		}
	}

	public class User {
		@Value(message = "姓名值不对", values = "admin")
		private String realName;

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}
	}
}
