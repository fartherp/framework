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
package com.github.fartherp.framework.poi.excel.read;

import com.github.fartherp.framework.poi.ReadDeal;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2017/12/22
 */
public interface ExcelReadDeal<E> extends ReadDeal<E> {
    /**
     * 一行excel数据返回业务BEAN
     * @param delegate 一行excel数据
     * @return 业务BEAN
     */
    E dealBean(RowDelegate delegate);
}
