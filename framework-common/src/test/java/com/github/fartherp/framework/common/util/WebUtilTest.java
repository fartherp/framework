/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import javax.mail.internet.MimeUtility;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class WebUtilTest {

    @Test
    public void testGuessContentType() throws Exception {
        assertEquals(WebUtil.guessContentType("1.jpg"), "image/jpeg");
		assertNull(WebUtil.guessContentType("2.xlsx"));
        assertEquals(WebUtil.guessContentType("3.png"), "image/png");
		assertNull(WebUtil.guessContentType("4.csv"));
        assertEquals(WebUtil.guessContentType("5.zip"), "application/zip");
        assertEquals(WebUtil.guessContentType("6.txt"), "text/plain");
		assertNull(WebUtil.guessContentType("7.docx"));
		assertNull(WebUtil.guessContentType("8.doc"));
		assertNull(WebUtil.guessContentType("9.ppt"));
        assertEquals(WebUtil.guessContentType("10.pdf"), "application/pdf");
		assertNull(WebUtil.guessContentType("11.xls"));
    }

    @Test
    public void testEncodeContentDisposition() throws Exception {
        assertEquals(MimeUtility.encodeWord("1.jpg"), "1.jpg");
    }
}
