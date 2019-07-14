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
package com.github.fartherp.framework.poi.pdf.write;

import com.github.fartherp.framework.common.util.PlatformDependent;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/12/20
 */
public class FilePdfWriteTest {

	File tmpdir = PlatformDependent.tmpdir();

	String fileName = new File(tmpdir, "test_pdf.pdf").getPath();

	String fontPath = PlatformDependent.isWindows() ? "C:\\Windows\\Fonts" : "/usr/share/fonts";

	@AfterMethod
	public void tearDown() throws IOException {
		File file = new File(fileName);
		Assert.assertTrue(file.exists());
		FileUtils.forceDelete(file);
	}

    @Test
    public void testWrite() {
		String path = FilePdfWriteTest.class.getResource("/d.html").getPath();
        FilePdfWrite.build(fileName)
                .addFontPath(fontPath)
                .deal(() -> {
                    File file = new File(path);
                    String html = null;
                    try {
                        html = FileUtils.readFileToString(file, Charset.forName("GBK"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String[] h = html.split("\\$\\$\\{}");
                    List<String> htmls = Collections.unmodifiableList(Arrays.asList(h));
                    StringBuilder sb = new StringBuilder();
                    sb.append(htmls.get(0));
                    sb.append("20183732736");
                    sb.append(htmls.get(1));
                    sb.append("机械");
                    sb.append(htmls.get(2));
                    sb.append("物流");
                    sb.append(htmls.get(3));
                    sb.append("20180064");
                    sb.append(htmls.get(4));
                    sb.append("2,567");
                    sb.append(htmls.get(5));
                    sb.append("食品");
                    sb.append(htmls.get(6));
                    sb.append("箱");
                    sb.append(htmls.get(7));
                    sb.append("80");
                    sb.append(htmls.get(8));
                    sb.append("10");
                    sb.append(htmls.get(9));
                    sb.append("30");
                    sb.append(htmls.get(10));
                    sb.append("浙江杭州");
                    sb.append(htmls.get(11));
                    sb.append("陕西西安");
                    sb.append(htmls.get(12));
                    sb.append("开发");
                    sb.append(htmls.get(13));
                    sb.append("测试");
                    sb.append(htmls.get(14));
                    return sb.toString();
                })
                .write();
    }
}
