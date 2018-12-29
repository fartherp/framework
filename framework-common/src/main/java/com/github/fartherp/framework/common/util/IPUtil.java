/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/12/13
 */
public class IPUtil {
    // 新浪地址
    public static final String SINA_PATH = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";

    /**
     * 获取当前网络IP
     *
     * @param request 请求
     * @return ip
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (checkIP(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (checkIP(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            //"***.***.***.***".length() = 15
            ip = StringUtils.substringBefore(ip, ",");
        }
        return ip;
    }

    private static boolean checkIP(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

    public static RemoteIpInfo getAddress(String ip) {
        String resp = HttpClientUtil.executeGet(SINA_PATH + ip);
        return JsonUtil.fromJson(resp, RemoteIpInfo.class);
    }
}
