package com.sample.orderdiscount;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    
    private String orderID;
    private List<Item> items;
    private String address;
    
    
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    
    
    
    
    public void giveDiscount(Item item) {
        item.setItemDiscount(new BigDecimal("10.0"));
    }
}
