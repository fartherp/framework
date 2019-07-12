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

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.List;

/**
 * 发送邮件工具类
 */
public class MailUtil {

    public static boolean sendEmail(MailInfo mailInfo) {
        try {
            HtmlEmail email = new HtmlEmail();
            // 配置信息
            email.setHostName(mailInfo.getHost());
            email.setFrom(mailInfo.getFrom());
            email.setAuthentication(mailInfo.getUser(), mailInfo.getPassword());
            email.setCharset("UTF-8");
            email.setSubject(mailInfo.getSubject());
            email.setHtmlMsg(mailInfo.getContent());

            // 添加附件
            List<EmailAttachment> attachments = mailInfo.getAttachments();
            if (null != attachments && !attachments.isEmpty()) {
                for (EmailAttachment attachment : attachments) {
                    email.attach(attachment);
                }
            }

            // 收件人
            List<String> toAddress = mailInfo.getToAddress();
            if (null != toAddress && !toAddress.isEmpty()) {
                for (String toAddres : toAddress) {
                    email.addTo(toAddres);
                }
            }
            // 抄送人
            List<String> ccAddress = mailInfo.getCcAddress();
            if (null != ccAddress && !ccAddress.isEmpty()) {
                for (String ccAddres : ccAddress) {
                    email.addCc(ccAddres);
                }
            }
            //邮件模板 密送人
            List<String> bccAddress = mailInfo.getBccAddress();
            if (null != bccAddress && !bccAddress.isEmpty()) {
                for (String bccAddres : bccAddress) {
                    email.addBcc(bccAddres);
                }
            }
            email.send();
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }
    }
}
