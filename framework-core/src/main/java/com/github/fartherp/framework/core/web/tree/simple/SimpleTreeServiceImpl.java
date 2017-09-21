/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.tree.simple;


import com.github.fartherp.framework.common.util.JsonUtil;
import com.github.fartherp.framework.core.web.easyUI.model.SimpleTreeModel;
import com.github.fartherp.framework.core.web.tree.SimpleTreeService;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单树结构实现
 * Auth: CK
 * Date: 2016/8/27
 */
public class SimpleTreeServiceImpl<T> implements SimpleTreeService<T> {
    public String findTreeStr(List<T> list, ModelCall<T> mc) {
        return JsonUtil.toJson(findTree(list, mc));
    }

    public List<SimpleTreeModel> findTree(List<T> list, ModelCall<T> mc) {
        List<SimpleTreeModel> simpleTreeModels = new ArrayList<SimpleTreeModel>(list.size());
        for (T t : list){
            simpleTreeModels.add(mc.convert(t));
        }
        return simpleTreeModels;
    }
}
