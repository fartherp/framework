/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import javax.mail.internet.MimeUtility;

public class WebUtilsTest {

    @Test
    public void testGuessContentType() throws Exception {
        System.out.println(WebUtils.guessContentType("1.jpg"));
        System.out.println(WebUtils.guessContentType("2.xlsx"));
        System.out.println(WebUtils.guessContentType("3.png"));
        System.out.println(WebUtils.guessContentType("4.csv"));
        System.out.println(WebUtils.guessContentType("5.zip"));
        System.out.println(WebUtils.guessContentType("6.txt"));
        System.out.println(WebUtils.guessContentType("7.docx"));
        System.out.println(WebUtils.guessContentType("8.doc"));
        System.out.println(WebUtils.guessContentType("9.ppt"));
        System.out.println(WebUtils.guessContentType("10.pdf"));

    }

    @Test
    public void testEncodeContentDisposition() throws Exception {
        System.out.println(MimeUtility.encodeWord("1.jpg"));
    }
}