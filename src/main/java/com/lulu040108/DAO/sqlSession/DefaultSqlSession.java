package com.lulu040108.DAO.sqlSession;

import com.lulu040108.DAO.executor.Executor;
import com.lulu040108.DAO.pojo.Configuration;
import com.lulu040108.DAO.pojo.MappedStatement;

import java.util.List;

public class DefaultSqlSession implements SqlSession{

    private Configuration configuration;

    private Executor executor;

    DefaultSqlSession(Configuration configuration,Executor executor){
        this.configuration=configuration;
        this.executor=executor;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object param) {
        //将查询操作委派给底层的执行器
        //传入数据库配置信息和sql配置信息
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<E> list=executor.query(configuration,statementId,mappedStatement,param);
        return list;
    }

    @Override
    public <T> T selectOne(String statementId, Object param) {
        //调用selectList方法
        List<Object> list = selectList(statementId, param);
        if(list.size() == 1){
            return (T) list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("返回结果过多");
        } else {
            return null;
        }
    }

    @Override
    public void close() {
        executor.close();
    }
}
