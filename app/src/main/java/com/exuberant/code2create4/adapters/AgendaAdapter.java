package com.exuberant.code2create4.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exuberant.code2create4.R;
import com.exuberant.code2create4.models.Agenda;

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
            agendaIndicator=itemView.findViewById(R.id.iv_agenda_indicator);

        }
    }

}
