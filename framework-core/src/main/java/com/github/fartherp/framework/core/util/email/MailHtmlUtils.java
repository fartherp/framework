/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util.email;

import com.github.fartherp.framework.core.bean.ServiceLocator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/8/5
 */
public class MailHtmlUtils {

    private static final Log log = LogFactory.getLog(MailHtmlUtils.class);

    /**
     * 发送邮件
     * @param from 发件人
     * @param title 标题
     * @param text 内容
     * @param priority 优先级
     * @param to 收件人 多人
     */
    public static void sendHtmlMail(Object from, Object title, Object text, Object priority, Object... to) {
        // 获取发邮件的支持类
        JavaMailSender mailSender = ServiceLocator.getInstance().getBean("javaMailSender");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "GBK");

            InternetAddress[] toArray = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                toArray[i] = new InternetAddress(to[i].toString());
            }
            messageHelper.setFrom(new InternetAddress(from.toString()));
            messageHelper.setTo(toArray);
            messageHelper.setSubject(title.toString());
            messageHelper.setText(text.toString(), true);
            mimeMessage = messageHelper.getMimeMessage();
            mimeMessage.addHeader("X-Priority", priority.toString());
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Send mail from " + from + " to " + Arrays.asList(to)
                    + " failed,because " + e.getMessage());
            throw new RuntimeException("HTML 邮件发送异常", e);
        }
    }
}
