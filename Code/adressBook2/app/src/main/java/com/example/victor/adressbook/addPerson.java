package com.example.victor.adressbook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class addPerson extends Activity {

    public SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addperson);
        final TextView enterName = (TextView) findViewById(R.id.EnterNameText);
        final TextView enterSecName = (TextView) findViewById(R.id.SecNameText);
        final TextView enterLastName = (TextView) findViewById(R.id.lastNameText);
        final TextView enterAge = (TextView) findViewById(R.id.ageText);
        final TextView enterTelNum = (TextView) findViewById(R.id.telephoneNumText);
        final TextView enterEmail = (TextView) findViewById(R.id.enterMailText);
        final TextView enterAdress = (TextView) findViewById(R.id.adressText);
        final TextView enterNote = (TextView) findViewById(R.id.noteText);
        Button enterPersonBtn = (Button) findViewById(R.id.EnterAddPerson);
        enterPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = enterName.getText().toString();
                String secName = enterSecName.getText().toString();
                String lastName = enterLastName.getText().toString();
                String age = enterAge.getText().toString();
                String telNum = enterTelNum.getText().toString();
                String email = enterEmail.getText().toString();
                String adress = enterAdress.getText().toString();
                String note = enterNote.getText().toString();
                database = getBaseContext().openOrCreateDatabase("book.db", SQLiteDatabase.OPEN_READWRITE, null);
                if(!name.isEmpty() & !secName.isEmpty() & !lastName.isEmpty()
                        & !age.isEmpty() & !telNum.isEmpty() & !email.isEmpty()
                        & !adress.isEmpty() & !note.isEmpty()){
                    ContentValues insertValues = new ContentValues();
                    insertValues.put("firstname" , name);
                    insertValues.put("secondname", secName);
                    insertValues.put("lastname", lastName);
                    insertValues.put("age", Integer.parseInt(age));
                    insertValues.put("telnum", telNum);
                    insertValues.put("email", email);
                    insertValues.put("adress", adress);
                    insertValues.put("note", note);
                    database.insert("info", null, insertValues);
                    Intent intent = new Intent(addPerson.this, MainActivity.class);
                    addPerson.this.finish();
                    startActivity(intent);
                }
            }
        });
    }
}
