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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faqs, container, false);

        CardView cv1 = view.findViewById(R.id.cv1);
        CardView cv2 = view.findViewById(R.id.cv2);
        CardView cv3 = view.findViewById(R.id.cv3);
        CardView cv4 = view.findViewById(R.id.cv4);
        CardView cv5 = view.findViewById(R.id.cv5);
        CardView cv6 = view.findViewById(R.id.cv6);
        CardView cv7 = view.findViewById(R.id.cv7);
        CardView cv8 = view.findViewById(R.id.cv8);
        CardView cv9 = view.findViewById(R.id.cv9);
        CardView cv10 = view.findViewById(R.id.cv10);
        CardView cv11 = view.findViewById(R.id.cv11);

        CardView cvContent1 = view.findViewById(R.id.cv_content1);
        CardView cvContent2 = view.findViewById(R.id.cv_content2);
        CardView cvContent3 = view.findViewById(R.id.cv_content3);
        CardView cvContent4 = view.findViewById(R.id.cv_content4);
        CardView cvContent5 = view.findViewById(R.id.cv_content5);
        CardView cvContent6 = view.findViewById(R.id.cv_content6);
        CardView cvContent7 = view.findViewById(R.id.cv_content7);
        CardView cvContent8 = view.findViewById(R.id.cv_content8);
        CardView cvContent9 = view.findViewById(R.id.cv_content9);
        CardView cvContent10 = view.findViewById(R.id.cv_content10);
        CardView cvContent11 = view.findViewById(R.id.cv_content11);

        changeState(cv1, cvContent1);
        changeState(cv2, cvContent2);
        changeState(cv3, cvContent3);
        changeState(cv4, cvContent4);
        changeState(cv5, cvContent5);
        changeState(cv6, cvContent6);
        changeState(cv7, cvContent7);
        changeState(cv8, cvContent8);
        changeState(cv9, cvContent9);
        changeState(cv10, cvContent10);
        changeState(cv11, cvContent11);

        return view;
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
