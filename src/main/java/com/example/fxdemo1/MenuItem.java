package com.example.fxdemo1;

import javafx.beans.property.SimpleDoubleProperty;

public class MenuItem {
    private String name;
    private SimpleDoubleProperty price;

    public MenuItem(String name, Double price){
        this.name = name;
        this.price = new SimpleDoubleProperty(price);
    }

    public String getName(){ return this.name; }
    public SimpleDoubleProperty getPrice(){ return this.price; }

    public void setName(String name) { this.name = name; }
    public void setPrice(Double price) { this.price = new SimpleDoubleProperty(price); }
}
