package com.example.victor.adressbook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class changeNote extends Activity {

    public SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changenote);
        final TextView enterNewNoteText = (TextView) findViewById(R.id.EnterNewNoteText);
        Button enterNewNoteBtn = (Button) findViewById(R.id.EnterNewNoteBtn);
        final TextView oldNote = (TextView) findViewById(R.id.OldNote);
        database = getBaseContext().openOrCreateDatabase("book.db", SQLiteDatabase.OPEN_READWRITE, null);
        String note = getIntent().getStringExtra("note");
        oldNote.setText(String.format("Прошлая заметка: %s", note));
        enterNewNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNote = enterNewNoteText.getText().toString();
                if(!newNote.isEmpty()){
                    String name = getIntent().getStringExtra("name");
                    Cursor cursor = database.rawQuery("SELECT * FROM info;", null);
                    for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                        int id = Integer.parseInt(cursor.getString(0));
                        if(name.equals(cursor.getString(2))){
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("note", newNote);
                            database.update("info", contentValues, "id=" + id, null );
                            Intent intent = new Intent(changeNote.this, MainActivity.class);
                            startActivity(intent);
                            changeNote.this.finish();
                        }
                    }
                }
            }
        });
    }
}
