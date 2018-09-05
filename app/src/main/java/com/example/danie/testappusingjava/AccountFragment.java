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
    int count = 10; //total number of questions

    public static AccountFragment newInstance() { //constructor
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

        //try to make this process only occur once
        FetchJSON process = new FetchJSON(); //gets json data of categories
        process.setOriginate(1); //originate is used to reset FetchJSON class, setting it as 1 tells the class to deal with the categories
        process.setLink("https://opentdb.com/api_category.php"); //sets link for what url to fetch
        process.execute();
        try {
            Thread.sleep(700); //used to give FetchJSON some time to fetch the data, before retrieving that data
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final HashMap<String, Integer> hmap = process.getHmap(); //PROBLEM: returned an empy HMAP, SOLUTION: use Thread.sleep to wait to retreive JSON

        Log.d(TAG, "Size of Hmap: " + hmap.size());
        final String[] categories = new String[hmap.size()+1]; //gets array of ALL the possible categories (and "All" category)

        int index = 0;
        categories[index] = "All"; //allows users to get questions from any category
        for (String key: hmap.keySet()){ //hmap.keySet() returns list of keys in the hmap
            index++;
            Log.d(TAG, "KEY: " + key);
            categories[index] = key; //sets categories array values to the values in the fetched hmap
            Log.d(TAG, "Categories: " + index + " " + categories[index]);

        }
        Log.d(TAG, "Categories: " + categories);


        Spinner categoriesSpinner = (Spinner) rootView.findViewById(R.id.category_spinner); //selects category spinner
        adaptSpinner(categories, categoriesSpinner); //puts values in category spinner

        final String[] difficulty = {"all", "easy", "medium", "hard"}; //the options for difficulty spinner
        Spinner difficultySpinner = (Spinner) rootView.findViewById(R.id.difficulty_spinner); //selects difficulty spinner
        adaptSpinner(difficulty, difficultySpinner); //puts values in difficulty spinner

        categoriesSpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() { //listener for when value of categoriesspinner changes
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){ //if user selects any option besides "all"
                    categoryID = hmap.get(categories[position]);
                }
                else if(position == 0){ //if user selects all
                    categoryID = 0; //changeLink() method checks this categoryID and if it is 0, there will be nothing added to the url related to categories
                }
                tempCategory = categories[position]; //sets category chosen BEFORE apply is pressed
                Log.d(TAG, "CategoryID: " + categoryID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        difficultySpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() { //listener for when difficulty spinner changes
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficult = difficulty[position]; //sets value of difficult BEFORE apply is pressed
                Log.d(TAG, "Difficulty: " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button applyChangesButton = (Button) rootView.findViewById(R.id.change_url_button); //selects apply changes button
        applyChangesButton.setOnClickListener(new View.OnClickListener() { //url does not change until user selects the apply changes button

            @Override
            public void onClick(View v) { //

                EditText temp = (EditText) rootView.findViewById(R.id.editText);
                count = Integer.parseInt(temp.getText().toString()); //count is the number of total questions

                category = tempCategory; //the actual category is now changed to what the user set it to before

                changeLink(); //changes the link based on new values
                Trivia.total = count; //trivia activity now knows that there is a new total amount of questinos
                Log.d(TAG, "Trivia.total = " +Trivia.total);
                Snackbar.make(rootView.findViewById(R.id.settingsLayout), R.string.link_success, Snackbar.LENGTH_SHORT)
                        .show(); //myLayout refers to the trivia activity layout
            }
        });


        return rootView;
    }

    private void changeLink() { //changes link
        link = "https://opentdb.com/api.php?type=multiple&amount="+count;
        if (categoryID != 0){
            link+="&category="+categoryID;
        }
        if (difficult.equals("all") == false){
            link+="&difficulty="+difficult;
        }
        Trivia.link = link;
    }

    private void adaptSpinner(String[] array, Spinner spinner) { //used to put values in spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}