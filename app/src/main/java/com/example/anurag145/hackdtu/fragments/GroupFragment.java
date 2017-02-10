package com.example.anurag145.hackdtu.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anurag145.hackdtu.AddGoalActivity;
import com.example.anurag145.hackdtu.R;

public class GroupFragment extends Fragment {

    RecyclerView mRecyclerView;
    FloatingActionButton mFab;

    public GroupFragment() {
    }

    public static GroupFragment newInstance() {
        return new GroupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_group, container, false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.group_rec);
        mFab=(FloatingActionButton) view.findViewById(R.id.group_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),AddGoalActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
