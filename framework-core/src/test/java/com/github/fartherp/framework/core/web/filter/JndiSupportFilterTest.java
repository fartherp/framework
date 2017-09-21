/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.filter;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class JndiSupportFilterTest {

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void testInit() throws Exception {
        Company company = new Company();
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(company);
        bw.setPropertyValue("name", "Some Company Inc.");
        PropertyValue value = new PropertyValue("name", "Some Company Inc1.");
        bw.setPropertyValue(value);

        Employee employee = new Employee();
        BeanWrapper jim = PropertyAccessorFactory.forBeanPropertyAccess(employee);
        PropertyValue name = new PropertyValue("name", "Some Employee Inc1.");
        List<PropertyValue> list = new ArrayList<PropertyValue>();
        list.add(name);
        PropertyValues propertyValues = new MutablePropertyValues(list);
        jim.setPropertyValues(propertyValues, true);
        jim.setPropertyValue("salary", 1L);
        bw.setPropertyValue("managingDirector", jim.getWrappedInstance());

        Float salary = (Float) bw.getPropertyValue("managingDirector.salary");
        System.out.println(salary);
    }

    @Test
    public void testDoInit() throws Exception {

    }

    public static class Company {
        private String name;
        private Employee managingDirector;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Employee getManagingDirector() {
            return this.managingDirector;
        }

        public void setManagingDirector(Employee managingDirector) {
            this.managingDirector = managingDirector;
        }
    }

    public static class Employee {
        private float salary;

        public float getSalary() {
            return salary;
        }

        public void setSalary(float salary) {
            this.salary = salary;
        }
    }
}