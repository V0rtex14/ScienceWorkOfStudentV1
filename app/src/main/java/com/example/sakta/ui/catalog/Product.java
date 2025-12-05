package com.example.sakta.ui.catalog;

public class Product {
    private long id;
    private String title;
    private String description;
    private int oldPrice;
    private int newPrice;
    private int discountPercent;
    private int imageResId; // локальный ресурс. Потом можно заменить на String imageUrl
    private double distanceKm;
    private String pickupUntil;
    private float rating;
    private boolean availableToday;

    public Product(long id, String title, String description,
                   int oldPrice, int newPrice, int discountPercent, int imageResId,
                   double distanceKm, String pickupUntil, float rating, boolean availableToday) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.discountPercent = discountPercent;
        this.imageResId = imageResId;
        this.distanceKm = distanceKm;
        this.pickupUntil = pickupUntil;
        this.rating = rating;
        this.availableToday = availableToday;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getOldPrice() { return oldPrice; }
    public int getNewPrice() { return newPrice; }
    public int getDiscountPercent() { return discountPercent; }
    public int getImageResId() { return imageResId; }
    public double getDistanceKm() { return distanceKm; }
    public String getPickupUntil() { return pickupUntil; }
    public float getRating() { return rating; }
    public boolean isAvailableToday() { return availableToday; }

    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setOldPrice(int oldPrice) { this.oldPrice = oldPrice; }
    public void setNewPrice(int newPrice) { this.newPrice = newPrice; }
    public void setDiscountPercent(int discountPercent) { this.discountPercent = discountPercent; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
    public void setPickupUntil(String pickupUntil) { this.pickupUntil = pickupUntil; }
    public void setRating(float rating) { this.rating = rating; }
    public void setAvailableToday(boolean availableToday) { this.availableToday = availableToday; }
}
