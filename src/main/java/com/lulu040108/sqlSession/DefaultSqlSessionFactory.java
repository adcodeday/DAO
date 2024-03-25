package com.lulu040108.sqlSession;

import com.lulu040108.pojo.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory{
    private Configuration configuration;
    DefaultSqlSessionFactory(Configuration configuration){
        this.configuration=configuration;
    }
}
