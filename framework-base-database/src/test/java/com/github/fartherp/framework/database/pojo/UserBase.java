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
package com.github.fartherp.framework.database.pojo;

import com.github.fartherp.framework.database.dao.FieldAccessVo;

import java.util.Date;

/**
 * `user` 
 */
public class UserBase extends FieldAccessVo<Integer> {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * This method returns the value of the database column `user`.id
     *
     * @return the value of `user`.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method sets the value of the database column `user`.id
     *
     * @param id the value for `user`.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column `user`.user_name
     *
     * @return the value of `user`.user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method sets the value of the database column `user`.user_name
     *
     * @param userName the value for `user`.user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method returns the value of the database column `user`.password
     *
     * @return the value of `user`.password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method sets the value of the database column `user`.password
     *
     * @param password the value for `user`.password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method returns the value of the database column `user`.phone
     *
     * @return the value of `user`.phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method sets the value of the database column `user`.phone
     *
     * @param phone the value for `user`.phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method returns the value of the database column `user`.create_time
     *
     * @return the value of `user`.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method sets the value of the database column `user`.create_time
     *
     * @param createTime the value for `user`.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method returns the value of the database column `user`.update_time
     *
     * @return the value of `user`.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method sets the value of the database column `user`.update_time
     *
     * @param updateTime the value for `user`.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	@Override
	public Integer primaryKey() {
		return id;
	}
}
