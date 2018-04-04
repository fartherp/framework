/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.mock.action;

import com.github.fartherp.framework.core.mock.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/4
 */
@Component("userAction")
public class UserAction {
    @Resource
    private UserService userService;

    public void getUser() {
        userService.getUser();
    }
}
