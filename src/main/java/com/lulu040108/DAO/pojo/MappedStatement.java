 package com.lulu040108.DAO.pojo;

/**
 * 映射配置类：存放mapper.xml解析内容
 */
public class MappedStatement {
    //唯一标识statementId
    private String statementId;
    //返回值类型
    private String resultType;
    //参数类型
    private String parameterType;
    //sql语句
    private String sql;

    public String getParameterType() {
        return parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public String getStatementId() {
        return statementId;
    }

    public String getSql() {
        return sql;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }
}
