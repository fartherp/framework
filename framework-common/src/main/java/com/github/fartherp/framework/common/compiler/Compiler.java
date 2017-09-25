/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.common.compiler;

import com.github.fartherp.framework.common.extension.SPI;

/**
 * Compiler. (SPI, Singleton, ThreadSafe)
 *
 * @author CK
 */
@SPI("javassist")
public interface Compiler {

    /**
     * Compile java source code.
     *
     * @param code        Java source code
     * @param classLoader TODO
     * @return Compiled class
     */
    Class<?> compile(String code, ClassLoader classLoader);

}
