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
package com.github.fartherp.framework.database.dao;

import java.io.Serializable;

/**
 * 字段操作类
 * Author: CK
 * Date: 2015/6/5.
 */
public abstract class FieldAccessVo<ID> implements Cloneable, Serializable {

    /**
     * get primary key
     *
     * @return primary value
     */
    public abstract ID primaryKey();
}
