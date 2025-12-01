package com.example.sakta.ui.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakta.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public interface OnOrderActionListener {
        void onDetailsClick(Order order);   // QR + подробности
        void onCancelClick(Order order);    // отмена заказа
    }

    private final List<Order> items = new ArrayList<>();
    private final OnOrderActionListener listener;

    public OrderAdapter(OnOrderActionListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Order> orders) {
        items.clear();
        if (orders != null) items.addAll(orders);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(
            @NonNull OrderViewHolder holder, int position
    ) {
        Order order = items.get(position);

        holder.tvOrderNumber.setText("Заказ №" + order.getOrderId());
        holder.tvProductName.setText(order.getProductName());
        holder.tvPartner.setText(order.getPartnerName());
        holder.tvPickupTime.setText("Забрать до " + order.getPickupUntil());

        // Статус
        holder.tvStatus.setText(order.getStatusText());

        // Цвет статуса
        switch (order.getStatus()) {
            case Order.STATUS_ACTIVE:
                holder.tvStatus.setTextColor(
                        holder.itemView.getResources().getColor(R.color.sakta_primary)
                );
                break;

            case Order.STATUS_CANCELLED:
                holder.tvStatus.setTextColor(0xFFFF3B30); // красный
                break;

            case Order.STATUS_EXPIRED:
                holder.tvStatus.setTextColor(0xFFA0A0A0); // серый
                break;

            case Order.STATUS_COMPLETED:
                holder.tvStatus.setTextColor(0xFF4CAF50); // зелёный
                break;
        }

        // Клик «Подробнее» — покажем QR
        holder.btnDetails.setOnClickListener(v -> {
            if (listener != null) listener.onDetailsClick(order);
        });

        // Клик «Отменить заказ»
        holder.btnCancel.setOnClickListener(v -> {
            if (listener != null) listener.onCancelClick(order);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvStatus, tvOrderNumber, tvProductName, tvPartner, tvPickupTime;
        MaterialButton btnDetails, btnCancel;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvOrderNumber = itemView.findViewById(R.id.tvOrderNumber);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPartner = itemView.findViewById(R.id.tvPartner);
            tvPickupTime = itemView.findViewById(R.id.tvPickupTime);

            btnDetails = itemView.findViewById(R.id.btnDetails);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
