/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.easyUI.service;

import com.github.fartherp.framework.core.web.easyUI.model.EasyUITreeModel;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/23
 */
public interface EasyUITreeService<T> {
    /**
     * 返回给前端Tree数据
     * @param list 业务数据list
     * @param mc 业务回调接口
     * @return 返回给前端Tree数据
     */
    public String findTreeStr(List<T> list, ModelCall<T> mc);

    /**
     * 返回树列表
     * @param list 列表
     * @param mc 回调接口
     * @return 返回值
     */
    public List<EasyUITreeModel> findChildren(List<T> list, ModelCall<T> mc);

    public void setCheck(List<EasyUITreeModel> easyUITreeModels);

    /**
     * 具体业务实现接口
     * @param <T> 业务数据转换成EasyUITreeModel
     */
    public static interface ModelCall<T> {
        /**
         * 业务数据转换成EasyUITreeModel
         * @param t 业务数据
         * @return EasyUITreeModel
         */
        EasyUITreeModel convert(T t);
    }
}
