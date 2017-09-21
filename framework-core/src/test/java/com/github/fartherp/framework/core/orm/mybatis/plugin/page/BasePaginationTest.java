/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.orm.mybatis.plugin.page;

import org.testng.annotations.Test;

public class BasePaginationTest {

    @Test
    public void testGetPageNumberList() throws Exception {
        Pagination<Integer> pagination = new BasePagination<Integer>(200, 10, 5);
        for (int i : pagination.getPageNumberList()) {
            System.out.print(i + "\t");
        }
    }
}