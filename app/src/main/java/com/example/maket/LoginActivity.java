package com.example.maket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.maket.SaktaDbHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private SaktaDbHelper dbHelper;
    private TextInputEditText etEmail, etPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new SaktaDbHelper(this);

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        MaterialButton btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> doLogin());
        btnRegister.setOnClickListener(v -> doRegister());
    }

    private void doLogin() {
        String email = safeText(etEmail);
        String pass = safeText(etPass);

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Заполните email и пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.checkUser(email, pass)) {
            Toast.makeText(this, "Успешный вход", Toast.LENGTH_SHORT).show();
            openMain();
        } else {
            Toast.makeText(this, "Неверный email или пароль", Toast.LENGTH_SHORT).show();
        }
    }

    private void doRegister() {
        String email = safeText(etEmail);
        String pass = safeText(etPass);

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Введите email и пароль для регистрации", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean ok = dbHelper.registerUser(email, pass);
        if (ok) {
            Toast.makeText(this, "Аккаунт создан, можно войти", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Такой email уже существует", Toast.LENGTH_SHORT).show();
        }
    }

    private void openMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private String safeText(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}
