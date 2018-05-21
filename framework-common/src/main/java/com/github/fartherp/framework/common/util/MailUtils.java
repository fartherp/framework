/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.List;

/**
 * 发送邮件工具类
 */
public class MailUtils {

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
            if (null != attachments && attachments.size() > 0) {
                for (EmailAttachment attachment : attachments) {
                    email.attach(attachment);
                }
            }

            // 收件人
            List<String> toAddress = mailInfo.getToAddress();
            if (null != toAddress && toAddress.size() > 0) {
                for (String toAddres : toAddress) {
                    email.addTo(toAddres);
                }
            }
            // 抄送人
            List<String> ccAddress = mailInfo.getCcAddress();
            if (null != ccAddress && ccAddress.size() > 0) {
                for (String ccAddres : ccAddress) {
                    email.addCc(ccAddres);
                }
            }
            //邮件模板 密送人
            List<String> bccAddress = mailInfo.getBccAddress();
            if (null != bccAddress && bccAddress.size() > 0) {
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
