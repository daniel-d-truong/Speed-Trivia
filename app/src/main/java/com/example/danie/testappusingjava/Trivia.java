package com.example.danie.testappusingjava;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import javax.xml.namespace.QName;

import static android.support.constraint.Constraints.TAG;


public class Trivia extends Activity {

    private Button btn;
    private RadioGroup choices;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        choices = (RadioGroup) findViewById (R.id.multiple_choice);
        btn = (Button) findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (choices.getCheckedRadioButtonId() != -1) {
                    changeQuestion();
                    changeChoices();
                    resetRadio();
                }
                else { //creates pop-up telling user to make a choice
                    Log.d(TAG, "Else Statement has been Run.");
                    Snackbar.make(findViewById(R.id.myLayout), R.string.make_choice, Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    private void changeQuestion(){
        count++;

        TextView q = (TextView) findViewById(R.id.question2);
        String message = getString(R.string.question);
        message+=" "+count;

        q.setText(message);
    }

    private void changeChoices() {
        System.out.println(0);
    }

    private void resetRadio() {
        choices.clearCheck();
    }
}