package com.exuberant.code2create.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exuberant.code2create.R;
import com.exuberant.code2create.models.Agenda;
import com.exuberant.code2create.models.AgendaModel;
import com.exuberant.code2create.models.User;
import com.exuberant.code2create.models.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mUserReference;
    DatabaseReference mAgendaReference;

    private final static String SHA_SALT = "ACM_Rocks";

    Button loginButton;
    EditText emailET, passwordET;

    List<Agenda> agendaList;
    AgendaModel model;
    List<User> userList;
    UserModel userModel;

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeView();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAgendaReference.setValue(model);
                mUserReference.setValue(userModel);

                if (emailET.getText() != null && emailET.getText().length() > 0 && passwordET.getText() != null && passwordET.getText().length() > 0) {
                    String email = emailET.getText().toString();
                    String password = passwordET.getText().toString();
                    String securedPass = get_SHA_512_password(password, SHA_SALT);
                    checkUser(email, securedPass);
//                    launchHome();
                } else {
                    showSnackbar("Email or password missing");
                }
            }
        });

        /*Agenda model1 = new Agenda("Registration", "", "6:00 PM", "8:00 PM", "22-MAR-2019", "reg");
        Agenda model2 = new Agenda("Opening Ceremony", "", "8:00 PM", "9:00 PM", "22-MAR-2019", "talk");
        Agenda model3 = new Agenda("Hack Starts", "", "9:00 PM", "", "22-MAR-2019", "event");
        Agenda model4 = new Agenda("Dinner", "", "10:30 PM", "11:30 PM", "22-MAR-2019", "food");
        Agenda model5 = new Agenda("Intruder", "", "11:30 PM", "11:59 PM", "22-MAR-2019", "event");
        Agenda model6 = new Agenda("Night Snacks", "", "1:30 AM", "2:00 AM", "23-MAR-2019", "food");
        agendaList = new ArrayList<>();
        agendaList.add(model1);
        agendaList.add(model2);
        agendaList.add(model3);
        agendaList.add(model4);
        agendaList.add(model5);
        agendaList.add(model6);
        model = new AgendaModel(agendaList);*/


        User user1 = new User("ssindher11@gmail.com", get_SHA_512_password("qwert", SHA_SALT), false, true, "bla78y");
        User user2 = new User("harsh.jain@gmail.com", get_SHA_512_password("ytrewq", SHA_SALT), true, false, "mudai897");
        User user3 = new User("yash@gmail.com", get_SHA_512_password("qwertyuiop", SHA_SALT), false, true, "nmudwdu7");
        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userModel = new UserModel(userList);
        mAgendaReference.setValue(model);
        mUserReference.setValue(userModel);
    }

    void initializeView() {
        constraintLayout = findViewById(R.id.cl_login);
        loginButton = findViewById(R.id.btn_login);
        mDatabase = FirebaseDatabase.getInstance();
        mAgendaReference = mDatabase.getReference().child("agendas");
        mUserReference = mDatabase.getReference().child("users_test");
        emailET = findViewById(R.id.et_email);
        passwordET = findViewById(R.id.et_password);
    }

    void checkUser(String email, String password) {

    }


    void launchHome() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(constraintLayout, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorAccent);
        snackbar.show();
    }

    public String get_SHA_512_password(String password_to_hash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(password_to_hash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
