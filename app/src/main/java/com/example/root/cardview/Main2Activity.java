package com.example.root.cardview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class Main2Activity extends AppCompatActivity {

    int noteID;
    EditText editText;


    public void  share(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Note");
        intent.putExtra(Intent.EXTRA_TEXT, "Here is the note:\n\n" + editText.getText());

        startActivity(Intent.createChooser(intent, "Send Email"));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        Button mail = (Button) findViewById(R.id.share);

        editText = (EditText)findViewById(R.id.editText);
      final Date currentTime = Calendar.getInstance().getTime();


        Intent intent = getIntent();

        noteID = intent.getIntExtra("noteID" , -1);




        if (noteID != -1){

            editText.setText(MainActivity.adapter.data.get(noteID));
            editText.setSelection(editText.getText().length());



        } else {

            //MainActivity.notes.add("");
            noteID = MainActivity.adapter.data.size() ;
            MainActivity.adapter.addItem(noteID,"");
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MainActivity.adapter.setx(noteID , String.valueOf(s));
               MainActivity.adapter.sety(noteID,currentTime.toString());
                MainActivity.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.root.cardview", Context.MODE_PRIVATE);

                HashSet<String> set  = new HashSet(MainActivity.adapter.data);
                sharedPreferences.edit().putStringSet("notes" , set).apply();


               HashSet<String> time  = new HashSet(MainActivity.adapter.timed);
                sharedPreferences.edit().putStringSet("time" , time).apply();




            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
