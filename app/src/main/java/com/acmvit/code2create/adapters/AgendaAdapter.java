package com.acmvit.code2create.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acmvit.code2create.R;
import com.acmvit.code2create.UtilsInterface;
import com.acmvit.code2create.models.Agenda;
import com.airbnb.lottie.utils.Utils;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.AgendaAdapterViewHolder> {

    List<Agenda> agendaList;

    public AgendaAdapter(List<Agenda> agendaList) {
        this.agendaList = agendaList;
    }

    @NonNull
    @Override
    public AgendaAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_agenda, parent, false);
        return new AgendaAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaAdapterViewHolder holder, int position) {
        Agenda agenda = agendaList.get(position);
        holder.agendaTitle.setText(agenda.getAgendaTitle());
        holder.agendaTime.setText(agenda.getStartTime());
        switch (agenda.getType()) {
            case "reg":
                holder.agendaImageView.setImageDrawable(holder.agendaImageView
                        .getContext().getResources().getDrawable(R.drawable.ic_regs));
                break;

            case "event":
                holder.agendaImageView.setImageDrawable(holder.agendaImageView
                        .getContext().getResources().getDrawable(R.drawable.ic_event));
                break;

            case "food":
                holder.agendaImageView.setImageDrawable(holder.agendaImageView
                        .getContext().getResources().getDrawable(R.drawable.ic_food));
                break;

            case "talk":
                holder.agendaImageView.setImageDrawable(holder.agendaImageView
                        .getContext().getResources().getDrawable(R.drawable.ic_talk));
                break;

            default:
                holder.agendaImageView.setImageDrawable(holder.agendaImageView
                        .getContext().getResources().getDrawable(R.drawable.ic_default));
        }

        Date startDate = UtilsInterface.getDateObject(agenda.getDate(), agenda.getStartTime());
        Date endDate = UtilsInterface.getDateObject(agenda.getDate(), agenda.getEndTime());
        int var1 = UtilsInterface.compareDates(startDate);
        int var2 = UtilsInterface.compareDates(endDate);
        if (var1 >= 1 && var2 < 1) {
            holder.agendaIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.agendaIndicator.setVisibility(View.GONE);
        }
        Log.d("TIME", startDate.toString());
        Log.d("TIME1", endDate.toString());
        Log.d("VAR1", String.valueOf(var1));
    }

    @Override
    public int getItemCount() {
        return agendaList.size();
    }

    class AgendaAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView agendaImageView;
        TextView agendaTitle;
        TextView agendaTime;
        ImageView agendaIndicator;

        public AgendaAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            agendaImageView = itemView.findViewById(R.id.iv_agenda_icon);
            agendaTitle = itemView.findViewById(R.id.tv_agenda_title);
            agendaTime = itemView.findViewById(R.id.tv_agenda_time);
            agendaIndicator = itemView.findViewById(R.id.iv_agenda_indicator);

        }

    }
}
