package com.example.anurag145.hackdtu.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anurag145.hackdtu.R;
import com.example.anurag145.hackdtu.datamodels.Bot_VH;
import com.example.anurag145.hackdtu.datamodels.DmGoal;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;

public class ActivityFragment extends Fragment {

    RecyclerView mRecyclerView;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter mAdapter;

    public ActivityFragment() {
    }

    public static ActivityFragment newInstance() {
        return new ActivityFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_activity, container, false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.activity_rec);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        String name=getContext().getSharedPreferences("datadata", MODE_PRIVATE).getString("name","1");
        databaseReference= FirebaseDatabase.getInstance().getReference(name);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        final String name=getContext().getSharedPreferences("datadata", MODE_PRIVATE).getString("name","1");
        mAdapter=new FirebaseRecyclerAdapter<DmGoal,Bot_VH>(
                DmGoal.class,R.layout.goal_item,Bot_VH.class,databaseReference) {
            @Override
            protected void populateViewHolder(Bot_VH viewHolder, final DmGoal model, int position) {
                viewHolder.setItems(model,name,getContext());
            }
        };


        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAdapter!=null) mAdapter.cleanup();
    }
}
