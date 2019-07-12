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
package com.github.fartherp.framework.core.util;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.util.ClassUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SpringProxyUtilsTest {

	AdvisorAdapterRegistry advisorAdapterRegistry;

	@BeforeClass
	public void setUp() {
		advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();
	}

	@Test
	public void testJdkGetProxy() {
		ProxyFactory proxyFactory = new ProxyFactory();

		Bmw bmw = new Bmw();
		TargetSource targetSource = new SingletonTargetSource(bmw);
		proxyFactory.setTargetSource(targetSource);

		Class<?>[] targetInterfaces = ClassUtils.getAllInterfacesForClass(bmw.getClass());
		for (Class<?> targetInterface : targetInterfaces) {
			proxyFactory.addInterface(targetInterface);
		}

		Advisor[] advisors = new Advisor[1];
		advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
		proxyFactory.addAdvisors(advisors);

		Car proxy = (Car) proxyFactory.getProxy();
		proxy.hashCode();
		Bmw obj = SpringProxyUtils.getRealTarget(proxy);
		assertEquals(bmw, obj);
	}

	@Test
	public void testCglibGetProxy() {
		ProxyFactory proxyFactory = new ProxyFactory();

		Audi audi = new Audi();
		TargetSource targetSource = new SingletonTargetSource(audi);
		proxyFactory.setTargetSource(targetSource);

		Advisor[] advisors = new Advisor[1];
		advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
		proxyFactory.addAdvisors(advisors);

		Audi proxy = (Audi) proxyFactory.getProxy();
		Audi obj = SpringProxyUtils.getRealTarget(proxy);
		assertEquals(audi, obj);
	}

	@Test
	public void testIsMultipleProxy() {
		//cglib代理
		Bmw bmw = new Bmw();
		ProxyFactory proxyFactory1 = new ProxyFactory();
		TargetSource targetSource1 = new SingletonTargetSource(bmw);
		proxyFactory1.setTargetSource(targetSource1);

		Advisor[] advisors = new Advisor[1];
		advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
		proxyFactory1.addAdvisors(advisors);
		//jdk代理
		Object object = proxyFactory1.getProxy();

		ProxyFactory proxyFactory = new ProxyFactory();
		TargetSource targetSource = new SingletonTargetSource(object);
		proxyFactory.setTargetSource(targetSource);

		Class<?>[] targetInterfaces = ClassUtils.getAllInterfacesForClass(bmw.getClass());
		for (Class<?> targetInterface : targetInterfaces) {
			proxyFactory.addInterface(targetInterface);
		}

		advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
		proxyFactory.addAdvisors(advisors);

		Car proxy = (Car) proxyFactory.getProxy();
		//双向代理验证
		assertTrue(SpringProxyUtils.isMultipleProxy(proxy));
		SpringProxyUtils.getRealTarget(proxy);
	}

	@Test
	public void testGetRealTarget() {
		//cglib代理
		Bmw bmw = new Bmw();
		ProxyFactory proxyFactory1 = new ProxyFactory();
		TargetSource targetSource1 = new SingletonTargetSource(bmw);
		proxyFactory1.setTargetSource(targetSource1);

		Advisor[] advisors = new Advisor[1];
		advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
		proxyFactory1.addAdvisors(advisors);
		//jdk代理
		Object object = proxyFactory1.getProxy();

		ProxyFactory proxyFactory = new ProxyFactory();
		TargetSource targetSource = new SingletonTargetSource(object);
		proxyFactory.setTargetSource(targetSource);

		Class<?>[] targetInterfaces = ClassUtils.getAllInterfacesForClass(bmw.getClass());
		for (Class<?> targetInterface : targetInterfaces) {
			proxyFactory.addInterface(targetInterface);
		}

		advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
		proxyFactory.addAdvisors(advisors);

		Car proxy = (Car) proxyFactory.getProxy();
		Object target = SpringProxyUtils.getRealTarget(proxy);
		assertEquals(bmw, target);
		assertEquals(bmw, SpringProxyUtils.getRealTarget(proxy));
	}

	@Test
	public void testFindJdkDynamicProxyFactory() {
		ProxyFactory proxyFactory = new ProxyFactory();

		Bmw bmw = new Bmw();
		TargetSource targetSource = new SingletonTargetSource(bmw);
		proxyFactory.setTargetSource(targetSource);

		Class<?>[] targetInterfaces = ClassUtils.getAllInterfacesForClass(bmw.getClass());
		for (Class<?> targetInterface : targetInterfaces) {
			proxyFactory.addInterface(targetInterface);
		}

		Advisor[] advisors = new Advisor[1];
		advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
		proxyFactory.addAdvisors(advisors);

		Car proxy = (Car) proxyFactory.getProxy();
		SpringProxyUtils.findJdkDynamicProxyFactory(proxy);

	}

	@Test
	public void testGetRealTarget2() {
		//cglib代理
		Bmw bmw = new Bmw();
		ProxyFactory proxyFactory1 = new ProxyFactory();
		TargetSource targetSource1 = new SingletonTargetSource(bmw);
		proxyFactory1.setTargetSource(targetSource1);

		Advisor[] advisors = new Advisor[1];
		advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
		proxyFactory1.addAdvisors(advisors);
		//jdk代理
		Object object = proxyFactory1.getProxy();

		ProxyFactory proxyFactory = new ProxyFactory();
		TargetSource targetSource = new SingletonTargetSource(object);
		proxyFactory.setTargetSource(targetSource);

		Class<?>[] targetInterfaces = ClassUtils.getAllInterfacesForClass(bmw.getClass());
		for (Class<?> targetInterface : targetInterfaces) {
			proxyFactory.addInterface(targetInterface);
		}

		advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
		proxyFactory.addAdvisors(advisors);

		Car proxy = (Car) proxyFactory.getProxy();
		Object target = SpringProxyUtils.getRealTarget(proxy);
		assertEquals(bmw, target);
		assertEquals(bmw, SpringProxyUtils.getRealTarget(proxy));
	}

	private static class TestMethodInterceptor implements MethodInterceptor {

		public Object invoke(MethodInvocation invocation) throws Throwable {
			Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
			if (targetClass != null) {
				System.out.println(targetClass.getSimpleName());
			}
			Object obj = invocation.proceed();
			if (obj != null) {
				System.out.println("-------" + obj.toString());
			}
			return obj;
		}
	}

	private static interface Car {

		void get();
	}

	private static class Bmw implements Car {
		public Bmw() {
			this.type = "宝马";
		}

		protected String type;

		public void get() {
			System.out.println(type);
		}
	}

	private static class Audi extends Bmw {

		public Audi() {
			this.type = "奥迪";
		}

		public void get() {
			System.out.println(type);
		}
	}
}
