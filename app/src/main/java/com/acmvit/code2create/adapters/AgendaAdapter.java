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

    private List<Agenda> agendaList;



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
        String[] date = agenda.getDate().split("-");
        String displayDate = date[0] + "th " + date[1];
        holder.agendaTime.setText(displayDate + ", " + agenda.getStartTime());
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
        if (UtilsInterface.compareDates(startDate) >= 1 && UtilsInterface.compareDates(endDate) < 0) {
            holder.agendaIndicator.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.agenda_activated_indicator));
            holder.agendaIndicator.setVisibility(View.VISIBLE);
        } else if (UtilsInterface.compareDates(endDate) >= 0){
            holder.agendaIndicator.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.agenda_deactivated_indicator));
            holder.agendaIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.agendaIndicator.setVisibility(View.GONE);
        }
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
