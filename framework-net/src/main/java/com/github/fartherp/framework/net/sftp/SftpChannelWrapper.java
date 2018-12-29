/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.net.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import static com.jcraft.jsch.ChannelSftp.SSH_FX_NO_SUCH_FILE;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/17
 */
public class SftpChannelWrapper extends ChannelWrapper<ChannelSftp> {

    public static final String FILE_NAME_SUFFIX = ".r";

    public static final String BLUR_NAME_TYPE = "";

    public static final String HMS_NAME_TYPE = "";

    public void openChannel(ChannelType channelType) throws JSchException {
        super.openChannel(channelType);
        this.getChannel().connect();
    }

    /**
     * 上传类 指定传上服务器后的名称
     *
     * @param remotefilename 上传到服务器后的文件名称
     * @param localfilename  本地的文件目录+文件名称
     * @param path           服务器上的目录
     */
    public void upload(String path, String localfilename, String remotefilename) throws SftpException {
        this.getChannel().cd(path);
        File file = new File(localfilename);
        try {
            this.getChannel().put(new FileInputStream(file), remotefilename);
        } catch (FileNotFoundException e) {
            throw new SftpException(SSH_FX_NO_SUCH_FILE, "local no such file ", e);
        }
    }

    /**
     * 上传类 未指定服务器上的文件名称
     *
     * @param path          服务器上的目录
     * @param localfilename 本地的文件名称
     */
    public void upload(String localfilename, String path) throws SftpException {
        this.getChannel().cd(path);
        String remoteName = FtpFileName(localfilename);
        String remoteNameU = remoteName + FILE_NAME_SUFFIX;
        File file = new File(localfilename);
        try {
            this.getChannel().put(new FileInputStream(file), remoteNameU);
        } catch (FileNotFoundException e) {
            throw new SftpException(SSH_FX_NO_SUCH_FILE, "local no such file ", e);
        }
        String srcFtpPath = path + File.separator + remoteNameU;
        String realFtpPath = path + File.separator + remoteName;
        this.getChannel().rename(srcFtpPath, realFtpPath);
    }

    /**
     * 根据本地的文件名，生成放到SFTP的文件名
     *
     * @param inFile 本地的文件
     * @return 新文件
     */
    private static String FtpFileName(String inFile) {
        inFile = inFile.replace('\\', '/');
        int lastSlash = inFile.lastIndexOf(File.separator);
        return inFile.substring(lastSlash + 1, inFile.length());
    }

