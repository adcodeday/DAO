package com.lulu040108.DAO.executor;

import com.lulu040108.DAO.pojo.Configuration;
import com.lulu040108.DAO.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

public interface Executor {
    <E> List<E> query(Configuration configuration, String statementId, MappedStatement mappedStatement, Object param) throws Exception;

    void close();
}
