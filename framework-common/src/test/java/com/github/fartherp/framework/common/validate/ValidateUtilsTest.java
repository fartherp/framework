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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/3/20
 */
public class ValidateUtilsTest {

    @Test
    public void testValidate() {
        User user = new User();
        user.setRealName("a");
        try {
            ValidateUtils.validate(user);
        } catch (Exception e) {
            String str = e.getMessage();
            assertEquals(str, "用户id不能为空");
        }
    }

    @Test
    public void testFastValidate() {
        User user = new User();
        try {
            ValidateUtils.validate(user, false);
        } catch (Exception e) {
            String str = e.getMessage();
            String [] arrays = str.split(",");
            assertEquals(arrays.length, 2);
        }
    }

	@Test
	public void testValidateSuccess() {
		User user = new User();
		user.setId(1L);
		user.setRealName("a");
		ValidateUtils.validate(user);
	}

	public class User {

		@NotNull(message = "用户id不能为空")
		private Long id;

		@NotEmpty(message = "真实姓名不能为空")
		private String realName;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}
	}
}
