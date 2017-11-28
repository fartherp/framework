/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.common.extension.ext1;

import com.github.fartherp.framework.common.extension.Adaptive;
import com.github.fartherp.framework.common.extension.SPI;

/**
 * 简单扩展点。
 * 没有Wrapper。
 *
 * @author ding.lid
 */
@SPI("impl1")
public interface SimpleExt {
    // 没有使用key的@Adaptive ！
    @Adaptive
    String echo(String s);

    @Adaptive({"key1", "key2"})
    String yell(String s);

    // 无@Adaptive ！
    String bang(int i);
}