/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.mock.service.mock;

import com.github.fartherp.framework.core.mock.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/4
 */
@Service("userServiceMock")
public class MockServiceImpl implements UserService {
    public void getUser() {
        System.out.println("我是mock数据。。。");
    }
}
