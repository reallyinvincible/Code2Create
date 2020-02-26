package com.exuberant.code2create.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.exuberant.code2create.R;
import com.exuberant.code2create.models.FaqsModel;

import java.util.ArrayList;
import java.util.List;

public class FaqsAdapter extends RecyclerView.Adapter<FaqsAdapter.FaqsViewHolder>{

    private List<FaqsModel> list;

    public FaqsAdapter(List<FaqsModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FaqsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FaqsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_faqs,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FaqsViewHolder holder, int position) {
        FaqsModel faqsModel=list.get(position);
        holder.tvQues.setText(faqsModel.getQuestion());
        holder.tvAns.setText(faqsModel.getAnswer());
        changeState(holder.cv, holder.cvContent);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FaqsViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private CardView cvContent;
        private TextView tvQues,tvAns;
        public FaqsViewHolder(@NonNull View itemView) {
            super(itemView);
            cv=itemView.findViewById(R.id.cv);
            cvContent=itemView.findViewById(R.id.cv_content);
            tvQues=itemView.findViewById(R.id.tv_ques);
            tvAns=itemView.findViewById(R.id.tv_ans);
        }
    }

    private void changeState(CardView cv, CardView cvContent) {
        cv.setOnClickListener(view -> {
            if (cvContent.getVisibility() == View.VISIBLE) {
                cvContent.setVisibility(View.GONE);
            } else {
                cvContent.setVisibility(View.VISIBLE);
            }
        });
    }
}
