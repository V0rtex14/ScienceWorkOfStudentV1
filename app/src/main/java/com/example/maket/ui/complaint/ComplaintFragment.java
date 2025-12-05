package com.example.maket.ui.complaint;


import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;

import com.example.sakta.R;


public class ComplaintFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
        return i.inflate(R.layout.fragment_complaint, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        v.findViewById(R.id.btnSubmit).setOnClickListener(x -> Toast.makeText(requireContext(), "(декорация) Жалоба отправлена", Toast.LENGTH_SHORT).show());
    }
}