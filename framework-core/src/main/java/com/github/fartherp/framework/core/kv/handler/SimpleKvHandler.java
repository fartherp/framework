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
package com.github.fartherp.framework.core.kv.handler;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/11/13
 */
public class SimpleKvHandler extends BaseDefaultKvHandler {

    public SimpleKvHandler(Integer name, String tableName, String keyField, String valueField,
                           Map<String, Object> extraFields, int sqlFlag) {
        this(name, tableName, keyField, valueField, extraFields, sqlFlag, true, 0);
    }

    public SimpleKvHandler(Integer name, String tableName, String keyField, String valueField,
                           Map<String, Object> extraFields, int sqlFlag, int defaultValueFlag) {
        this(name, tableName, keyField, valueField, extraFields, sqlFlag, true, defaultValueFlag);
    }

    public SimpleKvHandler(Integer name, String tableName, String keyField, String valueField,
                           Map<String, Object> extraFields, int sqFlag, boolean daoExecuteFlag,
                           int defaultValueFlag) {
        super(name, tableName, keyField, valueField, extraFields, sqFlag, daoExecuteFlag, defaultValueFlag);
    }
}
