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
package com.github.fartherp.framework.core.proxy;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/12/14
 */
public class PackageNameAutoProxyCreator extends BeanNameAutoProxyCreator {

    private List<String> packageNames;


    /**
     * Set the names of the beans that should automatically get wrapped with proxies.
     * A name can specify a prefix to match by ending with "*", e.g. "myBean,tx*"
     * will match the bean named "myBean" and all beans whose name start with "tx".
     * <p><b>NOTE:</b> In case of a FactoryBean, only the objects created by the
     * FactoryBean will get proxied. This default behavior applies as of Spring 2.0.
     * If you intend to proxy a FactoryBean instance itself (a rare use case, but
     * Spring 1.2's default behavior), specify the bean name of the FactoryBean
     * including the factory-bean prefix "&": e.g. "&myFactoryBean".
     * @see org.springframework.beans.factory.FactoryBean
     * @see org.springframework.beans.factory.BeanFactory#FACTORY_BEAN_PREFIX
     */
    public void setPackageNames(String... packageNames) {
        Assert.notEmpty(packageNames, "'packageNames' must not be empty");
        this.packageNames = new ArrayList<>(packageNames.length);
        for (String mappedName : packageNames) {
            this.packageNames.add(StringUtils.trimWhitespace(mappedName));
        }
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource targetSource) {
        Object[] objects = super.getAdvicesAndAdvisorsForBean(beanClass, beanName, targetSource);
        if (objects == PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS) {
            if (this.packageNames != null) {
                for (String mappedName : this.packageNames) {
                    if (beanClass.getName().contains(mappedName)) {
                        return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
                    }
                }
            }
        }
        return DO_NOT_PROXY;
    }
}
