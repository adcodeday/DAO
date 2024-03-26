package com.lulu040108.DAO.config;

import com.lulu040108.DAO.utils.ParameterMapping;

import java.util.List;

public class BoundSql {
    private String finalSql;
    private List<ParameterMapping> parameterMappingList;
    public BoundSql(String finalSql, List<ParameterMapping> list){
        this.finalSql=finalSql;
        this.parameterMappingList = list;
    }

    public String getFinalSql() {
        return finalSql;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setFinalSql(String finalSql) {
        this.finalSql = finalSql;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
