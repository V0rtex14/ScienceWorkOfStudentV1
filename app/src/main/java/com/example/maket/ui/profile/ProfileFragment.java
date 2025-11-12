package com.example.maket.ui.profile;


import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;

import com.example.maket.R;


public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
        return i.inflate(R.layout.fragment_profile, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        v.findViewById(R.id.btnSaveAccount).setOnClickListener(x -> Toast.makeText(requireContext(), "Сохранено (декорация)", Toast.LENGTH_SHORT).show());
        ((TextView) v.findViewById(R.id.statMoney)).setText("Вы спасли: 1 240 ₸");
        ((TextView) v.findViewById(R.id.statGrams)).setText("Сэкономлено еды: 820 г");
    }
}