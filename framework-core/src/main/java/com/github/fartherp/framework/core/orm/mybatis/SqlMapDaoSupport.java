/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.orm.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

/**
 * 扩展dao支持
 * Author: CK
 * Date: 2015/6/5.
 */
public abstract class SqlMapDaoSupport extends DaoSupport {

    private BatchSqlSupportSession sqlSession;

    protected void setSqlSessionFactoryInternal(SqlSessionFactory sqlSessionFactory) {
        this.sqlSession = new BatchSqlSessionTemplateWrapper(sqlSessionFactory);
    }

    public abstract void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    /**
     * {@inheritDoc}
     */
    protected void checkDaoConfig() {
        Assert.notNull(this.sqlSession, "Property 'sqlSessionFactory' are required");
    }

    /**
     * 获取批量的SqlSession，SqlSession被spring事务管理，不需要提交、回滚、关闭。
     *
     * @return Spring managed thread safe SqlSession
     */
    public BatchSqlSupportSession getSqlSession() {
        return this.sqlSession;
    }
}
