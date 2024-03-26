package com.lulu040108.DAO.sqlSession;

import java.util.List;

public interface SqlSession {
    /**
     * 查询多个结果
     */
    <E> List<E> selectList(String statementId,Object param) throws Exception;
    /**
      查询单个结果
     */
    <T> T selectOne(String statementId,Object param) throws Exception;
    /**
     * 清除资源
     */
    void close();
}
