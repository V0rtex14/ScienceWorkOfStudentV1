package com.example.maket.ui.catalog;

public class Product {
    public final String title;   // Название
    public final int price;      // Цена в сомах
    public final int imageRes;   // R.drawable.*
    public final String maker;   // Производитель (может быть пустым)

    public Product(String title, int price, int imageRes, String maker) {
        this.title = title;
        this.price = price;
        this.imageRes = imageRes;
        this.maker = maker;
    }
}
