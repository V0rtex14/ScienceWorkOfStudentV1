package com.example.maket.ui.categories;


import android.os.Bundle;
import android.view.*;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.R;

import java.util.*;


public class CategoriesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
        return i.inflate(R.layout.fragment_categories, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        RecyclerView rv = v.findViewById(R.id.rvCats);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(new SimpleListAdapter(Arrays.asList(
                "Сладкое", "Несладкое", "Фастфуд", "Не фастфуд",
                "Брак", "Почти просрочка", "Остаток еды", "Производитель: Sakta Local"
        )));
    }
}