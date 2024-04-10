package com.Restaurant.Ordering.System.Entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@Entity
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    public String getCategory() {
        return category;
    }
    private String category;
    private double price;
    private int servingTime; // in minutes
    private boolean available;



    public ImageModel getItemImage() {
        return itemImage;
    }

    public void setItemImage(ImageModel itemImage) {
        this.itemImage = itemImage;
    }


    @OneToOne
    @JoinTable(name="item_image",
            joinColumns= {
                    @JoinColumn(name="id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="image_id")
            }
    )

    private ImageModel itemImage;

    // Constructors, getters, and setters
    public MenuItem() {}

    public MenuItem(String itemName, String description, String category, double price, int servingTime, boolean available) {
        this.itemName = itemName;
       // this.imageUrl = imageUrl;
        this.description = description;
        this.category=category;
        this.price = price;
        this.servingTime = servingTime;
        this.available = available;
    }


    // Getters and Setters
    // You can generate these using your IDE or write them manually
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    private String itemName;


    public void setCategory(String category) {
        this.category = category;
    }



}
