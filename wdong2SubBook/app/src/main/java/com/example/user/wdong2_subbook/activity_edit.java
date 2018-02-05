package com.example.user.wdong2_subbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class activity_edit extends AppCompatActivity {
    private TextView editName;
    private TextView editDate;
    private TextView editCharge;
    private TextView editComment;
    private  Button editSave;
    private  Button editDelete;
    private ArrayList<Book> bookList;
    private Book editBook;
    private Book saveBook;
    private Context context;
    private static final String FILENAME = "wdong2.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        bookList = new ArrayList<>();
        context = getApplicationContext();
        loadFromFile(context);
        final Intent intent = getIntent();

        int position = Integer.parseInt(intent.getExtras().get("ind").toString());
        editBook=bookList.get(position);

        String oriName=editBook.getName();
        Date oriDate=editBook.getDate();
        Double oriCharge=editBook.getCharge();
        String oriComment=editBook.getComment();

        editName = (TextView) findViewById(R.id.editName);
        editCharge = (TextView) findViewById(R.id.editCharge);
        editComment = (TextView) findViewById(R.id.editComment);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String temp_date = df.format(oriDate);
        editDate = (TextView) findViewById(R.id.editDate);

        editName.setText(oriName);
        editCharge.setText(Double.toString(oriCharge));
        editComment.setText(oriComment);
        editDate.setText(temp_date);

        editSave = (Button) findViewById(R.id.editSave);
        editDelete = (Button) findViewById(R.id.editDelete);

        editDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(activity_edit.this, MainActivity.class);
                //extract inputs
                loadFromFile(context);
                int book_id = Integer.parseInt(intent.getExtras().get("ind").toString());
                bookList.remove(book_id);
                saveInFile(context);
                startActivity(newIntent);
            }


        });

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkName = editName.getText().toString();
                String checkCharge = editCharge.getText().toString();
                if (enoughValue(checkName,checkCharge)){
                    if (isValidFormat("yyyy-MM-dd", editDate.getText().toString())) {
                        Intent i = new Intent(activity_edit.this, MainActivity.class);
                        //extract inputs

                        int book_id = Integer.parseInt(intent.getExtras().get("ind").toString());
                        saveBook = bookList.get(book_id);
                        saveBook.setName(checkName);
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date new_date = new Date();
                        try {
                            new_date = format.parse(editDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        saveBook.setDate(new_date);
                        saveBook.setCommend(editComment.getText().toString());
                        saveBook.setCharge(Double.parseDouble(checkCharge));
                        saveInFile(context);
                        startActivity(i);
                    } else {
                        Toast toast = Toast.makeText(context, "Please Enter Date in 'yyyy-MM-dd' format", Toast.LENGTH_LONG);
                        TextView v2 = (TextView) toast.getView().findViewById(android.R.id.message);
                        v2.setTextColor(Color.RED);
                        v2.setTextSize(20);
                        v2.setGravity(Gravity.CENTER);
                        toast.show();
                    }
                }else{
                    Toast toast = Toast.makeText(context, "Please at least Enter: name, date started, monthly charge", Toast.LENGTH_LONG);
                    TextView v2 = (TextView) toast.getView().findViewById(android.R.id.message);
                    v2.setTextColor(Color.RED);
                    v2.setTextSize(20);
                    v2.setGravity(Gravity.CENTER);
                    toast.show();
                }
            }


        });
    }

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

    private void saveInFile(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(bookList, out);
            out.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    public static boolean enoughValue(String name, String charge){
        if (name.length()==0 || charge.length()==0){
            return false;
        }
        return true;
    }
}
