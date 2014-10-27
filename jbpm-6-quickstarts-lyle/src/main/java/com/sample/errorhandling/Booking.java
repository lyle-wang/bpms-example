package com.sample.errorhandling;

import java.io.Serializable;

public class Booking implements Serializable {

    private static final long serialVersionUID = 2268512780686070321L;
    
    
    private String id;
    private int guestNumber;
    private int bedrmNumber;
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getGuestNumber() {
        return guestNumber;
    }
    public void setGuestNumber(int guestNumber) {
        this.guestNumber = guestNumber;
    }
    public int getBedrmNumber() {
        return bedrmNumber;
    }
    public void setBedrmNumber(int bedrmNumber) {
        this.bedrmNumber = bedrmNumber;
    }
}
