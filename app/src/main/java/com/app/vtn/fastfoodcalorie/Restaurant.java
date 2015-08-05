package com.app.vtn.fastfoodcalorie;

/**
 * Created by Administrator on 25/7/2015.
 */
public class Restaurant {

    int id;
    String name;

    // constructors
    public Restaurant() {
    }

    public  Restaurant(String name) {
        this.name=name;
    }

    public Restaurant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String note) {
        this.name = note;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
