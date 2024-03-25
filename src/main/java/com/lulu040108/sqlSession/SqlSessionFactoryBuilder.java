package com.lulu040108.sqlSession;

import com.lulu040108.config.XMLConfigBuilder;
import com.lulu040108.pojo.Configuration;
import org.dom4j.DocumentException;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    /**
     * 1.解析配置文件，封装容器对象
     * 2.创建sqlSessionFactory工厂对象
     * @param inputStream
     * @return
     */
    public SqlSessionFactory build(InputStream inputStream) throws DocumentException {
        //解析文件的解析类
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration=xmlConfigBuilder.parse(inputStream);
        //创建工厂对象
        DefaultSqlSessionFactory defaultSqlSessionFactory=new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }
}
