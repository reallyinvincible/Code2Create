package com.acmvit.code2create.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.acmvit.code2create.R;
import com.acmvit.code2create.UtilsInterface;
import com.acmvit.code2create.adapters.AgendaAdapter;
import com.acmvit.code2create.models.Agenda;
import com.acmvit.code2create.models.AgendaModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonAdapter;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;

public class AgendaFragment extends Fragment {

    private FirebaseDatabase mDatabase;
    private DatabaseReference agendaReference;
    private List<Agenda> agendaList;
    private RecyclerView agendaRecyclerView;
    private RecyclerView.SmoothScroller smoothScroller;
    private static final float MILLISECONDS_PER_INCH = 40f;
    private SkeletonScreen skeletonScreen;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        agendaRecyclerView = view.findViewById(R.id.rv_agenda_recycler_view);
        smoothScroller = new LinearSmoothScroller(context) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
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

    private void fetchAgendaList() {
        agendaReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AgendaModel agendaModel = dataSnapshot.getValue(AgendaModel.class);
                agendaList = agendaModel.getAgendasList();
                AgendaAdapter adapter = new AgendaAdapter(agendaList);
                agendaRecyclerView.setAdapter(adapter);
                checkPresentEvent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkPresentEvent() {
        for (Agenda agenda : agendaList) {
            Date agendaStartTime = UtilsInterface.getDateObject(agenda.getDate(), agenda.getStartTime());
            Date agendaEndTime = UtilsInterface.getDateObject(agenda.getDate(), agenda.getEndTime());
            if (UtilsInterface.compareDates(agendaStartTime) >= 1 && UtilsInterface.compareDates(agendaEndTime) < 0) {
                moveToCurrentEvent(agendaList.indexOf(agenda));
                break;
            }
        }
        Agenda lastAgenda = agendaList.get(agendaList.size() - 1);
        Date lastAgendaEndTime = UtilsInterface.getDateObject(lastAgenda.getDate(), lastAgenda.getEndTime());
        if (UtilsInterface.compareDates(lastAgendaEndTime) >= 1) {
            moveToCurrentEvent(agendaList.indexOf(lastAgenda));
        }
    }

    private void moveToCurrentEvent(int position) {
        if (position != 0) {
            smoothScroller.setTargetPosition(position - 1);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (agendaRecyclerView != null && agendaRecyclerView.getLayoutManager() != null) {
                    agendaRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
                }
            }
        }, 400);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (agendaList != null) {
            checkPresentEvent();
        }
    }
}
