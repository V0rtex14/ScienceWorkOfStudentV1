package com.example.sakta.ui.catalog;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakta.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Адаптер для списка товаров в каталоге.
 * Ожидает layout item_product.xml
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnProductActionListener {
        void onProductClick(Product product);
        void onBookClick(Product product);
    }

    private final List<Product> items = new ArrayList<>();
    private final OnProductActionListener listener;

    public ProductAdapter(OnProductActionListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Product> products) {
        items.clear();
        if (products != null) {
            items.addAll(products);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = items.get(position);

        holder.tvTitle.setText(p.getTitle());
        holder.tvDesc.setText(p.getDescription());
        holder.tvPickup.setText(p.getPickupUntil());
        holder.tvDistance.setText(String.format(Locale.getDefault(), "%.1f км", p.getDistanceKm()));
        holder.tvRating.setText(String.format(Locale.getDefault(), "★ %.1f", p.getRating()));

        // Цены
        holder.tvPriceOld.setText(p.getOldPrice() + " с");
        holder.tvPriceNew.setText(p.getNewPrice() + " с");

        // Пересчёт скидки, если не задана явно
        int discount = p.getDiscountPercent();
        if (discount == 0 && p.getOldPrice() > 0 && p.getNewPrice() > 0) {
            discount = Math.round(
                    (1f - (float) p.getNewPrice() / (float) p.getOldPrice()) * 100f
            );
        }
        if (discount > 0) {
            holder.tvDiscount.setVisibility(View.VISIBLE);
            holder.tvDiscount.setText("-" + discount + "%");
        } else {
            holder.tvDiscount.setVisibility(View.GONE);
        }

        // Зачёркивание старой цены
        holder.tvPriceOld.setPaintFlags(
                holder.tvPriceOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
        );

        // Фото — пока просто по ресурсу (локальный drawable).
        // Потом сюда можно прикрутить Glide/Picasso для URL.
        if (p.getImageResId() != 0) {
            holder.imgPhoto.setImageResource(p.getImageResId());
            holder.imgLogo.setImageResource(p.getImageResId());
        } else {
            holder.imgPhoto.setImageResource(android.R.drawable.ic_menu_report_image);
            holder.imgLogo.setImageResource(R.drawable.ic_store);
        }

        holder.tvOpen.setText(p.isOpenNow() ? "Открыто" : "Закрыто");
        int openColor = androidx.core.content.ContextCompat.getColor(
                holder.tvOpen.getContext(),
                p.isOpenNow() ? R.color.sakta_success : R.color.sakta_text_muted
        );
        holder.tvOpen.setTextColor(openColor);

        // Клик по всей карточке
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(p);
        });

        // Клик по кнопке "Забронировать"
        holder.btnBook.setOnClickListener(v -> {
            if (listener != null) listener.onBookClick(p);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView tvTitle;
        TextView tvDesc;
        TextView tvPickup;
        TextView tvDistance;
        TextView tvRating;
        TextView tvPriceOld;
        TextView tvDiscount;
        TextView tvPriceNew;
        TextView tvCategory;
        TextView tvCompany;
        TextView tvOpen;
        ImageView imgLogo;
        MaterialButton btnBook;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvPickup = itemView.findViewById(R.id.tvPickup);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvPriceOld = itemView.findViewById(R.id.tvPriceOld);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvPriceNew = itemView.findViewById(R.id.tvPriceNew);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvOpen = itemView.findViewById(R.id.tvOpen);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}
