package com.lulu040108.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.lulu040108.io.Resources;
import com.lulu040108.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {
    public XMLConfigBuilder(){
        this.configuration=new Configuration();
    }
    private Configuration configuration;
    /**
     * 使用dom4j+xpath解析配置文件,封装configuration对象
     * @param inputStream
     * @return
     */
    public Configuration parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement=document.getRootElement();
        List<Element> list=rootElement.selectNodes("//property");
        Properties properties=new Properties();
        for(Element element:list){
            String name=element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }
        //创建数据源对象
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getProperty("driverClassName"));
        druidDataSource.setUrl("url");
        druidDataSource.setUsername("username");
        druidDataSource.setPassword("password");
        configuration.setDataSource(druidDataSource);
        List<Element> mapperList= rootElement.selectNodes("//mapper");
        for(Element mapper:mapperList){
            String mapperPath = mapper.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsStream);
        }
        return configuration;
    }
}