    /**
     * 下载文件
     *
     * @param folder            目录
     * @param destinationFolder 目标目录
     * @param fileName          文件名
     * @param nameType          文件类型
     * @return 文件名
     */
    public String getFile(String folder, String destinationFolder, String fileName, String nameType) throws SftpException {
        // 如果是模糊匹配，而且匹配正确，则需要将FTP上的文件名替代本地文件名。
        Vector<LsEntry> files = this.getChannel().ls(folder);
        Iterator<LsEntry> iter = files.iterator();
        if (BLUR_NAME_TYPE.equals(nameType.trim())) {
            while (iter.hasNext()) {
                LsEntry file = (LsEntry) iter.next();
                String name = file.getFilename().toLowerCase();
                String fileNameStart = null;
                if (fileName.contains(".")) {
                    fileNameStart = fileName.substring(0, fileName.lastIndexOf(".")).toLowerCase();
                } else {
                    fileNameStart = fileName.toLowerCase();
                }
                if (name.trim().startsWith(fileNameStart.trim())) {
                    fileName = file.getFilename();
                }
            }
        } else if (HMS_NAME_TYPE.equals(nameType.trim())) {
            while (iter.hasNext()) {
                LsEntry file = (LsEntry) iter.next();
                int length = Integer.parseInt(nameType.trim());
                String name = file.getFilename().toLowerCase();
                String fileNameStart = fileName.toLowerCase();
                // 如果数据库配置的文件名含有"."的话
                if (fileName.contains(".")) {
                    while (length > 0) {
                        fileNameStart = fileNameStart.substring(0, fileNameStart.lastIndexOf("."));
                        length--;
                    }
                }
                if (name.trim().startsWith(fileNameStart.trim())) {
                    fileName = file.getFilename();
                }
            }
        } else {
            while (iter.hasNext()) {
                LsEntry file = (LsEntry) iter.next();
                String name = file.getFilename();
                // 判断FTP文件名和数据库配置的文件名是否相等
                if (fileName.trim().equalsIgnoreCase(name)) {
                    fileName = name;
                }
            }
        }

        // 创建文件夹
        File fileFold = new File(destinationFolder);

        if (!fileFold.exists()) {
            fileFold.mkdirs();
        }
        String filePath = destinationFolder + File.separator + fileName;
        this.getChannel().cd(folder);
        File file = new File(filePath);
        try {
            this.getChannel().get(fileName, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new SftpException(SSH_FX_NO_SUCH_FILE, "local no such file ", e);
        }


        return fileName;
    }

    /**
     * 删除文件
     *
     * @param folder            目录
     * @param destinationFolder 目标目录
     * @param fileName          文件名
     * @param nameType          文件类型
     */
    public void delFile(String folder, String destinationFolder, String fileName, String nameType) throws SftpException {
        Vector<LsEntry> files = this.getChannel().ls(folder);
        Iterator<LsEntry> iter = files.iterator();
        if (BLUR_NAME_TYPE.equals(nameType.trim())) {
            while (iter.hasNext()) {
                LsEntry file = (LsEntry) iter.next();
                String name = file.getFilename().toLowerCase();
                String fileNameStart = null;
                if (fileName.contains(".")) {
                    fileNameStart = fileName.substring(0, fileName.lastIndexOf(".")).toLowerCase();
                } else {
                    fileNameStart = fileName.toLowerCase();
                }
                if (name.trim().startsWith(fileNameStart.trim())) {
                    fileName = file.getFilename();
                }
            }
        } else if (HMS_NAME_TYPE.equals(nameType.trim())) {
            while (iter.hasNext()) {
                LsEntry file = (LsEntry) iter.next();
                int length = Integer.parseInt(nameType.trim());
                String name = file.getFilename().toLowerCase();
                String fileNameStart = fileName.toLowerCase();
                // 如果数据库配置的文件名含有"."的话
                if (fileName.contains(".")) {
                    while (length > 0) {
                        fileNameStart = fileNameStart.substring(0, fileNameStart.lastIndexOf("."));
                        length--;
                    }
                }
                if (name.trim().startsWith(fileNameStart.trim())) {
                    fileName = file.getFilename();
                }
            }
        } else {
            while (iter.hasNext()) {
                LsEntry file = (LsEntry) iter.next();
                String name = file.getFilename();
                // 判断FTP文件名和数据库配置的文件名是否相等
                if (fileName.trim().equalsIgnoreCase(name)) {
                    fileName = name;
                }
            }
        }
        this.getChannel().cd(folder);
        this.getChannel().rm(fileName);
    }

    /**
     * 判断SFTP对应目录下是否存在需要下载的文件
     *
     * @param folder   目录
     * @param fileName 文件名
     * @param nameType 文件类型
     * @return true：存在，false：不存在
     */
    public boolean isFileExist(String folder, String fileName, String nameType) throws SftpException {
        boolean flag = false;
        int count = 0;
        Vector<LsEntry> files = this.getChannel().ls(folder);
        if (files.size() > 0) {
            Iterator<LsEntry> iter = files.iterator();
            // 如果是模糊匹配类型的话
            if (BLUR_NAME_TYPE.equals(nameType.trim())) {
                while (iter.hasNext()) {
                    LsEntry file = (LsEntry) iter.next();
                    String name = file.getFilename().toLowerCase();
                    String fileNameStart = null;
                    // 如果数据库配置的文件名含有"."的话
                    if (fileName.contains(".")) {
                        fileNameStart = fileName.substring(0, fileName.lastIndexOf(".")).toLowerCase();
                    } else {// 如果数据库配置的文件名没有"."的话
                        fileNameStart = fileName.toLowerCase();
                    }
                    // 判断FTP上的文件名是否是以数据库配置的文件名开始，如果是的话count加1
                    if (name.trim().startsWith(fileNameStart.trim())) {
                        count++;
                    }
                }

                // 如果匹配的文件数目只有1的话则表示模糊匹配正确，如果匹配数目大于1的话，则匹配出错
                if (count == 1) {
                    flag = true;
                } else {
                    throw new RuntimeException("匹配上的文件数大于1");
                }
            } else if (HMS_NAME_TYPE.equals(nameType.trim())) {
                while (iter.hasNext()) {
                    LsEntry file = (LsEntry) iter.next();
                    int length = Integer.parseInt(nameType.trim());
                    String name = file.getFilename().toLowerCase();
                    String fileNameStart = fileName.toLowerCase();

                    // 如果数据库配置的文件名含有"."的话
                    if (fileName.contains(".")) {
                        while (length > 0) {
                            fileNameStart = fileNameStart.substring(0, fileNameStart.lastIndexOf("."));
                            length--;
                        }
                    }
                    if (name.trim().startsWith(fileNameStart.trim())) {
                        count++;
                    }
                }

                // 如果匹配的文件数目只有1的话则表示模糊匹配正确，如果匹配数目大于1的话，则匹配出错
                if (count == 1) {
                    flag = true;
                } else if (count > 1) {
                    throw new RuntimeException("匹配上的文件数大于1");
                }
            } else {// 如果不是模糊匹配的话
                while (iter.hasNext()) {
                    LsEntry file = (LsEntry) iter.next();
                    String name = file.getFilename();
                    // 判断FTP文件名和数据库配置的文件名是否相等
                    if (fileName.trim().equalsIgnoreCase(name)) {
                        flag = true;
                        break;
                    }
                }
            }
        }

        return flag;
    }

    /**
     * 重命名
     *
     * @param from 原名称
     * @param to   新名称
     */
    public boolean rename(String from, String to) {
        try {
            this.getChannel().rename(from, to);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 级联创建远程目录（不存在的目录级联创建）
     * @param path 远程路径
     * @throws SftpException
     */
    public void mkdir(String path) throws SftpException {
        char result[] = path.toCharArray();
        List<String> dirs = new ArrayList<String>();
        for (int i = 0; i < result.length; i++) {
            if (result[i] == '/') {
                dirs.add(path.substring(0, i));
            }
        }
        // 如果没有自己
        if (!dirs.contains(path)) {
            dirs.add(path);
        }
        // 级联创建文件夹
        for (String dir: dirs) {
            try {
                if (StringUtils.isNotBlank(dir)) {
                    this.getChannel().cd(dir);
                }
            } catch (SftpException e) {
                if ("No such file".equals(e.getMessage())) {
                    // do nothing
                }
                this.getChannel().mkdir(dir);
            }
        }
    }
}
