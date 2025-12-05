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
    private ExtendedFloatingActionButton fabFilter;
    private ExtendedFloatingActionButton fabSort;
    private SortOption currentSort = SortOption.PRICE_ASC;
    private String selectedCategory = "";
    private String selectedCompany = "";
    private boolean onlyOpen;

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
        fabFilter = v.findViewById(R.id.fabFilter);
        fabSort = v.findViewById(R.id.fabSort);

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
        fabSort.setOnClickListener(this::showSortMenu);
        fabFilter.setOnClickListener(v -> showFilterSheet());
    }

    private void showFilterSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.ThemeOverlay_Material3_BottomSheetDialog);
        View sheet = LayoutInflater.from(requireContext()).inflate(R.layout.sheet_filters, null, false);
        ChipGroup categoryGroup = sheet.findViewById(R.id.groupCategories);
        ChipGroup companyGroup = sheet.findViewById(R.id.groupCompanies);
        SwitchMaterial switchOpen = sheet.findViewById(R.id.switchOpen);

        // Restore previous state
        switchOpen.setChecked(onlyOpen);
        selectChipByTag(categoryGroup, selectedCategory);
        selectChipByTag(companyGroup, selectedCompany);

        sheet.findViewById(R.id.btnClose).setOnClickListener(v -> dialog.dismiss());
        sheet.findViewById(R.id.btnReset).setOnClickListener(v -> {
            selectedCategory = "";
            selectedCompany = "";
            onlyOpen = false;
            categoryGroup.clearCheck();
            companyGroup.clearCheck();
            switchOpen.setChecked(false);
            chipFast.setChecked(false);
            chipToday.setChecked(false);
            chipDiscount.setChecked(false);
            applyFilters();
            dialog.dismiss();
        });

        sheet.findViewById(R.id.btnApplyFilters).setOnClickListener(v -> {
            selectedCategory = getCheckedTag(categoryGroup);
            selectedCompany = getCheckedTag(companyGroup);
            onlyOpen = switchOpen.isChecked();
            applyFilters();
            dialog.dismiss();
        });

        dialog.setContentView(sheet);
        dialog.show();
    }

    private void selectChipByTag(ChipGroup group, String tag) {
        if (tag == null || tag.isEmpty()) return;
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            Object childTag = child.getTag();
            if (childTag != null && childTag.toString().equalsIgnoreCase(tag)) {
                group.check(child.getId());
                break;
            }
        }
    }

    private String getCheckedTag(ChipGroup group) {
        int checkedId = group.getCheckedChipId();
        if (checkedId != View.NO_ID) {
            View chip = group.findViewById(checkedId);
            if (chip != null && chip.getTag() != null) {
                return chip.getTag().toString();
            }
        }
        return "";
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
                1.2, "Забрать до 20:45", 4.6f, true,
                "Супермаркет", "Sakta Market", true));
        allProducts.add(new Product(2, "Салат Цезарь",
                "Порция 350 г, срок до 20:30",
                180, 90, 50, R.drawable.ic_category_food,
                2.8, "До 19:30", 4.4f, true,
                "Кафе", "CAFE BONO", true));
        allProducts.add(new Product(3, "Сет выпечки",
                "6 шт. круассанов, осталось после завтрака",
                320, 160, 50, R.drawable.ic_category_food,
                4.3, "До 18:00", 4.8f, false,
                "Дистрибьютор", "Пекарня Доброе утро", false));
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
            if (onlyOpen && !p.isOpenNow()) continue;
            if (!selectedCategory.isEmpty() && !p.getCategory().equalsIgnoreCase(selectedCategory)) continue;
            if (!selectedCompany.isEmpty() && !p.getCompany().equalsIgnoreCase(selectedCompany)) continue;
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
