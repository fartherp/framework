/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/3/14
 */
public class UserVo {

    @NotNull(message = "用户id不能为空", groups = {EditGroup.class})
    @Range(min = 1, max = Integer.MAX_VALUE, message = "用户id必须大于0", groups = {EditGroup.class})
    private Long id;

    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @NotEmpty(message = "真实姓名不能为空")
    private String realName;

    @NotEmpty(message = "密码不能为空", groups = {AddGroup.class})
    @Length(min = 6, max = 10, message = "密码长度在[6,10]之间", groups = {AddGroup.class})
    private String password;

    @NotEmpty(message = "确认密码不能为空", groups = {AddGroup.class})
    @Length(min = 6, max = 10, message = "确认密码长度在[6,10]之间", groups = {AddGroup.class})
    private String confirmPassword;

    @NotNull(message = "权限ID不能为空")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "权限ID必须大于0")
    private Integer authId;

    @Email(message = "邮箱格式不正确")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
