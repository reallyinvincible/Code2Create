package com.exuberant.code2create.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exuberant.code2create.R;
import com.exuberant.code2create.models.Alert;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertViewHolder> {

    List<Alert> alertList;

    public AlertAdapter(List<Alert> alertList) {
        this.alertList = alertList;
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_alert, parent, false);
        return new AlertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        Alert alert = alertList.get(position);
        holder.alertTitleTextView.setText(alert.getTitle());
        holder.alertMessageTextView.setText(alert.getMessage());
        holder.alertTimeTextView.setText(alert.getTime());
        holder.alertDateTextView.setText(alert.getDate());
    }

    @Override
    public int getItemCount() {
        return alertList.size();
    }

    class AlertViewHolder extends RecyclerView.ViewHolder{

        TextView alertTitleTextView, alertMessageTextView, alertTimeTextView, alertDateTextView;

        AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            alertTitleTextView = itemView.findViewById(R.id.tv_alert_title);
            alertMessageTextView = itemView.findViewById(R.id.tv_alert_message);
            alertTimeTextView = itemView.findViewById(R.id.tv_alert_time);
            alertDateTextView = itemView.findViewById(R.id.tv_alert_date);
        }
    }

}
