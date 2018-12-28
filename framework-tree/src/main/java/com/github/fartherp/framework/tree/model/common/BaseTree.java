/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.tree.model.common;

import java.io.Serializable;

/**
 * Created by framework .
 * Auth: hyssop
 * Date: 2016-09-09
 */
public abstract class BaseTree implements Tree {
    /**
     * URL
     */
    private String url;
    /**
     * 节点名称
     */
    protected String text;
    /**
     * 节点是否禁用true:禁用、false:可用
     */
    protected Boolean chkDisabled;
    /**
     * 节点是否默认选择，true:被勾选、false:未勾选
     */
    private Boolean checked;
    /**
     * 节点是否打开，true:展开、false:折叠
     */
    private Boolean open;
    /**
     * 节点自定义图标的URL路径
     */
    private String icon;
    /**
     * 父节点自定义折叠时图标的URL路径
     */
    private String iconClose;
    /**
     * 父节点自定义展开时图标的URL路径
     */
    private String iconOpen;
    /**
     * 节点自定义图标的 className
     */
    private String iconSkin;
    /**
     * 节点是否被隐藏，true:隐藏、false:显示
     */
    private Boolean isHidden;

    /**
     * 节点是否隐藏，true:节点不显示 、false:点具有正常的勾选功能
     */
    private Boolean nocheck;
    /**
     * 设置点击节点后在何处打开 url
     * 同超链接 target 属性: "_blank", "_self" 或 其他指定窗口名称
     */
    private String target;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(Boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconClose() {
        return iconClose;
    }

    public void setIconClose(String iconClose) {
        this.iconClose = iconClose;
    }

    public String getIconOpen() {
        return iconOpen;
    }

    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Boolean getNocheck() {
        return nocheck;
    }

    public void setNocheck(Boolean nocheck) {
        this.nocheck = nocheck;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
