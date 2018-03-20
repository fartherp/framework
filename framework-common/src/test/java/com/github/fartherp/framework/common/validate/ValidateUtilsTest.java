/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/3/20
 */
public class ValidateUtilsTest {
    @Test
    public void testValidate() throws Exception {
        UserVo vo = new UserVo();
        vo.setEmail("214722930qq.com");
        vo.setRealName("a");
        try {
            ValidateUtils.validate(vo);
        } catch (Exception e) {
            String str = e.getMessage();
            Assert.assertNotNull(str);
        }
        try {
            ValidateUtils.validate(vo, AddGroup.class);
        } catch (Exception e) {
            String str = e.getMessage();
            Assert.assertNotNull(str);
        }
        try {
            ValidateUtils.validate(vo, EditGroup.class);
        } catch (Exception e) {
            String str = e.getMessage();
            Assert.assertNotNull(str);
        }
    }

}