package com.example.danie.testappusingjava;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowScoresResultFragment extends Fragment {


    public ShowScoresResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_scores_result, container, false);
        TextView result = (TextView) rootView.findViewById(R.id.scores);
        result.setText((Trivia.correct) + "/"+ (Trivia.total));
        return rootView;
    }

}
