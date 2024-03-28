package com.lulu040108.DAO.config;

import com.lulu040108.DAO.pojo.Configuration;
import com.lulu040108.DAO.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 *  parse方法：解析映射配置文件，封装mappedstatement对象，然后封装在configuration对象里面
 */
public class XMLMapperBuilder {
    private Configuration configuration;
    XMLMapperBuilder(Configuration configuration){
        this.configuration=configuration;
    }

    public void parse(InputStream resourceAsStream) throws DocumentException {
        Document document = new SAXReader().read(resourceAsStream);
        Element rootElement=document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectList = rootElement.selectNodes("//select");
        for(Element element:selectList){
            String id =element.attributeValue("id");
            String resultType =element.attributeValue("resultType");
            String parameterType =element.attributeValue("parameterType");
            String sql = element.getTextTrim();
            //封装mappedstatement对象
            MappedStatement mappedStatement = new MappedStatement();
            String StatementId=namespace+"."+id;
            mappedStatement.setStatementId(StatementId);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sql);
            mappedStatement.setSqlCommandType("select");
            configuration.getMappedStatementMap().put(StatementId,mappedStatement);
        }
    }
}
