/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util;

import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.util.ClassUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SpringProxyUtilsTest {

    AdvisorAdapterRegistry advisorAdapterRegistry;

    @BeforeMethod
    public void setUp() throws Exception {
        advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();

    }

    @Test
    public void testGetProxy() throws Exception {
        jdk();
        cglib();
    }

    public void jdk() {
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
        Assert.assertEquals(bmw, obj);
    }

    public void cglib() {
        ProxyFactory proxyFactory = new ProxyFactory();

        Audi audi = new Audi();
        TargetSource targetSource = new SingletonTargetSource(audi);
        proxyFactory.setTargetSource(targetSource);

        Advisor[] advisors = new Advisor[1];
        advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
        proxyFactory.addAdvisors(advisors);

        Audi proxy = (Audi) proxyFactory.getProxy();
        Audi obj = SpringProxyUtils.getRealTarget(proxy);
        Assert.assertEquals(audi, obj);
    }

    @Test
    public void isMultipleProxy(){
        //cglib代理
        Bmw bmw = new Bmw();
        ProxyFactory proxyFactory1 = new ProxyFactory();
        TargetSource targetSource1 = new SingletonTargetSource(bmw);
        proxyFactory1.setTargetSource(targetSource1);

        Advisor[] advisors = new Advisor[1];
        advisors = new Advisor[1];
        advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
        proxyFactory1.addAdvisors(advisors);
        //jdk代理
        Object object =  proxyFactory1.getProxy();

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
        System.out.println(SpringProxyUtils.isMultipleProxy(proxy));
        SpringProxyUtils.getRealTarget(proxy);
    }

    @Test
    public void testGetRealTarget() throws Exception {
        //cglib代理
        Bmw bmw = new Bmw();
        ProxyFactory proxyFactory1 = new ProxyFactory();
        TargetSource targetSource1 = new SingletonTargetSource(bmw);
        proxyFactory1.setTargetSource(targetSource1);

        Advisor[] advisors = new Advisor[1];
        advisors = new Advisor[1];
        advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
        proxyFactory1.addAdvisors(advisors);
        //jdk代理
        Object object =  proxyFactory1.getProxy();

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
        Assert.assertEquals(bmw,target);
        Assert.assertEquals(bmw, SpringProxyUtils.getRealTarget(proxy));
    }

    @Test
    public void testFindJdkDynamicProxyFactory() throws Exception {
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
    public void testGetRealTarget2() throws Exception {
        //cglib代理
        Bmw bmw = new Bmw();
        ProxyFactory proxyFactory1 = new ProxyFactory();
        TargetSource targetSource1 = new SingletonTargetSource(bmw);
        proxyFactory1.setTargetSource(targetSource1);

        Advisor[] advisors = new Advisor[1];
        advisors = new Advisor[1];
        advisors[0] = advisorAdapterRegistry.wrap(new TestMethodInterceptor());
        proxyFactory1.addAdvisors(advisors);
        //jdk代理
        Object object =  proxyFactory1.getProxy();

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
        Assert.assertEquals(bmw,target);
        Assert.assertEquals(bmw, SpringProxyUtils.getRealTarget(proxy));
    }
}