/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.tree.service;

import com.github.fartherp.framework.core.orm.mybatis.plugin.search.vo.Searchable;
import com.github.fartherp.framework.core.service.GenericService;
import com.github.fartherp.framework.tree.bo.Treeable;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

/**
 * <p>User: hyssop
 * <p>Date: 13-2-22 下午5:26
 * <p>Version: 1.0
 */
public interface BaseTreeableService<M extends Treeable<ID>, ID extends Serializable> extends GenericService {

    public M save(M m);

    public void deleteSelfAndChild(M m);

    public void deleteSelfAndChild(List<M> mList);

    public void appendChild(M parent, M child);

    public int nextWeight(ID id);

    /**
     * 移动节点
     * 根节点不能移动
     *
     * @param source   源节点
     * @param target   目标节点
     * @param moveType 位置
     */
    public void move(M source, M target, String moveType);

    /**
     * 把源节点全部变更为目标节点
     *
     * @param source
     * @param newParentIds
     */
    public void updateSelftAndChild(M source, ID newParentId, String newParentIds, int newWeight);

    /**
     * 查找目标节点及之后的兄弟  注意：值与越小 越排在前边
     *
     * @param parentIds
     * @param currentWeight
     * @return
     */
    public List<M> findSelfAndNextSiblings(String parentIds, int currentWeight);

    /**
     * 查看与name模糊匹配的名称
     *
     * @param name
     * @return
     */
    public Set<String> findNames(Searchable searchable, String name, ID excludeId) throws InvocationTargetException, IllegalAccessException;

    /**
     * 查询子子孙孙
     *
     * @return
     */
    public List<M> findChildren(List<M> parents, Searchable searchable) throws InvocationTargetException, IllegalAccessException;

    public List<M> findAllByName(Searchable searchable, M excludeM) throws InvocationTargetException, IllegalAccessException;

    /**
     * 查找根和一级节点
     *
     * @param searchable
     * @return
     */
    public List<M> findRootAndChild(Searchable searchable) throws InvocationTargetException, IllegalAccessException;

    public Set<ID> findAncestorIds(Iterable<ID> currentIds);

    public Set<ID> findAncestorIds(ID currentId);

    /**
     * 递归查询祖先
     *
     * @param parentIds
     * @return
     */
    public List<M> findAncestor(String parentIds) throws InvocationTargetException, IllegalAccessException;

    public void addExcludeSearchFilter(Searchable searchable, M excludeM);
}