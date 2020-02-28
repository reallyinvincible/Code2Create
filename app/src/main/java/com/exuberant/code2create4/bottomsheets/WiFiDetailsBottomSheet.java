package com.exuberant.code2create4.bottomsheets;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exuberant.code2create4.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;

public class WiFiDetailsBottomSheet extends BottomSheetDialogFragment {

    SharedPreferences sharedPreferences;
    TextView wifiCouponTextView;
    ImageView wifiCouponImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_wifi_details, container, false);
        wifiCouponTextView = view.findViewById(R.id.tv_wifi_coupon);
        wifiCouponImageView = view.findViewById(R.id.iv_wifi_coupon);
        sharedPreferences = getContext().getSharedPreferences(getContext().getString(R.string.shared_prefs_name), MODE_PRIVATE);
        String wifiCoupon = sharedPreferences.getString(getContext().getString(R.string.shared_prefs_wifi_coupon), null);
        if (wifiCoupon != null){
            wifiCouponTextView.setText("Your WiFi Coupon is " + wifiCoupon);
            wifiCouponImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_wifi_filled));
        } else {
            wifiCouponTextView.setText("WiFi coupons are only available for External Participants\nYou are suggested to use your credentials.");
            wifiCouponImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_wifi_error));

        }
        return view;
    }
}
