package com.exuberant.code2create.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exuberant.code2create.R;
import com.exuberant.code2create.adapters.FaqsAdapter;
import com.exuberant.code2create.models.FaqsModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FaqsFragment extends Fragment {

    private
    RecyclerView faqsRecyclerView;
    private
    ArrayList<FaqsModel>faqsList ;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faqs, container, false);

        faqsRecyclerView=view.findViewById(R.id.faqsRecyclerView);
        faqsList=new ArrayList<>();
        faqsList.add(new FaqsModel(R.string.faq_q1,R.string.faq_a1));
        faqsList.add(new FaqsModel(R.string.faq_q2,R.string.faq_a2));
        faqsList.add(new FaqsModel(R.string.faq_q3,R.string.faq_a3));
        faqsList.add(new FaqsModel(R.string.faq_q4,R.string.faq_a4));
        faqsList.add(new FaqsModel(R.string.faq_q5,R.string.faq_a5));
        faqsList.add(new FaqsModel(R.string.faq_q6,R.string.faq_a6));
        faqsList.add(new FaqsModel(R.string.faq_q7,R.string.faq_a7));
        faqsList.add(new FaqsModel(R.string.faq_q8,R.string.faq_a8));
        faqsList.add(new FaqsModel(R.string.faq_q9,R.string.faq_a9));
        faqsList.add(new FaqsModel(R.string.faq_q10,R.string.faq_a10));
        faqsList.add(new FaqsModel(R.string.faq_q11,R.string.faq_a11));

        FaqsAdapter faqsAdapter = new FaqsAdapter(faqsList);
        faqsRecyclerView.setAdapter(faqsAdapter);
        faqsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }
}
