package com.example.anurag145.hackdtu;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.PskKeyManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anurag145 on 11/2/17.
 */

public class CustomList extends ArrayAdapter<String> {
    private ArrayList<String> names;
    private String[] hello={"hello"};
    private ArrayList<String> phone;
    private ArrayList<String> imageid;
    private Activity context;

    public CustomList(Activity context, ArrayList<String> names, ArrayList<String> phone, ArrayList<String> imageid) {
        super(context, R.layout.list_inflate, names);
        this.context = context;
        this.names = names;
        this.phone = phone;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_inflate, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);
       if(names.get(position)!=null)
        textViewName.setText(names.get(position));
        if(phone.get(position)!=null)
        textViewDesc.setText(phone.get(position));
        if(imageid.get(position)!=null)
        {
            image.setImageURI(Uri.parse(imageid.get(position)));
        }
        return  listViewItem;
    }
}
