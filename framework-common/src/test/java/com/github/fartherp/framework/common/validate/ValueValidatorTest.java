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
