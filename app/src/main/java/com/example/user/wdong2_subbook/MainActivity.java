package com.example.user.wdong2_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * author: Wang Dong
 * Main Activity is responsible for display the main interface
 * version:1.0
 *
 */

public class MainActivity extends AppCompatActivity {
    private ListView dataList;
    //private android.widget.ArrayAdapter ArrayAdapter;
    private ArrayList<Book> bookList;
    private static final String FILENAME = "wdong2.sav";
    private Button button_add;
    private TextView totalCharge;
    private Context context;

    //source: https://stackoverflow.com/questions/2198410/how-to-change-title-of-activity-in-android
    private String myTitle = String.format("wdong2-SubBook");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        loadFromFile(context);
        dataList = (ListView) findViewById(R.id.dataList);
        button_add = (Button)findViewById(R.id.add);

        setTitle(myTitle);

        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editScreen = new Intent(view.getContext(), activity_edit.class);
                editScreen.putExtra("ind", position);
                startActivity(editScreen);
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, activity_add2.class);
                startActivity(intent);
            }
        });
    }


    /**
     * loadFromFile
     * the function loadfromfile use gson to load information from file
     * @param context
     */
    private void loadFromFile(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            //taken from stack overflow bby, idk what or how it works, but it is magical pony dust that i dont understand whatsoever.
            //http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //2018-02-3-18:10
            Type listType = new TypeToken<ArrayList<Book>>() {}.getType();
            bookList = gson.fromJson(in, listType);
            //Log.d("Value 3: " + dataList.size(), "mytag");
            //displayData(dataList);
        } catch (FileNotFoundException e) {
            bookList = new ArrayList<Book>();
        }
    }

    /**
     * onStart
     * load information from file
     * find the view of total charge
     * set text
     * set adapter
     */
    protected void onStart() {
        super.onStart();
        //From: http://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
        loadFromFile(context);
        totalCharge = (TextView) findViewById(R.id.total_charge);

        if (bookList == null) {
            totalCharge.setText("0.0");
        } else {
            totalCharge.setText(getTotalCharge(bookList));
        }

        UserAdapter adapter = new UserAdapter(this, bookList);
        // Attach the adapter to a ListView
        this.dataList.setAdapter(adapter);
    }

    /**
     * getTotalCharge
     * @param bookList
     * @return
     */
    public String getTotalCharge(ArrayList<Book> bookList){
        double result = 0.0;
        for(int i=0; i<bookList.size(); i++){
            result += bookList.get(i).getCharge();
        }
        return Double.toString(result);
    }
}

