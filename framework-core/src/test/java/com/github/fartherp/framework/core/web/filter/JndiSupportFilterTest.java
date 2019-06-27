/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.filter;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class JndiSupportFilterTest {

    @Test
    public void testInit() {
        Company company = new Company();
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(company);
        bw.setPropertyValue("name", "Some Company Inc.");
        PropertyValue value = new PropertyValue("name", "Some Company Inc1.");
        bw.setPropertyValue(value);

        Employee employee = new Employee();
        BeanWrapper jim = PropertyAccessorFactory.forBeanPropertyAccess(employee);
        PropertyValue name = new PropertyValue("name", "Some Employee Inc1.");
        List<PropertyValue> list = new ArrayList<>();
        list.add(name);
        PropertyValues propertyValues = new MutablePropertyValues(list);
        jim.setPropertyValues(propertyValues, true);
        jim.setPropertyValue("salary", 1L);
        bw.setPropertyValue("managingDirector", jim.getWrappedInstance());

        Float salary = (Float) bw.getPropertyValue("managingDirector.salary");
		assertEquals(salary, Float.parseFloat("1.0"));
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
