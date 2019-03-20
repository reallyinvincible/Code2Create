package com.exuberant.code2create.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.exuberant.code2create.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SponsorAdpater extends RecyclerView.Adapter<SponsorAdpater.SponsorViewHolder> {

    List<String> sponsorLinkList;

    public SponsorAdpater(List<String> sponsorLinkList) {
        this.sponsorLinkList = sponsorLinkList;
    }

    @NonNull
    @Override
    public SponsorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_sponsor, parent, false);
        return new SponsorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorViewHolder holder, int position) {
        String sponsorLink = sponsorLinkList.get(position);
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(holder.sponsorImageView.getContext());
        circularProgressDrawable.setColorSchemeColors(holder.sponsorImageView.getResources().getColor(R.color.colorAccent));
        circularProgressDrawable.setStrokeWidth(10f);
        circularProgressDrawable.setArrowEnabled(true);
        circularProgressDrawable.setCenterRadius(100f);
        circularProgressDrawable.start();
        Glide.with(holder.sponsorImageView.getContext())
                .load(sponsorLink)
                .placeholder(circularProgressDrawable)
                .into(holder.sponsorImageView);
    }

    @Override
    public int getItemCount() {
        return sponsorLinkList.size();
    }

    class SponsorViewHolder extends RecyclerView.ViewHolder{

        ImageView sponsorImageView;

        public SponsorViewHolder(@NonNull View itemView) {
            super(itemView);
            sponsorImageView = itemView.findViewById(R.id.iv_sponsor_image);
        }
    }

}
