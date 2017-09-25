/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.common.extension.ext1;

import com.github.fartherp.framework.common.extension.Adaptive;
import com.github.fartherp.framework.common.extension.SPI;
import com.github.fartherp.framework.common.extension.URL;

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
    String echo(URL url, String s);

    @Adaptive({"key1", "key2"})
    String yell(URL url, String s);

    // 无@Adaptive ！
    String bang(URL url, int i);
}