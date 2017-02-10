package com.example.anurag145.hackdtu.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anurag145.hackdtu.R;

public class ContactFragments extends Fragment {

    RecyclerView mRecyclerView;
    FloatingActionButton mFab;


    public ContactFragments() {
    }

    public static ContactFragments newInstance() {
        return new ContactFragments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_contact_fragments, container, false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.contact_rec);
        return view;

    }

}
