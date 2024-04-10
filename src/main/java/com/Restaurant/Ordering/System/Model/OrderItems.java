package com.Restaurant.Ordering.System.Model;

public class OrderItems {


    private String itemName;
    private int quantity;
    private String description;
    private double price;

    // Constructors, getters, and setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderItems() {}

    public OrderItems(String itemName, int quantity, String description, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
    }

    // Getters and Setters
    // You can generate these using your IDE or write them manually
}

