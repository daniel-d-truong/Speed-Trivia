package com.example.danie.testappusingjava;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;

public class AccountFragment extends Fragment {
    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        openShowScoreActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        FetchJSON process = new FetchJSON();
        process.setOriginate(1);
        process.setLink("https://opentdb.com/api_category.php");
        process.execute();
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        HashMap<String, Integer> hmap = process.getHmap(); //PROBLEM: returned an empy HMAP, SOLUTION: use Thread.sleep to wait to retreive JSON

        Log.d(TAG, "Size of Hmap: " + hmap.size());
        String[] categories = new String[hmap.size()+1];
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

        String[] difficulty = {"easy", "medium", "hard"};
        Spinner difficultySpinner = (Spinner) rootView.findViewById(R.id.difficulty_spinner);
        adaptSpinner(difficulty, difficultySpinner);

        return rootView;
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