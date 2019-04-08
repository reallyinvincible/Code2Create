package com.exuberant.code2create.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exuberant.code2create.R;
import com.exuberant.code2create.models.Agenda;
import com.exuberant.code2create.models.AgendaModel;
import com.exuberant.code2create.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.exuberant.code2create.UtilsInterface.get_SHA_512_password;
import static com.exuberant.code2create.UtilsInterface.transformString;


public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    FirebaseDatabase mDatabase;
    DatabaseReference mUserReference;
    DatabaseReference mAgendaReference;
    DatabaseReference mScannablesReference;
    DatabaseReference mAttendanceReference;

    private final static String SHA_SALT = "ACM_Rocks";

    Button loginButton;
    EditText emailET, passwordET;

    List<Agenda> agendaList;
    AgendaModel model;

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeView();

        sharedPreferences = getSharedPreferences(getString(R.string.shared_prefs_name), MODE_PRIVATE);
        if (sharedPreferences.contains(getString(R.string.shared_prefs_email))) {
            launchHome();
        }
        loginButton.setOnClickListener(view -> {
            if (emailET.getText() != null && emailET.getText().length() > 0 && passwordET.getText() != null && passwordET.getText().length() > 0) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                checkUser(email, password);
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
        mDatabase = FirebaseDatabase.getInstance();
        mAgendaReference = mDatabase.getReference().child("agendas");
        mUserReference = mDatabase.getReference().child("users");
        mScannablesReference = mDatabase.getReference().child("scannables");
        mAttendanceReference = mScannablesReference.child("attendance");
    }

    void checkUser(String email, String password) {
        String transformedEmail = transformString(email);
        mUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(transformedEmail)) {
                    try {
                        User user = dataSnapshot.child(transformedEmail).getValue(User.class);
                        if (password.equals(user.getPassword())) {
                            saveUserInfo(email, user.getWifiCoupon());
                            showConfirmationSnackbar("Logging you in!");
                        } else {
                            showErrorSnackbar("Incorrect Password!");
                        }
                    } catch (Exception e){
                        showErrorSnackbar("Data cannot be processed. Contact your nearest volunteer.");
                    }
                } else {
                    showErrorSnackbar("User not registered!!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
}
