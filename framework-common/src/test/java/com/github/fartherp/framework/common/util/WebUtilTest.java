/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import javax.mail.internet.MimeUtility;

public class WebUtilTest {

    @Test
    public void testGuessContentType() throws Exception {
        System.out.println(WebUtil.guessContentType("1.jpg"));
        System.out.println(WebUtil.guessContentType("2.xlsx"));
        System.out.println(WebUtil.guessContentType("3.png"));
        System.out.println(WebUtil.guessContentType("4.csv"));
        System.out.println(WebUtil.guessContentType("5.zip"));
        System.out.println(WebUtil.guessContentType("6.txt"));
        System.out.println(WebUtil.guessContentType("7.docx"));
        System.out.println(WebUtil.guessContentType("8.doc"));
        System.out.println(WebUtil.guessContentType("9.ppt"));
        System.out.println(WebUtil.guessContentType("10.pdf"));

    }

    @Test
    public void testEncodeContentDisposition() throws Exception {
        System.out.println(MimeUtility.encodeWord("1.jpg"));
    }
}