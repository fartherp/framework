/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.front;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by framework .
 * Auth: hyssop
 * Date: 2016-12-15-19:35
 */
public interface Context {

    <V> V execute(Callable<V> callable) throws ExecutionException;

    void execute(Runnable runnable);
}
