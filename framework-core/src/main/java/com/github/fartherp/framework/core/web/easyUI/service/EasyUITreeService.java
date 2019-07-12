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
package com.github.fartherp.framework.core.web.easyUI.service;

import com.github.fartherp.framework.core.web.easyUI.model.EasyUITreeModel;

import java.util.List;
import java.util.function.Function;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/23
 */
public interface EasyUITreeService<T> {
    /**
     * 返回树列表
     * @param list 列表
     * @param mc 回调接口
     * @return 返回值
     */
    public List<EasyUITreeModel> findChildren(List<T> list, Function<T, EasyUITreeModel> mc);

    public void setCheck(List<EasyUITreeModel> easyUITreeModels);
}
