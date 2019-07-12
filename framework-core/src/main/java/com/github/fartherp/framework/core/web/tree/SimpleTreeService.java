/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.core.web.tree;

import com.github.fartherp.framework.core.web.easyUI.model.SimpleTreeModel;

import java.util.List;
import java.util.function.Function;

/**
 * 简单树结构Service
 * Auth: CK
 * Date: 2016/8/27
 */
public interface SimpleTreeService<T> {
    /**
     * 返回给前端Tree数据
     * @param list 业务数据list
     * @param mc 业务回调接口
     * @return 返回给前端Tree数据
     */
    public String findTreeStr(List<T> list, Function<T, SimpleTreeModel> mc);

    /**
     * 返回树列表
     * @param list 列表
     * @param mc 回调接口
     * @return 返回值
     */
    public List<SimpleTreeModel> findTree(List<T> list, Function<T, SimpleTreeModel> mc);
}
