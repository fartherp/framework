/*
 * Copyright (c) 2018. CK. All rights reserved.
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
