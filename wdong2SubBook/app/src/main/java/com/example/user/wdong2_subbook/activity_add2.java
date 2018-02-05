package com.example.user.wdong2_subbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * author: Wang Dong
 * add Activity is responsible for display the add interface
 * version:1.0
 *
 */
public class activity_add2 extends AppCompatActivity {

    private EditText name;
    private EditText date;
    private EditText charge;
    private EditText comment;
    private Button saveButton;
    private ArrayList<Book> bookList = new ArrayList<>();
    private ArrayAdapter<Book> adapter;
    private Context context;
    private static final String FILENAME = "wdong2.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add2);
        this.name =  (EditText)findViewById(R.id.enterName);
        this.date = (EditText)findViewById(R.id.enterDate);
        this.charge = (EditText)findViewById(R.id.enterCharge);
        this.comment = (EditText)findViewById(R.id.enterComment);
        this.saveButton = (Button)findViewById(R.id.save);
        this.context = getApplicationContext();

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                Date saveDate = new Date();
                String checkName = name.getText().toString();
                String checkCharge = charge.getText().toString();
                if (enoughValue(checkName,checkCharge)) {
                    if (isValidFormat("yyyy-MM-dd", date.getText().toString())) {
                        loadFromFile(context);

                        setResult(RESULT_OK);
                        String saveName = checkName;
                        try {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            saveDate = format.parse(date.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Double saveCharge = Double.parseDouble(checkCharge);
                        String saveComment = comment.getText().toString();
                        Book book = new Book(saveName, saveDate, saveCharge);
                        book.setCommend(saveComment);

                        bookList.add(book);

                        saveInFile(context);

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast toast = Toast.makeText(context, "Please Enter Date in 'yyyy-MM-dd' format", Toast.LENGTH_LONG);
                        TextView v1 = (TextView) toast.getView().findViewById(android.R.id.message);
                        v1.setTextColor(Color.RED);
                        v1.setTextSize(20);
                        v1.setGravity(Gravity.CENTER);
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

    /**
     * loadFromFile
     * use gson to load information from the file
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
     * saveInFile
     * use gson to save the information to file
     * @param context
     */
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

    /**
     * isValidFormat
     * check if the format is correct or not
     * @param format
     * @param value
     * @return
     * source: https://stackoverflow.com/questions/20231539/java-check-the-date-format-of-current-string-is-according-to-required-format-or
     */
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

    /**
     * enoughValue
     * check if the name and charge are empty
     * @param name
     * @param charge
     * @return
     */
    public static boolean enoughValue(String name, String charge){
        if (name.length()==0 || charge.length()==0){
            return false;
        }
        return true;
    }

}