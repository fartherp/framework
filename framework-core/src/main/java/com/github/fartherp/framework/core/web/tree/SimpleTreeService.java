/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.tree;

import com.github.fartherp.framework.core.web.easyUI.model.SimpleTreeModel;

import java.util.List;
import java.util.function.Function;

/**
 * 简单树结构Service
 * Auth: CK
 * Date: 2016/8/27
 */
public interface SimpleTreeService<T> {
    /**
     * 返回给前端Tree数据
     * @param list 业务数据list
     * @param mc 业务回调接口
     * @return 返回给前端Tree数据
     */
    public String findTreeStr(List<T> list, Function<T, SimpleTreeModel> mc);

    /**
     * 返回树列表
     * @param list 列表
     * @param mc 回调接口
     * @return 返回值
     */
    public List<SimpleTreeModel> findTree(List<T> list, Function<T, SimpleTreeModel> mc);
}
