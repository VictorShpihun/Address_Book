package com.example.victor.adressbook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static android.database.sqlite.SQLiteDatabase.OPEN_READWRITE;

public class MainActivity extends AppCompatActivity {

    public SQLiteDatabase database;
    public ArrayAdapter<String> adapter;
    public ArrayList<String> myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        myList = new ArrayList<String>();

        database = getBaseContext().openOrCreateDatabase("book.db", OPEN_READWRITE , null);
        database.execSQL("CREATE TABLE IF NOT EXISTS info(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstname TEXT NOT NULL, " + "secondname TEXT NOT NULL, lastname TEXT NOT NULL, " +
                "age INTEGER NOT NULL, telnum TEXT NOT NULL," +
                "email TEXT NOT NULL, adress TEXT NOT NULL, note TEXT NOT NULL)");
        Cursor firstCursor = database.rawQuery("SELECT * FROM info;", null);
        if(firstCursor.getCount() == 0){
            Intent intent = new Intent(MainActivity.this, addPerson.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
        if(firstCursor.getCount() > 0){
            for(firstCursor.moveToFirst(); !firstCursor.isAfterLast(); firstCursor.moveToNext()){
                String carName = firstCursor.getString(2);
                myList.add(carName);
            }
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, myList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final TextView fname = (TextView) findViewById(R.id.fName);
        final TextView surName = (TextView) findViewById(R.id.surName);
        final TextView lastName = (TextView) findViewById(R.id.lasName);
        final TextView age = (TextView) findViewById(R.id.age);
        final TextView telNum = (TextView) findViewById(R.id.telNum);
        final TextView mail = (TextView) findViewById(R.id.mail);
        final TextView adress = (TextView) findViewById(R.id.adress);
        final TextView note = (TextView) findViewById(R.id.note);
        final TextView findBySurName = (TextView) findViewById(R.id.findBySurName);
        Button noteBtn = (Button) findViewById(R.id.noteBtn);
        Button searchBtn = (Button) findViewById(R.id.SearchBtn);
        Button delBtn = (Button) findViewById(R.id.deletBtn);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final Cursor newCursor = database.rawQuery("SELECT * FROM info;", null);
                if(newCursor.getCount() > 0){
                    int index = spinner.getSelectedItemPosition();
                    String getname = myList.get(index);
                    for(newCursor.moveToFirst(); !newCursor.isAfterLast(); newCursor.moveToNext()) {
                        String name = newCursor.getString(2);
                        if(name.equals(getname)){
                            fname.setText(String.format("Имя: %s", newCursor.getString(1)));
                            surName.setText(String.format("Фамилия: %s", newCursor.getString(2)));
                            lastName.setText(String.format("Отчество: %s", newCursor.getString(3)));
                            age.setText(String.format("Возраст: %s", newCursor.getString(4)));
                            telNum.setText(String.format("Телефонный номер: %s", newCursor.getString(5)));
                            mail.setText(String.format("e_mail: %s", newCursor.getString(6)));
                            adress.setText(String.format("Адрес: %s", newCursor.getString(7)));
                            note.setText(String.format("Заметка: %s", newCursor.getString(8)));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String surNameSearch = findBySurName.getText().toString();
                if(!surNameSearch.isEmpty()){
                    final Cursor cursor = database.rawQuery("SELECT * FROM info;", null);
                    if(cursor.getCount() > 0){
                        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                            String surName = cursor.getString(2);
                            if(surNameSearch.equals(surName)){
                                int id = cursor.getInt(0);
                                spinner.setSelection(id - 1);
                            }
                        }
                    }
                }
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = spinner.getSelectedItemPosition();
                String getname = myList.get(index);
                final Cursor cursor = database.rawQuery("SELECT * FROM info;", null);
                for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                    if(getname.equals(cursor.getString(2))){
                        int id = Integer.parseInt(cursor.getString(0));
                        database.delete("info", "id=" + id, null);
                    }
                }
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = spinner.getSelectedItemPosition();
                String getname = myList.get(index);
                String note = "";
                final Cursor cursor = database.rawQuery("SELECT * FROM info;", null);
                for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                    if(getname.equals(cursor.getString(2))){
                        note = cursor.getString(8);
                    }
                }
                Intent intent = new Intent(MainActivity.this, changeNote.class);
                intent.putExtra("name", getname);
                intent.putExtra("note", note);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addPerson.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
