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
