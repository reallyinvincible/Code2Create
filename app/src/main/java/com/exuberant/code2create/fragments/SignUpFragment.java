package com.exuberant.code2create.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.exuberant.code2create.R;
import com.exuberant.code2create.activities.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class SignUpFragment extends Fragment {

    private EditText emailET, passwordET;
    private ProgressBar progressBar;
    private String uid;
    private Button signUp;
    private FirebaseAuth mAuth;
    private String TAG="ACM ROCKS";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initializeView(view);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailET.getText() != null && emailET.getText().length() > 0 && passwordET.getText() != null && passwordET.getText().length() > 0) {
                    String email = emailET.getText().toString();
                    String password = passwordET.getText().toString();

                    userRegistration(email, password);

                } else {
                    showErrorSnackbar("Email or password missing");
                }
            }
        });

        return view;
    }

    void showConfirmationSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorAccent);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {

                LoginActivity.getLoginActivityInterface().launchHome();
                getActivity().finish();
            }
        });
        snackbar.show();
    }

    void showErrorSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorErrorSnackbar);
        snackbar.show();
    }

    private void userRegistration(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Snackbar snackbar = Snackbar.make(getView(), "Verification Link Sent to your Email Id", Snackbar.LENGTH_SHORT);
                                        snackbar.getView().setBackgroundResource(R.color.colorAccent);
                                        snackbar.show();
                                    } else {
                                        showErrorSnackbar("Error While Sending Verification Link");
                                    }
                                }
                            });
                            Snackbar snackbar = Snackbar.make(getView(), "User Registered", Snackbar.LENGTH_SHORT);
                            snackbar.getView().setBackgroundResource(R.color.colorAccent);
                            snackbar.addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    LoginActivity.getLoginActivityInterface().switchToLogin();
                                }
                            });
                            snackbar.show();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    private void initializeView(View view) {
        signUp = view.findViewById(R.id.btn_sign_up);
        emailET = view.findViewById(R.id.et_email);
        passwordET = view.findViewById(R.id.et_password);
        mAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.progressBar);
    }


}
