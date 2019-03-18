package com.exuberant.code2create.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exuberant.code2create.AgendaAdapter;
import com.exuberant.code2create.models.Agenda;
import com.exuberant.code2create.R;
import com.exuberant.code2create.models.AgendaModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class AgendaFragment extends Fragment {

    FirebaseDatabase mDatabase;
    DatabaseReference agendaReference;
    List<Agenda> agendaList;
    RecyclerView agendaRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        Date currentTime = Calendar.getInstance().getTime();
        agendaRecyclerView = view.findViewById(R.id.rv_agenda_recycler_view);
        mDatabase = FirebaseDatabase.getInstance();
        agendaReference = mDatabase.getReference().child("agendas");
        fetchAgendaList();
        return view;
    }

    void fetchAgendaList(){
        agendaReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AgendaModel agendaModel = dataSnapshot.getValue(AgendaModel.class);
                List<Agenda> agendaList = agendaModel.getAgendasList();
                AgendaAdapter adapter = new AgendaAdapter(agendaList);
                agendaRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
