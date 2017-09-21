/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util.email;

import org.testng.annotations.Test;

public class SimpleMailSenderTest {
    @Test
    public void testSend() {
        SimpleMail simpleMail = new SimpleMail();
        simpleMail.setSubject("tile");
        simpleMail.setContent("hello world");
        simpleMail.setRecipients("yuqiang.cui@gmail.com");
        SimpleMailSender sender = new SimpleMailSender("ck_queens@qq.com", "queens_tara");
//        sender.send(simpleMail);
    }
}