/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.remoting;

import com.github.fartherp.framework.remoting.exception.RemotingConnectException;
import com.github.fartherp.framework.remoting.exception.RemotingSendRequestException;
import com.github.fartherp.framework.remoting.exception.RemotingTimeoutException;
import com.github.fartherp.framework.remoting.exception.RemotingTooMuchRequestException;
import com.github.fartherp.framework.remoting.netty.NettyRequestProcessor;
import com.github.fartherp.framework.remoting.protocol.RemotingCommand;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author hyssop
 */
public interface RemotingClient extends RemotingService {

    public void updateNameServerAddressList(final List<String> addrs);

    public List<String> getNameServerAddressList();

    public RemotingCommand invokeSync(final String addr, final RemotingCommand request,
                                      final long timeoutMillis) throws InterruptedException, RemotingConnectException,
            RemotingSendRequestException, RemotingTimeoutException;

    public void invokeAsync(final String addr, final RemotingCommand request, final long timeoutMillis,
                            final InvokeCallback invokeCallback) throws InterruptedException, RemotingConnectException,
            RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;


    public void invokeOneway(final String addr, final RemotingCommand request, final long timeoutMillis)
            throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException,
            RemotingTimeoutException, RemotingSendRequestException;


    public void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
                                  final ExecutorService executor);

    public boolean isChannelWriteable(final String addr);
}
