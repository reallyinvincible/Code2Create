package com.exuberant.code2create.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.exuberant.code2create.R;
import com.exuberant.code2create.models.Agenda;
import com.exuberant.code2create.models.AgendaModel;
import com.exuberant.code2create.models.CouponsUser;
import com.exuberant.code2create.models.Scannable;
import com.exuberant.code2create.models.ScannableModel;
import com.exuberant.code2create.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
                String securedPass = get_SHA_512_password(password, SHA_SALT);
                checkUser(email, securedPass);
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
                    User user = dataSnapshot.child(transformedEmail).getValue(User.class);

                    if (password.equals(user.getPassword())) {
                        saveUserInfo(email, user.getWifiCoupon());
                        showConfirmationSnackbar("Logging you in!");
                    } else {
                        showErrorSnackbar("Incorrect Password!");
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

    void addDummyValue(){
//        Agenda model1 = new Agenda("Registration", "6:00 PM", "8:00 PM", "22-MAR-2019", "reg");
//        Agenda model2 = new Agenda("Opening Ceremony", "8:00 PM", "9:00 PM", "22-MAR-2019", "talk");
//        Agenda model3 = new Agenda("Hack Starts", "9:00 PM", "", "22-MAR-2019", "event");
//        Agenda model4 = new Agenda("Dinner", "10:30 PM", "11:30 PM", "22-MAR-2019", "food");
//        Agenda model5 = new Agenda("Intruder", "11:30 PM", "11:59 PM", "22-MAR-2019", "event");
//        Agenda model6 = new Agenda("Night Snacks", "1:30 AM", "2:00 AM", "23-MAR-2019", "food");
//        agendaList = new ArrayList<>();
//        agendaList.add(model1);
//        agendaList.add(model2);
//        agendaList.add(model3);
//        agendaList.add(model4);
//        agendaList.add(model5);
//        agendaList.add(model6);
//        model = new AgendaModel(agendaList);
//        mAgendaReference.setValue(model);


//        User user1 = new User("ssindher11@gmail.com", get_SHA_512_password("qwert", SHA_SALT), false, true, "bla78y");
//        User user2 = new User("harsh.jain@gmail.com", get_SHA_512_password("ytrewq", SHA_SALT), true, false, "mudai897");
//        User user3 = new User("yash@gmail.com", get_SHA_512_password("qwertyuiop", SHA_SALT), false, true, "nmudwdu7");
//        mUserReference.child(transformString(user1.getEmail())).setValue(user1);
//        mUserReference.child(transformString(user2.getEmail())).setValue(user2);
//        mUserReference.child(transformString(user3.getEmail())).setValue(user3);

        Scannable scannable1 = new Scannable("Registration", "r1", "registration1", "06:00 PM", "08:00 PM", "reg", "22-MAR-2019");
        Scannable scannable2 = new Scannable("Dinner", "d1", "dinner1", "10:30 PM", "11:30 PM", "food", "22-MAR-2019");
        Scannable scannable3 = new Scannable("Snacks", "s1", "snacks1", "01:30 AM", "02:30 AM", "food", "23-MAR-2019");
        Scannable scannable4 = new Scannable("Lunch", "l1", "lunch1", "01:30 PM", "02:30 PM", "food", "23-MAR-2019");
        Scannable scannable5 = new Scannable("Snacks", "s2", "snacks2", "06:00 PM", "07:00 PM", "food", "22-MAR-2019");
        Scannable scannable6 = new Scannable("Dinner", "d2", "dinner2", "09:00 PM", "10:00 PM", "food", "23-MAR-2019");

        List<Scannable> scannableList = new ArrayList<>();
        scannableList.add(scannable1);
        scannableList.add(scannable2);
        scannableList.add(scannable3);
        scannableList.add(scannable4);
        scannableList.add(scannable5);
        scannableList.add(scannable6);
        mScannablesReference.child("list").setValue(new ScannableModel(scannableList));

        List<String> usersList = new ArrayList<>();
        usersList.add("tushar@mail");
        usersList.add("sparsh@gmail.com");
        CouponsUser couponsUser = new CouponsUser(usersList);

        List<String> usersList1 = new ArrayList<>();
        usersList1.add("abc@yahoomail.com");
        usersList1.add("bcd@g.com");
        CouponsUser couponsUser1 = new CouponsUser(usersList1);
        mAttendanceReference.child(scannable1.getScannableValue()).setValue(couponsUser);
        mAttendanceReference.child(scannable2.getScannableValue()).setValue(couponsUser1);
    }
}
