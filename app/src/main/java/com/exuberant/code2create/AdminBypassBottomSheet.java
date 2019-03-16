package com.exuberant.code2create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdminBypassBottomSheet extends BottomSheetDialogFragment {

    EditText bypassPassword, bypassString;
    private static final String pass = "syklops";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_admin_bypass, container, false);

        bypassPassword = view.findViewById(R.id.et_admin_password);
        bypassString = view.findViewById(R.id.et_admin_string);
        if (bypassPassword.getText().toString() == pass){

        }


        return view;
    }
}
