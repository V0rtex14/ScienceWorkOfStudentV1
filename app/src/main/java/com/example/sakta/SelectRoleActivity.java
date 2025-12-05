package com.example.sakta;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sakta.core.ThemeManager;

public class SelectRoleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.applyThemeFromPreferences(this);
        setContentView(R.layout.activity_select_role);

        findViewById(R.id.cardBuyer).setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra("role", "buyer");
            startActivity(intent);
        });

        findViewById(R.id.cardSeller).setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra("role", "seller");
            startActivity(intent);
        });
    }
}