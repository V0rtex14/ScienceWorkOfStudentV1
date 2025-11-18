package com.example.maket.ui.catalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.R;
import com.example.maket.ui.food.FoodProfileActivity;

import java.util.ArrayList;
import java.util.List;

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
                // Открываем профиль еды
                Intent i = new Intent(requireContext(), FoodProfileActivity.class);
                i.putExtra(FoodProfileActivity.EXTRA_PRODUCT, product);
                startActivity(i);
            }

            @Override
            public void onBookClick(Product product) {
                // Здесь позже будет логика брони / создания заказа
                // Пока можно оставить декорацию или вообще пусто
                // Toast.makeText(requireContext(), "Бронь: " + product.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        rvCatalog.setAdapter(adapter);

        // Пока демо-данные, дальше заменишь на SQLite
        adapter.setItems(getDemoProducts());

        return v;
    }

    private List<Product> getDemoProducts() {
        List<Product> list = new ArrayList<>();

        // Вариант с несколькими фотками (карусель)
        list.add(new Product(
                1,
                "Шаурма куриная",
                "Осталась с обеда, забрать до 21:00",
                220,
                110,
                50,
                new int[]{
                        R.drawable.shawerma,      // первая фотка
                        R.drawable.plov  // вторая фотка (создай любой другой drawable)
                }
        ));

        // Если пока нет второй фотки – можешь использовать старый конструктор с одной картинкой:
        // new Product(2, "Салат Цезарь", "...", 180, 90, 50, R.drawable.salat);

        list.add(new Product(
                2,
                "Салат Цезарь",
                "Порция 350 г, срок до 20:30",
                180,
                90,
                50,
                new int[]{
                        R.drawable.salat,
                        R.drawable.samsa   // при желании вторая фотка
                }
        ));

        return list;
    }
}
