package com.example.danie.testappusingjava;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.support.constraint.Constraints.TAG;

public class QuizFragment extends Fragment {

    private Button btn;

    public static QuizFragment newInstance() {
        QuizFragment fragment = new QuizFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, ""+ R.id.button_quiz);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
        //OnClickListener for when user taps the button
        btn = (Button) rootView.findViewById(R.id.button_quiz);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openTriviaActivity();
            }
        });

        return rootView;
    }

    //method to lead to trivia activity
    public void openTriviaActivity(){
        Intent in = new Intent (getActivity(), Trivia.class); //must create an Intent object
        startActivity(in); //pass the Intent object into the startActivity(Intent) class in order to actually start the activity
    }

}