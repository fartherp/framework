/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.remoting;

import com.github.fartherp.framework.remoting.protocol.RemotingCommand;

public interface RPCHook {
    void doBeforeRequest(final String remoteAddr, final RemotingCommand request);

    void doAfterResponse(final String remoteAddr, final RemotingCommand request,
                         final RemotingCommand response);
}
