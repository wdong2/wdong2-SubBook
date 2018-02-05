package com.example.user.wdong2_subbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 2018/2/4.
 */

public class UserAdapter extends ArrayAdapter<Book> {
    public UserAdapter(Context context, ArrayList<Book> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Book book = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_list, parent, false);
        }
        // Lookup view for data population
        TextView item_name = convertView.findViewById(R.id.text_name);
        TextView item_date = convertView.findViewById(R.id.text_date);
        TextView item_charge = convertView.findViewById(R.id.text_charge);
        // Return the completed view to render on screen
        item_name.setText(book.getName().toString());
        item_charge.setText(Double.toString(book.getCharge()));
        Date temp= book.getDate();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String date = df.format(temp);
        item_date.setText(date);

        return convertView;
    }
}