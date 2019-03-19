package com.exuberant.code2create.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exuberant.code2create.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class FaqsFragment extends Fragment {

    CardView cv1, cvContent1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faqs, container, false);

        cv1 = view.findViewById(R.id.cv1);
        cvContent1 = view.findViewById(R.id.cv_content1);
        cv1.setOnClickListener(view1 -> {
            if (cvContent1.getVisibility() == View.VISIBLE) {
                cvContent1.setVisibility(View.GONE);
            } else {
                cvContent1.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}
