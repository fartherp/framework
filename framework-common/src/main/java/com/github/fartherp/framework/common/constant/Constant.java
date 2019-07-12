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
package com.github.fartherp.framework.common.constant;

/**
 * A Constant of the framework-common module that record with manage ALL Constant message
 * Author: CK
 * Date: 2015/6/30.
 */
public class Constant {

    public static final String EMPTY = "";

    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

    public static final String MAIL_SMTP_HOST = "mail.smtp.host";

    public static final String DEFAULT_DRIVE_NAME = "com.mysql.jdbc.Driver";

    public static final int DEFAULT_SCALE = 2;

    public static final String SEMICOLON_DELIMITER = ";";

    public static final String COMMA_DELIMITER = ",";

    public static final String DB_DEFAULT_DATE = "1970-01-01 00:00:00";

    public static final String DEFAULT_EMAIL_PATTTERN = "\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*";

    public static final String DEFAULT_MOBILE_PATTERN = "((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}";
}
