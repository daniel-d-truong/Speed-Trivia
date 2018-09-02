package com.example.danie.testappusingjava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowScoresResultFragment extends Fragment {

    private int correct;
    private int total;

    public ShowScoresResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_show_scores_result, container, false);
        TextView tempc = (TextView) rootView.findViewById(R.id.quiz_correct);
        correct = tempc.getText().toString();


        return rootView;
    }

}
