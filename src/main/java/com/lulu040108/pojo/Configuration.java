package com.lulu040108.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类：存放核心配置文件解析出来的内容
 */
public class Configuration {
     private DataSource dataSource;
     //statementId作为key
     private Map<String,MappedStatement> MappedStatementMap =new HashMap();

    public DataSource getDataSource() {
        return dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return MappedStatementMap;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.MappedStatementMap = mappedStatementMap;
    }
}
