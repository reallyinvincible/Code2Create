package com.exuberant.code2create.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.exuberant.code2create.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FoodCouponsFragment extends Fragment {

    Button btnAudio;
    LottieAnimationView ripple;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_coupons, container, false);

        ripple = view.findViewById(R.id.lav_ripple);

        btnAudio = view.findViewById(R.id.btn_audio);
        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ripple.playAnimation();
            }
        });

        return view;
    }
}
