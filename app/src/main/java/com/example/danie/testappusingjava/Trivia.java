package com.example.danie.testappusingjava;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.namespace.QName;

import static android.support.constraint.Constraints.TAG;
import static java.lang.String.format;


public class Trivia extends Activity {

    private Button btn;
    private RadioGroup choices;
    private int count = 0;
    public static int correct = 0;
    public static int incorrect = 0;

    public static void setTotal(int total) {
        Trivia.total = total;
    }

    public static int total = 10;

    public static ArrayList<String> questionList;
    public static ArrayList<String> correctList;
    public static ArrayList<String[]> incorrectList;
    public static ArrayList<String> userChoseIncorrectsOnly;

    public static TextView question;
    public static RadioButton choice1;
    public static RadioButton choice2;
    public static RadioButton choice3;
    public static RadioButton choice4;

    public static String link = "https://opentdb.com/api.php?amount=10&type=multiple";
    private RadioButton[] choicesList;
    private LinearLayout showRecapLayout;

    FetchJSON process = new FetchJSON();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        //variables that select a View in the XML must be initiated AFTER the activity xml has been loaded
        correct = 0;
        incorrect = 0;

        userChoseIncorrectsOnly = new ArrayList<String>();
        Log.d(TAG, link);

        choices = (RadioGroup) findViewById (R.id.multiple_choice);
        btn = (Button) findViewById(R.id.submit);
        question = (TextView) findViewById(R.id.question2);
        choice1 = (RadioButton) findViewById(R.id.radioButton1);
        choice2 = (RadioButton) findViewById(R.id.radioButton2);
        choice3 = (RadioButton) findViewById(R.id.radioButton3);
        choice4 = (RadioButton) findViewById(R.id.radioButton4);
        choicesList = new RadioButton[]{choice1, choice2, choice3, choice4};


        process.setLink(link);
        process.setOriginate(0);
        process.execute();

        questionList = process.getQuestionsList();
        correctList = process.getAnswersList();
        incorrectList = process.getIncorrectList();
        userChoseIncorrectsOnly = new ArrayList<String>();
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
                    checkAnswer(choices.getCheckedRadioButtonId());
                    count++;
                    if (count > total){
//                        showResults();
//                        showRecap();
                        BlankFragment.hmap.put(BlankFragment.count, new HashMap<String, String>());
                        openShowScoreActivity();
                    }
                    else{
                        changeQuestion();
                        changeChoices();
                        Log.d(TAG, count+"");
                        resetRadio();
                    }

                }
                else { //creates pop-up telling user to make a choice
                    Log.d(TAG, "Else Statement has been Run.");
                    //making a snackbar
                    Snackbar.make(findViewById(R.id.myLayout), R.string.make_choice, Snackbar.LENGTH_SHORT)
                            .show(); //myLayout refers to the trivia activity layout
                }
            }
        });
    }

    private void showRecap() {
        ListView listView = (ListView) findViewById(R.id.list_view);

        if (listView == null)
            Log.d(TAG, "listview is NULL");
        else
            Log.d(TAG, "listview is NOT NULL");
//        CustomAdapter customAdapter = new CustomAdapter();
        assert listView != null;
        listView.setAdapter((ListAdapter) listView);
    }

    private void showResults() {
////        ConstraintLayout showScore = (ConstraintLayout) findViewById(R.id.showScoresLayout);
//        TextView score = (TextView) findViewById(R.id.scores);
//        if (score != null){
//            Log.d(TAG, "Score is not null!");
//        }
//        else
//            Log.d(TAG, "Score is null!");
////        ConstraintLayout parent = (ConstraintLayout) findViewById(R.id.quizLayout);
//
//
//        score.setText(correct+"/"+total);//fix this!!!
////        showScore.setVisibility(View.VISIBLE);
////        parent.setVisibility(View.GONE);
        Log.d(TAG, "HI");

    }

    private void checkAnswer(int id) {
        RadioButton selected = (RadioButton) findViewById((id));
        if (selected.getText() == correctList.get(count)){
            Log.d(TAG, "Correct answer!");
            correct+=1;
        }
        else{
            Log.d(TAG, "Incorrect answer! The correct answer is: " + correctList.get(count));
            incorrect+=1;
            userChoseIncorrectsOnly.add((String) selected.getText());
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

    public void openShowScoreActivity(){
        Intent in = new Intent (this, ShowScoresActivity.class); //must create an Intent object
        in.putExtra("score", correct+"/"+total);
        startActivity(in); //pass the Intent object into the startActivity(Intent) class in order to actually start the activity
    }

    public void setLink(String link) {
        this.link = link;
    }


}