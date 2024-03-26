package com.lulu040108.DAO.sqlSession;

import com.lulu040108.DAO.executor.Executor;
import com.lulu040108.DAO.executor.SimpleExecutor;
import com.lulu040108.DAO.pojo.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory{
    private Configuration configuration;

    DefaultSqlSessionFactory(Configuration configuration){
        this.configuration=configuration;
    }

    @Override
    public SqlSession openSession() {
        Executor simpleExecutor=new SimpleExecutor();
        DefaultSqlSession defaultSqlSession=new DefaultSqlSession(configuration,simpleExecutor);
        return defaultSqlSession;
    }
}
