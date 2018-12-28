/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.tree.simple;


import com.github.fartherp.framework.common.util.JsonUtil;
import com.github.fartherp.framework.core.web.easyUI.model.SimpleTreeModel;
import com.github.fartherp.framework.core.web.tree.SimpleTreeService;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 简单树结构实现
 * Auth: CK
 * Date: 2016/8/27
 */
public class SimpleTreeServiceImpl<T> implements SimpleTreeService<T> {
    public String findTreeStr(List<T> list, Function<T, SimpleTreeModel> mc) {
        return JsonUtil.toJson(findTree(list, mc));
    }

    public List<SimpleTreeModel> findTree(List<T> list, Function<T, SimpleTreeModel> mc) {
        return list.stream().map(mc).collect(Collectors.toList());
    }
}
