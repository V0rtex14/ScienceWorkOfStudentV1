package com.example.maket.ui.favorites;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class FavoritesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
        RecyclerView rv = new RecyclerView(requireContext());
        rv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Пасхалка + ещё один заказ (декорация)
        List<Order> demo = Arrays.asList(
                new Order("Яблочный штрудель — 1488 ₸ — Hansa Landa Backers"),
                new Order("Пицца Маргарита — 1890 ₸ — Sakta Kitchen")
        );

        rv.setAdapter(new OrderAdapter(demo));
        return rv;
    }
}
