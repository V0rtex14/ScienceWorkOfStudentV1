package com.example.sakta.ui.categories;


import android.view.*;
import android.widget.TextView;

import androidx.annotation.*;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sakta.R;

import java.util.*;


public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.VH> {
    private final List<String> items;

    public SimpleListAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_list_row, p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        h.title.setText(items.get(pos));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView title;

        VH(View v) {
            super(v);
            title = v.findViewById(R.id.rowTitle);
        }
    }
}