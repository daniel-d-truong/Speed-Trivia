package com.example.danie.testappusingjava;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

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

        final View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
        //OnClickListener for when user taps the button
        btn = (Button) rootView.findViewById(R.id.button_quiz);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Runtime runtime = Runtime.getRuntime();
                Log.d(TAG, "Established Runtime");
                try {
                    Process proc = runtime.exec("ping " + Trivia.link);
                    Log.d(TAG, "executing Runtime");
                    int mPingResult = proc.waitFor();
                    Log.d(TAG, "finished waiting for proc");
                    if (mPingResult == 0){
                        openTriviaActivity();
                    }
                    else{
                        Snackbar.make(rootView.findViewById(R.id.quiz_main_layout), R.string.fail_to_access, Snackbar.LENGTH_SHORT)
                                .show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                 //starts trivia activity when user selects button
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