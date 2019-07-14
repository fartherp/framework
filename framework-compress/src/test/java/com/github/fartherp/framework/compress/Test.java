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
package com.github.fartherp.framework.compress;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by IntelliJ IDEA .
 * @author CK
 * @date 2016/5/18
 */
public class Test {
    public static final int bufferLen = 1024 * 1024;

    public static final SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat mm = new SimpleDateFormat("MM");
    public static final SimpleDateFormat dd = new SimpleDateFormat("dd");

    public static String unCompress(String draftPath, String zipFilePath) {
        String id = Thread.currentThread().getId() + "" + System.currentTimeMillis();
        Date date = new Date();
        File zipFile = new File(zipFilePath);
        File outDir = new File(draftPath, (yyyy.format(date) + File.separator + mm.format(date) + File.separator + dd.format(date)));
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        ZipArchiveInputStream is = null;
        try {
            is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile), bufferLen), "GBK");
            ZipArchiveEntry entry = null;
            boolean flag = false;
            while ((entry = is.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = null;
                    if (!flag) {
                        directory = new File(outDir, id + entry.getName());
                        flag = true;
                    } else {
                        directory = new File(outDir, id + entry.getName());
                    }
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                } else {
                    OutputStream os = null;
                    try {
                        if (entry.getName().contains("xlsx") || entry.getName().contains("xls")) {
                            File file = new File(outDir, id + entry.getName());
                            os = new BufferedOutputStream(new FileOutputStream(file), bufferLen);
                        } else {
                            File file = new File(outDir, id + entry.getName());
                            os = new BufferedOutputStream(new FileOutputStream(file), bufferLen);
                        }
                        IOUtils.copy(is, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
            zipFile.delete();
        }
        return outDir + File.separator + id;
    }

    public static void doCompress(File sourceFile, File targetFile) {
//        if (!targetFile.exists()) {
//            targetFile.mkdirs();
//        }
        ZipOutputStream zipOutputStream = null;
        CheckedOutputStream checkedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetFile);
            checkedOutputStream = new CheckedOutputStream(fileOutputStream, new CRC32());
            zipOutputStream = new ZipOutputStream(new BufferedOutputStream(checkedOutputStream));
            doCompress(zipOutputStream, sourceFile, sourceFile.getName());
            zipOutputStream.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ZIP 压缩文件或文件夹错误");
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (checkedOutputStream != null) {
                try {
                    checkedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void doCompress(ZipOutputStream zipOutputStream, File sourceFile, String path) throws Exception {
        if (sourceFile.isDirectory()) {
            File[] listFiles = sourceFile.listFiles();
            zipOutputStream.putNextEntry(new ZipEntry(path + File.separator));
            path = path.equals("") ? "" : path + File.separator;
            if (null != listFiles) {
                for (File file : listFiles) {
                    doCompress(zipOutputStream, file, path + file.getName());
                }
            }
        } else {
            zipOutputStream.putNextEntry(new ZipEntry(path));
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            byte[] bytes = new byte[1024];
            int count;
            while ((count = fileInputStream.read(bytes, 0, 1024)) != -1) {
                zipOutputStream.write(bytes, 0, count);
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        String file = unCompress("D:\\draftPath", "D:\\1001.zip");
//        System.out.println(file);
//        doCompress(new File("D:\\batch_merchant_template"), new File("D:\\a.zip"));
    }
}
