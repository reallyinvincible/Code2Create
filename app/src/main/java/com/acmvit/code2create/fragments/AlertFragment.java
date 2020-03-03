package com.acmvit.code2create.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.acmvit.code2create.R;
import com.acmvit.code2create.adapters.AlertAdapter;
import com.acmvit.code2create.adapters.SkeletonAdapter;
import com.acmvit.code2create.models.Alert;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class AlertFragment extends Fragment {

    FirebaseDatabase mDatabase;
    DatabaseReference alertReference;
    RecyclerView alertRecyclerView;
    SkeletonScreen skeletonScreen;
    List<Alert> alertList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert, container, false);
        mDatabase = FirebaseDatabase.getInstance();
        alertReference = mDatabase.getReference().child("notification");
        alertRecyclerView = view.findViewById(R.id.rv_alert_recycler_view);

        SkeletonAdapter skeletonAdapter = new SkeletonAdapter();
        skeletonScreen = Skeleton.bind(alertRecyclerView)
                .adapter(skeletonAdapter)
                .shimmer(true)
                .angle(20)
                .duration(1200)
                .load(R.layout.skeleton_item_agenda)
                .count(10)
                .show();

        alertReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alertList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Alert alert = data.getValue(Alert.class);
                    alertList.add(alert);
                }
                AlertAdapter adapter = new AlertAdapter(alertList);
                alertRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
