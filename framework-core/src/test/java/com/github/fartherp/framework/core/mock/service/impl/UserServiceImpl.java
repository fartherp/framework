/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.mock.service.impl;

import com.github.fartherp.framework.core.mock.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/4
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    public void getUser() {
        System.out.println("我是实际业务逻辑");
    }
}
