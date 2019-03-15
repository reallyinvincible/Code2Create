package com.exuberant.code2create.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.exuberant.code2create.models.Agenda;
import com.exuberant.code2create.models.AgendaModel;
import com.exuberant.code2create.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mUserReference;
    DatabaseReference mAgendaReference;
    Button loginButton;

    List<Agenda> agendaList;
    AgendaModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeView();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAgendaReference.setValue(model);
                launchHome();
            }
        });

        Agenda model1 = new Agenda("Registration", "", "6:00 PM", "8:00 PM", "22-MAR-2019", "reg");
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

    }

    void initializeView(){
        loginButton = findViewById(R.id.btn_login);
        mDatabase = FirebaseDatabase.getInstance();
        mAgendaReference = mDatabase.getReference().child("agendas");
    }

    void launchHome(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
