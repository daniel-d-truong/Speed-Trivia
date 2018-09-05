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
        TextView result = (TextView) rootView.findViewById(R.id.scores2);
        TextView resultPercent = (TextView) rootView.findViewById(R.id.percentage_result2);
        TextView timeResult = (TextView) rootView.findViewById(R.id.time_result);
        int percent = (int) Math.round((double) (Trivia.correct)/(Trivia.total)*100);

        result.setText((Trivia.correct) + "/"+ (Trivia.total));
        timeResult.setText(Trivia.time);
        resultPercent.setText(Integer.toString(percent)+"%");

        int colorID ;

        //depending on users score, changes the background color of score box
        if (percent < 50){
            colorID=R.color.red_wrong;
        }
        else if (percent < 80){
            colorID=R.color.orange;
        }
        else {
            colorID=R.color.green;
        }

        //adds value into homefragment hmap in order to put into the customscoreview xml
        BlankFragment.hmap.get(BlankFragment.count).put("scoreFraction", Trivia.correct + "/" + Trivia.total); //adds to blankfragment hmap
        BlankFragment.hmap.get(BlankFragment.count).put("scorePercent", percent+"%");
        BlankFragment.hmap.get(BlankFragment.count).put("colorBG", Integer.toString(colorID));//adds to blankfragment hmap
        BlankFragment.count++; //this is the last key-value pair put into the hmap so count increases

        return rootView;
    }



}
