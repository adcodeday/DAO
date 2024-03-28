package com.lulu040108.DAO.sqlSession;

import com.lulu040108.DAO.executor.Executor;
import com.lulu040108.DAO.pojo.Configuration;
import com.lulu040108.DAO.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession{

    private Configuration configuration;

    private Executor executor;

    DefaultSqlSession(Configuration configuration,Executor executor){
        this.configuration=configuration;
        this.executor=executor;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object param) throws Exception {
        //将查询操作委派给底层的执行器
        //传入数据库配置信息和sql配置信息
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<E> list=executor.query(configuration,statementId,mappedStatement,param);
        return list;
    }

    @Override
    public <T> T selectOne(String statementId, Object param) throws Exception {
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

    @Override
    public <T> T getMapper(Class<T> mapperClass) {
        Object proxy = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            /**
             * proxy，代理对象的引用
             * Method：被调用的方法字节码文件
             * Object[]:调用方法的参数
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //参数准备：statementId和param
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId=className+"."+methodName;
                //方法调用,调用sqlsession哪个方法
                //通过sqlCommandType得知操作类型
                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
                String sqlCommandType =mappedStatement.getSqlCommandType();
                switch (sqlCommandType){
                    case "select":
                        //执行查询方法调用
                        AnnotatedType annotatedReturnType = method.getAnnotatedReturnType();
                        if(annotatedReturnType instanceof ParameterizedType){
                            if (args!=null)
                                return selectList(statementId,args[0]);
                            return selectList(statementId,null);
                        }
                        return selectOne(statementId,args[0]);
                    case "update":
                        break;
                    case "delete":
                        break;
                    case "insert":
                        break;

                }
                return null;
            }
        });

        return (T) proxy;
    }
}
