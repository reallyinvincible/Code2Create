package com.acmvit.code2create.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.acmvit.code2create.R;
import com.acmvit.code2create.activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment {

    private EditText emailET, passwordET;
    private ProgressBar progressBar;
    private String uid;
    private Button signUp;
    private FirebaseAuth mAuth;
    private String TAG = "ACM ROCKS";
    private TextView tvLogin, tvSentence;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initializeView(view);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    }
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    Log.e("DISMISS KEYBOARD", "" + e.getMessage());
                }
                if (emailET.getText() != null && emailET.getText().length() > 0 && passwordET.getText() != null && passwordET.getText().length() > 0) {

                    if (Patterns.EMAIL_ADDRESS.matcher(emailET.getText().toString()).matches()) {
                        String email = emailET.getText().toString();
                        String password = passwordET.getText().toString();
                        userRegistration(email, password);
                    } else {
                        showErrorSnackbar("Email format is invalid");
                    }

                } else {
                    showErrorSnackbar("Email or password missing");
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.getLoginActivityInterface().switchToLogin();
            }
        });

        return view;
    }


    void showErrorSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorErrorSnackbar);
        snackbar.show();
    }

    private void userRegistration(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        disableUserInteraction();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        enableUserInteraction();
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Snackbar snackbar = Snackbar.make(getView(), "Verification link sent to your email id", Snackbar.LENGTH_SHORT);
                                        snackbar.getView().setBackgroundResource(R.color.colorAccent);
                                        snackbar.show();
                                    } else {
                                        showErrorSnackbar("Error while sending verification link");
                                    }
                                }
                            });
                            Snackbar snackbar = Snackbar.make(getView(), "User Registered Successfully", Snackbar.LENGTH_SHORT);
                            snackbar.getView().setBackgroundResource(R.color.colorAccent);
                            snackbar.addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    LoginActivity.getLoginActivityInterface().switchToLogin();
                                }
                            });
                            snackbar.show();
                        } else {
                            showErrorSnackbar(task.getException().getMessage());
                        }
                    }
                });
    }

    private void disableUserInteraction() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void enableUserInteraction() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }


    private void initializeView(View view) {
        tvSentence = view.findViewById(R.id.tv_sentence);
        tvLogin = view.findViewById(R.id.tv_login);
        signUp = view.findViewById(R.id.btn_sign_up);
        emailET = view.findViewById(R.id.et_email);
        passwordET = view.findViewById(R.id.et_password);
        mAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.progressBar);
    }


}
