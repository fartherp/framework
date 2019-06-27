/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.dao;

import com.github.fartherp.framework.database.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * `user` 
 */
@Mapper
public interface UserMapper extends DaoMapper<User, Integer> {

    User findByUsername(@Param("userName") String userName);
}
