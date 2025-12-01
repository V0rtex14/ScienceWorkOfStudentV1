package com.example.sakta.ui.categories;

public class Category {
    public final String title;
    public final int iconRes;   // маленькая пиктограмма категории
    public final int thumbRes;  // мини-фото (пример блюда)
    public final int count;     // сколько позиций (для подписи)

    public Category(String title, int iconRes, int thumbRes, int count) {
        this.title = title;
        this.iconRes = iconRes;
        this.thumbRes = thumbRes;
        this.count = count;
    }
}
