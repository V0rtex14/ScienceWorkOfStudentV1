package com.example.sakta.ui.favorites;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import android.widget.LinearLayout;

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
    private LinearLayout emptyState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvOrders = v.findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        emptyState = v.findViewById(R.id.emptyState);

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
        updateEmptyState();

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

    private void updateEmptyState() {
        boolean isEmpty = adapter.getItemCount() == 0;
        rvOrders.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        emptyState.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }
}
