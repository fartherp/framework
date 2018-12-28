/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.pdf.write;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/12/19
 */
@FunctionalInterface
public interface PdfWriteDeal<R> {
    R deal();
}
