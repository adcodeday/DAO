package com.lulu040108.DAO.config.sqlSession;

import com.lulu040108.DAO.config.pojo.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory{
    private Configuration configuration;
    DefaultSqlSessionFactory(Configuration configuration){
        this.configuration=configuration;
    }
}
