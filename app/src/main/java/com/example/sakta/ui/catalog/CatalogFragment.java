package com.example.sakta.ui.catalog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakta.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CatalogFragment extends Fragment {

    private RecyclerView rvCatalog;
    private ProductAdapter adapter;
    private final List<Product> allProducts = new ArrayList<>();
    private TextInputEditText etSearch;
    private Chip chipFast;
    private Chip chipToday;
    private Chip chipDiscount;
    private MaterialButton btnSort;
    private SortOption currentSort = SortOption.PRICE_ASC;

    private enum SortOption {
        PRICE_ASC,
        PRICE_DESC,
        DISCOUNT_DESC,
        DISTANCE_ASC
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_catalog, container, false);

        rvCatalog = v.findViewById(R.id.rvCatalog);
        rvCatalog.setLayoutManager(new LinearLayoutManager(requireContext()));

        etSearch = v.findViewById(R.id.etSearch);
        chipFast = v.findViewById(R.id.chipFast);
        chipToday = v.findViewById(R.id.chipToday);
        chipDiscount = v.findViewById(R.id.chipDiscount);
        btnSort = v.findViewById(R.id.btnSort);

        adapter = new ProductAdapter(new ProductAdapter.OnProductActionListener() {
            @Override
            public void onProductClick(Product product) {
                Bundle args = new Bundle();
                args.putString("title", product.getTitle());
                args.putString("description", product.getDescription());
                args.putInt("oldPrice", product.getOldPrice());
                args.putInt("newPrice", product.getNewPrice());
                args.putInt("discount", product.getDiscountPercent());
                args.putInt("imageRes", product.getImageResId());
                args.putString("pickup", product.getPickupUntil());
                args.putDouble("distance", product.getDistanceKm());
                args.putFloat("rating", product.getRating());
                NavHostFragment.findNavController(CatalogFragment.this)
                        .navigate(R.id.action_catalog_to_detail, args);
            }

            @Override
            public void onBookClick(Product product) {
                // Здесь можно вызвать реальный запрос бронирования.
            }
        });

        rvCatalog.setAdapter(adapter);

        setupFilters();
        seedProducts();
        applyFilters();

        return v;
    }

    private void setupFilters() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        Chip.OnCheckedChangeListener chipListener = (buttonView, isChecked) -> applyFilters();
        chipFast.setOnCheckedChangeListener(chipListener);
        chipToday.setOnCheckedChangeListener(chipListener);
        chipDiscount.setOnCheckedChangeListener(chipListener);

        btnSort.setOnClickListener(this::showSortMenu);
    }

    private void showSortMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), anchor);
        popupMenu.inflate(R.menu.menu_catalog_sort);
        popupMenu.setOnMenuItemClickListener(this::onSortItemSelected);
        popupMenu.show();
    }

    private boolean onSortItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_price_asc) currentSort = SortOption.PRICE_ASC;
        else if (id == R.id.sort_price_desc) currentSort = SortOption.PRICE_DESC;
        else if (id == R.id.sort_discount) currentSort = SortOption.DISCOUNT_DESC;
        else if (id == R.id.sort_distance) currentSort = SortOption.DISTANCE_ASC;
        applyFilters();
        return true;
    }

    private void seedProducts() {
        allProducts.clear();
        allProducts.add(new Product(1, "Шаурма куриная",
                "Осталась с обеда, забрать до 21:00",
                220, 110, 50, R.drawable.ic_category_food,
                1.2, "Забрать до 20:45", 4.6f, true));
        allProducts.add(new Product(2, "Салат Цезарь",
                "Порция 350 г, срок до 20:30",
                180, 90, 50, R.drawable.ic_category_food,
                2.8, "До 19:30", 4.4f, true));
        allProducts.add(new Product(3, "Сет выпечки",
                "6 шт. круассанов, осталось после завтрака",
                320, 160, 50, R.drawable.ic_category_food,
                4.3, "До 18:00", 4.8f, false));
    }

    private void applyFilters() {
        String query = etSearch.getText() != null ? etSearch.getText().toString().toLowerCase(Locale.getDefault()) : "";
        boolean onlyNear = chipFast.isChecked();
        boolean onlyToday = chipToday.isChecked();
        boolean onlyDiscounted = chipDiscount.isChecked();

        List<Product> filtered = new ArrayList<>();
        for (Product p : allProducts) {
            if (!query.isEmpty() &&
                    !(p.getTitle().toLowerCase(Locale.getDefault()).contains(query)
                            || p.getDescription().toLowerCase(Locale.getDefault()).contains(query))) {
                continue;
            }
            if (onlyNear && p.getDistanceKm() > 3.0) continue;
            if (onlyToday && !p.isAvailableToday()) continue;
            if (onlyDiscounted && p.getDiscountPercent() < 40) continue;
            filtered.add(p);
        }

        sortList(filtered);
        adapter.setItems(filtered);
    }

    private void sortList(List<Product> data) {
        Comparator<Product> comparator;
        switch (currentSort) {
            case PRICE_DESC:
                comparator = (a, b) -> Integer.compare(b.getNewPrice(), a.getNewPrice());
                break;
            case DISCOUNT_DESC:
                comparator = (a, b) -> Integer.compare(b.getDiscountPercent(), a.getDiscountPercent());
                break;
            case DISTANCE_ASC:
                comparator = (a, b) -> Double.compare(a.getDistanceKm(), b.getDistanceKm());
                break;
            case PRICE_ASC:
            default:
                comparator = (a, b) -> Integer.compare(a.getNewPrice(), b.getNewPrice());
                break;
        }
        Collections.sort(data, comparator);
    }
}
