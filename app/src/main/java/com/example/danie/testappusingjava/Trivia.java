package com.example.danie.testappusingjava;

import android.app.Activity;
import android.graphics.Color;
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

        //variables that select a View in the XML must be initiated AFTER the activity xml has been loaded
        choices = (RadioGroup) findViewById (R.id.multiple_choice);
        btn = (Button) findViewById(R.id.submit);
        Log.d(TAG, "Trivia has been created");

        //special method to use when any of the buttons in the RadioGroup are selected
        choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            //group is the RadioGroup that the button tapped on is in, checkedId is the ID of the specific radio button chosen
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "ON Click has been run");
                btn.setBackgroundColor(getResources().getColor(R.color.buttonColor)); //sets background color to what is set in the colors.xml

            }
        });

        //occurs when button is tapped
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //checks if any RadioButton has been selected
                if (choices.getCheckedRadioButtonId() != -1) { //when value is -1, that means no button has been selected
                    changeQuestion();
                    changeChoices();
                    resetRadio();
                }
                else { //creates pop-up telling user to make a choice
                    Log.d(TAG, "Else Statement has been Run.");
                    //making a snackbar
                    Snackbar.make(findViewById(R.id.myLayout), R.string.make_choice, Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    //changes the Question when button is clicked
    private void changeQuestion(){
        count++;

        TextView q = (TextView) findViewById(R.id.question2);
        String message = getString(R.string.question); //used to access the String resource
        message+=" "+count; //changes the message 

        q.setText(message);
    }

    //changes the Choices when button is clicked
    private void changeChoices() {
        System.out.println(0);
    }

    //resets the question layout
    private void resetRadio() {
        choices.clearCheck(); //clears the checkedRadioButton in RadioGroup
        btn.setBackgroundResource(android.R.drawable.btn_default); //special code that sets button object to default color
    }
}