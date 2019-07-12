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
/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.mybatis.plugin.page;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 分页基础Vo
 * Author: CK
 * Date: 2016/1/20
 */
public class BaseVo<T extends Serializable> extends BasePagination<T> {
    /**
     * 获取对象属性对应的map
     * 
     * @param ignoreProperties 不需要的属性
     * @return map
     */
    public Map<String, Object> convertPageMap(String...ignoreProperties) {
        PropertyDescriptor[] ts = BeanUtils.getPropertyDescriptors(this.getClass());
        List<String> ignoreList = (ignoreProperties != null && ignoreProperties.length > 0)
                ? Arrays.asList(ignoreProperties) : new ArrayList<String>();
        ignoreList.add("pageNumberList");
        Map<String, Object> m = new HashMap<String, Object>();
        boolean page = false;
        for (PropertyDescriptor t : ts) {
            Method r = t.getReadMethod();
            if (r != null && (ignoreProperties == null || (!ignoreList.contains(t.getName())))) {
                try {
                    if (!Modifier.isPublic(r.getDeclaringClass().getModifiers())) {
                        r.setAccessible(true);
                    }
                    // 属性中包括分页类
                    if (r.invoke(this) instanceof Pagination) {
                        m.put(MAP_PAGE_FIELD, r.invoke(this));
                        page = true;
                    } else {
                        m.put(t.getName(), r.invoke(this));
                    }
                } catch (Throwable e) {
                    throw new FatalBeanException("Could not copy property '" + t.getName()
                            + "' from source to target", e);
                }
            }
        }
        if (!page) {
            m.put(MAP_PAGE_FIELD, this);
        }
        return m;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
