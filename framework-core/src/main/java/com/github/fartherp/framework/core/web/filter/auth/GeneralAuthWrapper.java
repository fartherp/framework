/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.filter.auth;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/5/23
 */
public class GeneralAuthWrapper {
    private int status;
    private String statusInfo;
    private AuthWrapper data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public AuthWrapper getData() {
        return data;
    }

    public void setData(AuthWrapper data) {
        this.data = data;
    }
}
