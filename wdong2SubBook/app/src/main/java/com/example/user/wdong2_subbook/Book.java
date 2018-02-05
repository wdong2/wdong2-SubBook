package com.example.user.wdong2_subbook;

import java.util.Date;

/**
 * Created by User on 2018/2/2.
 */

class Book {
    private String name;
    private Date date;
    private double charge;
    private String comment;

    public Book (String name,Date date, double charge){
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public void setCommend(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public double getCharge() {
        return charge;
    }

    public String getComment() {
        return comment;
    }
}
