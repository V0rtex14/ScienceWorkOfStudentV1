package com.example.sakta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextInputEditText etEmail, etPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        // Если пользователь уже авторизован — сразу переходим в главное приложение
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);

        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        MaterialButton btnRegister = findViewById(R.id.btnRegister);

        // Кнопка ВОЙТИ
        btnLogin.setOnClickListener(v -> loginUser());

        // Кнопка СОЗДАТЬ НОВЫЙ АККАУНТ → сразу выбор роли
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SelectRoleActivity.class);
            // Передаём введённый email, чтобы не заставлять вводить заново
            String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
            if (!email.isEmpty()) {
                intent.putExtra("prefilled_email", email);
            }
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = getTextSafe(etEmail);
        String password = getTextSafe(etPass);

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните email и пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(LoginActivity.this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    String errorMsg = e.getMessage();
                    if (errorMsg != null && errorMsg.contains("password is invalid")) {
                        errorMsg = "Неверный пароль";
                    } else if (errorMsg != null && errorMsg.contains("no user record")) {
                        errorMsg = "Пользователь с таким email не найден";
                    }
                    Toast.makeText(LoginActivity.this, "Ошибка входа: " + errorMsg,
                            Toast.LENGTH_LONG).show();
                });
    }

    // Безопасное получение текста из EditText
    private String getTextSafe(TextInputEditText editText) {
        return editText.getText() != null ? editText.getText().toString().trim() : "";
    }
}