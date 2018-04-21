/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.tree.bo;

import com.github.fartherp.framework.database.dao.FieldAccessVo;

import java.io.Serializable;

/**
 * Created by framework .
 * Auth: hyssop
 * Date: 2016-09-09
 * Description: 所有的属性结构extends该该类，
 */
public abstract class Treeable<ID extends Serializable> extends FieldAccessVo {

    public abstract void setName(String name);

    public abstract String getName();

    /**
     * 显示的图标 大小为16×16
     *
     * @return
     */
    public abstract String getIcon();

    public abstract void setIcon(String icon);

    /**
     * 父路径
     *
     * @return
     */
    public abstract ID getParentId();

    public abstract void setParentId(ID parentId);

    /**
     * 所有父路径 如1,2,3,
     *
     * @return
     */
    public abstract String getParentIds();

    public abstract void setParentIds(String parentIds);

    /**
     * 获取 parentIds 之间的分隔符
     *
     * @return
     */
    public abstract String getSeparator();

    /**
     * 把自己构造出新的父节点路径
     *
     * @return
     */
    public abstract String makeSelfAsNewParentIds();

    /**
     * 权重 用于排序 越小越排在前边
     *
     * @return
     */
    public abstract Integer getWeight();

    public abstract void setWeight(Integer weight);

    /**
     * 是否是根节点
     *
     * @return
     */
    public abstract boolean isRoot();

    /**
     * 是否是叶子节点
     *
     * @return
     */
    public abstract boolean isLeaf();

    /**
     * 根节点默认图标 如果没有默认 空即可  大小为16×16
     */
    public abstract String getRootDefaultIcon();

    /**
     * 树枝节点默认图标 如果没有默认 空即可  大小为16×16
     */
    public abstract String getBranchDefaultIcon();

    /**
     * 树叶节点默认图标 如果没有默认 空即可  大小为16×16
     */
    public abstract String getLeafDefaultIcon();

    public abstract Object getId();
}
