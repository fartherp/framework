/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.dao;

import com.github.fartherp.framework.database.pojo.User;

/**
 * `user` 
 */
public interface UserMapper extends DaoMapper<User, Integer> {

    User findByUsername(String userName);
}
