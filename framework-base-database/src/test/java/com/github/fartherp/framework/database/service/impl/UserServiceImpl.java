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
package com.github.fartherp.framework.database.service.impl;

import com.github.fartherp.framework.database.dao.DaoMapper;
import com.github.fartherp.framework.database.dao.UserMapper;
import com.github.fartherp.framework.database.pojo.User;
import com.github.fartherp.framework.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * `user` 
 */
@Service("userService")
public class UserServiceImpl extends BaseGenericSqlMapServiceImpl<User, Integer> implements UserService {

    @Autowired
    private UserMapper userMapper;

    public DaoMapper<User, Integer> getDao() {
        return userMapper;
    }

    public User findByUsername(String userName) {
        return userMapper.findByUsername(userName);
    }
}
