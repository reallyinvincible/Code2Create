package com.exuberant.code2create.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exuberant.code2create.R;
import com.exuberant.code2create.models.Agenda;
import com.exuberant.code2create.models.AgendaModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.exuberant.code2create.UtilsInterface.transformString;


public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    FirebaseDatabase mDatabase;
    DatabaseReference mUserReference;
    DatabaseReference mAgendaReference;
    DatabaseReference mEmailReference;
    DatabaseReference mScannablesReference;
    DatabaseReference mAttendanceReference;

    private static final int GOOGLE_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private static final String TAG = "GoogleAuth";
    private final static String SHA_SALT = "ACM_Rocks";

    Button loginButton;
    EditText emailET, passwordET;
    CardView cardView;
    ProgressBar progressBar;
    String uid;
    TextView tvLogin, tvSentence;
    LinearLayout linerarLayout1;
    Button signInButton;
    List<Agenda> agendaList;
    AgendaModel model;

    ConstraintLayout constraintLayout;

    public LoginActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeView();

        try {
            if (mAuth.getCurrentUser() != null) {
                launchHome();
            }
        } catch (Exception e) {
            Log.e(TAG, "Crash");

        }

        loginButton.setOnClickListener(view -> {
            signIn();

        });

        tvLogin.setOnClickListener(v -> {
            tvClicked();
        });


        signInButton.setOnClickListener(view -> {
            if (emailET.getText() != null && emailET.getText().length() > 0 && passwordET.getText() != null && passwordET.getText().length() > 0) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                if (signInButton.getText().toString().equals("Register")) {

                    userRegistration(email, password);

                } else if (signInButton.getText().toString().equals("Login")) {

                    userLogin(email, password);
                }

            } else {
                showErrorSnackbar("Email or password missing");
            }
        });


    }


    void initializeView() {
        constraintLayout = findViewById(R.id.cl_login);
        loginButton = findViewById(R.id.btn_login);
        emailET = findViewById(R.id.et_email);
        passwordET = findViewById(R.id.et_password);
        tvLogin = findViewById(R.id.tv_login);
        tvSentence = findViewById(R.id.tv_sentence);
        cardView = findViewById(R.id.cardView);
        mAuth = FirebaseAuth.getInstance();
        signInButton = findViewById(R.id.btn_sign_in);
        progressBar = findViewById(R.id.progressBar);
        mDatabase = FirebaseDatabase.getInstance();
        mEmailReference = mDatabase.getReference().child("registeredEmails");
        mAgendaReference = mDatabase.getReference().child("agendas");
        mUserReference = mDatabase.getReference().child("users");
        mScannablesReference = mDatabase.getReference().child("scannables");
        mAttendanceReference = mScannablesReference.child("attendance");
        linerarLayout1 = findViewById(R.id.linearLayout1);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    void saveUserInfo(String email, String wifiCoupon) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.shared_prefs_email), email);
        editor.putString(getString(R.string.shared_prefs_transformed_email), transformString(email));
        editor.putString(getString(R.string.shared_prefs_wifi_coupon), wifiCoupon);
        editor.apply();
    }

    void launchHome() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finishAfterTransition();
    }

    void showConfirmationSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(constraintLayout, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorAccent);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                launchHome();
                finish();
            }
        });
        snackbar.show();
    }

    void showErrorSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(constraintLayout, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorErrorSnackbar);
        snackbar.show();
    }

    private void signIn() {
        loginButton.setAlpha((float) 0.5);
        progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
                Log.e(TAG, "Google Sign In successful with Account Id" + account);
            } catch (ApiException e) {
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
                            progressBar.setVisibility(View.GONE);
                            Log.d(TAG, "signInWithCredential:success");
                            mAuth = FirebaseAuth.getInstance();
                            String email = mAuth.getCurrentUser().getEmail();
                            uid = mAuth.getUid();
                            mUserReference.child(uid).setValue(email);
                            showConfirmationSnackbar("Sign In Success");
                        } else {
                            loginButton.setAlpha(1);
                            progressBar.setVisibility(View.GONE);
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            showErrorSnackbar("Sign In Error, Contact the Admin");
                        }
                    }
                });

    }


    private void compareEmail(String email) {
        mEmailReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String registeredEmail = dataSnapshot1.getValue(String.class);
                        if (registeredEmail.equals(email)) {
                            uid = mAuth.getUid();
                            mUserReference.child(uid).setValue(email);
                            showConfirmationSnackbar("Sign In Success");
                        } else {
                            showErrorSnackbar("User does not exist in Database");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void tvClicked() {
        if (tvLogin.getText().toString().equals("Click Here")) {
            tvLogin.setText("Login");
            tvSentence.setText("Already Registered, ");
            linerarLayout1.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
        } else if (tvLogin.getText().toString().equals("Login")) {
            signInButton.setText("Login");
            tvSentence.setVisibility(View.INVISIBLE);
            tvLogin.setVisibility(View.INVISIBLE);
        }


    }

    private void userRegistration(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            signInButton.setText("Login");
                            passwordET.setText(null);
                            emailET.setText(null);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    private void userLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            compareEmail(email);
                        } else {
                            showErrorSnackbar("User Not Registered");
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }



}

