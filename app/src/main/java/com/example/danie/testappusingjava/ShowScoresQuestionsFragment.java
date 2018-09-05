package com.example.danie.testappusingjava;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowScoresQuestionsFragment extends Fragment {


    public ShowScoresQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_scores_questions, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        if (listView == null){
            Log.d(TAG, "onCreateView listView IS NULL");
        }
        else
            Log.d(TAG, "onCreateView listView IS NOT NULL");
        CustomAdapter customAdapter = new CustomAdapter(getActivity(), Trivia.questionList);
        listView.setAdapter(customAdapter);


        return rootView;
    }

    class CustomAdapter extends ArrayAdapter {

        public CustomAdapter(@NonNull Context context, ArrayList<String> questions) {
            super(context, 0, questions);
        }

        @Override
        public int getCount() {
            return Trivia.total;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String currentQuestion = (String) getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, null);
            }


            TextView qView = (TextView) convertView.findViewById(R.id.show_question);
            String quAnTemp = "Question: " + Trivia.questionList.get(position) + "\n\nAnswer: " + Trivia.correctList.get(position);
            if (!Trivia.rightOrWring.get(position)){
                quAnTemp+="\nYou Chose: " + Trivia.youChose.get(position);
                convertView.setBackgroundColor(getResources().getColor(R.color.red_wrong));
            }
            else{
                convertView.setBackgroundColor(getResources().getColor(R.color.green));
            }


            qView.setText(quAnTemp);
//            aView.setText("Answer: " + Trivia.correctList.get(position));
            return convertView;
        }
    }

}
