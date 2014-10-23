package com.sample.gateway;

import java.io.Serializable;
import java.math.BigDecimal;

public class Person implements Serializable {

    private static final long serialVersionUID = -7999692448801223492L;
    
    private String id;
    private String name;
    private int age;
    private BigDecimal income;
    private String processedBy;
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public BigDecimal getIncome() {
        return income;
    }
    public void setIncome(BigDecimal income) {
        this.income = income;
    }
    public String getProcessedBy() {
        return processedBy;
    }
    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }
    
}
