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
package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import static com.github.fartherp.framework.common.util.ISOUtil.*;
import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author cuiyuqiang
 * @date 2019/7/16
 */
public class ISOUtilTest {

	@Test
	public void testUnicode2String() {
		assertEquals(unicode2String("\u674e"), "李");
	}
}
