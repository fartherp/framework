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
package com.github.fartherp.framework.compress.jar;

import com.github.fartherp.framework.compress.CommonCompress;
import org.apache.commons.io.IOUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/30.
 */
public class JarCompress extends CommonCompress {

    public JarCompress(CommonCompress commonCompress) {
        this.commonCompress = commonCompress;
    }

    public void compress() {
        startCompress(commonCompress.getSourceFile(), commonCompress.getTargetFile());
    }

    public String unCompress() {
        File target = commonCompress.getTargetFile();
        startUnCompress(commonCompress.getSourceFile(), target);
        return target.getAbsolutePath();
    }

    public void startCompress(File sourceFile, File targetFile) {
        JarOutputStream jarOutputStream = null;
        try {
            jarOutputStream = new JarOutputStream(new FileOutputStream(targetFile));
            doCompress(jarOutputStream, sourceFile, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(jarOutputStream);
        }
    }

    public void doCompress(JarOutputStream outputStream, File sourceFile, String path) throws Exception {
        if (sourceFile.isDirectory()) {
            String[] dirList = sourceFile.list();
            String subPath = (path == null) ? "" : (path + sourceFile.getName() + File.separator);
            if (path != null) {
                JarEntry je = new JarEntry(subPath);
                je.setTime(sourceFile.lastModified());
                outputStream.putNextEntry(je);
                outputStream.flush();
                outputStream.closeEntry();
            }
            for (String dir : dirList) {
                File f = new File(sourceFile, dir);
                doCompress(outputStream, f, subPath);
            }
        } else {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(sourceFile);
                JarEntry jarEntry = new JarEntry(path + sourceFile.getName());
                jarEntry.setTime(sourceFile.lastModified());
                outputStream.putNextEntry(jarEntry);
                IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
                outputStream.closeEntry();
            } catch (IOException e) {
                throw e;
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    protected void startUnCompress(File sourceFile, File target) {
        JarInputStream inputStream = null;
        try {
            inputStream = new JarInputStream(new FileInputStream(sourceFile));
            JarEntry entry;
            while ((entry = inputStream.getNextJarEntry()) != null) {
                if (entry.isDirectory()) {
                    File dir = new File(target, entry.getName());
                    dir.mkdir();
                    if (entry.getTime() != -1) {
                        dir.setLastModified(entry.getTime());
                    }
                    continue;
                }
                int count;
                byte data[] = new byte[BUFFER];
                File destFile = new File(target, entry.getName());
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER);
                while ((count = inputStream.read(data, 0, BUFFER)) != -1) {
                    outputStream.write(data, 0, count);
                }
                outputStream.flush();
                IOUtils.closeQuietly(outputStream);
                if (entry.getTime() != -1) {
                    destFile.setLastModified(entry.getTime());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
