/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util.email;

import com.github.fartherp.framework.common.constant.Constant;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/8/16
 */
public class SimpleMail {
    /**
     * 标题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 收件人列表
     */
    private List<String> recipientList;

    private String recipients;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRecipients(String recipient) {
        String [] arrays = recipient.split(Constant.COMMA_DELIMITER);
        recipientList = Arrays.asList(arrays);
        this.recipients = recipient;
    }

    public List<String> getRecipients() {
        return recipientList;
    }
}
