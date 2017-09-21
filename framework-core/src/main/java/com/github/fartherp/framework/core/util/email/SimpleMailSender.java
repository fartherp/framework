/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util.email;

import com.github.fartherp.framework.common.constant.Constant;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/8/16
 */
public class SimpleMailSender {
    /**
     * 发送邮件的props文件
     */
    private Properties props = System.getProperties();
    /**
     * 邮件服务器登录验证
     */
    private MailAuthenticator authenticator;

    /**
     * 邮箱session
     */
    private Session session;

    /**
     * 初始化邮件发送器
     *
     * @param smtpHostName SMTP邮件服务器地址
     * @param username     发送邮件的用户名(地址)
     * @param password     发送邮件的密码
     */
    public SimpleMailSender(String smtpHostName, String username, String password) {
        init(username, password, smtpHostName);
    }

    /**
     * 初始化邮件发送器
     *
     * @param username 发送邮件的用户名(地址)，并以此解析SMTP服务器地址
     * @param password 发送邮件的密码
     */
    public SimpleMailSender(String username, String password) {
        //通过邮箱地址解析出smtp服务器，对大多数邮箱都管用
        String smtpHostName = "smtp." + username.split("@")[1];
        init(username, password, smtpHostName);
    }

    /**
     * 初始化
     *
     * @param username     发送邮件的用户名(地址)
     * @param password     密码
     * @param smtpHostName SMTP主机地址
     */
    private void init(String username, String password, String smtpHostName) {
        // 初始化props
        props.put(Constant.MAIL_SMTP_AUTH, Boolean.TRUE);
        props.put(Constant.MAIL_SMTP_HOST, smtpHostName);
        // 验证
        authenticator = new MailAuthenticator(username, password);
        // 创建session
        session = Session.getInstance(props, authenticator);
    }

    /**
     * 群发邮件
     *
     * @param subject    主题
     * @param content    内容
     * @param recipients 收件人们
     */
    private void send(String subject, String content, Object... recipients) {
        try {
            // 创建mime类型邮件
            Message message = new MimeMessage(session);
            // 设置发信人
            message.setFrom(new InternetAddress(authenticator.getUsername()));
            // 设置收件人们
            Address[] addresses = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                addresses[i] = new InternetAddress(recipients[i].toString());
            }
            message.setRecipients(Message.RecipientType.TO, addresses);
            // 设置主题
            message.setSubject(MimeUtility.encodeText(subject, Constant.UTF_8, "B"));
            // 设置邮件内容
            BodyPart mdp = new MimeBodyPart();
            mdp.setContent(content, "text/html;charset=utf-8");
            // 字符集编码
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mdp); // 转码后的内容
            message.setContent(multipart);
            message.saveChanges();
            message.setSentDate(new Date());
            // 发送
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持UTF-8编码", e);
        }
    }

    /**
     * 群发邮件
     *
     * @param mail 邮件对象
     * @throws MessagingException
     */
    public void send(SimpleMail mail) throws MessagingException, UnsupportedEncodingException {
        send(mail.getSubject(), mail.getContent(), mail.getRecipients().toArray());
    }
}
