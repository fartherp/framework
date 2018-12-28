/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.tree.convert;

import com.github.fartherp.framework.tree.bo.Treeable;
import com.github.fartherp.framework.tree.model.common.Tree;

import java.util.List;
import java.util.function.Function;

/**
 * Created by framework .
 * Auth: hyssop
 * Date: 2016-09-09
 */
@FunctionalInterface
public interface ConvertTool<T extends Tree, S extends Treeable> {
    /**
     * 返回树列表
     *
     * @param list 列表
     * @param mc   回调接口
     * @return 返回值
     */
    public List<T> findChildren(List<S> list, Function<S, T> mc);
}
