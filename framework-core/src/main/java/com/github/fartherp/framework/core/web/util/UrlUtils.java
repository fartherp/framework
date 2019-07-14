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
package com.github.fartherp.framework.core.web.util;

import com.github.fartherp.framework.common.util.PathPatternMatcher;

import java.util.SortedSet;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/8/12
 */
public class UrlUtils {

    /**
     * 匹配路径是否在控制域的范围内
     *
     * @param urls URL列表
     * @param path 路径
     * @return String
     */
    public static String urlMatch(SortedSet<String> urls, String path) {
        if (urls == null || urls.size() == 0) {
            return null;
        }

        SortedSet<String> hurl = urls.headSet(path);
        SortedSet<String> turl = urls.tailSet(path);

        if (hurl.size() > 0) {
            String before = hurl.last();
            if (pathMatch(path, before)) {
                return before;
            }
        }

        if (turl.size() > 0) {
            String after = turl.first();
            if (pathMatch(path, after)) {
                return after;
            }
        }

        return null;
    }

    /**
     * 匹配路径是否在控制域的范围内
     *
     * @param path 匹配路径
     * @param domain 实际路径
     * @return boolean
     */
    private static boolean pathMatch(String path, String domain) {
        if (PathPatternMatcher.isPattern(domain)) {
            return PathPatternMatcher.match(domain, path);
        } else {
            return domain.equals(path);
        }
    }
}
