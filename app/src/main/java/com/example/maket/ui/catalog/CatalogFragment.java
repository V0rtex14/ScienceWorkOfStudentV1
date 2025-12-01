package com.example.maket.ui.catalog;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.*;

public class CatalogFragment extends Fragment {

    private RecyclerView rvCatalog;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_catalog, container, false);

        rvCatalog = v.findViewById(R.id.rvCatalog);
        rvCatalog.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new ProductAdapter(new ProductAdapter.OnProductActionListener() {
            @Override
            public void onProductClick(Product product) {
                // Открыть экран деталей
                // TODO: навигация к ProductDetailFragment
            }

            @Override
            public void onBookClick(Product product) {
                // TODO: логика бронирования (пока можно Toast)
                // Toast.makeText(requireContext(), "Бронь: " + product.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        rvCatalog.setAdapter(adapter);

        // Пока тестовые данные, потом заменим на данные из SQLite
        adapter.setItems(getDemoProducts());

        return v;
    }

    private List<Product> getDemoProducts() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(1, "Шаурма куриная",
                "Осталась с обеда, забрать до 21:00",
                220, 110, 50, R.drawable.ic_category_food));
        list.add(new Product(2, "Салат Цезарь",
                "Порция 350 г, срок до 20:30",
                180, 90, 50, R.drawable.ic_category_food));
        return list;
    }
}
