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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/12/19
 */
public class FilePdfWrite extends AbstractPdfWrite {

    private FilePdfWrite(String fileName) {
        super(fileName);
    }

    public PdfWrite createOutputStream() {
        try {
            this.outputStream = new FileOutputStream(this.fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件不存在", e);
        }
        return this;
    }

    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     * FilePdfWrite.build("合同.pdf")
     *         .addFontPath(getFontPath())//字体路径
     *         .deal((PdfWriteDeal&lt;String&gt;) () -> "合同内容")
     *         .write();
     * </pre>
     *
     * @param fileName the output file name
     *
     * @see <a href="https://github.com/fartherp/framework/blob/master/framework-poi/src/test/resources/d.html">
     *     file content</a>
     */
    public static FilePdfWrite build(String fileName) {
        Objects.requireNonNull(fileName);
        return new FilePdfWrite(fileName);
    }
}
