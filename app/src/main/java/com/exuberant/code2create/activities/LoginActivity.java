package com.exuberant.code2create.activities;

import android.content.Intent;
import android.os.Bundle;
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

import static com.exuberant.code2create.interfaces.UtilsInterface.get_SHA_512_password;
import static com.exuberant.code2create.interfaces.UtilsInterface.transformString;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mUserReference;
    DatabaseReference mAgendaReference;

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
        loginButton.setOnClickListener(view -> {
//            mAgendaReference.setValue(model);
//            mUserReference.setValue(userModel);

            if (emailET.getText() != null && emailET.getText().length() > 0 && passwordET.getText() != null && passwordET.getText().length() > 0) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                String securedPass = get_SHA_512_password(password, SHA_SALT);
//                Toast.makeText(LoginActivity.this, transformString(email), Toast.LENGTH_SHORT).show();
                checkUser(transformString(email), securedPass);
//                    launchHome();
            } else {
                showErrorSnackbar("Email or password missing");
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
        model = new AgendaModel(agendaList);
        mAgendaReference.setValue(model);*/


        /*User user1 = new User("ssindher11@gmail.com", get_SHA_512_password("qwert", SHA_SALT), false, true, "bla78y");
        User user2 = new User("harsh.jain@gmail.com", get_SHA_512_password("ytrewq", SHA_SALT), true, false, "mudai897");
        User user3 = new User("yash@gmail.com", get_SHA_512_password("qwertyuiop", SHA_SALT), false, true, "nmudwdu7");
        mAgendaReference.setValue(model);
        mUserReference.child(transformString(user1.getEmail())).setValue(user1);
        mUserReference.child(transformString(user2.getEmail())).setValue(user2);
        mUserReference.child(transformString(user3.getEmail())).setValue(user3);*/
    }

    void initializeView() {
        constraintLayout = findViewById(R.id.cl_login);
        loginButton = findViewById(R.id.btn_login);
        emailET = findViewById(R.id.et_email);
        passwordET = findViewById(R.id.et_password);
        mDatabase = FirebaseDatabase.getInstance();
        mAgendaReference = mDatabase.getReference().child("agendas");
        mUserReference = mDatabase.getReference().child("users");
//        mAllUsersReference = mDatabase.getReference().child("users").child("userList");
    }

    void checkUser(String email, String password) {
        mUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(email)) {
                    User user = dataSnapshot.child(email).getValue(User.class);
//                    int a = 10;

                    if (password.equals(user.getPassword())) {
                        showConfirmationSnackbar("Logging you in!");
                    } else {
                        showErrorSnackbar("Incorrect Password!");
                    }
                } else {
                    showErrorSnackbar("User not registered!!");
                }

                /*List<User> userList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    userList.add(dataSnapshot1.getValue(User.class));
                }
                int a = 10;
                if (userList.contains(email)) {
                    User user = dataSnapshot.child(email).getValue(User.class);
                    int b = 0;
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    void launchHome() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    void showConfirmationSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(constraintLayout, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorAccent);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                launchHome();
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
