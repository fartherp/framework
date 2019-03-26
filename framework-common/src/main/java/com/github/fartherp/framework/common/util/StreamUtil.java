package com.github.fartherp.framework.common.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by zhjie.zhang on 2019/3/26.
 */
public class StreamUtil {
    public static <T,R> List<R> map(List<T> data, Function<T,R> mapFunc) {
        return data.stream().map(mapFunc).collect(Collectors.toList());
    }
}
