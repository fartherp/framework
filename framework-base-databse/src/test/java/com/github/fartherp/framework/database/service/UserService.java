/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.service;

import com.github.fartherp.framework.database.pojo.User;

/**
 * `user` 
 */
public interface UserService extends GenericService<User, Integer> {

    User findByUsername(String userName);
}
