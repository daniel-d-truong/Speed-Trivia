package com.example.danie.testappusingjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import JSON.*;

import static android.support.constraint.Constraints.TAG;
import static java.lang.System.out;

public class Trivia extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Trivia Activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
//
//        MainParser json = new MainParser();
//        Log.d(TAG, json.getJSON());
    }


}
