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
package com.github.fartherp.framework.exception;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SystemExceptionTest {

    @Test
    public void testGetMessageCustomExceptionAndCustomMessage() {
        try {
            try {
                String s = null;
                s.toString();
            } catch (Exception e) {
                String[] str = {"a", "b"};
                throw new SystemException("first={0}, second={1}", e, str);
            }
        }  catch (Exception e) {
			assertEquals(e.getMessage(), "first=a, second=b");
        }
    }

    @Test
    public void testGetMessageCustomMessage() {
        try {
            try {
                String s = null;
                s.toString();
            } catch (Exception e) {
                throw new SystemException("自定义异常信息");
            }
        }  catch (Exception e) {
			assertEquals(e.getMessage(), "自定义异常信息");
        }
    }

    @Test
    public void testGetMessageNullPointerException() {
        try {
            try {
                String s = null;
                s.toString();
            } catch (Exception e) {
                throw new SystemException(e);
            }
        }  catch (SystemException e) {
			assertEquals(e.getMessage(), "空指针异常");
        }
    }

    @Test
    public void testGetMessageCustomExceptionAndMessage() {
        try {
            try {
                String s = null;
                s.toString();
            } catch (Exception e) {
                throw new SystemException("自定义异常信息", e);
            }
        }  catch (Exception e) {
			assertEquals(e.getMessage(), "空指针异常");
        }
    }
}
