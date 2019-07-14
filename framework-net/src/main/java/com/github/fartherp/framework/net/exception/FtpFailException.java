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
package com.github.fartherp.framework.net.exception;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/6/30.
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
