/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.exception;

import org.testng.annotations.Test;

public class SystemExceptionTest {

    @Test
    public void testGetMessage() throws Exception {
        try {
            try {
                String s = null;
                s.toString();
            } catch (Exception e) {
                String[] str = {"a", "b"};
                throw new SystemException("first={0}, second={1}", e, str);
            }
        }  catch (Exception e) {
            String error = e.getMessage();
            System.out.println(error);
        }
    }

    @Test
    public void testGetMessage1() throws Exception {
        try {
            try {
                String s = null;
                s.toString();
            } catch (Exception e) {
                throw new SystemException("自定义异常信息");
            }
        }  catch (Exception e) {
            String error = e.getMessage();
            System.out.println(error);
        }
    }

    @Test
    public void testGetMessage2() throws Exception {
        try {
            try {
                String s = null;
                s.toString();
            } catch (Exception e) {
                throw new SystemException(e);
            }
        }  catch (SystemException e) {
            String error = e.getMessage();
            System.out.println(error);
        }
    }

    @Test
    public void testGetMessage3() throws Exception {
        try {
            try {
                String s = null;
                s.toString();
            } catch (Exception e) {
                throw new SystemException("自定义异常信息", e);
            }
        }  catch (Exception e) {
            String error = e.getMessage();
            System.out.println(error);
        }
    }
}