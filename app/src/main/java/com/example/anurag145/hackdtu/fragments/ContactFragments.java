package com.example.anurag145.hackdtu.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anurag145.hackdtu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class ContactFragments extends Fragment {

    ImageView profilePic;
    TextView name;
    TextView level;
    TextView loss;
    TextView profit;

    public ContactFragments() {
    }

    public static ContactFragments newInstance() {
        return new ContactFragments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_contact_fragments, container, false);
        profilePic=(ImageView) view.findViewById(R.id.prof_icon);
        name=(TextView) view.findViewById(R.id.person_name);
        level=(TextView)view.findViewById(R.id.level_icon);
        loss=(TextView)view.findViewById(R.id.loss_details);
        profit=(TextView)view.findViewById(R.id.profit_details);

        String fcmId=getContext().getSharedPreferences("datadata", MODE_PRIVATE).getString("fcm","1");
        DatabaseReference mref=FirebaseDatabase.getInstance().getReference("contacts/"+fcmId);
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if(dataSnapshot.hasChild("level")){
                        level.setText(dataSnapshot.child("level").getValue().toString());
                    }
                    if(dataSnapshot.hasChild("loss")){
                        loss.setText(dataSnapshot.child("loss").getValue().toString()+"\nLost");
                    }
                    if(dataSnapshot.hasChild("name")){
                        name.setText(dataSnapshot.child("name").getValue().toString());
                        getContext().getSharedPreferences("datadata", MODE_PRIVATE).edit()
                                .putString("name",dataSnapshot.child("name").getValue().toString()).commit();
                    }
                    if(dataSnapshot.hasChild("profile_pic")){
                        Glide.with(getContext()).load(dataSnapshot.child("profile_pic").getValue().toString()).into(profilePic);
                    }
                    if(dataSnapshot.hasChild("profit")){
                        profit.setText(dataSnapshot.child("profit").getValue().toString()+"\nReclaimed");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

}
