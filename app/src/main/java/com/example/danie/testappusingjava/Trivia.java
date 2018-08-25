package com.example.danie.testappusingjava;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.xml.namespace.QName;



public class Trivia extends Activity {

    private Button btn;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        btn = (Button) findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeQuestion();
                changeChoices();
            }
        });
    }

    private void changeQuestion(){
        count++;

        TextView question = (TextView) findViewById(R.id.question);
        String message = getString(R.string.question);
        message+=" "+count;

        question.setText(message);
    }

    private void changeChoices() {
        System.out.println(0);
    }


}
