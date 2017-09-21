/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.remoting;

import com.github.fartherp.framework.remoting.exception.RemotingCommandException;

/**
 * @author hyssop
 */
public interface CommandCustomHeader {
    void checkFields() throws RemotingCommandException;
}
