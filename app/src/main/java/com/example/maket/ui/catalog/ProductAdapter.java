package com.example.maket.ui.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {
    public interface Listener {
        void onAdd(Product p);
        void onOpen(Product p);
    }

    private final List<Product> items;
    private final Listener cb;

    public ProductAdapter(List<Product> items, Listener cb) {
        this.items = items;
        this.cb = cb;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Product p = items.get(pos);

        h.img.setImageResource(p.imageRes);
        h.title.setText(p.title);
        h.price.setText(p.price + " сом");

        if (p.maker != null && !p.maker.isEmpty()) {
            h.maker.setText(p.maker);
            h.maker.setVisibility(View.VISIBLE);
        } else {
            h.maker.setVisibility(View.GONE);
        }

        h.itemView.setOnClickListener(v -> cb.onOpen(p));
        h.btnAdd.setOnClickListener(v -> cb.onAdd(p));
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, maker, price;
        MaterialButton btnAdd;

        VH(View v) {
            super(v);
            img = v.findViewById(R.id.img);
            title = v.findViewById(R.id.title);
            maker = v.findViewById(R.id.maker);
            price = v.findViewById(R.id.price);
            btnAdd = v.findViewById(R.id.btnAdd);
        }
    }
}
