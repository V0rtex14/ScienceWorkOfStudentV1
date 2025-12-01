package com.example.sakta.ui.categories;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.Px;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    public GridSpacingDecoration(@Px int spacePx) {
        this.space = spacePx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(space, space, space, space);
    }
}
