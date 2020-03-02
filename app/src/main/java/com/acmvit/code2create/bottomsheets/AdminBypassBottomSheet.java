package com.acmvit.code2create.bottomsheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.acmvit.code2create.BuildConfig;
import com.acmvit.code2create.R;
import com.acmvit.code2create.fragments.FoodCouponsFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdminBypassBottomSheet extends BottomSheetDialogFragment {

    EditText bypassPassword, bypassString;
    TextView adminSecretButton;
    private static String PASS = "syklops";
    FirebaseRemoteConfig firebaseRemoteConfig;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_admin_bypass, container, false);

        bypassPassword = view.findViewById(R.id.et_admin_password);
        bypassString = view.findViewById(R.id.et_admin_string);
        adminSecretButton = view.findViewById(R.id.tv_admin_bypass_label);
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        firebaseRemoteConfig.setConfigSettings(configSettings);

        Map<String, Object> defaultconfigMap = new HashMap<>();
        defaultconfigMap.put("admin_bypass_password", PASS);
        firebaseRemoteConfig.setDefaults(defaultconfigMap);

        adminSecretButton.setOnClickListener(view1 -> {
            String bypass = bypassPassword.getText().toString();
            fetchConfig(bypass);
        });

        return view;
    }

    void fetchConfig(String bypass){
        firebaseRemoteConfig.fetch(0)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseRemoteConfig.activateFetched();
                checkAdminPassword(bypass);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            firebaseRemoteConfig.activateFetched();
            checkAdminPassword(bypass);
            e.printStackTrace();
            }
        });

    }

    void checkAdminPassword(String bypass){
        PASS = firebaseRemoteConfig.getString("admin_bypass_password");
        if (bypass.equals(PASS)) {
            FoodCouponsFragment.getAdminBypassInterface().bypassScan(bypassString.getText().toString());
        }
    }
}
