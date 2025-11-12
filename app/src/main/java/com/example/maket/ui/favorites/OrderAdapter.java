package com.example.maket.ui.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maket.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Holder> {
    private final List<Order> orders;

    public OrderAdapter(List<Order> orders) { this.orders = orders; }

    @NonNull @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new Holder(v);
    }

    @Override public void onBindViewHolder(@NonNull Holder h, int pos) {
        Order o = orders.get(pos);
        h.title.setText(o.title);
        h.btnMore.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_favorites_to_qr)
        );
    }

    @Override public int getItemCount() { return orders.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        TextView title; MaterialButton btnMore;
        Holder(View v) {
            super(v);
            title = v.findViewById(R.id.orderTitle); // 👈 нужен такой id в layout
            btnMore = v.findViewById(R.id.btnMore);
        }
    }
}