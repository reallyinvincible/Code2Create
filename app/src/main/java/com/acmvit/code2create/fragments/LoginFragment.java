package com.acmvit.code2create.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.acmvit.code2create.R;
import com.acmvit.code2create.activities.LoginActivity;
import com.acmvit.code2create.models.Agenda;
import com.acmvit.code2create.models.AgendaModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mUserReference;
    private DatabaseReference mEmailReference;
    public static final int GOOGLE_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private static final String TAG = "GoogleAuth";
    private final static String SHA_SALT = "ACM_Rocks";
    private Integer loginState = 0;
    private Boolean flag;
    private Button loginButton;
    private EditText emailET, passwordET;
    private ProgressBar progressBar;
    private String uid;
    private TextView LoginTV, sentenceTv;
    private Button signInButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ConstraintLayout constraintLayout;

    public LoginFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initializeView(view);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });

        LoginTV.setOnClickListener(v -> {
            LoginActivity.getLoginActivityInterface().switchToSignUp();
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
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
                        progressBar.setVisibility(View.VISIBLE);
                        disableUserInteraction();
                        userLogin(email, password);
                    } else {
                        showErrorSnackbar("Email format in invalid");
                    }
                } else {
                    showErrorSnackbar("Email or password missing");
                }
            }
        });


        return view;
    }

    private void resendEmailSnackbar() {
        Snackbar snackbar = Snackbar.make(getView(), "You do not seem to have verified your email", Snackbar.LENGTH_LONG);
        snackbar.setAction("RESEND LINK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getCurrentUser().reload();
                {
                    mAuth.getCurrentUser().sendEmailVerification();
                    Snackbar snackbar = Snackbar.make(constraintLayout, "Verification email sent", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundResource(R.color.colorAccent);
                    snackbar.show();
                }
            }
        });
        snackbar.getView().setBackgroundResource(R.color.colorErrorSnackbar);
        snackbar.show();
    }


    void showConfirmationSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(constraintLayout, message, Snackbar.LENGTH_SHORT);
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                progressBar.setVisibility(View.VISIBLE);
                disableUserInteraction();
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
                Log.e(TAG, "Google Sign In successful with Account Id" + account);
            } catch (ApiException e) {
                progressBar.setVisibility(View.GONE);
                enableUserInteraction();
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            enableUserInteraction();
                            Log.d(TAG, "signInWithCredential:success");
                            mAuth = FirebaseAuth.getInstance();
                            String email = mAuth.getCurrentUser().getEmail();
                            uid = mAuth.getUid();
                            compareEmail(email);
                        } else {
                            loginButton.setAlpha(1);
                            progressBar.setVisibility(View.GONE);
                            enableUserInteraction();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            showErrorSnackbar("Sign In Error, Check Your Internet Connection or Contact the Admin");
                        }
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        loginButton.setAlpha(1);
                        enableUserInteraction();
                        progressBar.setVisibility(View.GONE);
                        showErrorSnackbar("Sign In Error, Contact the Admin");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginButton.setAlpha(1);
                        enableUserInteraction();
                        progressBar.setVisibility(View.GONE);
                        showErrorSnackbar("Sign In Error, Contact the Admin");
                    }
                });

    }


    private void compareEmail(String email) {
        progressBar.setVisibility(View.VISIBLE);
        disableUserInteraction();
        mEmailReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                flag = true;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String registeredEmail = dataSnapshot1.getValue(String.class);
                        if (registeredEmail.equals(email)) {
                            flag = false;
                            break;
                        }
                    }
                    if (!flag) {
                        mAuth = FirebaseAuth.getInstance();
                        String email = mAuth.getCurrentUser().getEmail();
                        uid = mAuth.getUid();
                        mUserReference.child(uid).setValue(email);
                        progressBar.setVisibility(View.GONE);
                        enableUserInteraction();
                        loginState = 1;
                        editor.putInt("loginState", loginState);
                        editor.apply();
                        progressBar.setVisibility(View.GONE);
                        enableUserInteraction();
                        showConfirmationSnackbar("Sign In Success");
                    } else {
                        progressBar.setVisibility(View.GONE);
                        enableUserInteraction();
                        showErrorSnackbar("User RSVP not received. Contact nearest organiser.");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showErrorSnackbar("Some Error Occured");
            }
        });


    }


    private void userLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser.isEmailVerified()) {
                                compareEmail(email);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                enableUserInteraction();
                                resendEmailSnackbar();
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            enableUserInteraction();
                            showErrorSnackbar("User Not Registered");
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
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
        constraintLayout = view.findViewById(R.id.cl_login);
        loginButton = view.findViewById(R.id.btn_login);
        emailET = view.findViewById(R.id.et_email);
        passwordET = view.findViewById(R.id.et_password);
        LoginTV = view.findViewById(R.id.tv_login);
        sentenceTv = view.findViewById(R.id.tv_sentence);
        mAuth = FirebaseAuth.getInstance();
        signInButton = view.findViewById(R.id.btn_sign_in);
        progressBar = view.findViewById(R.id.progressBar);
        mDatabase = FirebaseDatabase.getInstance();
        mEmailReference = mDatabase.getReference().child("registeredEmails");
        mUserReference = mDatabase.getReference().child("users");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

    }

}
