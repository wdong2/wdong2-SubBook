package com.example.user.wdong2_subbook;

import java.util.Date;

/**
 * Created by User on 2018/2/2.
 */

/**
 * author: Wang Dong
 * Book is a class that contains the setters and getters
 * version:1.0
 *
 */

class Book {
    private String name;
    private Date date;
    private double charge;
    private String comment;

    /**
     * Book
     * constructor
     * @param name
     * @param date
     * @param charge
     */
    public Book (String name,Date date, double charge){
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = null;
    }

    /**
     * setName
     * setter of the name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setDate
     * setter of the date
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * setCharge
     * setter of the charge
     * @param charge
     */
    public void setCharge(double charge) {
        this.charge = charge;
    }

    /**
     * setCommand
     * setter of the comment
     * @param comment
     */

    public void setCommend(String comment) {
        this.comment = comment;
    }

    /**
     * getName
     * getter of the name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * getDate
     * getter of the date
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * getCharge
     * getter of the charge
     * @return
     */
    public double getCharge() {
        return charge;
    }

    /**
     * getComment
     * @return
     */

    public String getComment() {
        return comment;
    }
}
