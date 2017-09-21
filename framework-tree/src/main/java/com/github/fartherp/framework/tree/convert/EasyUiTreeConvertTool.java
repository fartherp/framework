/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.tree.convert;

import com.github.fartherp.framework.common.util.JsonUtil;
import com.github.fartherp.framework.tree.bo.Treeable;
import com.github.fartherp.framework.tree.model.easyui.SimpleEasyUiTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by framework .
 * Auth: hyssop
 * Date: 2016-09-09
 */
public class EasyUiTreeConvertTool<T extends SimpleEasyUiTree, S extends Treeable, ID> implements ConvertTool {
    public boolean[] booleans = new boolean[2];

    public String findTreeStr(List list, ModelCall mc) {
        return JsonUtil.toJson(findModel(list, mc).getChildren());
    }

    public List<T> findChildren(List list, ModelCall mc) {
        return findModel(list, mc).getChildren();
    }

    private T findModel(List<S> list, ConvertTool.ModelCall<S, T> mc) {
        if (null == list) {
            throw new RuntimeException("没有属性结构");
        }
        Map<ID, T> p = new HashMap<ID, T>(list.size() + 1);
        // 最外层,默认为0
        T root = (T) new SimpleEasyUiTree();
        root.setId((ID) list.get(0).getParentId());
        root.setId(0);
        p.put((ID) root.getId(), (T) root);
        findModel(list, p, mc);
        root.setId(null);
        return root;
    }

    /**
     * 递归遍历菜单
     *
     * @param list 菜单列表
     * @param p    最终的菜单map
     * @param mc   具体业务回调
     */
    private void findModel(List<S> list, Map<ID, T> p, ConvertTool.ModelCall<S, T> mc) {
        if (null == list || list.isEmpty()) {
            return;
        }
        List<S> fail = new ArrayList<S>();
        for (S t : list) {
            // 当前菜单没添加到map中
            setTreeModel(t, p, mc, fail);
        }
        if (!fail.isEmpty()) {
            // 递归
            findModel(fail, p, mc);
        }
    }

    /**
     * map中不包括当前菜单且父菜单存在,添加当前菜单到map中
     *
     * @param t    业务自己数据
     * @param p    最终map
     * @param mc   自己实现的业务回调接口
     * @param fail 没有找到父菜单的菜单列表
     */
    private void setTreeModel(S t, Map<ID, T> p, ConvertTool.ModelCall<S, T> mc, List<S> fail) {
        T model = mc.convert(t);
        if (model == null) {
            throw new RuntimeException("回调方法生成EasyUITreeModel错误");
        }
        // map中不包括当前菜单且包括父菜单
        if (!p.containsKey(model.getId()) && null != p.get(model.getPid())) {
            // 添加到map中
            p.put((ID) model.getId(), model);
            T obj = p.get(model.getPid());
            if (null != obj) {
                obj.addChildren(model);
            }
        } else {
            fail.add(t);
        }
    }

}
