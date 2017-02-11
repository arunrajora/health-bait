package com.example.anurag145.hackdtu.datamodels;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DmGoal {

    public String goalName;
    public String name;
    public String desc;
    public String amount;

    public String voteStatus;
    public String contacts;
    public String status;
    public String votePos;
    public String voteNeg;
    public String duration;
    public String time_created;
    public String people_count;

    public DmGoal() {
    }

    public DmGoal(String goalName, String name, String desc, String amount, String voteStatus, String contacts, String status, String votePos, String voteNeg, String duration, String time_created, String people_count) {
        this.goalName = goalName;
        this.name = name;
        this.desc = desc;
        this.amount = amount;
        this.voteStatus = voteStatus;
        this.contacts = contacts;
        this.status = status;
        this.votePos = votePos;
        this.voteNeg = voteNeg;
        this.duration = duration;
        this.time_created = time_created;
        this.people_count = people_count;
    }
}
