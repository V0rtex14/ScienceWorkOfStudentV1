package com.example.sakta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private TextInputEditText etEmail, etPass, etPassConfirm, etBusinessName;
    private TextView tvTitle;
    private LinearLayout sellerFields;
    private ImageView ivBusinessPhoto, ivLicense;
    private MaterialButton btnRegister;

    private String role;
    private Uri businessPhotoUri = null;
    private Uri licenseUri = null;

    // Современный способ выбора фото/файлов
    private final ActivityResultLauncher<String> pickMediaLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    // Определяем, какое поле обновляем
                    if (getCurrentFocus() == ivBusinessPhoto) {
                        businessPhotoUri = uri;
                        ivBusinessPhoto.setImageURI(uri);
                    } else if (getCurrentFocus() == ivLicense) {
                        licenseUri = uri;
                        ivLicense.setImageURI(uri);
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        role = getIntent().getStringExtra("role"); // "buyer" или "seller"
        String prefilledEmail = getIntent().getStringExtra("prefilled_email");

        // Привязываем все View один раз
        tvTitle = findViewById(R.id.tvTitle);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        etPassConfirm = findViewById(R.id.etPassConfirm);
        etBusinessName = findViewById(R.id.etBusinessName);
        sellerFields = findViewById(R.id.sellerFields);
        ivBusinessPhoto = findViewById(R.id.ivBusinessPhoto);
        ivLicense = findViewById(R.id.ivLicense);
        btnRegister = findViewById(R.id.btnRegister);

        // Предзаполняем email, если пришёл с экрана логина
        if (prefilledEmail != null && !prefilledEmail.isEmpty()) {
            etEmail.setText(prefilledEmail);
        }

        // Настраиваем заголовок и видимость полей в зависимости от роли
        if ("seller".equals(role)) {
            tvTitle.setText("Регистрация поставщика");
            sellerFields.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setText("Регистрация покупателя");
            sellerFields.setVisibility(View.GONE);
        }

        // Клик по фото → выбор изображения
        ivBusinessPhoto.setOnClickListener(v -> {
            v.requestFocus();
            pickMediaLauncher.launch("image/*");
        });

        ivLicense.setOnClickListener(v -> {
            v.requestFocus();
            pickMediaLauncher.launch("*/*"); // фото + PDF
        });

        // Кнопка регистрации
        btnRegister.setOnClickListener(v -> attemptRegister());
    }

    private void attemptRegister() {
        String email = getTextSafe(etEmail);
        String pass = getTextSafe(etPass);
        String passConfirm = getTextSafe(etPassConfirm);

        if (email.isEmpty() || pass.isEmpty() || passConfirm.isEmpty()) {
            Toast.makeText(this, "Заполните все обязательные поля", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Введите корректный email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.length() < 6) {
            Toast.makeText(this, "Пароль должен быть не менее 6 символов", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.equals(passConfirm)) {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            return;
        }

        // Дополнительная валидация для поставщиков
        if ("seller".equals(role)) {
            String businessName = getTextSafe(etBusinessName);
            if (businessName.isEmpty()) {
                Toast.makeText(this, "Введите название заведения", Toast.LENGTH_SHORT).show();
                return;
            }
            if (businessPhotoUri == null) {
                Toast.makeText(this, "Загрузите фото заведения", Toast.LENGTH_SHORT).show();
                return;
            }
            if (licenseUri == null) {
                Toast.makeText(this, "Загрузите лицензию", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Создаём аккаунт в Firebase Auth
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {
                    String uid = authResult.getUser().getUid();

                    Map<String, Object> userData = new HashMap<>();
                    userData.put("email", email);
                    userData.put("role", role);
                    userData.put("created_at", FieldValue.serverTimestamp());

                    if ("seller".equals(role)) {
                        userData.put("business_name", getTextSafe(etBusinessName));
                        userData.put("status", "pending");

                        // ВРЕМЕННО — без реальных URI, чтобы не крашилось
                        userData.put("business_photo_uri", "pending");
                        userData.put("license_uri", "pending");

                        // Позже здесь будет загрузка в Storage и подстановка ссылок
                    }

                    // Сохраняем в Firestore
                    db.collection("users").document(uid)
                            .set(userData)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(RegisterActivity.this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            })
                            .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this,
                                    "Ошибка сохранения профиля: " + e.getMessage(), Toast.LENGTH_LONG).show());
                })
                .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this,
                        "Ошибка регистрации: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private String getTextSafe(TextInputEditText editText) {
        return editText.getText() != null ? editText.getText().toString().trim() : "";
    }
}