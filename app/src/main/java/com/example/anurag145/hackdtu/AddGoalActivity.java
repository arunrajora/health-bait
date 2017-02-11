package com.example.anurag145.hackdtu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anurag145.hackdtu.datamodels.DmGoal;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class AddGoalActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button bt_amt;
    private Button bt_done;
    private Button bt_addcontact;
    private Button bt_duration;
    private EditText tv_name;
    private EditText tv_about;
    private ListView listView;
    ArrayList<String> mName;
    ArrayList<String> mContact;
    ArrayList<String> mImage;
    private Dialog dialog;
    String mtime;
    String amount="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mName=new ArrayList<>();
        mContact=new ArrayList<>();
        mImage=new ArrayList<>();

        setContentView(R.layout.activity_add_goal);;
        bt_amt=(Button)findViewById(R.id.goal_amount);
        bt_done=(Button)findViewById(R.id.goal_submit);
        bt_addcontact=(Button)findViewById(R.id.goal_add_contact);
        bt_duration=(Button)findViewById(R.id.goal_time);

        tv_name=(EditText) findViewById(R.id.goal_name_textview);
        tv_about=(EditText)findViewById(R.id.goal_about_textview);

        listView=(ListView) findViewById(R.id.goal_listview);
        bt_amt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog= new Dialog(AddGoalActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(true);
                dialog.show();
                final EditText editTexttemp=(EditText)dialog.findViewById(R.id.texter);
                Button button=(Button)dialog.findViewById(R.id.dialog_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        amount=editTexttemp.getText().toString();
                        dialog.dismiss();
                    }
                });
            }
        });

        bt_duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dialog=new DatePickerDialog(AddGoalActivity.this,AddGoalActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        bt_addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, 100);
            }
        });
        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contacts_list="";
                for(int i=0;i<mName.size();i++){
                    contacts_list+=mName.get(i)+" , ";
                }
                amount="Rs "+amount;
                String name=getSharedPreferences("datadata", MODE_PRIVATE).getString("name","1");
                String timest=String.valueOf(System.currentTimeMillis());
                String psh=tv_name.getText().toString()+name+timest;
                DmGoal item=new DmGoal(tv_name.getText().toString(),name,
                        tv_about.getText().toString(),amount,
                        "not_allowed",contacts_list,"created","0","0",mtime,timest,String.valueOf(mName.size()));
                FirebaseDatabase.getInstance().getReference(getSharedPreferences("datadata", MODE_PRIVATE).getString("name","1")+"/"+psh).setValue(item);
                for(int i=0;i<mName.size();i++){
                    DmGoal newItem=new DmGoal(tv_name.getText().toString(),name,
                            tv_about.getText().toString(),amount,
                            "allowed",contacts_list,"created","0","0",mtime,timest,String.valueOf(mName.size()));
                    FirebaseDatabase.getInstance().getReference(mName.get(i)+"/"+psh).setValue(newItem);
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
                    contactPicked(data);
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            String imgUri=null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();

            int  imgIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            imgUri=cursor.getString(imgIndex);
            mName.add(name);
            mContact.add(phoneNo);
            mImage.add(imgUri);
            CustomList customList = new CustomList(this,mName,mContact,mImage);
            listView.setAdapter(customList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mtime=new StringBuilder().append(dayOfMonth).append("/").append(month+1).append("/").append(year).toString();
        bt_duration.setText(mtime);
    }
}
