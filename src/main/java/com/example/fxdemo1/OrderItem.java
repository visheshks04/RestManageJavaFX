package com.example.fxdemo1;

import javafx.beans.property.SimpleIntegerProperty;

public class OrderItem {

    private MenuItem menuItem;
    private SimpleIntegerProperty quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getTotalCost() {
        return menuItem.getPrice().get() * quantity.get();
    }

    @Override
    public String toString() {
        return menuItem.getName() + " - $" + menuItem.getPrice().get() + " x " + quantity.get() + " = $" + getTotalCost();
    }
}

