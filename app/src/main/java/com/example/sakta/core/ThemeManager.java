package com.example.sakta.core;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public final class ThemeManager {
    private static final String PREFS_NAME = "sakta_prefs";
    private static final String KEY_THEME = "app_theme";

    private ThemeManager() { }

    public static void applyThemeFromPreferences(Context context) {
        int mode = getSavedMode(context);
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    public static void switchTo(Context context, int mode) {
        saveMode(context, mode);
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    public static int getSavedMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private static void saveMode(Context context, int mode) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_THEME, mode).apply();
    }
}
