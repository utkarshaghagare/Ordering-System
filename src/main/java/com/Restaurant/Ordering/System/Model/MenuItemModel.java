package com.Restaurant.Ordering.System.Model;

import org.springframework.web.multipart.MultipartFile;

public class MenuItemModel {
//    public MultipartFile getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(MultipartFile imageUrl) {
//        this.imageUrl = imageUrl;
//    }

    //private MultipartFile imageUrl;
    private String itemName;
    private String description;
    private String category;
    private double price;
    private int servingTime; // in minutes
    private boolean available;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getServingTime() {
        return servingTime;
    }

    public void setServingTime(int servingTime) {
        this.servingTime = servingTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
