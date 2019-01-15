///*
// * Copyright (c) 2017. CK. All rights reserved.
// */
//
///**
// * $Id: ExceptionTest.java 1831 2013-05-16 01:39:51Z hyssop $
// */
//package com.github.fartherp.framework.remoting.exception;
//
//import com.github.fartherp.framework.remoting.RemotingClient;
//import com.github.fartherp.framework.remoting.RemotingServer;
//import com.github.fartherp.framework.remoting.netty.NettyClientConfig;
//import com.github.fartherp.framework.remoting.netty.NettyRemotingClient;
//import com.github.fartherp.framework.remoting.netty.NettyRemotingServer;
//import com.github.fartherp.framework.remoting.netty.NettyRequestProcessor;
//import com.github.fartherp.framework.remoting.netty.NettyServerConfig;
//import com.github.fartherp.framework.remoting.protocol.RemotingCommand;
//import io.netty.channel.ChannelHandlerContext;
//
//import java.util.concurrent.Executors;
//
///**
// * @author hyssop
// */
//public class ExceptionTest {
//    private static RemotingServer createRemotingServer() throws InterruptedException {
//        NettyServerConfig config = new NettyServerConfig();
//        RemotingServer client = new NettyRemotingServer(config);
//        client.registerProcessor(0, new NettyRequestProcessor() {
//            private int i = 0;
//
//            public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) {
//                System.out.println("processRequest=" + request + " " + (i++));
//                request.setRemark("hello, I am respponse " + ctx.channel().remoteAddress());
//                return request;
//            }
//
//            public boolean rejectRequest() {
//                return false;
//            }
//        }, Executors.newCachedThreadPool());
//        client.start();
//        return client;
//    }
//
////    @Test
//    public void test_CONNECT_EXCEPTION() {
//        RemotingClient client = createRemotingClient();
//
//        RemotingCommand request = RemotingCommand.createRequestCommand(0, null);
//        RemotingCommand response = null;
//        try {
//            response = client.invokeSync("localhost:8858", request, 1000 * 3);
//        } catch (RemotingConnectException e) {
//            e.printStackTrace();
//        } catch (RemotingSendRequestException e) {
//            e.printStackTrace();
//        } catch (RemotingTimeoutException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("invoke result = " + response);
//
//        client.shutdown();
//        System.out.println("-----------------------------------------------------------------");
//    }
//
//    private static RemotingClient createRemotingClient() {
//        NettyClientConfig config = new NettyClientConfig();
//        RemotingClient client = new NettyRemotingClient(config);
//        client.start();
//        return client;
//    }
//
//}
