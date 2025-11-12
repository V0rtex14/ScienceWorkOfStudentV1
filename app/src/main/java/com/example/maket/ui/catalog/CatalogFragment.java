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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
        return i.inflate(R.layout.fragment_catalog, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        RecyclerView rv = v.findViewById(R.id.rvCatalog); // <-- ВАЖНО: тот же id, что в fragment_catalog.xml
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        ProductAdapter ad = new ProductAdapter(demo(), new ProductAdapter.Listener() {
            @Override
            public void onAdd(Product p) {
                Toast.makeText(requireContext(), "Добавлено: " + p.title, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpen(Product p) {
                NavHostFragment.findNavController(CatalogFragment.this)
                        .navigate(R.id.action_catalog_to_detail);
            }
        });
        rv.setAdapter(ad);

        TextInputEditText search = v.findViewById(R.id.etSearch);
        if (search != null) {
            search.setOnFocusChangeListener((vv, has) -> { /* декорация */ });
        }
    }

    private List<Product> demo() {
        return Arrays.asList(
                new Product("Шаурма куриная", 240, R.drawable.shawerma, "Bishkek Shawarma Co."),
                new Product("Самса с говядиной", 120, R.drawable.samsa, "Ош-Базар Bakery"),
                new Product("Салат свежий", 95, R.drawable.salat, "Dastorkon Foods"),
                new Product("Плов по-фергански", 270, R.drawable.plov, "Asia Taste")
        );
    }
}
