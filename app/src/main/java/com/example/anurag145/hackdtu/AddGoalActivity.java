package com.example.anurag145.hackdtu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddGoalActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Button button1;
    private Button button2;
    private Button button3;
    private EditText editText1;
    private EditText editText2;
    private ListView listView;
     int day,year, month;
    String[] array = {"Hello"};
    Integer[] array1= {R.mipmap.ic_launcher};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        button2=(Button)findViewById(R.id.time);

        listView =(ListView) findViewById(R.id.listView);

        CustomList customList = new CustomList(this,array,array,array1);
        listView.setAdapter(customList);

    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }

    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {

               day=arg3;
                month=arg2+1;
                    year=arg1;
                    button2.setText(new StringBuffer().append(day).append("/").append(month).append("/").append(year));

                }
            };
}
