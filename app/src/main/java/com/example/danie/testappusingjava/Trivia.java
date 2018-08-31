package com.example.danie.testappusingjava;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import javax.xml.namespace.QName;

import static android.support.constraint.Constraints.TAG;


public class Trivia extends Activity {

    private Button btn;
    private RadioGroup choices;
    private int count = 0;
    private int correct = 0;
    private int incorrect = 0;
    private int total = 10;

    private ArrayList<String> questionList;
    private ArrayList<String> correctList;
    private ArrayList<String[]> incorrectList;

    public static TextView question;
    public static RadioButton choice1;
    public static RadioButton choice2;
    public static RadioButton choice3;
    public static RadioButton choice4;
    private RadioButton[] choicesList;

    FetchJSON process = new FetchJSON();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        //variables that select a View in the XML must be initiated AFTER the activity xml has been loaded
        choices = (RadioGroup) findViewById (R.id.multiple_choice);
        btn = (Button) findViewById(R.id.submit);
        question = (TextView) findViewById(R.id.question2);
        choice1 = (RadioButton) findViewById(R.id.radioButton1);
        choice2 = (RadioButton) findViewById(R.id.radioButton2);
        choice3 = (RadioButton) findViewById(R.id.radioButton3);
        choice4 = (RadioButton) findViewById(R.id.radioButton4);
        choicesList = new RadioButton[]{choice1, choice2, choice3, choice4};

        process.setLink("https://opentdb.com/api.php?amount=10&type=multiple");
        process.execute();

        questionList = process.getQuestionsList();
        correctList = process.getAnswersList();
        incorrectList = process.getIncorrectList();
        Log.d(TAG, "Trivia has been created");

        //set question to API

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
                    count++;
                    if (count > 9){
                        showResults();
                    }
                    else{
                        checkAnswer(choices.getCheckedRadioButtonId());
                        changeQuestion();
                        changeChoices();
                        resetRadio();
                    }

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

    private void showResults() {
        ConstraintLayout showScore = (ConstraintLayout) findViewById(R.id.showScoresLayout);
        TextView score = (TextView) findViewById(R.id.score);
        ConstraintLayout parent = (ConstraintLayout) findViewById(R.id.quizLayout);


        score.setText(correct+"/"+total);
        showScore.setVisibility(View.VISIBLE);
        parent.setVisibility(View.GONE);

    }

    private void checkAnswer(int id) {
        RadioButton selected = (RadioButton) findViewById((id));
        if (selected.getText() == correctList.get(count-1)){
            Log.d(TAG, "Correct answer!");
            correct+=1;
        }
        else{
            Log.d(TAG, "Incorrect answer! The correct answer is: " + correctList.get(count-1));
            incorrect+=1;
        }
    }

    //changes the Question when button is clicked
    private void changeQuestion(){
        question.setText(questionList.get(count));
//        count++;
//
//        TextView q = (TextView) findViewById(R.id.question2);
//        String message = getString(R.string.question); //used to access the String resource
//        message+=" "+count; //changes the message
//
//        q.setText(message);
    }

    //changes the Choices when button is clicked
    private void changeChoices() {
        int correctIndex = (int) Math.random()*4;
        String[] incorrects = incorrectList.get(count);
        int index = 0;
        for (int i = 0; i < 4; i ++){
            RadioButton temp = choicesList[i];
            if (i == correctIndex){
                temp.setText(correctList.get(count));
            }
            else{
                temp.setText(incorrects[index]);
                index+=1;
            }

        }

    }

    //resets the question layout
    private void resetRadio() {
        choices.clearCheck(); //clears the checkedRadioButton in RadioGroup
        btn.setBackgroundResource(android.R.drawable.btn_default); //special code that sets button object to default color
    }
}