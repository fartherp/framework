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
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<EasyUITreeModel> children) {
        this.children = children;
    }

    public void addChildren(EasyUITreeModel model) {
        if (null == children) {
            children = new ArrayList<>();
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
