/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.easyUI.model;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/23
 */
public class SimpleTreeModel extends BaseTreeModel {
    // 树节点的标识，必需
    private Integer id;
    // 父节点id，非必需，如果没有设置该节点就为根节点
    private Integer pid;
    // 是否默认展开，非必须，默认值是false
    private Boolean expanded;
    // 树节点样式，非必需，默认有folder和file，如果用户自定制为其他，则显示用户自定义样式
    private String classes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
