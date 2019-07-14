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
package com.github.fartherp.framework.core.bean.web;

import com.github.fartherp.framework.core.bean.ServiceLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 初始化系统监听器
 * @author CK
 * @date 2015/6/24
 */
public class InitSystemListener implements ServletContextListener {
    @Override
	public void contextInitialized(ServletContextEvent event) {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        ServiceLocator.setFactory(context);
    }

    @Override
	public void contextDestroyed(ServletContextEvent event) {
        ServiceLocator.setFactory(null);
    }
}
