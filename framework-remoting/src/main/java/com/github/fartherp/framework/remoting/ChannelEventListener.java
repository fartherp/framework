/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.remoting;

import io.netty.channel.Channel;

/**
 * @author hyssop
 */
public interface ChannelEventListener {
    void onChannelConnect(final String remoteAddr, final Channel channel);


    void onChannelClose(final String remoteAddr, final Channel channel);


    void onChannelException(final String remoteAddr, final Channel channel);


    void onChannelIdle(final String remoteAddr, final Channel channel);
}
