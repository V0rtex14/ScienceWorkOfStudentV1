package com.example.sakta.ui.favorites;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakta.R;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvOrders;
    private OrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvOrders = v.findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new OrderAdapter(new OrderAdapter.OnOrderActionListener() {
            @Override
            public void onDetailsClick(Order order) {
                // открыть QR + подробности
                // можно через BottomSheet
            }

            @Override
            public void onCancelClick(Order order) {
                // отмена (пока Toast)
                Toast.makeText(requireContext(),
                        "Отменён заказ " + order.getOrderId(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        rvOrders.setAdapter(adapter);

        adapter.setItems(getDemoOrders());

        return v;
    }

    private List<Order> getDemoOrders() {
        List<Order> list = new ArrayList<>();
        list.add(new Order(10421, "Набор выпечки “Утро”",
                "Пекарня Доброе утро", "21:30", Order.STATUS_ACTIVE));

        list.add(new Order(10410, "Салат Цезарь",
                "CAFE BONO", "19:40", Order.STATUS_COMPLETED));

        return list;
    }
}
