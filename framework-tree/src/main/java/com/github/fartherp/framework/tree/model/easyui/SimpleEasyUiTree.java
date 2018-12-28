/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.tree.model.easyui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by framework .
 * Auth: hyssop
 * Date: 2016-09-09
 */
public class SimpleEasyUiTree<T extends EasyUiTree, ID extends Serializable> extends EasyUiTree<T, ID> {
    private List<T> children;

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public void addChildren(T model) {
        if (null == children) {
            children = new ArrayList<T>();
        }
        children.add(model);
    }
}
