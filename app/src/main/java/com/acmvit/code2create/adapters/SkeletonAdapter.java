package com.acmvit.code2create.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.acmvit.code2create.R;

import androidx.recyclerview.widget.RecyclerView;

public class SkeletonAdapter extends RecyclerView.Adapter<SimpleRcvViewHolder> {


    @Override
    public SimpleRcvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleRcvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_agenda, parent, false));
    }

    @Override
    public void onBindViewHolder(SimpleRcvViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


}
