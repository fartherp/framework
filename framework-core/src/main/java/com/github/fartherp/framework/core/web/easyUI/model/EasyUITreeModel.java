/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.easyUI.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/23
 */
public class EasyUITreeModel extends SimpleTreeModel {
    private List<EasyUITreeModel> children;

    public List<EasyUITreeModel> getChildren() {
        if (this.children == null) {
            children = new ArrayList<EasyUITreeModel>(0);
        }
        return children;
    }

    public void setChildren(List<EasyUITreeModel> children) {
        this.children = children;
    }

    public void addChildren(EasyUITreeModel model) {
        if (null == children) {
            children = new ArrayList<EasyUITreeModel>();
        }
        children.add(model);
    }

    /**     * E50 code（250的意思）

     * @return 是否有子数据
     */
    public boolean isHasChildren() {
        return this.children != null && !this.children.isEmpty();
    }
}
