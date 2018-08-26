package com.example.danie.testappusingjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button_quiz);

        //OnClickListener for when user taps the button
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openTriviaActivity();
            }
        });
    }

    //method to lead to trivia activity
    public void openTriviaActivity(){
        Intent in = new Intent (this, Trivia.class); //must create an Intent object
        startActivity(in); //pass the Intent object into the startActivity(Intent) class in order to actually start the activity
    }






}


