/*
 * Copyright (c) 2019. CK. All rights reserved.
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
