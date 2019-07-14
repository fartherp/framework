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
package com.github.fartherp.framework.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by IntelliJ IDEA.
 *
 * @author zhjie.zhang.
 * @date 2019/3/26.
 */
public class ForeachUtil {
    public static <T> List<T> foreachAddWithReturn(int num, Function<Integer, List<T>> getFunc) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            result.addAll(CatchUtil.tryDo(i, getFunc));
        }
        return result;
    }

    public static <T> void foreachDone(List<T> data, Consumer<T> doFunc) {
        for (T part : data) {
            CatchUtil.tryDo(part, doFunc);
        }
    }
}
