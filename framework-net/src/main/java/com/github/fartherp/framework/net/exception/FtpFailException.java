/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.net.exception;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/30.
 */
public class FtpFailException extends RuntimeException {

    public FtpFailException(int replyCode, String replyString) {
        this.replyCode = replyCode;
        this.replyString = replyString;
    }

    /**
     * 返回码, 与ftp reply的值一致.
     */
    private int replyCode;

    /**
     * 返回信息, 与ftp协议约定一致.
     */
    private String replyString;

    /**
     * Getter for property 'replyCode'.
     *
     * @return Value for property 'replyCode'.
     */
    public int getReplyCode() {
        return replyCode;
    }

    /**
     * Setter for property 'replyCode'.
     *
     * @param replyCode Value to set for property 'replyCode'.
     */
    public void setReplyCode(int replyCode) {
        this.replyCode = replyCode;
    }

    /**
     * Getter for property 'replyString'.
     *
     * @return Value for property 'replyString'.
     */
    public String getReplyString() {
        return replyString;
    }

    /**
     * Setter for property 'replyString'.
     *
     * @param replyString Value to set for property 'replyString'.
     */
    public void setReplyString(String replyString) {
        this.replyString = replyString;
    }
}
