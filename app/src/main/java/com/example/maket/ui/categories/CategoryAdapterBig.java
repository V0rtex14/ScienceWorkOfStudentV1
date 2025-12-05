package com.example.maket.ui.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakta.R;

import java.util.List;

public class CategoryAdapterBig extends RecyclerView.Adapter<CategoryAdapterBig.VH> {

    public interface OnClick {
        void onCategoryClick(Category c);
    }

    private final List<Category> data;
    private final OnClick onClick;

    public CategoryAdapterBig(List<Category> data, OnClick onClick) {
        this.data = data;
        this.onClick = onClick;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_big, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Category c = data.get(pos);
        h.title.setText(c.title);
        h.icon.setImageResource(c.iconRes);
        h.thumb.setImageResource(c.thumbRes);
        h.count.setText(c.count + " позиций");
        h.itemView.setOnClickListener(v -> {
            if (onClick != null) onClick.onCategoryClick(c);
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        final ImageView icon, thumb;
        final TextView title, count;
        VH(@NonNull View v) {
            super(v);
            icon = v.findViewById(R.id.icon);
            thumb = v.findViewById(R.id.thumb);
            title = v.findViewById(R.id.title);
            count = v.findViewById(R.id.count);
        }
    }
}
