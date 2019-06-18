/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by zhjie.zhang on 2019/3/26.
 */
public class CatchUtil {
    public static <T, R> R tryDo(T t, Function<T, R> func) {
        try {
            return func.apply(t);
        } catch (Exception e) {
            e.printStackTrace();  // for log
            throw new RuntimeException(e.getCause());
        }
    }

    public static <T> void tryDo(T t, Consumer<T> func) {
        try {
            func.accept(t);
        } catch (Exception e) {
            e.printStackTrace();  // for log
            throw new RuntimeException(e.getCause());
        }
    }
}
