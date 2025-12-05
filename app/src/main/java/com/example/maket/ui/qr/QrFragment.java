package com.example.maket.ui.qr;


import android.os.Bundle;
import android.view.*;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;

import com.example.maket.R;


public class QrFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
        return i.inflate(R.layout.fragment_qr, c, false);
    }
}