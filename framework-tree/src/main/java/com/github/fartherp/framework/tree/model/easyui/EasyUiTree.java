/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.tree.model.easyui;

import com.github.fartherp.framework.tree.model.common.BaseTree;

import java.io.Serializable;
import java.util.List;

/**
 * Created by framework .
 * Auth: hyssop
 * Date: 2016-09-09
 */
public abstract class EasyUiTree<T extends EasyUiTree, ID extends Serializable> extends BaseTree {

    // 树节点的标识，必需
    private ID id;
    // 父节点id，非必需，如果没有设置该节点就为根节点
    private ID pid;
    // 是否默认展开，非必须，默认值是false
    private Boolean expanded;
    // 树节点样式，非必需，默认有folder和file，如果用户自定制为其他，则显示用户自定义样式
    private String classes;

    public abstract List<T> getChildren();

    public abstract void setChildren(List<T> children);

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ID getPid() {
        return pid;
    }

    public void setPid(ID pid) {
        this.pid = pid;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}
