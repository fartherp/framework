/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.net.ftp;

import com.github.fartherp.framework.net.exception.FtpFailException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/30.
 */
public class FtpUtils {

    public static final Logger log = LoggerFactory.getLogger(FtpUtils.class);

    /**
     * 配置一个ftp连接.
     *
     * @param ftp 新连接对象.
     * @param config 配置对象.
     */
    private static void prepareConnection(FTPClient ftp, FtpConfig config) {
        log.info("prepare.connection:{}", config);

        try {
            ftp.connect(config.getHost(), config.getPort());
        } catch (IOException e) {
            log.error("ftp connect fail[refused,config={}]", config);
            setReplyCode(ftp, e);
        }

        try {
            if (config.isBinaryMode()) {
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
            } else {
                ftp.setFileType(FTP.ASCII_FILE_TYPE);
            }
        } catch (IOException e) {
            log.error("ftp set file type fail[refused,config={}]", config);
            setReplyCode(ftp, e);
        }


        if (config.isLocalPassiveMode()) {
            ftp.enterLocalPassiveMode();
        } else {
            ftp.enterLocalActiveMode();
        }

        try {
            if (StringUtils.isNotBlank(config.getUsername()) && StringUtils.isNotBlank(config.getPassword())) {
                ftp.user(config.getUsername());
                ftp.pass(config.getPassword());
            }
        } catch (IOException e) {
            log.error("ftp set username/password fail[refused,config={}]", config);
            setReplyCode(ftp, e);
        }
    }

    /**
     * 向ftp服务器存储一个文件.
     *
     * @param config 配置对象,不能为空
     * @param ftpPath ftp目录路径, 不能为空.
     * @param fileStream 文件流
     * @param fileName 文件名
     * @throws FtpFailException the FtpFailException
     */
    public static void store(FtpConfig config, String ftpPath, InputStream fileStream, String fileName) {
        log.info("ftp.store[{}]", config);
        if (StringUtils.isBlank(fileName)) {
            log.error("ftp.store.fileName=blank");
            throw new FtpFailException(0, "File Name is blank");
        }

        FTPClient ftp = new FTPClient();
        prepareConnection(ftp, config);

        try {
            if (!StringUtils.isBlank(ftpPath)) {
                ftp.changeWorkingDirectory(ftpPath);
            } else {
                ftp.changeWorkingDirectory(config.getDefaultDir());
            }
        } catch (IOException e) {
            log.error("ftp.cwd.error:{}", e);
            closeFtpQuietly(ftp);
            setReplyCode(ftp, e);
        }

        try {
            ftp.storeFile(fileName, fileStream);
        } catch (IOException e) {
            log.error("ftp.store.error:{}", e);
            setReplyCode(ftp, e);
        } finally {
            closeFtpQuietly(ftp);
        }
    }

    /**
     * 从ftp服务器下载指定文件.
     *
     * FtpFailException 下载文件失败抛出该异常(如文件不存在等.)
     *
     * @param config ftp配置,不能为null.
     * @param path ftp文件路径, 不能为null.
     * @param output 输出流,写回下载的文件流. 不能为null, 资源管理由调用方负责.
     */
    public static void fetch(FtpConfig config, String path, OutputStream output) {
        log.info("ftp.fetch[{}]", config);

        FTPClient ftp = new FTPClient();
        prepareConnection(ftp, config);

        try {
            ftp.retrieveFile(path, output);
        } catch (IOException e) {
            log.error("ftp.retrieve.error:{}", e);
            setReplyCode(ftp, e);
        } finally {
            closeFtpQuietly(ftp);
        }
    }

    /**
     * 设置错误码
     * @param ftp ftp
     * @param e 异常
     */
    private static void setReplyCode(FTPClient ftp, Exception e) {
        int reply = ftp.getReplyCode();
        log.info("reply={}", reply);
        if (!FTPReply.isPositiveCompletion(reply)) {
            throw new FtpFailException(0, e.getMessage());
        } else {
            String message = ftp.getReplyString();
            if (null == message) {
                message = e.getMessage();
            }
            throw new FtpFailException(reply, message);
        }
    }

    /**
     * 轻轻的关闭ftp连接.
     *
     * @param ftp ftp连接对象.
     */
    private static void closeFtpQuietly(FTPClient ftp) {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException e) {
                log.error("closeFtpQuietly.error:{}", e);
            }
        }
    }
}
