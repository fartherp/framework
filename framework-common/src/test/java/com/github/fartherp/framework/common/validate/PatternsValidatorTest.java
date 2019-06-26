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
 *  @date: 2019/6/26 16:20
 *  @project: risk-control-parent
 * </pre>
 */
public class PatternsValidatorTest {

	@Test
	public void testShoujiValidateSuccess() {
		User user = new User();
		user.setPhone("18611714790");
		ValidateUtils.validate(user);
	}

	@Test
	public void testZuojiValidateSuccess() {
		User user = new User();
		user.setPhone("0755-84034100");
		ValidateUtils.validate(user);
	}

	@Test
	public void testValidateFail() {
		User user = new User();
		user.setPhone("14611714790");
		try {
			ValidateUtils.validate(user);
		} catch (Exception e) {
			String str = e.getMessage();
			assertEquals(str, "手机或座机不对");
		}
	}

	public class User {
		@Patterns(message = "手机或座机不对", regexps = {"^(13|15|18)\\d{9}$", "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?$"})
		private String phone;

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
	}
}
