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

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by zhjie.zhang on 2019/3/26.
 */
public class StreamUtil {
    public static <T, R> List<R> map(List<T> data, Function<T, R> mapFunc) {
        return data.stream().map(mapFunc).collect(Collectors.toList());
    }
}
