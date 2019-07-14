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
package com.github.fartherp.framework.poi;

import java.util.List;

/**
 * 读取接口
 * @author CK
 * @date 2016/1/17
 */
@FunctionalInterface
public interface ReadDeal<E> {
    /**
     * 批处理数量
     * @return 批处理数量
     */
    default int getBatchCount() {
        return 1000;
    }

    /**
     * 跳过开始行数
     * @return 跳过行数
     */
    default int skipLine() {
        return 1;
    }

    /**
     * 批处理List
     * @param list List
     */
    void dealBatchBean(List<E> list);
}
