package com.example.danie.testappusingjava;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.util.Log;

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

        choices = (RadioGroup) findViewById (R.id.multiple_choice); //must be given a value AFTER the xml has been loaded
        btn = (Button) findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (choices.getCheckedRadioButtonId() != -1) { //checks if user has picked a choice
                    changeQuestion();
                    changeChoices();
                    resetRadio();
                }
//                else { //creates pop-up telling user to make a choice
//                    Log.d(TAG, "Else Statement has been Run.");
//                    Snackbar.make(findViewById(R.id.myLayout), R.string.make_choice, Snackbar.LENGTH_SHORT)
//                            .show();
//                }
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

    private void resetRadio() {
        choices.clearCheck();
    }
}
