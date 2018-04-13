/*
 * Copyright (C) 2018 hyssop, Inc. All Rights Reserved.
 */

/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.database.mybatis.orm;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/8/27
 */
public interface SqlEvent {
    /**
     * Sql event call back.
     *
     * @param sql
     * @param timetook
     * @param queryRows
     */
    void onExecuteSql(String sql, Date begin, long timetook, int queryRows, Throwable t);
}
