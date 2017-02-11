package com.example.anurag145.hackdtu.fcm;

import android.content.SharedPreferences;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        SharedPreferences sharedPreferences = getSharedPreferences("datadata", MODE_PRIVATE);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken().substring(50);
        sharedPreferences.edit().putString("fcm",refreshedToken).commit();
        HashMap<String,Object> mp=new HashMap<>();
        mp.put("bla","bla");
        FirebaseDatabase.getInstance().getReference("contacts/"+refreshedToken).updateChildren(mp);
    }

}