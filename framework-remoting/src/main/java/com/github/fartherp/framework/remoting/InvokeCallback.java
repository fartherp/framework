/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.remoting;

import com.github.fartherp.framework.remoting.netty.ResponseFuture;

/**
 * @author hyssop
 */
public interface InvokeCallback {
    void operationComplete(final ResponseFuture responseFuture);
}
