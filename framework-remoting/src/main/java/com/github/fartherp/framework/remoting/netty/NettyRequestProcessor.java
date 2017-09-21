/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.remoting.netty;

import com.github.fartherp.framework.remoting.protocol.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;

/**
 * Common remoting command processor
 *
 * @author hyssop
 */
public interface NettyRequestProcessor {
    RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request) throws Exception;

    boolean rejectRequest();
}
