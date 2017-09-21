/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

public class ValidateUtilsTest {

    @Test
    public void testValidateBaiduMail() throws Exception {
        String mail = "v_cuiyuqiang@baidu.com;214722930@qq.com;yuqiang.cui@gmail.com;cuiyuqiang@163.com";
        String s = ValidateUtils.validateBaiduMail(mail);
        System.out.println(s);
    }

    @Test
    public void testValidateMobile() throws Exception {
        String mobile = "18611714795;186117147951";
        String s = ValidateUtils.validateMobile(mobile);
        System.out.println(s);
    }
}