/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/5/21
 */
public class MailUtilTest {
//    @Test
    public void testSendEmail() {
        MailInfo mailInfo = new MailInfo();
        mailInfo.setHost("smtp.exmail.qq.com");
        mailInfo.setFrom("support@juzix.io");
        mailInfo.setUser("support@juzix.io");
        mailInfo.setPassword("JuzhenP@C2018");
        mailInfo.setSubject("标题");
        mailInfo.setContent("内容");
        List<String> list = new ArrayList<>();
        list.add("214722930@qq.com");
        mailInfo.setToAddress(list);
        MailUtil.sendEmail(mailInfo);
    }

}
