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
package com.github.fartherp.framework.common.validate;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2019/6/26
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
