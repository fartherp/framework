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
package com.github.fartherp.framework.poi.excel.write;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/5/9
 */
public enum SheetsTitlesEnum {
    USER_LOGIN_LOG("系统登录记录", "登录时间,账号,访问终端,登录IP,状态"),
    ;
    private String head;

    private String[] title;

    private SheetsTitlesEnum(String head, String title) {
        this.head = head;
        this.title = title.split(",");
    }

    public String getHead() {
        return head;
    }

    public String[] getTitle() {
        return title;
    }
}
