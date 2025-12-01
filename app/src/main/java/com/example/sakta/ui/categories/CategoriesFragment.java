package com.example.sakta.ui.categories;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakta.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle b) {
        return i.inflate(R.layout.fragment_categories, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        RecyclerView rv = v.findViewById(R.id.rvCats);

        // 2 столбца, крупные значки
        rv.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        // аккуратные внешние отступы
        int spacePx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        rv.addItemDecoration(new GridSpacingDecoration(spacePx));
        rv.setClipToPadding(false);

        // данные (иконки можешь заменить на свои, thumb — пока placeholder)
        List<Category> items = new ArrayList<>();
        items.add(new Category("Сладкое", R.drawable.ic_category_food, android.R.drawable.ic_menu_report_image, 12));
        items.add(new Category("Выпечка", R.drawable.ic_category_food, android.R.drawable.ic_menu_report_image, 9));
        items.add(new Category("Овощи",  R.drawable.ic_category_food, android.R.drawable.ic_menu_report_image, 7));
        items.add(new Category("Фрукты", R.drawable.ic_category_food, android.R.drawable.ic_menu_report_image, 8));
        items.add(new Category("Фастфуд",R.drawable.ic_category_food, android.R.drawable.ic_menu_report_image, 5));
        items.add(new Category("Готовые наборы", R.drawable.ic_category_food, android.R.drawable.ic_menu_report_image, 4));

        CategoryAdapterBig adapter = new CategoryAdapterBig(items, c -> {
            // TODO: открыть список товаров категории c.title
            // пока можно тост или навигацию
        });
        rv.setAdapter(adapter);
    }
}
