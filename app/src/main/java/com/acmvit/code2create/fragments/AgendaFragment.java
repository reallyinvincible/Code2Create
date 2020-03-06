package com.acmvit.code2create.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonAdapter;
import com.ethanhua.skeleton.SkeletonScreen;
import com.acmvit.code2create.adapters.AgendaAdapter;
import com.acmvit.code2create.models.Agenda;
import com.acmvit.code2create.R;
import com.acmvit.code2create.models.AgendaModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    SkeletonScreen skeletonScreen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        agendaRecyclerView = view.findViewById(R.id.rv_agenda_recycler_view);
        SkeletonAdapter skeletonAdapter = new SkeletonAdapter();
        skeletonScreen = Skeleton.bind(agendaRecyclerView)
                .adapter(skeletonAdapter)
                .shimmer(true)
                .angle(20)
                .duration(1200)
                .load(R.layout.skeleton_item_agenda)
                .count(10)
                .show();
        mDatabase = FirebaseDatabase.getInstance();
        agendaReference = mDatabase.getReference().child("agendas");
        fetchAgendaList();
        return view;
    }

    void fetchAgendaList() {
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
