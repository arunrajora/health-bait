package com.example.anurag145.hackdtu.datamodels;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.anurag145.hackdtu.R;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import static android.content.Context.MODE_PRIVATE;

public class Bot_VH extends RecyclerView.ViewHolder {

    public CardView root;
    public TextView goalName;
    public TextView contactName;
    public TextView vote;
    public TextView price;
    public TextView date;
    public Button action;

    public Bot_VH(View itemView) {
        super(itemView);
        root=(CardView)itemView.findViewById(R.id.baap);
        goalName=(TextView)itemView.findViewById(R.id.goal_name);
        contactName=(TextView)itemView.findViewById(R.id.contact_name);
        vote=(TextView)itemView.findViewById(R.id.vote);
        price=(TextView)itemView.findViewById(R.id.price);
        date=(TextView) itemView.findViewById(R.id.date);
        action=(Button) itemView.findViewById(R.id.action);
    }

    static private float convertToInt(String x){
        return Integer.valueOf(x);
    }

    static private float convertToFloat(String x){
        return Float.valueOf(x);
    }


    public void setItems(final DmGoal model,final String name, final Context context){
        goalName.setText(model.goalName);
        contactName.setText(model.name);
        if(convertToInt(model.voteNeg)+convertToInt(model.votePos)>0){
            vote.setText((String.format("%.2f",convertToFloat(model.votePos)/(convertToFloat(model.votePos)+convertToFloat(model.voteNeg))*100.00))+"%");
        }
        else{
            vote.setText("0%");
        }
        price.setText(model.amount);
        date.setText(model.duration);
        String str_date=model.duration;

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = (Date)formatter.parse(str_date);
        } catch (ParseException e) {
        }
        long timestamp=date.getTime();
        boolean is_expired=(timestamp<=System.currentTimeMillis()) || (model.status.equals("done"));
        if(name.equals(model.name) && is_expired){
            vote.setVisibility(View.VISIBLE);
        }
        else{
            vote.setVisibility(View.GONE);
        }

        if(is_expired && (convertToInt(model.votePos)+convertToInt(model.voteNeg)==convertToInt(model.people_count))){
            float perct=convertToFloat(model.votePos)/(convertToFloat(model.votePos)+convertToFloat(model.voteNeg))*100.00f;
            if(perct>=50.0){
              root.setAlpha(0.9f);
                price.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
            }
            else{
                root.setAlpha(1.0f);
                price.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
            }

        }
        else{
            root.setAlpha(1.0f);
            price.setTextColor(context.getResources().getColor(android.R.color.white));

        }

        if(name.equals(model.name)){
            if(is_expired){
                action.setVisibility(View.GONE);
            }
            else{
                action.setText("Completed");
                action.setVisibility(View.VISIBLE);
                action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String psh=model.goalName+model.name+model.time_created;
                        HashMap<String,Object> mp=new HashMap<String, Object>();
                        mp.put("status","done");
                        FirebaseDatabase.getInstance().getReference(model.name+"/"+psh).updateChildren(mp);

                        String rname=model.contacts;
                        StringTokenizer st = new StringTokenizer(rname,",");
                        while(st.hasMoreTokens()) {
                          String temmp=st.nextToken().trim();
                            FirebaseDatabase.getInstance().getReference(temmp + "/" + psh).updateChildren(mp);
                        }
                        }
                });
            }

        }
        else{
            if(is_expired && model.voteStatus.equals("allowed")){
                action.setText("Vote");
                action.setVisibility(View.VISIBLE);
                action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(context).setMessage("Is this task completed?")
                                .setTitle("Vote")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String psh=model.goalName+model.name+model.time_created;
                                        HashMap<String,Object> mp=new HashMap<String, Object>();
                                        mp.put("votePos",String.valueOf(Integer.parseInt(model.votePos)+1));
                                        FirebaseDatabase.getInstance().getReference(model.name+"/"+psh).updateChildren(mp);

                                        String npsh=model.goalName+model.name+model.time_created;
                                        HashMap<String,Object> nmp=new HashMap<String, Object>();
                                        nmp.put("status","done");
                                        String myName=context.getSharedPreferences("datadata", MODE_PRIVATE).getString("name","1");
                                        FirebaseDatabase.getInstance().getReference(myName+"/"+npsh).updateChildren(nmp);

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String psh=model.goalName+model.name+model.time_created;
                                        HashMap<String,Object> mp=new HashMap<String, Object>();
                                        mp.put("voteNeg",String.valueOf(Integer.parseInt(model.voteNeg)+1));
                                        FirebaseDatabase.getInstance().getReference(model.name+"/"+psh).updateChildren(mp);

                                        String npsh=model.goalName+model.name+model.time_created;
                                        HashMap<String,Object> nmp=new HashMap<String, Object>();
                                        nmp.put("status","done");
                                        String myName=context.getSharedPreferences("datadata", MODE_PRIVATE).getString("name","1");
                                        FirebaseDatabase.getInstance().getReference(myName+"/"+npsh).updateChildren(nmp);

                                    }
                                })
                        .create().show();
                    }
                });
            }
            else{
                action.setVisibility(View.GONE);
            }
        }


        //action
    }
}
