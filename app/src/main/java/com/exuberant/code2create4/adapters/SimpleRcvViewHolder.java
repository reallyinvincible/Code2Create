package com.exuberant.code2create4.adapters;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SimpleRcvViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> views = new SparseArray<>();

    public SimpleRcvViewHolder(View itemView) {
        super(itemView);
    }
}
