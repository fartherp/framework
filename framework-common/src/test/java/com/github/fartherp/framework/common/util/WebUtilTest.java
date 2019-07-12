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
