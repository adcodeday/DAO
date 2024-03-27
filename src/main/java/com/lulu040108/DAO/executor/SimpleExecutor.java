package com.lulu040108.DAO.executor;

import com.lulu040108.DAO.config.BoundSql;
import com.lulu040108.DAO.pojo.Configuration;
import com.lulu040108.DAO.pojo.MappedStatement;
import com.lulu040108.DAO.utils.GenericTokenParser;
import com.lulu040108.DAO.utils.ParameterMapping;
import com.lulu040108.DAO.utils.ParameterMappingTokenHandler;

import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    private Connection connection=null;
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;

    @Override
    public <E> List<E> query(Configuration configuration, String statementId, MappedStatement mappedStatement, Object param) throws Exception {
        //加载驱动获取数据库链接
        connection = configuration.getDataSource().getConnection();

        //获取preparedStatement预编译对象
        String sql = mappedStatement.getSql();
        BoundSql boundSql=getBoundSql(sql);
        String finalSql=boundSql.getFinalSql();
        preparedStatement = connection.prepareStatement(finalSql);

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
        resultSet = preparedStatement.executeQuery();
        //处理返回结果集
        ArrayList<E> list=new ArrayList<>();
        while (resultSet.next()){
            String resultType= mappedStatement.getResultType();
            Class<?> resultTypeClass = Class.forName(resultType);
            Object o=resultTypeClass.newInstance();
            ResultSetMetaData metaData= resultSet.getMetaData();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名
                String columName=metaData.getColumnName(i);
                //字段的值
                Object value=resultSet.getObject(columName);
                //进行封装
                //属性描述器，通过API获取某个属性的读写方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                //参数1：实例对象。参数2：要设置的值
                writeMethod.invoke(o,value);
            }
            list.add((E)o);
        }
        return list;
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
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
