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
package com.github.fartherp.framework.core.web.easyUI.model;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/23
 */
public class BaseTreeModel {
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
     * 是否父节点，true:父节点、false:不是父节点
     */
    private Boolean isParent;
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

    public Boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(Boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean isOpen() {
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

    public Boolean isHidden() {
        return isHidden;
    }

    public void setHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Boolean isParent() {
        return isParent;
    }

    public void setParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Boolean isNocheck() {
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
