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
package com.github.fartherp.framework.poi;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2016/1/17
 */
public interface Constant {
    /**
     *
     */
	String OFFICE_EXCEL_2003_POSTFIX = "xls";
    /**
     *
     */
	String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

    int WIDTH_DEFAULT = 6000;

    short HEIGHT_DEFAULT = 500;

    String PDF_CONTENT_TYPE = "application/pdf;charset=GBK";

    String EXCEL_CONTENT_TYPE = "application/msexcel;charset=GBK";

    String DOC_CONTENT_TYPE = "application/msword;charset=GBK";
}
