package com.example.maket.ui.catalog;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.maket.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

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

        holder.tvPriceOld.setText(p.getOldPrice() + " с");
        holder.tvPriceNew.setText(p.getNewPrice() + " с");

        // Пересчёт скидки
        int discount = p.getDiscountPercent();
        if (discount == 0 && p.getOldPrice() > 0 && p.getNewPrice() > 0) {
            discount = Math.round((1f - (float) p.getNewPrice() / (float) p.getOldPrice()) * 100f);
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

        // Карусель фоток
        int[] images = p.getImageResIds();
        if (images == null || images.length == 0) {
            images = new int[]{ android.R.drawable.ic_menu_report_image };
        }
        holder.vpImages.setAdapter(new ImagePagerAdapter(images));

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

        ViewPager2 vpImages;
        TextView tvTitle;
        TextView tvDesc;
        TextView tvPriceOld;
        TextView tvDiscount;
        TextView tvPriceNew;
        MaterialButton btnBook;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            vpImages = itemView.findViewById(R.id.vpImages);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvPriceOld = itemView.findViewById(R.id.tvPriceOld);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvPriceNew = itemView.findViewById(R.id.tvPriceNew);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }

    // ----- Адаптер для ViewPager2 -----
    public static class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageVH> {

        private final int[] images;

        public ImagePagerAdapter(int[] images) {
            this.images = images;
        }

        @NonNull
        @Override
        public ImageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product_image, parent, false);
            return new ImageVH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageVH holder, int position) {
            holder.ivFood.setImageResource(images[position]);
        }

        @Override
        public int getItemCount() {
            return images != null ? images.length : 0;
        }

        static class ImageVH extends RecyclerView.ViewHolder {
            ImageView ivFood;
            public ImageVH(@NonNull View itemView) {
                super(itemView);
                ivFood = itemView.findViewById(R.id.ivFood);
            }
        }
    }
}
