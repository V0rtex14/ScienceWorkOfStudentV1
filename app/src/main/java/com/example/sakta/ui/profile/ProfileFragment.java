package com.example.sakta.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.sakta.LoginActivity;
import com.example.sakta.R;
import com.example.sakta.core.ThemeManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private TextView tvUserEmail;
    private TextView statMoney;
    private TextView statGrams;

    private TextView itemHistory;
    private TextView itemComplaints;
    private TextView itemSupport;

    private MaterialButton btnEditAccount;
    private MaterialButton btnEcoNavigator;
    private MaterialButton btnKyrgyzSpeak;
    private MaterialButton btnLogout;

    // Имя преференсов и ключи (на будущее для сессии)
    public static final String PREFS_NAME = "sakta_prefs";
    public static final String KEY_USER_EMAIL = "user_email";
    private MaterialSwitch switchTheme;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        bindViews(v);
        setupUserInfo();
        setupStats();
        setupClicks();

        return v;
    }

    private void bindViews(View v) {
        tvUserEmail = v.findViewById(R.id.tvUserEmail);
        statMoney = v.findViewById(R.id.statMoney);
        statGrams = v.findViewById(R.id.statGrams);

        itemHistory = v.findViewById(R.id.itemHistory);
        itemComplaints = v.findViewById(R.id.itemComplaints);
        itemSupport = v.findViewById(R.id.itemSupport);

        btnEditAccount = v.findViewById(R.id.btnEditAccount);
        btnEcoNavigator = v.findViewById(R.id.btnEcoNavigator);
        btnKyrgyzSpeak = v.findViewById(R.id.btnKyrgyzSpeak);
        btnLogout = v.findViewById(R.id.btnLogout);
        switchTheme = v.findViewById(R.id.switchTheme);
    }

    private void setupUserInfo() {
        String email = "user@sakta.kg";
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String firebaseEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            if (firebaseEmail != null && !firebaseEmail.isEmpty()) {
                email = firebaseEmail;
            }
        } else {
            email = requireContext()
                    .getSharedPreferences(PREFS_NAME, 0)
                    .getString(KEY_USER_EMAIL, email);
        }
        tvUserEmail.setText(email);
    }

    private void setupStats() {
        // Пока заглушки, потом можно подгружать из SQLite
        statMoney.setText("0 с");
        statGrams.setText("0 г");
    }

    private void setupClicks() {

        btnEditAccount.setOnClickListener(v -> {
            // Здесь можно открыть отдельный экран/диалог для изменения email/пароля
            Toast.makeText(requireContext(),
                    "Редактирование аккаунта (пока заглушка)",
                    Toast.LENGTH_SHORT).show();
        });

        itemHistory.setOnClickListener(v -> {
            // Тут логика перехода в историю заказов
            // Можно или переключить таб "История" во вкладке Любимые,
            // или открыть отдельный экран
            Toast.makeText(requireContext(),
                    "История заказов (пока заглушка)",
                    Toast.LENGTH_SHORT).show();
        });

        itemComplaints.setOnClickListener(v -> {
            // Переход в экран жалоб (если есть ComplaintFragment)
            Toast.makeText(requireContext(),
                    "Мои жалобы (пока заглушка)",
                    Toast.LENGTH_SHORT).show();
        });

        itemSupport.setOnClickListener(v -> {
            // Можно открыть email-клиент
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:support@sakta.kg"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Поддержка Sakta");
            try {
                startActivity(Intent.createChooser(intent, "Выбрать почтовый клиент"));
            } catch (Exception e) {
                Toast.makeText(requireContext(),
                        "Не найдено приложение для отправки почты",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnEcoNavigator.setOnClickListener(v -> {
            // Открыть проект друга
            openUrl("https://example.com/eco-navigator");
        });

        btnKyrgyzSpeak.setOnClickListener(v -> {
            // Открыть второй проект
            openUrl("https://example.com/kyrgyzspeak");
        });

        int savedMode = ThemeManager.getSavedMode(requireContext());
        switchTheme.setChecked(savedMode == AppCompatDelegate.MODE_NIGHT_YES);
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int mode = isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
            ThemeManager.switchTo(requireContext(), mode);
        });

        btnLogout.setOnClickListener(v -> {
            // Здесь можно подчистить сессию / SharedPreferences
            clearSession();
            FirebaseAuth.getInstance().signOut();

            // И вернуть пользователя на экран логина
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }

    private void openUrl(String url) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(requireContext(),
                    "Не удалось открыть ссылку",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void clearSession() {
        requireContext()
                .getSharedPreferences(PREFS_NAME, 0)
                .edit()
                .clear()
                .apply();
    }
}
