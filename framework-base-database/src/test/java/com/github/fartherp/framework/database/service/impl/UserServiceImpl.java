/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.service.impl;

import com.github.fartherp.framework.database.dao.DaoMapper;
import com.github.fartherp.framework.database.dao.UserMapper;
import com.github.fartherp.framework.database.pojo.User;
import com.github.fartherp.framework.database.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * `user` 
 */
@Service("userService")
public class UserServiceImpl extends GenericSqlMapServiceImpl<User, Integer> implements UserService {

    @Resource
    private UserMapper userMapper;

    public DaoMapper<User, Integer> getDao() {
        return userMapper;
    }

    public User findByUsername(String userName) {
        return userMapper.findByUsername(userName);
    }
}
