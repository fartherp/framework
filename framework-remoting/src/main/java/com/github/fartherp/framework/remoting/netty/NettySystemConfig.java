/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.remoting.netty;

public class NettySystemConfig {
    public static final String COM_framework_REMOTING_NETTY_POOLED_BYTE_BUF_ALLOCATOR_ENABLE =
            "com.framework.remoting.nettyPooledByteBufAllocatorEnable";
    public static final String COM_framework_REMOTING_SOCKET_SNDBUF_SIZE = //
            "com.framework.remoting.socket.sndbuf.size";
    public static final String COM_framework_REMOTING_SOCKET_RCVBUF_SIZE = //
            "com.framework.remoting.socket.rcvbuf.size";
    public static final String COM_framework_REMOTING_CLIENT_ASYNC_SEMAPHORE_VALUE = //
            "com.framework.remoting.clientAsyncSemaphoreValue";
    public static final String COM_framework_REMOTING_CLIENT_ONEWAY_SEMAPHORE_VALUE = //
            "com.framework.remoting.clientOnewaySemaphoreValue";
    public static final boolean NETTY_POOLED_BYTE_BUF_ALLOCATOR_ENABLE = //
            Boolean.parseBoolean(System.getProperty(COM_framework_REMOTING_NETTY_POOLED_BYTE_BUF_ALLOCATOR_ENABLE, "false"));
    public static int socketSndbufSize = //
            Integer.parseInt(System.getProperty(COM_framework_REMOTING_SOCKET_SNDBUF_SIZE, "65535"));
    public static int socketRcvbufSize = //
            Integer.parseInt(System.getProperty(COM_framework_REMOTING_SOCKET_RCVBUF_SIZE, "65535"));
    public static final int CLIENT_ASYNC_SEMAPHORE_VALUE = //
            Integer.parseInt(System.getProperty(COM_framework_REMOTING_CLIENT_ASYNC_SEMAPHORE_VALUE, "65535"));
    public static final int CLIENT_ONEWAY_SEMAPHORE_VALUE = //
            Integer.parseInt(System.getProperty(COM_framework_REMOTING_CLIENT_ONEWAY_SEMAPHORE_VALUE, "65535"));
}
