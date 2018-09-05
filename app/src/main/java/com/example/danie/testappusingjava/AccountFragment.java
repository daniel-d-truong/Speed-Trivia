package com.example.danie.testappusingjava;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;

public class AccountFragment extends Fragment {

    String link = "https://opentdb.com/api.php?amount=10";
    boolean jsonFlag = false;
    int categoryID = 0;
    private String tempCategory = "All"; //before user selects apply changes
    public static String category = "All";

    public static String difficult = "all"; //use for blankfragment hmap AND link
    int count = 10;

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        int tempCategoryID = 0;

        //try to make this process only occur once
        FetchJSON process = new FetchJSON();
        process.setOriginate(1);
        process.setLink("https://opentdb.com/api_category.php");
        process.execute();
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final HashMap<String, Integer> hmap = process.getHmap(); //PROBLEM: returned an empy HMAP, SOLUTION: use Thread.sleep to wait to retreive JSON

        Log.d(TAG, "Size of Hmap: " + hmap.size());
        final String[] categories = new String[hmap.size()+1];
        int index = 0;
        categories[index] = "All"; //make sure to account for this
        for (String key: hmap.keySet()){
            index++;
            Log.d(TAG, "KEY: " + key);
            categories[index] = key;
            Log.d(TAG, "Categories: " + index + " " + categories[index]);

        }
        Log.d(TAG, "Categories: " + categories);


        Spinner categoriesSpinner = (Spinner) rootView.findViewById(R.id.category_spinner);
        adaptSpinner(categories, categoriesSpinner);

        final String[] difficulty = {"all", "easy", "medium", "hard"};
        Spinner difficultySpinner = (Spinner) rootView.findViewById(R.id.difficulty_spinner);
        adaptSpinner(difficulty, difficultySpinner);

        categoriesSpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){
                    categoryID = hmap.get(categories[position]);
                }
                else if(position == 0){
                    categoryID = 0;
                }
                tempCategory = categories[position];
                Log.d(TAG, "CategoryID: " + categoryID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        difficultySpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficult = difficulty[position];
                Log.d(TAG, "Difficulty: " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button applyChangesButton = (Button) rootView.findViewById(R.id.change_url_button);
        applyChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText temp = (EditText) rootView.findViewById(R.id.editText);
                count = Integer.parseInt(temp.getText().toString());
                category = tempCategory;
                changeLink();
                Trivia.total = count;
                Log.d(TAG, "Trivia.total = " +Trivia.total);
                Snackbar.make(rootView.findViewById(R.id.settingsLayout), R.string.link_success, Snackbar.LENGTH_SHORT)
                        .show(); //myLayout refers to the trivia activity layout
            }
        });


        return rootView;
    }

    private void changeLink() {
        link = "https://opentdb.com/api.php?type=multiple&amount="+count;
        if (categoryID != 0){
            link+="&category="+categoryID;
        }
        if (difficult.equals("all") == false){
            link+="&difficulty="+difficult;
        }
        Trivia.link = link;
    }

    private void adaptSpinner(String[] array, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void openShowScoreActivity(){
//        Intent in = new Intent (getActivity(), ShowScoresActivity.class); //must create an Intent object
//        startActivity(in); //pass the Intent object into the startActivity(Intent) class in order to actually start the activity
        /*MAKE SETTINGS LAYOUT THAT ALLOWS USERS TO SELECT WHICH QUESTIONS TO TAKE:
        https://opentdb.com/api_config.php
            -CATEGORY:  https://opentdb.com/api_category.php
            -DIFFICULTY: easy, medium, hard
            -COUNT: max is 50
         */
    }
}