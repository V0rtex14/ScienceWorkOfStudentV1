package com.example.maket.ui.catalog;

public class Product {
    private long id;
    private String title;
    private String description;
    private int oldPrice;
    private int newPrice;
    private int discountPercent;
    private int imageResId; // локальный ресурс. Потом можно заменить на String imageUrl

    public Product(long id, String title, String description,
                   int oldPrice, int newPrice, int discountPercent, int imageResId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.discountPercent = discountPercent;
        this.imageResId = imageResId;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getOldPrice() { return oldPrice; }
    public int getNewPrice() { return newPrice; }
    public int getDiscountPercent() { return discountPercent; }
    public int getImageResId() { return imageResId; }

    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setOldPrice(int oldPrice) { this.oldPrice = oldPrice; }
    public void setNewPrice(int newPrice) { this.newPrice = newPrice; }
    public void setDiscountPercent(int discountPercent) { this.discountPercent = discountPercent; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }
}
