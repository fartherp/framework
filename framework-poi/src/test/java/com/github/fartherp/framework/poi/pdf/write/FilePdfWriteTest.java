/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.pdf.write;

import org.apache.commons.io.FileUtils;
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
 * @author: CK
 * @date: 2018/12/20
 */
public class FilePdfWriteTest {

    @Test
    public void write() {
        String fileName = "D:\\pdf1.pdf";
        FilePdfWrite.build(fileName)
                .addFontPath("D:\\project\\githubnew\\framework\\framework-poi\\src\\test\\resources")
                .deal(() -> {
                    String path = FilePdfWriteTest.class.getResource("/d.html").getPath();
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