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

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/5/21
 */
public class MailUtilTest {
//    @Test
    public void testSendEmail() {
        MailInfo mailInfo = new MailInfo();
        mailInfo.setHost("smtp.exmail.qq.com");
        mailInfo.setFrom("support@juzix.io");
        mailInfo.setUser("support@juzix.io");
        mailInfo.setPassword("JuzhenP@C2018");
        mailInfo.setSubject("标题");
        mailInfo.setContent("内容");
        List<String> list = new ArrayList<>();
        list.add("214722930@qq.com");
        mailInfo.setToAddress(list);
        MailUtil.sendEmail(mailInfo);
    }

}
