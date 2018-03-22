/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Locale;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/3/20
 */
public class ValidateUtilsTest {
    @Test
    public void validate() throws Exception {
        UserVo vo = new UserVo();
        vo.setEmail("214722930qq.com");
        vo.setRealName("a");
        try {
            ValidateUtils.validate(vo);
        } catch (Exception e) {
            String str = e.getMessage();
            System.out.println(str);
        }
    }

    @Test
    public void validateEnglish() throws Exception {
        UserVo vo = new UserVo();
        vo.setEmail("214722930qq.com");
        vo.setRealName("a");
        try {
            ValidateUtils.validate(vo, Locale.ENGLISH, AddGroup.class);
        } catch (Exception e) {
            String str = e.getMessage();
            System.out.println(str);
        }
    }

    @Test
    public void validateChinese() throws Exception {
        UserVo vo = new UserVo();
        vo.setEmail("214722930qq.com");
        vo.setRealName("a");
        try {
            ValidateUtils.validate(vo, Locale.CHINESE, EditGroup.class);
        } catch (Exception e) {
            String str = e.getMessage();
            System.out.println(str);
        }
    }
}