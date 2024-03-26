package com.lulu040108.DAO.executor;

import com.lulu040108.DAO.config.BoundSql;
import com.lulu040108.DAO.pojo.Configuration;
import com.lulu040108.DAO.pojo.MappedStatement;
import com.lulu040108.DAO.utils.GenericTokenParser;
import com.lulu040108.DAO.utils.ParameterMapping;
import com.lulu040108.DAO.utils.ParameterMappingTokenHandler;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, String statementId, MappedStatement mappedStatement, Object param) throws Exception {
        //加载驱动获取数据库链接
        Connection connection = configuration.getDataSource().getConnection();

        //获取preparedStatement预编译对象
        String sql = mappedStatement.getSql();
        BoundSql boundSql=getBoundSql(sql);
        String finalSql=boundSql.getFinalSql();
        PreparedStatement preparedStatement = connection.prepareStatement(finalSql);

        //3.设置参数
        String parameterType= mappedStatement.getParameterType();
        Class<?> parameterTypeClass = Class.forName(parameterType);
        List<ParameterMapping> parameterMappingList=boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            //获取id或者username
            String paramName = parameterMapping.getContent();
            //反射
            Field declaredField = parameterTypeClass.getDeclaredField(paramName);
            //暴力访问
            declaredField.setAccessible(true);

            Object value = declaredField.get(param);
            //赋值占位符
            preparedStatement.setObject(i+1,value);
        }
        //执行Sql
        ResultSet resultSet = preparedStatement.executeQuery();
        //处理返回结果集

        return null;
    }

    private BoundSql getBoundSql(String sql){
        //标记处理器
        ParameterMappingTokenHandler parameterMappingTokenHandler=new ParameterMappingTokenHandler();
        //创建标记解析器
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{","}",parameterMappingTokenHandler);
        String finalSql = genericTokenParser.parse(sql);
        //获取#{}里面的值的集合
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(finalSql, parameterMappings);
        return boundSql;
    }
    @Override
    public void close() {

    }
}
