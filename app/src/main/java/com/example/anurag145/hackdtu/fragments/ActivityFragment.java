package com.example.anurag145.hackdtu.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anurag145.hackdtu.R;

public class ActivityFragment extends Fragment {

    RecyclerView mRecyclerView;

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
        return view;

    }

}
