package com.sample.orderdiscount;

import java.math.BigDecimal;

public class Item {
    
    private String itemName;
    private BigDecimal itemValue;
    private BigDecimal itemDiscount;
    
    
    public Item (String itemName) {
        this.itemName = itemName;
        this.itemDiscount = new BigDecimal("0.0");
    }

    
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public BigDecimal getItemValue() {
        return itemValue;
    }
    public void setItemValue(BigDecimal itemValue) {
        this.itemValue = itemValue;
    }
    public BigDecimal getItemDiscount() {
        return itemDiscount;
    }
    public void setItemDiscount(BigDecimal itemDiscount) {
        this.itemDiscount = itemDiscount;
    }
}
