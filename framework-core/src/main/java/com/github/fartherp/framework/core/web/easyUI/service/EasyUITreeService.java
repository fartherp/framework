/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.easyUI.service;

import com.github.fartherp.framework.core.web.easyUI.model.EasyUITreeModel;

import java.util.List;
import java.util.function.Function;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/23
 */
public interface EasyUITreeService<T> {
    /**
     * 返回树列表
     * @param list 列表
     * @param mc 回调接口
     * @return 返回值
     */
    public List<EasyUITreeModel> findChildren(List<T> list, Function<T, EasyUITreeModel> mc);

    public void setCheck(List<EasyUITreeModel> easyUITreeModels);
}
