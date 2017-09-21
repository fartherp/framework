/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.tree.model.ztree;

import com.github.fartherp.framework.tree.model.common.BaseTree;

import java.util.List;

/**
 * Created by framework .
 * Auth: hyssop
 * Date: 2016-09-09
 */
public abstract class Ztree<T extends Ztree, ID> extends BaseTree {
    /**
     * 字段唯一标示
     */
    private ID id;
    /**
     * 父id
     */
    private ID pId;
    /**
     * 是否是根节点
     */
    private Boolean root;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 是否含子节点
     */
    private Boolean isParent;

    public abstract List<T> getChildren();

    public abstract void setChildren(List<T> children);

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ID getpId() {
        return pId;
    }

    public void setpId(ID pId) {
        this.pId = pId;
    }

    public Boolean isRoot() {
        return root;
    }

    public Boolean getRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }
}
